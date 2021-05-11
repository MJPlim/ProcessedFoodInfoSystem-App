package com.plim.kati_app.domain2.view.main.search.detail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.plim.kati_app.R;
import com.plim.kati_app.domain.constant.Constant;
import com.plim.kati_app.domain.asset.KatiDialog;
import com.plim.kati_app.domain.asset.LoadingDialog;
import com.plim.kati_app.domain2.model.forBackend.searchFood.FoodDetailResponse;
import com.plim.kati_app.domain2.model.forFrontend.KatiFoodFragment;
import com.plim.kati_app.domain2.view.main.search.review.WriteReviewActivity;
import com.plim.kati_app.retrofit.RestAPI;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.plim.kati_app.domain.constant.Constant_yun.ABSTRACT_TABLE_FRAGMENT_BUNDLE_TABLE_HASH_MAP;
import static com.plim.kati_app.domain.constant.Constant_yun.ABSTRACT_TABLE_FRAGMENT_BUNDLE_TABLE_NAME;
import static com.plim.kati_app.domain.constant.Constant_yun.DETAIL_PHOTO_VIEW_FRAGMENT_BUNDLE_BACK_IMAGE;
import static com.plim.kati_app.domain.constant.Constant_yun.DETAIL_PHOTO_VIEW_FRAGMENT_BUNDLE_FRONT_IMAGE;
import static com.plim.kati_app.domain.constant.Constant_yun.DETAIL_PHOTO_VIEW_FRAGMENT_BUNDLE_KEY;
import static com.plim.kati_app.domain.constant.Constant_yun.DETAIL_PRODUCT_INFO_FRAGMENT_SHOPPING_LINK_;
import static com.plim.kati_app.domain.constant.Constant_yun.DETAIL_PRODUCT_INFO_TABLE_FRAGMENT_BUNDLE_KEY;
import static com.plim.kati_app.domain.constant.Constant_yun.DETAIL_PRODUCT_INFO_TABLE_FRAGMENT_EXPIRATION_DATE;
import static com.plim.kati_app.domain.constant.Constant_yun.DETAIL_PRODUCT_INFO_TABLE_FRAGMENT_FOOD_ID_EXTRA;
import static com.plim.kati_app.domain.constant.Constant_yun.DETAIL_PRODUCT_INFO_TABLE_FRAGMENT_MANUFACTURER_NAME;
import static com.plim.kati_app.domain.constant.Constant_yun.DETAIL_PRODUCT_INFO_TABLE_FRAGMENT_PRODUCT_NAME;
import static com.plim.kati_app.domain.constant.Constant_yun.DETAIL_PRODUCT_INFO_TABLE_FRAGMENT_TABLE_NAME;
import static com.plim.kati_app.domain.constant.Constant_yun.DETAIL_PRODUCT_MATERIAL_TABLE_FRAGMENT_BUNDLE_KEY;
import static com.plim.kati_app.domain.constant.Constant_yun.DETAIL_PRODUCT_MATERIAL_TABLE_FRAGMENT_INGREDIENT_NAME;
import static com.plim.kati_app.domain.constant.Constant_yun.DETAIL_PRODUCT_MATERIAL_TABLE_FRAGMENT_MATERIAL_NAME;
import static com.plim.kati_app.domain.constant.Constant_yun.DETAIL_PRODUCT_MATERIAL_TABLE_FRAGMENT_TABLE_NAME;
import static com.plim.kati_app.domain.constant.Constant_yun.FOOD_SEARCH_RESULT_LIST_FRAGMENT_FAILURE_DIALOG_TITLE;
import static com.plim.kati_app.domain.constant.Constant_yun.NEW_DETAIL_ACTIVITY_EXTRA_IS_AD;

/**
 * 상세페이지, 제품 기본 정보가 들어있는 fragment.
 */
public class DetailProductInfoFragment extends KatiFoodFragment {

    private Button purchaseSiteButton, writeReviewButton;
    private LoadingDialog loadingDialog;
    private Long foodId;
    private boolean isAd;

    public DetailProductInfoFragment() {
        // Required empty public constructor
    }

    @Override protected int getLayoutId() { return R.layout.fragment_detail_product_info; }

    @Override
    protected void associateView(View view) {
        this.purchaseSiteButton = view.findViewById(R.id.detailProductInfoFragment_buyButton);
        this.writeReviewButton = view.findViewById(R.id.detailProductInfoFragment_writeReviewButton);

    }

    @Override
    protected void initializeView() {
        this.writeReviewButton.setOnClickListener(v -> this.startActivity(WriteReviewActivity.class));
        this.purchaseSiteButton.setOnClickListener(v ->
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(DETAIL_PRODUCT_INFO_FRAGMENT_SHOPPING_LINK_
                            + this.foodModel.getFoodDetailResponse().getFoodName())))
        );
    }

    @Override
    protected void katiEntityUpdated() {
        this.loadingDialog = new LoadingDialog(this.getContext());
        this.foodId = this.getActivity().getIntent().getLongExtra(DETAIL_PRODUCT_INFO_TABLE_FRAGMENT_FOOD_ID_EXTRA, 0L);
        this.isAd = this.getActivity().getIntent().getBooleanExtra(NEW_DETAIL_ACTIVITY_EXTRA_IS_AD, false);
        this.search();
    }

    /**
     * 이미지 프래그먼트, 정보 테이블, 재료 테이블에 번들로 보낸다.
     */
    public void putBundle(FoodDetailResponse value) {
        if(value==null){
            Log.d("디버그","value가 널임");
            return;
        }

        Bundle imageBundle = new Bundle();
        imageBundle.putString(DETAIL_PHOTO_VIEW_FRAGMENT_BUNDLE_FRONT_IMAGE, value.getFoodImageAddress());
        imageBundle.putString(DETAIL_PHOTO_VIEW_FRAGMENT_BUNDLE_BACK_IMAGE, value.getFoodMeteImageAddress());
        getActivity().getSupportFragmentManager().setFragmentResult(DETAIL_PHOTO_VIEW_FRAGMENT_BUNDLE_KEY, imageBundle);

        HashMap<String, String> infoMap = new HashMap<>();
        infoMap.put(DETAIL_PRODUCT_INFO_TABLE_FRAGMENT_PRODUCT_NAME, value.getFoodName());
        infoMap.put(DETAIL_PRODUCT_INFO_TABLE_FRAGMENT_MANUFACTURER_NAME, value.getManufacturerName());
        infoMap.put(DETAIL_PRODUCT_INFO_TABLE_FRAGMENT_EXPIRATION_DATE, "-");
        Bundle infoBundle = new Bundle();
        infoBundle.putString(ABSTRACT_TABLE_FRAGMENT_BUNDLE_TABLE_NAME, DETAIL_PRODUCT_INFO_TABLE_FRAGMENT_TABLE_NAME);
        infoBundle.putSerializable(ABSTRACT_TABLE_FRAGMENT_BUNDLE_TABLE_HASH_MAP, infoMap);
        getActivity().getSupportFragmentManager().setFragmentResult(DETAIL_PRODUCT_INFO_TABLE_FRAGMENT_BUNDLE_KEY, infoBundle);

        HashMap<String, String> materialMap = new HashMap<>();
        materialMap.put(DETAIL_PRODUCT_MATERIAL_TABLE_FRAGMENT_MATERIAL_NAME, value.getMaterials());
        materialMap.put(DETAIL_PRODUCT_MATERIAL_TABLE_FRAGMENT_INGREDIENT_NAME, value.getNutrient());
        Bundle materialBundle = new Bundle();
        materialBundle.putString(ABSTRACT_TABLE_FRAGMENT_BUNDLE_TABLE_NAME, DETAIL_PRODUCT_MATERIAL_TABLE_FRAGMENT_TABLE_NAME);
        materialBundle.putSerializable(ABSTRACT_TABLE_FRAGMENT_BUNDLE_TABLE_HASH_MAP, materialMap);
        getActivity().getSupportFragmentManager().setFragmentResult(DETAIL_PRODUCT_MATERIAL_TABLE_FRAGMENT_BUNDLE_KEY, materialBundle);
    }

    /**
     * api 불러서 검색.
     */
    private void search() {
        this.getActivity().runOnUiThread(() -> {
            this.loadingDialog.show();
        });

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constant.URL)
                .build();
        RestAPI service = retrofit.create(RestAPI.class);
        if (!isAd) {
            Call<FoodDetailResponse> listCall = service.getFoodDetailByFoodId(this.foodId);
            listCall.enqueue(new Callback<FoodDetailResponse>() {
                @Override
                public void onResponse(Call<FoodDetailResponse> call, Response<FoodDetailResponse> response) {
                    FoodDetailResponse item = response.body();
                    putBundle(item);

                    getActivity().runOnUiThread(() -> {
                        loadingDialog.hide();
                    });
                }

                @Override
                public void onFailure(Call<FoodDetailResponse> call, Throwable t) {
                    getActivity().runOnUiThread(() -> {
                        loadingDialog.hide();
                    });
                    KatiDialog.simpleAlertDialog(getContext(),
                            FOOD_SEARCH_RESULT_LIST_FRAGMENT_FAILURE_DIALOG_TITLE,
                            t.getMessage(), null,
                            getContext().getResources().getColor(R.color.kati_coral, getContext().getTheme())
                    ).showDialog();
                }
            });
        } else {
            Call<FoodDetailResponse> listCall = service.getAdvertisementFoodDetail(this.foodId);
            listCall.enqueue(new Callback<FoodDetailResponse>() {
                @Override
                public void onResponse(Call<FoodDetailResponse> call, Response<FoodDetailResponse> response) {
                    FoodDetailResponse item = response.body();
                    putBundle(item);

                    getActivity().runOnUiThread(() -> {
                        loadingDialog.hide();
                    });
                }

                @Override
                public void onFailure(Call<FoodDetailResponse> call, Throwable t) {
                    getActivity().runOnUiThread(() -> {
                        loadingDialog.hide();
                    });
                    KatiDialog.simpleAlertDialog(getContext(),
                            FOOD_SEARCH_RESULT_LIST_FRAGMENT_FAILURE_DIALOG_TITLE,
                            t.getMessage(), null,
                            getContext().getResources().getColor(R.color.kati_coral, getContext().getTheme())
                    ).showDialog();
                }
            });

        }
    }
}