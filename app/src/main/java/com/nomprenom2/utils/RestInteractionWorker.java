/*
 * created by Pavel Golubev golubev.pavel.spb@gmail.com
 * no license applied
 * You may use this file without any restrictions
 */

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

/**
 * Implements methods for work with Retrofil library
 */
public class RestInteractionWorker {
    private RestApi restApi;

    /**
     * Intercepts request for logging purposes
     */
    private class LoggingInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            return chain.proceed(request);
        }
    }

    /**
     * Constructs worker class
     *
     * @param base_addr base address for rest service server
     */
    public RestInteractionWorker(String base_addr) {
        String base_url = "http://" + base_addr + "/names/";
        OkHttpClient client = new OkHttpClient().newBuilder()
                .addInterceptor(new LoggingInterceptor())
                .build();

        final Gson gson =
                new GsonBuilder()
                        .excludeFieldsWithoutExposeAnnotation() // inclue only @Expose fields
                        .create();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_url)  // base url for request
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // rxJava adapter
                .addConverterFactory(GsonConverterFactory.create(gson)) // gson coverter
                .client(client) //okhttp client with our interceptor
                .build();
        restApi = retrofit.create(RestApi.class);
    }

    /**
     * Gets names added after current version
     *
     * @param last_upd_ver Last local update version
     */
    public void getNamesUpdate(String last_upd_ver) {
        if (last_upd_ver == null)
            last_upd_ver = "0";
        restApi.getNamesUpdate(last_upd_ver)
                .subscribeOn(Schedulers.newThread()) // using new thread for responce
                .timeout(10, TimeUnit.SECONDS) // set timeout to 10 s
                .map(new Func1<List<NameRecord>, ActionEvent>() { // map result list to call
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
                            if (ins_id > 0)
                                PrefsRecord.saveStringValue(PrefsRecord.LAST_UPD_NAME_ID,
                                        String.valueOf(nr._id));
                        }
                        // return ActionEvent with successful status
                        return new ActionEvent(ActionEvent.TYPE_LOAD_NEW_NAMES, true, "ok");
                    }
                })
                .onErrorReturn(new Func1<Throwable, ActionEvent>() {
                    @NonNull
                    @Override
                    public ActionEvent call(Throwable throwable) {
                        // return actionevent with error status
                        return new ActionEvent(ActionEvent.TYPE_LOAD_NEW_NAMES,
                                false, throwable.getMessage());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread()) // further precessing on main thread
                .subscribe(new Action1<ActionEvent>() {
                    @Override
                    public void call(ActionEvent event) {
                        EventBus.getDefault().post(event); // post result action event on eventbus
                    }
                });

    }
}