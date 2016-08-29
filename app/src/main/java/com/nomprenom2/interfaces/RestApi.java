package com.nomprenom2.interfaces;
import com.nomprenom2.model.NameRecord;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface RestApi {

    @GET("{zod}/{sex}/{group}/")
    Call<List<NameRecord>> getName(@Path("zod") String zod,
                                 @Path("sex") String sex,
                                 @Path("group") String group);
}

