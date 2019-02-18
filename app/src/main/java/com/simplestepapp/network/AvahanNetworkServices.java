package com.simplestepapp.network;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/***
 * Created by ankit on 14/2/16.
 */
public interface AvahanNetworkServices {

    @FormUrlEncoded
    @POST(Constants.URL_USER_LOGIN)
    Call<String> login(@Field("username") String username,
                       @Field("password") String password);

    @GET(Constants.URL_GET_SURVEYQUESTIONS)
    Call<String> getSurveyQuestions();

    @GET(Constants.URL_GET_DATA_FOR_QUESTIONS)
    Call<String> getDataForQuestions();

    @POST(Constants.URL_GET_SURVEYANSWERS)
    Call<String> insertSurveyQuestion(@Body JsonObject diagnosisHeader);

    @PUT(Constants.URL_GET_SURVEYANSWERS)
    Call<String> updateSurveyQuestion(@Body JsonObject diagnosisHeader);

    @POST(Constants.URL_GET_USER_SURVEY_DETAILS)
    Call<String> insertUserSurveyDetail(@Body JsonObject diagnosisHeader);
}


