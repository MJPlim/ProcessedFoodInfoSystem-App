package com.plim.kati_app.tech;

import com.plim.kati_app.domain.model.FindEmailRequest;
import com.plim.kati_app.domain.model.FindEmailResponse;
import com.plim.kati_app.domain.model.ItemRankingResponse;
import com.plim.kati_app.domain.model.ModifyPasswordRequest;
import com.plim.kati_app.domain.model.ModifyPasswordResponse;
import com.plim.kati_app.domain.model.FindPasswordRequest;
import com.plim.kati_app.domain.model.FindPasswordResponse;

import com.plim.kati_app.domain.model.SetSecondEmailRequest;
import com.plim.kati_app.domain.model.SetSecondEmailResponse;
import com.plim.kati_app.domain.model.UserFavoriteResponse;
import com.plim.kati_app.domain.model.UserReviewResponse;
import com.plim.kati_app.domain.model.dto.CreateReviewRequest;
import com.plim.kati_app.domain.model.dto.CreateReviewResponse;
import com.plim.kati_app.domain.model.dto.CreateUserAllergyRequest;
import com.plim.kati_app.domain.model.dto.DeleteReviewRequest;
import com.plim.kati_app.domain.model.dto.FindFoodByBarcodeRequest;

import com.plim.kati_app.domain.model.UserInfoResponse;

import com.plim.kati_app.domain.model.dto.FindFoodBySortingResponse;
import com.plim.kati_app.domain.model.dto.FoodDetailResponse;
import com.plim.kati_app.domain.model.FoodResponse;
import com.plim.kati_app.domain.model.Password;
import com.plim.kati_app.domain.model.SignUpResponse;
import com.plim.kati_app.domain.model.User;
import com.plim.kati_app.domain.model.WithdrawResponse;
import com.plim.kati_app.domain.model.dto.ReadReviewDto;
import com.plim.kati_app.domain.model.dto.ReadReviewRequest;
import com.plim.kati_app.domain.model.dto.ReadReviewResponse;
import com.plim.kati_app.domain.model.dto.ReadUserAllergyResponse;
import com.plim.kati_app.domain.model.dto.UpdateReviewLikeRequest;
import com.plim.kati_app.domain.model.dto.UpdateReviewLikeResponse;
import com.plim.kati_app.domain.model.dto.UpdateReviewRequest;
import com.plim.kati_app.domain.model.dto.UserInfoModifyRequest;
import com.plim.kati_app.domain.view.user.login.LoginRequest;
import com.plim.kati_app.domain.model.dto.AdvertisementResponse;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
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

    @POST("find-email")
    Call<FindEmailResponse> findEmail(@Body FindEmailRequest request);

    @POST("api/v1/user/set-secondEmail")
    Call<SetSecondEmailResponse> setSecondEmail(@Body SetSecondEmailRequest request);

    @POST("api/v1/user/modify-password")
    Call<ModifyPasswordResponse> ChangePassword(@Body ModifyPasswordRequest request);

    @POST("login")
    Call<LoginRequest> postRetrofitData(@Body LoginRequest loginRequest);

//    @GET("/api/v1/food/findFood/foodName")
//    Call<List<FoodResponse>> getFoodListByProductName(@Query("foodName") String foodName);
//    @GET("/api/v1/food/findFood/manufacturerName")
//    Call<List<FoodResponse>> getFoodListByCompanyName(@Query("manufacturerName") String manufacturerName);

    @GET("/api/v1/food/getFoodListBySorting")
    Call<FindFoodBySortingResponse> getNameFoodListBySorting(@Query("pageNo")int pageNo,
                                                         @Query("size")int size,
                                                         @Query("sort")String sortElement,
                                                         @Query("foodName")String foodName);
    @GET("/api/v1/food/getFoodListBySorting")
    Call<FindFoodBySortingResponse> getManufacturerFoodListBySorting(@Query("pageNo")int pageNo,
                                                         @Query("size")int size,
                                                         @Query("sort")String sortElement,
                                                         @Query("manufacturerName")String manufacturerName);

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

    @POST("api/v1/user/updateReviewLike")
    Call<UpdateReviewLikeResponse> likeReview(@Body UpdateReviewLikeRequest updateReviewLikeRequest);

    @POST("api/v1/user/createReview")
    Call<CreateReviewResponse>createReview(@Body CreateReviewRequest dto);

    @POST("api/v1/user/updateReview")
    Call<CreateReviewResponse>updateReview(@Body UpdateReviewRequest dto);

    @POST("api/v1/user/deleteReview")
    Call<CreateReviewResponse>deleteReview(@Body DeleteReviewRequest dto);

    @GET("api/v1/user/user-info")
    Call<UserInfoResponse> getUserInfo();

    @POST("api/v1/user/modify-user-info")
    Call<UserInfoResponse> modifyUserInfo(@Body UserInfoModifyRequest request);

    @GET("api/v1/user/favorite/checkFavorite")
    Call<Boolean>getFavoriteStateForFood(@Query("foodId") Long foodId);

    @POST("api/v1/user/createUserAllergy")
    Call<UserInfoResponse>createUserAllergy(@Body CreateUserAllergyRequest dto);

    @GET("api/v1/user/readUserAllergy")
    Call<ReadUserAllergyResponse>readUserAllergy();

    @POST("api/v1/user/favorite/addFavorite")
    Call<Boolean>addFavoriteFood(@Query("foodId") Long foodId);

    @DELETE("api/v1/user/favorite/deleteFavorite")
    Call<Void> deleteFavoriteFood(@Query("foodId") Long foodId);

    @GET("reviewRanking")
    Call<List<ItemRankingResponse>> getRankingList();

    @GET("/api/v1/user/favorite/list")
    Call<List<UserFavoriteResponse>> getUserFavorite();

    @GET("/api/v1/user/readReviewByUserID")
    Call<List<UserReviewResponse>> getUserReview();
}
