package com.plim.kati_app.kati.domain.main.category;

import android.app.Activity;
import android.app.Service;
import android.view.LayoutInflater;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.plim.kati_app.R;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitTool;
import com.plim.kati_app.kati.crossDomain.domain.model.Constant;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.KatiCategoryFragment;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.KatiRetrofitTool;
import com.plim.kati_app.kati.domain.main.myKati.allergy.model.CreateUserAllergyRequest;
import com.plim.kati_app.kati.domain.main.myKati.allergy.model.CreateUserAllergyResponse;
import com.plim.kati_app.kati.domain.main.myKati.allergy.model.ReadUserAllergyResponse;

import java.util.Vector;

import retrofit2.Response;

public class CategoryFilterFragment extends KatiCategoryFragment {

    private Chip rankingChip, manufacturerChip, reviewCountChip, assendChip, desendChip;
    private Vector<Chip> chipVector;
    private ChipGroup allergyChipGroup, elementChipGroup, orderChipGroup;
    private Vector<String> allergyVector;

    private ConstraintLayout allergyLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_category_filter;
    }

    @Override
    protected void associateView(View view) {
        this.allergyVector = new Vector<>();
        this.chipVector = new Vector<>();

        this.elementChipGroup=view.findViewById(R.id.searchResultFragment_elementChipGroup);
        this.orderChipGroup=view.findViewById(R.id.searchResultFragment_orderChipGroup);

        this.rankingChip = view.findViewById(R.id.categoryFilterFragment_rankingChip);
        this.manufacturerChip = view.findViewById(R.id.categoryFilterFragment_manufacturerChip);
        this.reviewCountChip = view.findViewById(R.id.categoryFilterFragment_reviewCountChip);

        this.assendChip = view.findViewById(R.id.categoryFilterFragment_assendChip);
        this.desendChip = view.findViewById(R.id.categoryFilterFragment_desendChip);

        this.allergyChipGroup = view.findViewById(R.id.categoryFilterFragment_categoryAllergyFilterChipGroup);

        this.allergyLayout = view.findViewById(R.id.categoryFilterFragment_allergyLayout);
    }

    @Override
    protected void initializeView() {
        this.elementChipGroup.setSelectionRequired(true);
        this.orderChipGroup.setSelectionRequired(true);

        this.rankingChip.setChecked(false);
        this.manufacturerChip.setChecked(false);
        this.reviewCountChip.setChecked(false);

        this.assendChip.setChecked(false);
        this.desendChip.setChecked(false);


        this.rankingChip.setOnCheckedChangeListener((buttonView, isChecked) -> this.setSortElementModeCheck(isChecked, Constant.SortElement.RANK));
        this.manufacturerChip.setOnCheckedChangeListener((buttonView, isChecked) -> this.setSortElementModeCheck(isChecked, Constant.SortElement.MANUFACTURER));
        this.reviewCountChip.setOnCheckedChangeListener((buttonView, isChecked) -> this.setSortElementModeCheck(isChecked, Constant.SortElement.REVIEW_COUNT));

        this.assendChip.setOnCheckedChangeListener((buttonView, isChecked) -> this.setSortOrderModeCheck(isChecked, Constant.SortOrder.asc));
        this.desendChip.setOnCheckedChangeListener((buttonView, isChecked) -> this.setSortOrderModeCheck(isChecked, Constant.SortOrder.desc));


        if (this.categoryModel.getFoodSortElement().equals(Constant.SortElement.RANK.getMessage())) this.rankingChip.setChecked(true);
         else if (this.categoryModel.getFoodSortElement().equals(Constant.SortElement.MANUFACTURER.getMessage())) this.manufacturerChip.setChecked(true);
         else this.reviewCountChip.setChecked(true);

        if (this.categoryModel.getSortOrder().equals(Constant.SortOrder.asc)) this.assendChip.setChecked(true);
         else this.desendChip.setChecked(true);

        this.createAllergyChips();
    }

    @Override
    public void onPause() {
        super.onPause();
        allergyVector.clear();
        for (int id : allergyChipGroup.getCheckedChipIds()) {
            Chip chip = getView().findViewById(id);
            allergyVector.add(chip.getText().toString());
        }
        if (!dataset.get(KatiEntity.EKatiData.AUTHORIZATION).equals(KatiEntity.EKatiData.NULL.name()))
            this.saveAllergy(this.dataset.get(KatiEntity.EKatiData.AUTHORIZATION));
    }

    @Override
    protected void categoryDataUpdated() {

    }

    @Override
    protected void katiEntityUpdatedAndLogin() {
        this.loadAllergy(this.dataset.get(KatiEntity.EKatiData.AUTHORIZATION));
    }

    @Override
    protected void katiEntityUpdatedAndNoLogin() {
        this.hideAllergy();
    }


    /**
     * callback
     */
    private class ReadUserAllergyCallBack extends SimpleLoginRetrofitCallBack<ReadUserAllergyResponse> {
        public ReadUserAllergyCallBack(Activity activity) {
            super(activity);
        }

        @Override
        public void onSuccessResponse(Response<ReadUserAllergyResponse> response) {
            allergyVector.addAll(response.body().getUserAllergyMaterials());
            setOnAllergyFilter();
        }


    }


    private class ModifyUserAllergyCallBack extends SimpleLoginRetrofitCallBack<CreateUserAllergyResponse> {

        public ModifyUserAllergyCallBack(Activity activity) {
            super(activity);
        }

        @Override
        public void onSuccessResponse(Response<CreateUserAllergyResponse> response) {
            categoryModel.setAllergyList(allergyVector);
            saveCategory();
        }
    }

    /**
     * method
     */
    private void setOnAllergyFilter() {
        for (String string : this.allergyVector) {
            for (Chip chip : chipVector) {
                if (chip.getText().equals(string)) {
                    chip.setChecked(true);
                }
            }
        }
    }

    private void saveAllergy(String token) {
        CreateUserAllergyRequest request = new CreateUserAllergyRequest();
        request.setAllergyList(this.allergyVector);
        KatiRetrofitTool.getAPIWithAuthorizationToken(token).createUserAllergy(request).enqueue(JSHRetrofitTool.getCallback(new ModifyUserAllergyCallBack(this.getActivity())));
    }

    private void loadAllergy(String token) {
        KatiRetrofitTool.getAPIWithAuthorizationToken(token).readUserAllergy().enqueue(JSHRetrofitTool.getCallback(new ReadUserAllergyCallBack(this.getActivity())));
    }

    private void hideAllergy() {
        this.allergyLayout.setVisibility(View.GONE);
    }

    private void createAllergyChips() {
        this.chipVector.clear();
        this.allergyChipGroup.removeAllViews();
        for (Constant.EAllergyList allergy : Constant.EAllergyList.values()) {
            LayoutInflater inflater = (LayoutInflater) this.getActivity().getSystemService(Service.LAYOUT_INFLATER_SERVICE);
            Chip chip = (Chip) inflater.inflate(R.layout.jsh_chip, null);
            chip.setText(allergy.name());
            chip.setCheckable(true);
            this.chipVector.add(chip);
            this.allergyChipGroup.addView(chip);
        }
    }

    private void setSortOrderModeCheck(boolean isChecked, Constant.SortOrder sortOrder){
        if (isChecked) {
            categoryModel.setSortOrder(sortOrder);
            saveCategory();
        }
    }

    private void setSortElementModeCheck(boolean isChecked, Constant.SortElement sortElement){
        if (isChecked) {
            categoryModel.setFoodSortElement(sortElement.getMessage());
            saveCategory();
        }
    }

}
