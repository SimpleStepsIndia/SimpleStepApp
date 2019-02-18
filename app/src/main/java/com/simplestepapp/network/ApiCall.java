package com.simplestepapp.network;



import com.simplestepapp.data.Exercise;
import com.simplestepapp.data.UserExercise;
import com.simplestepapp.data.offline.User;
import com.simplestepapp.models.UserExerciseMaster;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Suren Reddy on 24-Jul-17.
 */

interface ApiCall {
    @GET("users")
    Call<List<User>> getUsers(@Query("access_token") String accessToken);

    @GET("exercises")
    Call<List<Exercise>> getExercise();

    @GET("userexercisemasters")
    Call<List<UserExerciseMaster>> getUserExerciseMasters(@Query("filter[where][userid]") String Id);

    @GET("userexercises")
    Call<List<UserExercise>> getUserExercise(@Query("filter[where][masterid]") String Id);

    @POST("userexercises")
    Call<UserExercise> insertUserExercise(@Body UserExercise insertUserExercise);

    @POST("userexercisemasters")
    Call<UserExerciseMaster> insertUserExerciseMaster(@Body UserExerciseMaster insertUserExercise);

    @PUT("userexercisemasters/{id}")
    Call<UserExerciseMaster> updateUserExerciseMaster(@Path("id") String id, @Body UserExerciseMaster uploadRequest);
}
