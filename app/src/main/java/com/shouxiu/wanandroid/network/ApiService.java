package com.shouxiu.wanandroid.network;

import android.util.Log;

import com.shouxiu.wanandroid.cons.Constant;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiService {
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constant.API_HOST)
            .client(new OkHttpClient.Builder()
                    .addInterceptor(new HttpLoggingInterceptor(
                            new HttpLoggingInterceptor.Logger() {
                                @Override
                                public void log(String message) {
                                    Log.d("retrofit", message);
                                }
                            }).setLevel(HttpLoggingInterceptor.Level.BODY))
                    .readTimeout(30, TimeUnit.SECONDS)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .cookieJar(new CookiesManager())
                    .build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    public static UserService createHotTopicService() {
        return retrofit.create(UserService.class);
    }

    public static HomeService createHomeService() {
        return retrofit.create(HomeService.class);
    }

    public static SearchService createSearchService() {
        return retrofit.create(SearchService.class);
    }
}
