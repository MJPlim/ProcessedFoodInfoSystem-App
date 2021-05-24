package com.plim.kati_app.kati.crossDomain.tech.retrofit;

import com.plim.kati_app.kati.domain.changePW.model.ModifyPasswordRequest;
import com.plim.kati_app.kati.domain.changePW.model.ModifyPasswordResponse;
import com.plim.kati_app.kati.domain.login.emailFind.model.FindEmailRequest;
import com.plim.kati_app.kati.domain.login.emailFind.model.FindEmailResponse;
import com.plim.kati_app.kati.domain.login.login.model.LoginRequest;
import com.plim.kati_app.kati.domain.login.pwFind.model.FindPasswordRequest;
import com.plim.kati_app.kati.domain.login.pwFind.model.FindPasswordResponse;
import com.plim.kati_app.kati.domain.login.signIn.model.SignUpRequest;
import com.plim.kati_app.kati.domain.login.signIn.model.SignUpResponse;
import com.plim.kati_app.kati.domain.mypage.main.model.UserSummaryResponse;
import com.plim.kati_app.kati.domain.mypage.myFavorite.model.UserFavoriteResponse;
import com.plim.kati_app.kati.domain.mypage.myReview.model.ReadReviewResponse;
import com.plim.kati_app.kati.domain.search.foodInfo.model.FoodDetailResponse;
import com.plim.kati_app.kati.domain.search.foodInfo.view.foodInfo.model.CreateAndUpdateReviewRequest;
import com.plim.kati_app.kati.domain.search.foodInfo.view.foodInfo.model.CreateReviewResponse;
import com.plim.kati_app.kati.domain.search.foodInfo.view.foodInfo.model.DeleteReviewRequest;
import com.plim.kati_app.kati.domain.search.foodInfo.view.foodInfo.model.FindFoodByBarcodeRequest;
import com.plim.kati_app.kati.domain.search.foodInfo.view.foodInfo.model.ReadReviewDto;
import com.plim.kati_app.kati.domain.search.foodInfo.view.foodInfo.model.ReadReviewIdResponse;
import com.plim.kati_app.kati.domain.search.foodInfo.view.foodInfo.model.UpdateReviewLikeRequest;
import com.plim.kati_app.kati.domain.search.foodInfo.view.foodInfo.model.UpdateReviewLikeResponse;
import com.plim.kati_app.kati.domain.search.search.model.AdvertisementResponse;
import com.plim.kati_app.kati.domain.search.search.model.FindFoodBySortingResponse;
import com.plim.kati_app.kati.domain.search.search.model.FoodResponse;
import com.plim.kati_app.kati.domain.temp.editData.allergy.model.CreateUserAllergyRequest;
import com.plim.kati_app.kati.domain.temp.editData.allergy.model.CreateUserAllergyResponse;
import com.plim.kati_app.kati.domain.temp.editData.allergy.model.ReadUserAllergyResponse;
import com.plim.kati_app.kati.domain.temp.editData.userData.model.UserInfoModifyRequest;
import com.plim.kati_app.kati.domain.temp.editData.userData.model.UserInfoResponse;
import com.plim.kati_app.kati.domain.temp.itemRank.model.ItemRankingResponse;
import com.plim.kati_app.kati.domain.temp.setSecondEmail.model.SetSecondEmailRequest;
import com.plim.kati_app.kati.domain.temp.setSecondEmail.model.SetSecondEmailResponse;
import com.plim.kati_app.kati.domain.temp.signOut.model.WithdrawRequest;
import com.plim.kati_app.kati.domain.temp.signOut.model.WithdrawResponse;

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

    @POST("api/v1/user/modify-password")
    Call<ModifyPasswordResponse> modifyPassword(@Body ModifyPasswordRequest request);

    @POST("login")
    Call<LoginRequest> login(@Body LoginRequest loginRequest);


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

    @GET("api/v1/user/readdReviewByReviewID")
    Call<ReadReviewIdResponse> readReviewByReviewID(@Query("reviewId") Long reviewId);

    //동욱

//    @GET("/api/v1/user/favorite/list")
//    Call<List<UserFavoriteResponse>> getUserFavorite();
//    @GET("/api/v1/user/readReviewByUserID")
//    Call<List<ReadReviewResponse>> getUserReview();
//    @GET("/api/v1/user/summary")
//    Call<UserSummaryResponse> getUserSummary();








    //송은
//    @POST("find-email")
//    Call<FindEmailResponse> findEmail(@Body FindEmailRequest request);
//    @POST("api/v1/user/set-secondEmail")
//    Call<SetSecondEmailResponse> setSecondEmail(@Body SetSecondEmailRequest request);
//    @GET("reviewRanking")
//    Call<List<ItemRankingResponse>> getRankingList();



    @POST("find-email") Call<FindEmailResponse> findEmail(@Body FindEmailRequest request);
    @POST("api/v1/user/set-secondEmail") Call<SetSecondEmailResponse> setSecondEmail(@Body SetSecondEmailRequest request);
    @GET("/api/v1/food/findFood/foodName") Call<List<FoodResponse>> getSearchResultByFoodName(@Query("foodName") String foodName);
    @GET("/api/v1/food/findFood/manufacturerName") Call<List<FoodResponse>> getSearchResultByCompanyName(@Query("manufacturerName") String manufacturerName);
    @GET("/api/v1/advertisement/foodDetail") Call<FoodDetailResponse> getAdvertisementFoodDetail(@Query("adId") Long adId);
    @GET("api/v1/advertisement/ads") Call<List<AdvertisementResponse>> getAdvertisementFoodList();
    @GET("reviewRanking") Call<List<ItemRankingResponse>> getRankingList();
    @GET("/api/v1/user/summary") Call<UserSummaryResponse> getUserSummary();
    @GET("/api/v1/user/favorite/list") Call<List<UserFavoriteResponse>> getUserFavorite();
    @GET("/api/v1/user/readReviewByUserID") Call<List<ReadReviewResponse>> getUserReview();

}

