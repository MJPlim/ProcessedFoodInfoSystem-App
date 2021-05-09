package com.plim.kati_app.domain.view.search.food.detail.fragment;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.plim.kati_app.R;
import com.plim.kati_app.constants.Constant;
import com.plim.kati_app.domain.asset.BlankFragment;
import com.plim.kati_app.domain.asset.KatiDialog;
import com.plim.kati_app.domain.asset.LoadingDialog;
import com.plim.kati_app.domain.model.dto.FindFoodByBarcodeRequest;
import com.plim.kati_app.domain.model.dto.FoodDetailResponse;
import com.plim.kati_app.domain.model.dto.ReviewViewmodel;
import com.plim.kati_app.domain.model.room.KatiData;
import com.plim.kati_app.domain.model.room.KatiDatabase;
import com.plim.kati_app.domain.view.MainActivity;
import com.plim.kati_app.domain.view.search.food.review.WriteReviewActivity;
import com.plim.kati_app.tech.RestAPI;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.plim.kati_app.constants.Constant_yun.ABSTRACT_TABLE_FRAGMENT_BUNDLE_TABLE_HASH_MAP;
import static com.plim.kati_app.constants.Constant_yun.ABSTRACT_TABLE_FRAGMENT_BUNDLE_TABLE_LINK_MAP;
import static com.plim.kati_app.constants.Constant_yun.ABSTRACT_TABLE_FRAGMENT_BUNDLE_TABLE_NAME;
import static com.plim.kati_app.constants.Constant_yun.ABSTRACT_TABLE_FRAGMENT_LARGE;
import static com.plim.kati_app.constants.Constant_yun.ABSTRACT_TABLE_FRAGMENT_SMALL;
import static com.plim.kati_app.constants.Constant_yun.DETAIL_PHOTO_VIEW_FRAGMENT_BUNDLE_BACK_IMAGE;
import static com.plim.kati_app.constants.Constant_yun.DETAIL_PHOTO_VIEW_FRAGMENT_BUNDLE_FRONT_IMAGE;
import static com.plim.kati_app.constants.Constant_yun.DETAIL_PHOTO_VIEW_FRAGMENT_BUNDLE_KEY;
import static com.plim.kati_app.constants.Constant_yun.DETAIL_PRODUCT_INFO_FRAGMENT_SHOPPING_LINK_;
import static com.plim.kati_app.constants.Constant_yun.DETAIL_PRODUCT_INFO_TABLE_FRAGMENT_BUNDLE_KEY;
import static com.plim.kati_app.constants.Constant_yun.DETAIL_PRODUCT_INFO_TABLE_FRAGMENT_EXPIRATION_DATE;
import static com.plim.kati_app.constants.Constant_yun.DETAIL_PRODUCT_INFO_TABLE_FRAGMENT_FOOD_ID_EXTRA;
import static com.plim.kati_app.constants.Constant_yun.DETAIL_PRODUCT_INFO_TABLE_FRAGMENT_MANUFACTURER_NAME;
import static com.plim.kati_app.constants.Constant_yun.DETAIL_PRODUCT_INFO_TABLE_FRAGMENT_PRODUCT_NAME;
import static com.plim.kati_app.constants.Constant_yun.DETAIL_PRODUCT_INFO_TABLE_FRAGMENT_TABLE_NAME;
import static com.plim.kati_app.constants.Constant_yun.DETAIL_PRODUCT_MATERIAL_TABLE_FRAGMENT_BUNDLE_KEY;
import static com.plim.kati_app.constants.Constant_yun.DETAIL_PRODUCT_MATERIAL_TABLE_FRAGMENT_INGREDIENT_NAME;
import static com.plim.kati_app.constants.Constant_yun.DETAIL_PRODUCT_MATERIAL_TABLE_FRAGMENT_MATERIAL_NAME;
import static com.plim.kati_app.constants.Constant_yun.DETAIL_PRODUCT_MATERIAL_TABLE_FRAGMENT_TABLE_NAME;
import static com.plim.kati_app.constants.Constant_yun.FOOD_SEARCH_RESULT_LIST_FRAGMENT_FAILURE_DIALOG_TITLE;
import static com.plim.kati_app.constants.Constant_yun.NEW_DETAIL_ACTIVITY_EXTRA_IS_AD;

/**
 * 상세페이지, 제품 기본 정보가 들어있는 fragment.
 */
public class DetailProductInfoFragment extends Fragment {

    private Intent intent;
    private Button writeReviewButton;

    private Button purchaseSiteButton;
    private LoadingDialog loadingDialog;
    private Long foodId;
    private boolean isAd;
    private String barcode;



    public DetailProductInfoFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_product_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.intent= new Intent(this.getActivity(),WriteReviewActivity.class);
    this.writeReviewButton=view.findViewById(R.id.detailProductInfoFragment_reviewWriteButton);
        this.purchaseSiteButton = view.findViewById(R.id.detailProductInfoFragment_buyButton);
        this.loadingDialog = new LoadingDialog(this.getContext());
        this.barcode = this.getActivity().getIntent().getStringExtra("barcode");
        this.foodId = this.getActivity().getIntent().getLongExtra(DETAIL_PRODUCT_INFO_TABLE_FRAGMENT_FOOD_ID_EXTRA, 0L);
        this.isAd = this.getActivity().getIntent().getBooleanExtra(NEW_DETAIL_ACTIVITY_EXTRA_IS_AD, false);

        if (this.barcode != null) this.barcodeSearch();
        else this.search();

        this.writeReviewButton.setOnClickListener(v->{
            startActivity(this.intent);
        });

    }



    private void parseCall(Call<FoodDetailResponse> call) {
        call.enqueue(new Callback<FoodDetailResponse>() {
            @Override
            public void onResponse(Call<FoodDetailResponse> call, Response<FoodDetailResponse> response) {
                FoodDetailResponse item = response.body();
                if (item == null) {
                    KatiDialog.showRetrofitNotSuccessDialog(
                            getContext(),
                            "일치하는 제품이 없습니다.",
                            (dialog, which) -> startActivity(new Intent(getActivity(), MainActivity.class))
                    ).showDialog();
                } else {
                    if(isAd)foodId=item.getFoodId();
                    putBundle(item);
                }
                    writeForm(item);
                    getReview(foodId);

                getActivity().runOnUiThread(() -> {
                    loadingDialog.hide();
                });
            }

            @Override
            public void onFailure(Call<FoodDetailResponse> call, Throwable t) {
                getActivity().runOnUiThread(() -> {
                    loadingDialog.hide();
                });
                KatiDialog.showRetrofitFailDialog(getContext(), t.getMessage(), null).showDialog();
            }
        });
    }



    private void barcodeSearch() {
        this.getActivity().runOnUiThread(() -> {
            this.loadingDialog.show();
        });

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constant.URL)
                .build();
        RestAPI service = retrofit.create(RestAPI.class);
        Call<FoodDetailResponse> listCall = service.findByBarcode(new FindFoodByBarcodeRequest(this.barcode));
        this.parseCall(listCall);
    }

    private void writeForm(FoodDetailResponse item) {
//        Log.d("e디버그","넣음넣음");
//        Bundle writeBundle= new Bundle();
//        writeBundle.putInt("score",0);
//        writeBundle.putLong("foodId",item.getFoodId());
//        writeBundle.putString("image",item.getFoodImageAddress());
//
//        writeBundle.putString("manufacturer",item.getManufacturerName());
//        writeBundle.putString("product",item.getFoodName());
//        writeBundle.putString("value","");
//        getActivity().getSupportFragmentManager().setFragmentResult("formBundle", writeBundle);


        intent.putExtra("score",0);
        intent.putExtra("foodId",item.getFoodId());
        intent.putExtra("image",item.getFoodImageAddress());

        intent.putExtra("manufacturer",item.getManufacturerName());
        intent.putExtra("product",item.getFoodName());
        intent.putExtra("value","");

    }

    /**
     * 리뷰 불러오기 하기.
     * @param foodId
     */
    private void getReview(Long foodId) {
        Log.d("리뷰 불러오게 FragmentResult 설정", this.foodId + "");
        Bundle reviewBundle = new Bundle();
        reviewBundle.putLong("foodId", foodId);
        getActivity().getSupportFragmentManager().setFragmentResult("reviewBundle", reviewBundle);


    }

    /**
     * 이미지 프래그먼트, 정보 테이블, 재료 테이블에 번들로 보낸다.
     */
    public void putBundle(FoodDetailResponse value) {

        if (value == null) {
            Log.d("디버그", "value가 널임");
            return;
        }
        Bundle imageBundle = new Bundle();
        imageBundle.putString(DETAIL_PHOTO_VIEW_FRAGMENT_BUNDLE_FRONT_IMAGE, value.getFoodImageAddress());
        imageBundle.putString(DETAIL_PHOTO_VIEW_FRAGMENT_BUNDLE_BACK_IMAGE, value.getFoodMeteImageAddress());

        imageBundle.putLong("foodId",this.foodId);
        getActivity().getSupportFragmentManager().setFragmentResult(DETAIL_PHOTO_VIEW_FRAGMENT_BUNDLE_KEY, imageBundle);


        //디테일 맵.
        HashMap<String, String> infoMap = new HashMap<>();
        infoMap.put(DETAIL_PRODUCT_INFO_TABLE_FRAGMENT_PRODUCT_NAME, value.getFoodName());
        infoMap.put(DETAIL_PRODUCT_INFO_TABLE_FRAGMENT_MANUFACTURER_NAME, value.getManufacturerName());
        infoMap.put(DETAIL_PRODUCT_INFO_TABLE_FRAGMENT_EXPIRATION_DATE, "-");
        HashMap<String, String> infoLink = new HashMap<>();
        infoLink.put(DETAIL_PRODUCT_INFO_TABLE_FRAGMENT_MANUFACTURER_NAME, "https://m.search.naver.com/search.naver?query=");
        Bundle infoBundle = new Bundle();
        infoBundle.putString(ABSTRACT_TABLE_FRAGMENT_BUNDLE_TABLE_NAME, DETAIL_PRODUCT_INFO_TABLE_FRAGMENT_TABLE_NAME);
        infoBundle.putSerializable(ABSTRACT_TABLE_FRAGMENT_BUNDLE_TABLE_HASH_MAP, infoMap);
        infoBundle.putSerializable(ABSTRACT_TABLE_FRAGMENT_BUNDLE_TABLE_LINK_MAP, infoLink);
        getActivity().getSupportFragmentManager().setFragmentResult(DETAIL_PRODUCT_INFO_TABLE_FRAGMENT_BUNDLE_KEY, infoBundle);

        this.purchaseSiteButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(DETAIL_PRODUCT_INFO_FRAGMENT_SHOPPING_LINK_ + value.getFoodName()));
            startActivity(intent);
        });

        //원재료 성분 맵.
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
            this.parseCall(listCall);
        } else {
            Call<FoodDetailResponse> listCall = service.getAdFoodDetail(this.foodId);
            this.parseCall(listCall);

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.loadingDialog.dismiss();
    }
}