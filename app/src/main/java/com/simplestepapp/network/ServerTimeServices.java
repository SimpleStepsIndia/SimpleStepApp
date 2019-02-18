package com.simplestepapp.network;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Suren Reddy on 22-Nov-16.
 */
public interface ServerTimeServices {
    @GET("/utc/now")
    Call<String> getActualServerTime();
}
