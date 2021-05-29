package com.plim.kati_app.kati.domain.nnew.foodDetail;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.plim.kati_app.R;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitTool;
import com.plim.kati_app.kati.crossDomain.domain.view.etc.JSHInfoItem;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.KatiFoodFragment;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.KatiSearchFragment;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.KatiViewModelFragment;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.KatiRetrofitTool;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.SimpleRetrofitCallBackImpl;
import com.plim.kati_app.kati.domain.old.search.foodInfo.model.FoodDetailResponse;
import com.plim.kati_app.kati.domain.old.search.foodInfo.view.foodInfo.model.FindFoodByBarcodeRequest;
import com.plim.kati_app.kati.domain.old.search.foodInfo.view.foodInfo.view.ProductInfoFragment;

import retrofit2.Response;

import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.DETAIL_PRODUCT_INFO_FRAGMENT_ISSUE_LINK;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.DETAIL_PRODUCT_INFO_FRAGMENT_SHOPPING_LINK_;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.DETAIL_PRODUCT_INFO_TABLE_FRAGMENT_FOOD_ID_EXTRA;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.NEW_DETAIL_ACTIVITY_EXTRA_IS_AD;

public class ExtraInfoFragment extends KatiFoodFragment {

    //associate
    //view
    private Button companyShowIssueButton, showOnlineBuyButton;
    private JSHInfoItem companyName;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_extra_info;
    }

    @Override
    protected void associateView(View view) {
        this.companyShowIssueButton = view.findViewById(R.id.extraInfoFragment_companyShowIssueButton);
        this.showOnlineBuyButton = view.findViewById(R.id.extraInfoFragment_showOnlineBuyButton);
        this.companyName = view.findViewById(R.id.extraInfoFragment_companyName);
    }

    @Override
    protected void initializeView() {
        this.companyName.setContentText(this.foodDetailResponse.getManufacturerName().split("_")[0]);

        this.companyShowIssueButton.setOnClickListener(v ->
                startActivity(
                        new Intent(Intent.ACTION_VIEW, Uri.parse(DETAIL_PRODUCT_INFO_FRAGMENT_ISSUE_LINK
                                + this.foodModel.getFoodDetailResponse().getValue().getManufacturerName().split("_")[0])
                        )));
        this.showOnlineBuyButton.setOnClickListener(v ->
                startActivity(
                        new Intent(Intent.ACTION_VIEW, Uri.parse(DETAIL_PRODUCT_INFO_FRAGMENT_SHOPPING_LINK_
                                + this.foodModel.getFoodDetailResponse().getValue().getFoodName())
                        ))
        );
    }

    @Override
    protected void katiEntityUpdated() {
    }

    @Override
    public void foodModelDataUpdated() {

    }

    @Override
    protected void summaryDataUpdated() {

    }


}