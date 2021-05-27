package com.plim.kati_app.kati.domain.old.search.search.view.foodList.searchSetting;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.chip.Chip;
import com.plim.kati_app.R;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitTool;
import com.plim.kati_app.kati.crossDomain.domain.model.Constant.SortElement;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.BlankFragment;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.KatiSearchFragment;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.KatiRetrofitTool;
import com.plim.kati_app.kati.domain.old.temp.editData.allergy.model.ReadUserAllergyResponse;

import java.util.Vector;

import retrofit2.Response;


public class SearchSettingFragment extends KatiSearchFragment {
    //working variable
    private boolean showAllergy = false;

    // Associate
    // View
    private ImageView allergyImageView;
    private TextView allergyTextView;
    private Chip rankChip, manufacturerChip, reviewChip;

    //component
    private BlankFragment blankFragment;
    private Fragment allergyViewFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search_setting_fragment;
    }

    @Override
    protected void associateView(View view) {
        this.allergyImageView = view.findViewById(R.id.searchFragment_allergyImageView);
        this.allergyTextView = view.findViewById(R.id.searchFragment_allergyTextView);
        this.manufacturerChip = view.findViewById(R.id.searchSettingFragment_manufacturerChip);
        this.rankChip = view.findViewById(R.id.searchSettingFragment_rankingChip);
        this.reviewChip = view.findViewById(R.id.searchSettingFragment_reviewChip);
        this.allergyViewFragment = new AllergyViewFragment();
        this.blankFragment = new BlankFragment();
    }

    @Override
    protected void initializeView() {
        this.manufacturerChip.setOnCheckedChangeListener((buttonView, isChecked) -> this.doSort(isChecked, SortElement.MANUFACTURER));
        this.rankChip.setOnCheckedChangeListener((buttonView, isChecked) -> this.doSort(isChecked, SortElement.RANK));
        this.reviewChip.setOnCheckedChangeListener((buttonView, isChecked) -> this.doSort(isChecked, SortElement.REVIEW_COUNT));
        this.allergyTextView.setOnClickListener(v -> this.setAllergyFragmentVisibility(showAllergy));
        this.allergyImageView.setOnClickListener((v) -> setAllergyFilter());
    }

    @Override
    protected boolean isLoginNeeded() {
        return false;
    }

    @Override
    protected void katiEntityUpdatedAndLogin() {
        if (this.dataset.containsKey(KatiEntity.EKatiData.AUTHORIZATION)) {
            String token = this.dataset.get(KatiEntity.EKatiData.AUTHORIZATION);
            this.getAllergyData(token);
        }
        }

    @Override
    protected void katiEntityUpdatedAndNoLogin() {
        this.allergyTextView.setVisibility(View.GONE);
        this.allergyImageView.setVisibility(View.GONE);}

    @Override
    protected void searchModelDataUpdated() {
    }


    private class ReadUserAllergyShowCallBack extends SimpleLoginRetrofitCallBack<ReadUserAllergyResponse> {
        public ReadUserAllergyShowCallBack(Activity activity) {
            super(activity);
        }

        @Override
        public void onSuccessResponse(Response<ReadUserAllergyResponse> response) {
            Vector<String> vector = new Vector<>();
            vector.addAll(response.body().getUserAllergyMaterials());
            setVector(vector);
        }
    }

    private void setAllergyFragmentVisibility(boolean showAllergy) {
        this.showAllergy = !showAllergy;
        if (showAllergy)
            getChildFragmentManager().beginTransaction().replace(R.id.searchFragment_allergyFrameLayout, allergyViewFragment).commit();
        else
            getChildFragmentManager().beginTransaction().replace(R.id.searchFragment_allergyFrameLayout, blankFragment).commit();
    }

    private void doSort(boolean isChecked, SortElement element) {
        if (isChecked) {
            this.searchModel.setSearchPageNum(1);
            this.searchModel.setFoodSortElement(element.getMessage());
            this.saveSearch();
        }
    }

    private void setVector(Vector<String> allergyVector) {
        this.searchModel.setAllergyList(allergyVector);
        this.saveSearch();
    }

    private void setAllergyFilter() {
        this.searchModel.setSearchPageNum(1);
        this.searchModel.setFiltered(!this.searchModel.isFiltered());
        int newTint = this.searchModel.isFiltered() ? R.color.kati_orange : R.color.gray;
        this.allergyImageView.setColorFilter(ContextCompat.getColor(getContext(), newTint), android.graphics.PorterDuff.Mode.SRC_IN);
        this.saveSearch();
    }

    private void getAllergyData(String token) {
        KatiRetrofitTool.getAPIWithAuthorizationToken(token).readUserAllergy().
                enqueue(JSHRetrofitTool.getCallback(new ReadUserAllergyShowCallBack(getActivity())));
    }

}