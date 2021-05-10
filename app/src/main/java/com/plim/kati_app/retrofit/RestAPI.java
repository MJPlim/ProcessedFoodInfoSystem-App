package com.plim.kati_app.retrofit;

import com.plim.kati_app.retrofit.dto.ModifyPasswordRequest;
import com.plim.kati_app.retrofit.dto.ModifyPasswordResponse;
import com.plim.kati_app.retrofit.dto.FindPasswordRequest;
import com.plim.kati_app.retrofit.dto.FindPasswordResponse;
import com.plim.kati_app.retrofit.dto.FoodDetailResponse;
import com.plim.kati_app.retrofit.dto.FoodResponse;
import com.plim.kati_app.retrofit.dto.Password;
import com.plim.kati_app.retrofit.dto.SignUpResponse;
import com.plim.kati_app.retrofit.dto.User;
import com.plim.kati_app.retrofit.dto.WithdrawResponse;
import com.plim.kati_app.retrofit.dto.LoginRequest;
import com.plim.kati_app.retrofit.dto.AdvertisementResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RestAPI {
    @POST("signup") Call<SignUpResponse> signUp(@Body User user);
    @POST("withdraw") Call<WithdrawResponse> withdraw(@Body Password user);
    @POST("find-password") Call<FindPasswordResponse> findPassword(@Body FindPasswordRequest request);
    @POST("modify-password") Call<ModifyPasswordResponse> modifyPassword(@Body ModifyPasswordRequest request);
    @POST("login") Call<LoginRequest> login(@Body LoginRequest loginRequest);
    @GET("/api/v1/food/findFood/foodName") Call<List<FoodResponse>> getSearchResultByFoodName(@Query("foodName") String foodName);
    @GET("/api/v1/food/findFood/manufacturerName") Call<List<FoodResponse>> getSearchResultByCompanyName(@Query("manufacturerName") String manufacturerName);
    @GET("/api/v1/food/findFood/foodDetail") Call<FoodDetailResponse> getFoodDetailByFoodId(@Query("foodId") Long foodId);
    @GET("/api/v1/advertisement/foodDetail") Call<FoodDetailResponse> getAdvertisementFoodDetail(@Query("adId") Long adId);
    @GET("api/v1/advertisement/ads") Call<List<AdvertisementResponse>> getAdvertisementFoodList();
}
