package com.simplestepapp.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/***
 * Created by ankit on 14/2/16.
 */
public class RetrofitHelper {

    public static RetrofitHelper mInstance;
    public AvahanNetworkServices mServices;
    ServerTimeServices mServerTimeServices;


    private RetrofitHelper() {
        mInstance = this;
    }

    public static RetrofitHelper getInstance() {
        return mInstance == null ? new RetrofitHelper() : mInstance;
    }

    public AvahanNetworkServices getAvahanNetworkService(Converter.Factory converterFactory) {
        if (mServices == null) {

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            // set your desired log level
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
                // add your other interceptors …

                // add logging as last interceptor
            httpClient.addInterceptor(logging);  // <-- this is the important line!

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(converterFactory)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
            mServices = retrofit.create(AvahanNetworkServices.class);
        }
        return mServices;
    }

//    public AvahanAnalyticsNetworkServices getAvahanAnalyticsNetworkService(Converter.Factory converterFactory) {
//        if (mAnalyticsServices == null) {
//            Retrofit retrofit = new Retrofit.Builder()
//                    .baseUrl(Constants.ANALYTICS_BASE_URL)
//                    .addConverterFactory(converterFactory)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//            mAnalyticsServices = retrofit.create(AvahanAnalyticsNetworkServices.class);
//        }
//        return mAnalyticsServices;
//    }

    public ServerTimeServices getServerTimeService(Converter.Factory converterFactory) {
        if (mServerTimeServices == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.SERVERTIME_BASE_URL)
                    .addConverterFactory(converterFactory)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mServerTimeServices = retrofit.create(ServerTimeServices.class);
        }
        return mServerTimeServices;
    }
}
