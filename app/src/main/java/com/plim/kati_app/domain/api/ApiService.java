package com.plim.kati_app.domain.api;

import com.plim.kati_app.domain.model.FoodSearchListItem;
import com.plim.kati_app.domain.view.user.login.LoginRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
//    @FormUrlEncoded
    @POST("login")
//    Call<LoginRequest> postRetrofitData(@Field("email") String email, @Field("password") String password);
    Call<LoginRequest> postRetrofitData(@Body LoginRequest loginRequest);


    @GET("/api/v1/food/findFood/foodName")
    Call<List<FoodSearchListItem>> getFoodListByCompanyName(@Header("Authorization") String header,String foodName, String pageNo);

    @GET("/api/v1/food/findFood/bsshName")
    Call<List<FoodSearchListItem>> getFoodListByProductName(@Header("Authorization") String header, String bsshName, String pageNo);
}
