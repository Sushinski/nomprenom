package com.nomprenom2.utils;


import android.os.Debug;
import android.support.annotation.NonNull;
import android.util.Log;

import com.nomprenom2.interfaces.RestApi;
import com.nomprenom2.model.GroupRecord;
import com.nomprenom2.model.NameRecord;
import com.nomprenom2.model.PrefsRecord;
import com.nomprenom2.model.ZodiacRecord;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RestInteractionWorker {

    final String base_url = "http://85.143.215.126/names/";
    RestApi restApi;

    public class LoggingInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            okhttp3.Response response = chain.proceed(request);
            return response;
        }
    }

    public RestInteractionWorker(){

        OkHttpClient client = new OkHttpClient().newBuilder()
                .addInterceptor(new LoggingInterceptor())
                .build();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        restApi = retrofit.create(RestApi.class);
    }

    public void perfomServerOperation(String zod, String sex, String group){

        restApi.getName(zod, sex, group)
                .subscribeOn(Schedulers.newThread()) //для запроса используем отдельный поток
                .timeout(30, TimeUnit.SECONDS) // таймаут
                .map(new Func1<List<NameRecord>, ActionEvent>() {
                    @NonNull
                    @Override
                    public ActionEvent call(List<NameRecord> res_list) {
                        List<String> zods = new ArrayList<>();
                        List<String> groups = new ArrayList<>();
                        for (NameRecord nr : res_list) {
                            zods.clear();
                            groups.clear();
                            for (String zr: nr.zodiacs) {
                                zods.add(String.valueOf(zr));
                            }
                            groups.add(nr.group);
                            NameRecord.saveName(nr.name, NameRecord.Sex.values()[nr.sex].toString(),
                                    zods, groups, nr.description);
                        }
                        // обрабатываем результат, после чего высылаем событие с флагом успешного выполнения запроса
                        return new ActionEvent(true, "ok");
                    }
                })
                .onErrorReturn(new Func1<Throwable, ActionEvent>() {
                    @NonNull
                    @Override
                    public ActionEvent call(Throwable throwable) {
                        // произошла ошибка, создаем событие сообщающее об ошибке
                        return new ActionEvent(false, throwable.getMessage());
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

    public void getNamesUpdate(String last_upd_ver){
        if(last_upd_ver == null)
            last_upd_ver = "0";
        restApi.getNamesUpdate(last_upd_ver)
                .subscribeOn(Schedulers.newThread()) //для запроса используем отдельный поток
                .timeout(30, TimeUnit.SECONDS) // таймаут
                .map(new Func1<List<NameRecord>, ActionEvent>() {
                    @NonNull
                    @Override
                    public ActionEvent call(List<NameRecord> res_list) {
                        List<String> zods = new ArrayList<>();
                        List<String> groups = new ArrayList<>();
                        long last_id = -1;
                        for (NameRecord nr : res_list) {
                            zods.clear();
                            groups.clear();
                            for (String zr: nr.zodiacs) {
                                zods.add(String.valueOf(zr));
                            }
                            groups.add(nr.group);
                            long ins_id = NameRecord.saveName(nr.name,
                                        NameRecord.Sex.values()[nr.sex].toString(),
                                        zods, groups, nr.description);
                            if(ins_id > 0)
                                last_id = nr._id;
                        }
                        PrefsRecord.saveStringValue(PrefsRecord.LAST_UPD_NAME_ID, String.valueOf(last_id));
                        // обрабатываем результат, после чего высылаем событие с флагом успешного выполнения запроса
                        return new ActionEvent(true, "ok");
                    }
                })
                .onErrorReturn(new Func1<Throwable, ActionEvent>() {
                    @NonNull
                    @Override
                    public ActionEvent call(Throwable throwable) {
                        // произошла ошибка, создаем событие сообщающее об ошибке
                        return new ActionEvent(false, throwable.getMessage());
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