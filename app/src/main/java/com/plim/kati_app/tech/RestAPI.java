package com.plim.kati_app.tech;

import com.plim.kati_app.domain.model.ModifyPasswordRequest;
import com.plim.kati_app.domain.model.ModifyPasswordResponse;
import com.plim.kati_app.domain.model.FindPasswordRequest;
import com.plim.kati_app.domain.model.FindPasswordResponse;
import com.plim.kati_app.domain.model.dto.FindFoodByBarcodeRequest;
import com.plim.kati_app.domain.model.dto.FoodDetailResponse;
import com.plim.kati_app.domain.model.FoodResponse;
import com.plim.kati_app.domain.model.Password;
import com.plim.kati_app.domain.model.SignUpResponse;
import com.plim.kati_app.domain.model.User;
import com.plim.kati_app.domain.model.WithdrawResponse;
import com.plim.kati_app.domain.model.dto.ReadReviewDto;
import com.plim.kati_app.domain.model.dto.ReadReviewRequest;
import com.plim.kati_app.domain.model.dto.ReadReviewResponse;
import com.plim.kati_app.domain.model.dto.UserInfoModifyRequest;
import com.plim.kati_app.domain.model.dto.UserInfoResponse;
import com.plim.kati_app.domain.view.user.login.LoginRequest;
import com.plim.kati_app.domain.model.dto.AdvertisementResponse;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface RestAPI {

    @POST("signup")
    Call<SignUpResponse> signUp(@Body User user);

    @POST("withdraw")
    Call<WithdrawResponse> withdraw(@Body Password user);

    @POST("find-password")
    Call<FindPasswordResponse> findPassword(@Body FindPasswordRequest request);

    @POST("modify-password")
    Call<ModifyPasswordResponse> ChangePassword(@Body ModifyPasswordRequest request);

    @POST("login")
    Call<LoginRequest> postRetrofitData(@Body LoginRequest loginRequest);

    @GET("/api/v1/food/findFood/foodName")
    Call<List<FoodResponse>> getFoodListByProductName(@Query("foodName") String foodName);

    @GET("/api/v1/food/findFood/manufacturerName")
    Call<List<FoodResponse>> getFoodListByCompanyName(@Query("manufacturerName") String manufacturerName);




    @GET("/api/v1/food/findFood/foodDetail")
    Call<FoodDetailResponse> getFoodDetailByFoodId(@Query("foodId") Long foodId);

    @GET("/api/v1/advertisement/foodDetail")
    Call<FoodDetailResponse> getAdFoodDetail(@Query("adId") Long adId);

    @POST("/api/v1/food/findFood/barcode")
    Call<FoodDetailResponse> findByBarcode(@Body FindFoodByBarcodeRequest request);




    @GET("/api/v1/advertisement/ads")
    Call<List<AdvertisementResponse>> getAdFoodList();

    @GET("/readReview")
    Call<ReadReviewDto> readReview(@Query("foodId") Long foodId, @Query("pageNum") int pageNum);

    @GET("api/v1/user/readReview")
    Call<ReadReviewDto> readReviewByUser(@Query("foodId") Long foodId, @Query("pageNum") int pageNum);




    @GET("api/v1/user/user-info")
    Call<UserInfoResponse> getUserInfo();

    @POST("api/v1/user/modify-user-info")
    Call<UserInfoResponse> modifyUserInfo(@Body UserInfoModifyRequest request);

}
