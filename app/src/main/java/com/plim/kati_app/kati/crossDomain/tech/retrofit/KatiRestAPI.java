package com.plim.kati_app.kati.crossDomain.tech.retrofit;

import com.plim.kati_app.kati.domain.nnew.findId.model.FindEmailRequest;
import com.plim.kati_app.kati.domain.nnew.findId.model.FindEmailResponse;
import com.plim.kati_app.kati.domain.nnew.main.favorite.model.UserFavoriteResponse;
import com.plim.kati_app.kati.domain.nnew.setRestoreEmail.model.SetSecondEmailRequest;
import com.plim.kati_app.kati.domain.nnew.setRestoreEmail.model.SetSecondEmailResponse;
import com.plim.kati_app.kati.domain.nnew.editPassword.model.ModifyPasswordRequest;
import com.plim.kati_app.kati.domain.nnew.editPassword.model.ModifyPasswordResponse;
import com.plim.kati_app.kati.domain.nnew.login.model.LoginResponse;
import com.plim.kati_app.kati.domain.nnew.findPassword.model.FindPasswordRequest;
import com.plim.kati_app.kati.domain.nnew.findPassword.model.FindPasswordResponse;
import com.plim.kati_app.kati.domain.nnew.signUp.model.SignUpRequest;
import com.plim.kati_app.kati.domain.nnew.signUp.model.SignUpResponse;
import com.plim.kati_app.kati.domain.old.mypage.main.model.UserSummaryResponse;
import com.plim.kati_app.kati.domain.old.mypage.myReview.model.ReadReviewResponse;
import com.plim.kati_app.kati.domain.old.search.foodInfo.model.FoodDetailResponse;
import com.plim.kati_app.kati.domain.old.model.CreateAndUpdateReviewRequest;
import com.plim.kati_app.kati.domain.old.model.CreateReviewResponse;
import com.plim.kati_app.kati.domain.old.model.DeleteReviewRequest;
import com.plim.kati_app.kati.domain.old.model.FindFoodByBarcodeRequest;
import com.plim.kati_app.kati.domain.old.model.ReadReviewDto;
import com.plim.kati_app.kati.domain.old.model.ReadReviewIdResponse;
import com.plim.kati_app.kati.domain.old.model.UpdateReviewLikeRequest;
import com.plim.kati_app.kati.domain.old.model.UpdateReviewLikeResponse;
import com.plim.kati_app.kati.domain.nnew.main.search.model.AdvertisementResponse;
import com.plim.kati_app.kati.domain.nnew.main.search.model.FindFoodBySortingResponse;
import com.plim.kati_app.kati.domain.old.temp.editData.allergy.model.CreateUserAllergyRequest;
import com.plim.kati_app.kati.domain.old.temp.editData.allergy.model.CreateUserAllergyResponse;
import com.plim.kati_app.kati.domain.old.temp.editData.allergy.model.ReadUserAllergyResponse;
import com.plim.kati_app.kati.domain.old.dataChange.model.UserInfoModifyRequest;
import com.plim.kati_app.kati.domain.old.dataChange.model.UserInfoResponse;
import com.plim.kati_app.kati.domain.nnew.itemRank.model.ItemRankingResponse;
import com.plim.kati_app.kati.domain.nnew.signOut.model.WithdrawRequest;
import com.plim.kati_app.kati.domain.nnew.signOut.model.WithdrawResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;



public interface KatiRestAPI {

    @POST("signup")
    Call<SignUpResponse> signUp(@Body SignUpRequest signUpRequest);

    @POST("api/v1/user/withdraw")
    Call<WithdrawResponse> withdraw(@Body WithdrawRequest user);

    @POST("find-password")
    Call<FindPasswordResponse> findPassword(@Body FindPasswordRequest request);

    @POST("find-email")
    Call<FindEmailResponse> findEmail(@Body FindEmailRequest request);

    @POST("api/v1/user/set-secondEmail")
    Call<SetSecondEmailResponse> setSecondEmail(@Body SetSecondEmailRequest request);

    @POST("api/v1/user/modify-password")
    Call<ModifyPasswordResponse> modifyPassword(@Body ModifyPasswordRequest request);

    @POST("login")
    Call<LoginResponse> login(@Body LoginResponse loginRequest);

    @GET("api/v1/user/user-info")
    Call<UserInfoResponse> getUserInfo();

    @POST("api/v1/user/modify-user-info")
    Call<UserInfoResponse> modifyUserInfo(@Body UserInfoModifyRequest request);

    //알레르기
    @POST("api/v1/user/createUserAllergy")
    Call<CreateUserAllergyResponse> createUserAllergy(@Body CreateUserAllergyRequest dto);
    @GET("api/v1/user/readUserAllergy")
    Call<ReadUserAllergyResponse> readUserAllergy();



    //제품 검색
    @GET("/api/v1/food/getFoodListBySorting")
    Call<FindFoodBySortingResponse> getNameFoodListBySorting(
            @Query("pageNo") int pageNo,
            @Query("size") int size,
            @Query("sort") String sortElement,
            @Query("foodName") String foodName,
            @Query("allergies") List<String> allergyList
    );

    @GET("/api/v1/food/getFoodListBySorting")
    Call<FindFoodBySortingResponse> getManufacturerFoodListBySorting(
            @Query("pageNo") int pageNo,
            @Query("size") int size,
            @Query("sort") String sortElement,
            @Query("manufacturerName") String manufacturerName,
            @Query("allergies") List<String> allergyList
    );

    @GET("/api/v1/food/findFood/foodDetail")
    Call<FoodDetailResponse> getFoodDetailByFoodId(@Query("foodId") Long foodId);

    @POST("/api/v1/food/findFood/barcode")
    Call<FoodDetailResponse> findByBarcode(@Body FindFoodByBarcodeRequest request);

    //제품 즐겨찾기
    @GET("api/v1/user/favorite/checkFavorite")
    Call<Boolean> getFavoriteStateForFood(@Query("foodId") Long foodId);

    @POST("api/v1/user/favorite/addFavorite")
    Call<Boolean> addFavoriteFood(@Query("foodId") Long foodId);

    @DELETE("api/v1/user/favorite/deleteFavorite")
    Call<Void> deleteFavoriteFood(@Query("foodId") Long foodId);

    //제품 광고
    @GET("/api/v1/advertisement/ads")
    Call<List<AdvertisementResponse>> getAdFoodList();

    @GET("/api/v1/advertisement/foodDetail")
    Call<FoodDetailResponse> getAdFoodDetail(@Query("adId") Long adId);

    //카테고리
//    @GET("/api/v1/food/list/category")
//    Call<CategoryFoodListResponse> getCategoryFood(@Query("category") String category,
//                                                   @Query("page") int page);

    //제품 리뷰
    @GET("/readReview")
    Call<ReadReviewDto> readReview(@Query("foodId") Long foodId, @Query("pageNum") int pageNum);

    @GET("api/v1/user/readReview")
    Call<ReadReviewDto> readReviewByUser(@Query("foodId") Long foodId, @Query("pageNum") int pageNum);

    @POST("api/v1/user/updateReviewLike")
    Call<UpdateReviewLikeResponse> likeReview(@Body UpdateReviewLikeRequest updateReviewLikeRequest);

    @POST("api/v1/user/createReview")
    Call<CreateReviewResponse> createReview(@Body CreateAndUpdateReviewRequest dto);

    @POST("api/v1/user/updateReview")
    Call<CreateReviewResponse> updateReview(@Body CreateAndUpdateReviewRequest dto);

    @POST("api/v1/user/deleteReview")
    Call<CreateReviewResponse> deleteReview(@Body DeleteReviewRequest dto);

    @GET("api/v1/user/readReviewByReviewID")
    Call<ReadReviewIdResponse> readReviewByReviewID(@Query("reviewId") Long reviewId);






    @GET("/api/v1/advertisement/foodDetail") Call<FoodDetailResponse> getAdvertisementFoodDetail(@Query("adId") Long adId);
    @GET("api/v1/advertisement/ads") Call<List<AdvertisementResponse>> getAdvertisementFoodList();
    @GET("reviewRanking") Call<List<ItemRankingResponse>> getRankingList();
    @GET("/api/v1/user/summary") Call<UserSummaryResponse> getUserSummary();
    @GET("/api/v1/user/favorite/list") Call<List<UserFavoriteResponse>> getUserFavorite();
    @GET("/api/v1/user/readReviewByUserID") Call<List<ReadReviewResponse>> getUserReview();
}

