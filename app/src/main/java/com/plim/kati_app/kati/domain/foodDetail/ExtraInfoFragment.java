package com.plim.kati_app.kati.domain.foodDetail;

import android.content.Intent;
import android.net.Uri;

import android.view.View;
import android.widget.Button;

import com.plim.kati_app.R;
import com.plim.kati_app.kati.crossDomain.domain.view.etc.JSHInfoItem;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.KatiFoodFragment;

import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.DETAIL_PRODUCT_INFO_FRAGMENT_ISSUE_LINK;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.DETAIL_PRODUCT_INFO_FRAGMENT_SHOPPING_LINK_;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.DETAIL_PRODUCT_INFO_START_NONE;

public class ExtraInfoFragment extends KatiFoodFragment {

    //associate
    //view
    private Button companyShowIssueButton, showOnlineBuyButton;
    private JSHInfoItem companyName, companyIssueItem;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_extra_info;
    }

    @Override
    protected void associateView(View view) {
        this.companyShowIssueButton = view.findViewById(R.id.extraInfoFragment_companyShowIssueButton);
        this.showOnlineBuyButton = view.findViewById(R.id.extraInfoFragment_showOnlineBuyButton);
        this.companyName = view.findViewById(R.id.extraInfoFragment_companyName);
        this.companyIssueItem = view.findViewById(R.id.extraInfoFragment_companyIssue);
    }

    @Override
    protected void initializeView() {
        this.companyName.setContentText(this.foodDetailResponse.getManufacturerName().split("_")[0]);

        String manufacturer = this.foodDetailResponse.getManufacturerName().split("_")[0];
        String foodName = this.foodDetailResponse.getFoodName();

        if (manufacturer.startsWith(DETAIL_PRODUCT_INFO_START_NONE)) {
            this.companyName.setContentText(null);
            this.companyShowIssueButton.setVisibility(View.GONE);
            this.companyIssueItem.setContentText(null);
        }
        this.companyShowIssueButton.setOnClickListener(v -> this.moveToBrowser(DETAIL_PRODUCT_INFO_FRAGMENT_ISSUE_LINK, manufacturer));
        this.showOnlineBuyButton.setOnClickListener(v -> this.moveToBrowser(DETAIL_PRODUCT_INFO_FRAGMENT_SHOPPING_LINK_, foodName));
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

    /*
        method
     */
    private void moveToBrowser(String baseUrl, String query) {
        if (!query.startsWith(DETAIL_PRODUCT_INFO_START_NONE))
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(baseUrl + query)));
    }


}