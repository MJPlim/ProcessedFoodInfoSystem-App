package com.plim.kati_app.kati.domain.main.myKati.allergy;

import android.app.Activity;
import android.app.Service;

import android.view.LayoutInflater;
import android.view.View;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.plim.kati_app.R;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitTool;
import com.plim.kati_app.kati.crossDomain.domain.model.Constant;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.KatiHasTitleFragment;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.KatiRetrofitTool;
import com.plim.kati_app.kati.domain.main.myKati.allergy.model.CreateUserAllergyRequest;
import com.plim.kati_app.kati.domain.main.myKati.allergy.model.CreateUserAllergyResponse;
import com.plim.kati_app.kati.domain.main.myKati.allergy.model.ReadUserAllergyResponse;


import java.util.Vector;

import retrofit2.Response;

public class AllergyFragment extends KatiHasTitleFragment {

    //associate
    //view
    private ChipGroup allergyChipGroup;
    private Vector<Chip> chipVector;
    private Vector<String> allergyVector;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_allergy;
    }

    @Override
    protected void associateView(View view) {
        super.associateView(view);
        this.allergyChipGroup = view.findViewById(R.id.allergyFragment_allergyChipGroup);
        this.chipVector = new Vector<>();
        this.allergyVector = new Vector<>();
    }

    @Override
    protected void initializeView() {
        super.initializeView();
        this.setAllergyChips();
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
    protected boolean isLoginNeeded() {
        return true;
    }

    @Override
    protected void katiEntityUpdatedAndLogin() {
        this.loadAllergy(this.dataset.get(KatiEntity.EKatiData.AUTHORIZATION));
    }

    @Override
    protected void katiEntityUpdatedAndNoLogin() {
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
        }
    }

    /**
     * method
     */
    private void saveAllergy(String token) {
        CreateUserAllergyRequest request = new CreateUserAllergyRequest();
        request.setAllergyList(this.allergyVector);
        KatiRetrofitTool.getAPIWithAuthorizationToken(token).createUserAllergy(request).enqueue(JSHRetrofitTool.getCallback(new ModifyUserAllergyCallBack(this.getActivity())));
    }

    private void loadAllergy(String token) {
        KatiRetrofitTool.getAPIWithAuthorizationToken(token).readUserAllergy().enqueue(JSHRetrofitTool.getCallback(new ReadUserAllergyCallBack(this.getActivity())));
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

    protected void setAllergyChips() {
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
}