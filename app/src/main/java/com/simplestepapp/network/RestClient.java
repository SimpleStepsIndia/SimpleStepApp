package com.simplestepapp.network;


import com.simplestepapp.data.Exercise;
import com.simplestepapp.data.UserExercise;
import com.simplestepapp.data.offline.User;
import com.simplestepapp.models.UserExerciseMaster;

import java.io.IOException;
import java.util.List;


import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by Suren Reddy on 24-Jul-17.
 */

public class RestClient implements INetwork {

    ApiCall iNetwork;

    public RestClient() {
        String url = "https://simple-steps-api.herokuapp.com/api/v1/";
        Retrofit retrofit = new Retrofit.Builder()
                .client(new OkHttpClient())
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        iNetwork = retrofit.create(ApiCall.class);
    }


    /**
     * Add common parameter in Get API
     *
     * @return
     */
    private OkHttpClient.Builder getOkHttpClient() {

        OkHttpClient.Builder httpClient =
                new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                okhttp3.Request original = chain.request();
                HttpUrl originalHttpUrl = original.url();
                HttpUrl url = originalHttpUrl.newBuilder()

                        .addQueryParameter("filter[where][deleteflag]", "null")
                        .addQueryParameter("filter[where][deleteflag]", "lakmepilot")
                        .addQueryParameter("appVersion", "v100")
                        .addQueryParameter("userName", "api_capgemini")
                        .build();
                okhttp3.Request.Builder requestBuilder = original.newBuilder()
                        .url(url);

                okhttp3.Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });
        return httpClient;


    }

    @Override
    public void getUsers(String accessToken, MyCallBack<List<User>> uListMyCallBack) {

        Call<List<User>> call = iNetwork.getUsers(accessToken);
        call.enqueue(new DuplicateNetWorkCallBack(uListMyCallBack));
    }

    @Override
    public void getExercises(MyCallBack<List<Exercise>> uListMyCallBack) {

        Call<List<Exercise>> call = iNetwork.getExercise();
        call.enqueue(new DuplicateNetWorkCallBack(uListMyCallBack));
    }

    @Override
    public void getUserExerciseMasters(String UserId, MyCallBack<List<UserExerciseMaster>> uListMyCallBack) {

        Call<List<UserExerciseMaster>> call = iNetwork.getUserExerciseMasters(UserId);
        call.enqueue(new DuplicateNetWorkCallBack(uListMyCallBack));
    }

    @Override
    public void getUserExercises(String masterId, MyCallBack<List<UserExercise>> uListMyCallBack) {

        Call<List<UserExercise>> call = iNetwork.getUserExercise(masterId);
        call.enqueue(new DuplicateNetWorkCallBack(uListMyCallBack));
    }

    @Override
    public void insertUserExercise(UserExercise userExerciseData, LakmeCallBack<UserExercise> userExerciseRowDataLakmeCallBack) {

        Call<UserExercise> call = iNetwork.insertUserExercise(userExerciseData);
        call.enqueue(new NetWorkCallBack<>(userExerciseRowDataLakmeCallBack));
    }

    @Override
    public void insertUserExerciseMaster(UserExerciseMaster userExerciseData, LakmeCallBack<UserExerciseMaster> userExerciseRowDataLakmeCallBack) {

        Call<UserExerciseMaster> call = iNetwork.insertUserExerciseMaster(userExerciseData);
        call.enqueue(new NetWorkCallBack<>(userExerciseRowDataLakmeCallBack));
    }

    @Override
    public void updateUserExerciseMaster(String Id, UserExerciseMaster userExerciseData, LakmeCallBack<UserExerciseMaster> userExerciseRowDataLakmeCallBack) {

        Call<UserExerciseMaster> call = iNetwork.updateUserExerciseMaster(Id,userExerciseData);
        call.enqueue(new NetWorkCallBack<>(userExerciseRowDataLakmeCallBack));
    }
}

