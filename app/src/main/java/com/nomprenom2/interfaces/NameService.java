package com.nomprenom2.interfaces;

import com.nomprenom2.model.NameRecord;
import com.nomprenom2.pojo.NamePojo;

import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface NameService {
    @GET("names")
    Call<List<NameRecord>> getNamesList(@QueryMap Map<String, String> options);
}
