package com.plim.kati_app.kati.domain.old.search.foodInfo.view.foodInfo.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;

import com.plim.kati_app.R;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.LoadingDialog;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.SimpleRetrofitCallBackImpl;
import com.plim.kati_app.kati.domain.old.search.foodInfo.model.FoodDetailResponse;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.KatiFoodFragment;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.KatiRetrofitTool;
import com.plim.kati_app.kati.domain.old.search.foodInfo.view.foodInfo.model.FindFoodByBarcodeRequest;
import com.plim.kati_app.kati.domain.old.search.foodInfo.view.review.WriteReviewActivity;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitTool;

import retrofit2.Response;

import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.DETAIL_PRODUCT_INFO_FRAGMENT_SHOPPING_LINK_;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.DETAIL_PRODUCT_INFO_TABLE_FRAGMENT_FOOD_ID_EXTRA;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.NEW_DETAIL_ACTIVITY_EXTRA_IS_AD;

public class ProductInfoFragment extends KatiFoodFragment {

    //attribute
    private String barcode;
    private Long foodId;
    private boolean isAd;

    // Associate
    // View
    private Button purchaseSiteButton, writeReviewButton;

    // Component
    // View
    private LoadingDialog loadingDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_detail_product_info;
    }

    @Override
    protected void associateView(View view) {
        this.purchaseSiteButton = view.findViewById(R.id.detailProductInfoFragment_buyButton);
        this.writeReviewButton = view.findViewById(R.id.detailProductInfoFragment_writeReviewButton);
        this.loadingDialog = new LoadingDialog(this.getContext());
    }

    @Override
    protected void initializeView() {
        this.writeReviewButton.setOnClickListener(v -> {
            Intent intent = new Intent(this.getActivity(), WriteReviewActivity.class);
            intent.putExtra("foodId", foodDetailResponse.getFoodId());
            intent.putExtra("photo", foodDetailResponse.getFoodImageAddress());
            intent.putExtra("foodName", foodDetailResponse.getFoodName());
            intent.putExtra("manufacturerName", foodDetailResponse.getManufacturerName());
            this.startActivity(intent);
        });
//        this.purchaseSiteButton.setOnClickListener(v ->
//                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(DETAIL_PRODUCT_INFO_FRAGMENT_SHOPPING_LINK_
//                        + this.foodModel.getFoodDetailResponse().getValue().getFoodName())))
//        );
    }

    @Override
    protected void katiEntityUpdated() {
//        this.barcode = this.getActivity().getIntent().getStringExtra("barcode");
//        this.foodId = this.getActivity().getIntent().getLongExtra(DETAIL_PRODUCT_INFO_TABLE_FRAGMENT_FOOD_ID_EXTRA, 0L);
//        this.isAd = this.getActivity().getIntent().getBooleanExtra(NEW_DETAIL_ACTIVITY_EXTRA_IS_AD, false);
//
//        if (this.barcode != null) this.barcodeSearch();
//        else this.search();
    }

    @Override
    protected boolean isLoginNeeded() {
        return false;
    }

    @Override
    public void foodModelDataUpdated() {
    }

    @Override
    protected void summaryDataUpdated() {

    }


//    private class FoodDetailRequestCallback extends SimpleRetrofitCallBackImpl<FoodDetailResponse> {
//        public FoodDetailRequestCallback(Activity activity) {
//            super(activity);
//        }
//        @Override
//        public void onSuccessResponse(Response<FoodDetailResponse> response) {
////            loadingDialog.hide();
//            foodDetailResponse=response.body();
//            saveFoodDetail();
//        }
//
//    }
//
//    private void search() {
////        this.loadingDialog.show();
//        if (!isAd) {
//            KatiRetrofitTool.getAPI().getFoodDetailByFoodId(this.foodId).enqueue(JSHRetrofitTool.getCallback(new FoodDetailRequestCallback(this.getActivity())));
//        } else {
//            KatiRetrofitTool.getAPI().getAdFoodDetail(this.foodId).enqueue(JSHRetrofitTool.getCallback(new FoodDetailRequestCallback(this.getActivity())));
//        }
//    }
//
//    private void barcodeSearch() {
////        this.loadingDialog.show();
//        KatiRetrofitTool.getAPI().findByBarcode(new FindFoodByBarcodeRequest(this.barcode)).enqueue(JSHRetrofitTool.getCallback(new FoodDetailRequestCallback(this.getActivity())));
//    }

}