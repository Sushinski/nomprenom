package com.nomprenom2.utils;


import android.support.annotation.NonNull;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nomprenom2.interfaces.RestApi;
import com.nomprenom2.model.NameRecord;
import com.nomprenom2.model.PrefsRecord;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RestInteractionWorker {
    private RestApi restApi;

    private class LoggingInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            return chain.proceed(request);
        }
    }

    public RestInteractionWorker( String base_addr ){
        String base_url = "http://" + base_addr + "/names/";
        OkHttpClient client = new OkHttpClient().newBuilder()
                .addInterceptor(new LoggingInterceptor())
                .build();

        final Gson gson =
                new GsonBuilder()
                        .excludeFieldsWithoutExposeAnnotation()
                        .create();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
        restApi = retrofit.create(RestApi.class);
    }


    public void getNamesUpdate(String last_upd_ver){
        if(last_upd_ver == null)
            last_upd_ver = "0";
        restApi.getNamesUpdate(last_upd_ver)
                .subscribeOn(Schedulers.newThread()) //для запроса используем отдельный поток
                .timeout(10, TimeUnit.SECONDS) // таймаут
                .map(new Func1<List<NameRecord>, ActionEvent>() {
                    @NonNull
                    @Override
                    public ActionEvent call(List<NameRecord> res_list) {
                        List<String> groups = new ArrayList<>();
                        for (NameRecord nr : res_list) {
                            groups.clear();
                            groups.add(nr.group);
                            long ins_id = NameRecord.saveName(nr.name,
                                        nr.sex,
                                        nr.zodiacs, groups, nr.description);
                            if(ins_id > 0)
                                PrefsRecord.saveStringValue(PrefsRecord.LAST_UPD_NAME_ID,
                                        String.valueOf(nr._id));
                        }
                        // обрабатываем результат, после чего высылаем событие с флагом успешного выполнения запроса
                        return new ActionEvent(ActionEvent.TYPE_LOAD_NEW_NAMES, true, "ok");
                    }
                })
                .onErrorReturn(new Func1<Throwable, ActionEvent>() {
                    @NonNull
                    @Override
                    public ActionEvent call(Throwable throwable) {
                        // произошла ошибка, создаем событие сообщающее об ошибке
                        return new ActionEvent(ActionEvent.TYPE_LOAD_NEW_NAMES,
                                false, throwable.getMessage());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread()) // дальше код будет вызываться в главном потоке приложения
                .subscribe(new Action1<ActionEvent>() {
                    @Override
                    public void call(ActionEvent event) {
                        EventBus.getDefault().post(event); // высылаем событие
                    }
                });

    }
}