package com.plim.kati_app.kati.domain.search.foodInfo.view.foodInfo.view;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;

import com.plim.kati_app.R;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.KatiDialog;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.LoadingDialog;
import com.plim.kati_app.kati.domain.search.foodInfo.model.FoodDetailResponse;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.KatiFoodFragment;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.KatiRetrofitTool;
import com.plim.kati_app.kati.domain.search.foodInfo.view.review.WriteReviewActivity;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitCallback;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitTool;

import retrofit2.Response;

import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.DETAIL_PRODUCT_INFO_FRAGMENT_SHOPPING_LINK_;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.DETAIL_PRODUCT_INFO_TABLE_FRAGMENT_FOOD_ID_EXTRA;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.FOOD_SEARCH_RESULT_LIST_FRAGMENT_FAILURE_DIALOG_TITLE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.NEW_DETAIL_ACTIVITY_EXTRA_IS_AD;

public class ProductInfoFragment extends KatiFoodFragment {

    // Working Variable
    private Long foodId;
    private boolean isAd;

    // Associate
        // View
        private Button purchaseSiteButton, writeReviewButton;

    // Component
        // View
        private LoadingDialog loadingDialog;

    @Override
    protected int getLayoutId() { return R.layout.fragment_detail_product_info; }
    @Override
    protected void associateView(View view) {
        this.purchaseSiteButton = view.findViewById(R.id.detailProductInfoFragment_buyButton);
        this.writeReviewButton = view.findViewById(R.id.detailProductInfoFragment_writeReviewButton);
    }
    @Override
    protected void initializeView() {
        this.writeReviewButton.setOnClickListener(v -> this.startActivity(WriteReviewActivity.class));
        this.purchaseSiteButton.setOnClickListener(v ->
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(DETAIL_PRODUCT_INFO_FRAGMENT_SHOPPING_LINK_
                            + this.foodModel.getFoodDetailResponse().getValue().getFoodName())))
        );
    }
    @Override
    protected void katiEntityUpdated() {
        this.foodId = this.getActivity().getIntent().getLongExtra(DETAIL_PRODUCT_INFO_TABLE_FRAGMENT_FOOD_ID_EXTRA, 0L);
        this.isAd = this.getActivity().getIntent().getBooleanExtra(NEW_DETAIL_ACTIVITY_EXTRA_IS_AD, false);
        this.loadingDialog = new LoadingDialog(this.getContext());
        this.search();
    }
    @Override
    public void foodModelDataUpdated() { }

    private void search() {
        this.loadingDialog.show();
        if (!isAd) { KatiRetrofitTool.getAPI().getFoodDetailByFoodId(this.foodId).enqueue(JSHRetrofitTool.getCallback(new FoodDetailRequestCallback())); }
        else { KatiRetrofitTool.getAPI().getAdvertisementFoodDetail(this.foodId).enqueue(JSHRetrofitTool.getCallback(new FoodDetailRequestCallback())); }
    }
    private class FoodDetailRequestCallback implements JSHRetrofitCallback<FoodDetailResponse> {
        @Override
        public void onSuccessResponse(Response<FoodDetailResponse> response) {
            loadingDialog.hide();
            foodModel.getFoodDetailResponse().setValue(response.body());
        }
        @Override
        public void onFailResponse(Response<FoodDetailResponse> response) { }
        @Override
        public void onConnectionFail(Throwable t) {
            loadingDialog.hide();
            KatiDialog.simplerAlertDialog(getActivity(),
                FOOD_SEARCH_RESULT_LIST_FRAGMENT_FAILURE_DIALOG_TITLE, t.getMessage(),
                null
            );
        }
    }
}