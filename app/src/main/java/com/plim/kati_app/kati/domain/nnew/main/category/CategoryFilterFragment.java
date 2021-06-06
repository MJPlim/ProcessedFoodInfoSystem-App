package com.plim.kati_app.kati.domain.nnew.main.category;

import android.app.Activity;
import android.app.Service;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.plim.kati_app.R;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitTool;
import com.plim.kati_app.kati.crossDomain.domain.model.Constant;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.KatiCategoryFragment;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.KatiRetrofitTool;
import com.plim.kati_app.kati.domain.nnew.main.myKati.allergy.AllergyFragment;
import com.plim.kati_app.kati.domain.nnew.main.myKati.allergy.model.CreateUserAllergyRequest;
import com.plim.kati_app.kati.domain.nnew.main.myKati.allergy.model.CreateUserAllergyResponse;
import com.plim.kati_app.kati.domain.nnew.main.myKati.allergy.model.ReadUserAllergyResponse;

import java.util.Vector;

import retrofit2.Response;

public class CategoryFilterFragment extends KatiCategoryFragment {

    private Chip rankingChip, manufacturerChip, reviewCountChip, assendChip, desendChip;
    private Vector<Chip> chipVector;
    private ChipGroup allergyChipGroup;
    private Vector<String> allergyVector;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_category_filter;
    }

    @Override
    protected void associateView(View view) {
        this.allergyVector = new Vector<>();
        this.chipVector=new Vector<>();

        this.rankingChip = view.findViewById(R.id.categoryFilterFragment_rankingChip);
        this.manufacturerChip = view.findViewById(R.id.categoryFilterFragment_manufacturerChip);
        this.reviewCountChip = view.findViewById(R.id.categoryFilterFragment_reviewCountChip);

        this.assendChip = view.findViewById(R.id.categoryFilterFragment_assendChip);
        this.desendChip = view.findViewById(R.id.categoryFilterFragment_desendChip);

        this.allergyChipGroup = view.findViewById(R.id.categoryFilterFragment_categoryAllergyFilterChipGroup);
    }

    @Override
    protected void initializeView() {

        this.rankingChip.setChecked(false);
        this.manufacturerChip.setChecked(false);
        this.reviewCountChip.setChecked(false);

        this.assendChip.setChecked(false);
        this.desendChip.setChecked(false);


        this.rankingChip.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                categoryModel.setFoodSortElement(Constant.SortElement.RANK.getMessage());
                saveCategory();
            }
        });
        this.manufacturerChip.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                categoryModel.setFoodSortElement(Constant.SortElement.MANUFACTURER.getMessage());
                saveCategory();
            }
        });
        this.reviewCountChip.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                categoryModel.setFoodSortElement(Constant.SortElement.REVIEW_COUNT.getMessage());
                saveCategory();
            }
        });

        this.assendChip.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                categoryModel.setSortOrder(Constant.SortOrder.asc);
                saveCategory();
            }
        });
        this.desendChip.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                categoryModel.setSortOrder(Constant.SortOrder.desc);
                saveCategory();
            }
        });


        if (this.categoryModel.getFoodSortElement().equals(Constant.SortElement.RANK.getMessage())) {
            this.rankingChip.setChecked(true);
        } else if (this.categoryModel.getFoodSortElement().equals(Constant.SortElement.MANUFACTURER.getMessage())) {
            this.manufacturerChip.setChecked(true);
        } else {
            this.reviewCountChip.setChecked(true);
        }

        if (this.categoryModel.getSortOrder().equals(Constant.SortOrder.asc)) {
            this.assendChip.setChecked(true);
        } else {
            this.desendChip.setChecked(true);
        }

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

    @Override
    public void onPause() {
        super.onPause();
        allergyVector.clear();
        for (int id : allergyChipGroup.getCheckedChipIds()) {
            Chip chip = getView().findViewById(id);
            allergyVector.add(chip.getText().toString());
        }
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

    }


    private class ReadUserAllergyCallBack extends SimpleLoginRetrofitCallBack<ReadUserAllergyResponse> {
        public ReadUserAllergyCallBack(Activity activity) {
            super(activity);
        }

        @Override
        public void onSuccessResponse(Response<ReadUserAllergyResponse> response) {
            allergyVector.addAll(response.body().getUserAllergyMaterials());
            setOnAllergyFilter();
//            setItemValues(new AbstractExpandableItemList.ExpandableListItem(ALLERGY_EXPANDABLE_LIST_TITLE, vector));
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

}
