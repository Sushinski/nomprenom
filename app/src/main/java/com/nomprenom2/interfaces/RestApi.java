/*
 * created by Pavel Golubev golubev.pavel.spb@gmail.com
 * no license applied
 * You may use this file without any restrictions
 */

package com.nomprenom2.interfaces;

import com.nomprenom2.model.NameRecord;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Represents API`s for working with Retrofit library
 */
public interface RestApi {

    /**
     * Sends request for name with current parameters
     *
     * @param zod   Zodiacal sign
     * @param sex   Gender group
     * @param group Region Group
     * @return Observable list of name model model objects
     */
    @GET("{zod}/{sex}/{group}/")
    //
    Observable<List<NameRecord>> getName(@Path("zod") String zod,
                                         @Path("sex") String sex,
                                         @Path("group") String group);

    /**
     * Gets update with new name, which was added after this version
     *
     * @param version Database version, for which we want to get difference with newest
     * @return Observable list of name objects
     */
    @GET("updates/{version}/")
    Observable<List<NameRecord>> getNamesUpdate(@Path("version") String version);
}

