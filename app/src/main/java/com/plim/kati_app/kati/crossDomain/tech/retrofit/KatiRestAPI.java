package com.plim.kati_app.kati.crossDomain.tech.retrofit;

import com.plim.kati_app.kati.domain.changePW.model.ModifyPasswordRequest;
import com.plim.kati_app.kati.domain.changePW.model.ModifyPasswordResponse;
import com.plim.kati_app.kati.domain.login.pwFind.model.FindPasswordRequest;
import com.plim.kati_app.kati.domain.login.pwFind.model.FindPasswordResponse;
import com.plim.kati_app.kati.domain.search.foodInfo.model.FoodDetailResponse;
import com.plim.kati_app.kati.domain.search.search.model.FoodResponse;
import com.plim.kati_app.kati.domain.temp.signOut.model.WithdrawRequest;
import com.plim.kati_app.kati.domain.login.signIn.model.SignUpResponse;
import com.plim.kati_app.kati.domain.login.signIn.model.SignUpRequest;
import com.plim.kati_app.kati.domain.temp.signOut.model.WithdrawResponse;
import com.plim.kati_app.kati.domain.login.login.model.LoginRequest;
import com.plim.kati_app.kati.domain.search.search.model.AdvertisementResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface KatiRestAPI {
    @POST("signup") Call<SignUpResponse> signUp(@Body SignUpRequest signUpRequest);
    @POST("withdraw") Call<WithdrawResponse> withdraw(@Body WithdrawRequest user);
    @POST("find-password") Call<FindPasswordResponse> findPassword(@Body FindPasswordRequest request);
    @POST("modify-password") Call<ModifyPasswordResponse> modifyPassword(@Body ModifyPasswordRequest request);
    @POST("login") Call<LoginRequest> login(@Body LoginRequest loginRequest);
    @GET("/api/v1/food/findFood/foodName") Call<List<FoodResponse>> getSearchResultByFoodName(@Query("foodName") String foodName);
    @GET("/api/v1/food/findFood/manufacturerName") Call<List<FoodResponse>> getSearchResultByCompanyName(@Query("manufacturerName") String manufacturerName);
    @GET("/api/v1/food/findFood/foodDetail") Call<FoodDetailResponse> getFoodDetailByFoodId(@Query("foodId") Long foodId);
    @GET("/api/v1/advertisement/foodDetail") Call<FoodDetailResponse> getAdvertisementFoodDetail(@Query("adId") Long adId);
    @GET("api/v1/advertisement/ads") Call<List<AdvertisementResponse>> getAdvertisementFoodList();
}
