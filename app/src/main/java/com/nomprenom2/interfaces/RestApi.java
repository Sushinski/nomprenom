package com.nomprenom2.interfaces;
import com.nomprenom2.model.NameRecord;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;


public interface RestApi {

    @GET("{zod}/{sex}/{group}/")
    Observable<List<NameRecord>> getName(@Path("zod") String zod,
                                 @Path("sex") String sex,
                                 @Path("group") String group);

    @GET("updates/{version}/")
    Observable<List<NameRecord>> getNamesUpdate(@Path("version") String version);
}

