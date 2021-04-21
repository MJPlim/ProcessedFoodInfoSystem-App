package com.plim.kati_app.tech;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.plim.kati_app.constants.Constant;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestAPIClient {
    private static final String BASE_URL = Constant.URL;

    public static RestAPI getApiService(){return RestAPIClient.getInstance().create(RestAPI.class);}

    private static Retrofit getInstance(){
        Gson gson = new GsonBuilder().create();
        return new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
    }

    public static RestAPI getApiService2(String token){return RestAPIClient.getInstance2(token).create(RestAPI.class);}

    private static Retrofit getInstance2(String token){
        Gson gson = new GsonBuilder().create();
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request newRequest  = chain.request().newBuilder()
                        .addHeader("Authorization", token)
                        .build();
                return chain.proceed(newRequest);
            }
        }).build();
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}
