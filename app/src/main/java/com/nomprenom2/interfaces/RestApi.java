package com.nomprenom2.interfaces;
import com.nomprenom2.pojo.NamePojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public interface RestApi {

    @GET("1/1/all/")
    Call<List<NamePojo>> getName();
}

