package com.plim.kati_app.kati.domain.nnew.main.myKati.allergy;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.plim.kati_app.R;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitTool;
import com.plim.kati_app.kati.crossDomain.domain.model.Constant;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.KatiLoginCheckViewModelFragment;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.KatiRetrofitTool;
import com.plim.kati_app.kati.domain.nnew.main.myKati.allergy.model.CreateUserAllergyRequest;
import com.plim.kati_app.kati.domain.nnew.main.myKati.allergy.model.CreateUserAllergyResponse;
import com.plim.kati_app.kati.domain.nnew.main.myKati.allergy.model.ReadUserAllergyResponse;


import java.util.Vector;

import retrofit2.Response;

import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.ALLERGY_EXPANDABLE_LIST_TITLE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.ALLERGY_MODIFY_SUCCESS_DIALOG_MESSAGE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.ALLERGY_MODIFY_SUCCESS_DIALOG_TITLE;

public class AllergyFragment extends KatiLoginCheckViewModelFragment {

    private ChipGroup allergyChipGroup;

    private Vector<Chip> chipVector;
    private Vector<String> allergyVector;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_allergy;
    }

    @Override
    protected void associateView(View view) {
        this.allergyChipGroup = view.findViewById(R.id.allergyFragment_allergyChipGroup);
        this.chipVector=new Vector<>();
        this.allergyVector=new Vector<>();
    }

    @Override
    protected void initializeView() {
        for (Constant.EAllergyList allergy : Constant.EAllergyList.values()) {
            Chip chip = new Chip(getContext());
            chip.setChipStrokeWidth(1.5f);
            chip.setChipStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.kati_middle_gray, getActivity().getTheme())));
            chip.setChipBackgroundColor(ColorStateList.valueOf(getResources().getColor(R.color.chip_choice_bg, getContext().getTheme())));
            chip.setText(allergy.name());
            chip.setCheckable(true);
            chip.setCheckedIconTint(ColorStateList.valueOf(getResources().getColor(R.color.kati_red, getContext().getTheme())));
            this.chipVector.add(chip);
            this.allergyChipGroup.addView(chip);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        allergyVector.clear();
        for(int id: allergyChipGroup.getCheckedChipIds()){
            Chip chip= getView().findViewById(id);
            allergyVector.add(chip.getText().toString());
        }
        this.saveAllergy(this.dataset.get(KatiEntity.EKatiData.AUTHORIZATION));
    }

    @Override
    protected boolean isLoginNeeded() {
        return true;
    }

    @Override
    protected void katiEntityUpdatedAndLogin() {
        this.loadAllergy(this.dataset.get(KatiEntity.EKatiData.AUTHORIZATION));
    }

    @Override
    protected void katiEntityUpdatedAndNoLogin() {}

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
        }
    }

    private void setOnAllergyFilter() {
        for(String string: this.allergyVector){
            for(Chip chip: chipVector){
                if(chip.getText().equals(string)){
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