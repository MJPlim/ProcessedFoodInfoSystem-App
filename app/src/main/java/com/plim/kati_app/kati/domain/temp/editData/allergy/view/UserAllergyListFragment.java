package com.plim.kati_app.kati.domain.temp.editData.allergy.view;

import android.app.Activity;

import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitTool;
import com.plim.kati_app.kati.crossDomain.domain.model.Constant.EAllergyList;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.expandableFragment.AbstractExpandableItemList;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.KatiRetrofitTool;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.SimpleRetrofitCallBack;
import com.plim.kati_app.kati.domain.temp.TempActivity;
import com.plim.kati_app.kati.domain.temp.editData.allergy.model.CreateUserAllergyRequest;
import com.plim.kati_app.kati.domain.temp.editData.allergy.model.CreateUserAllergyResponse;
import com.plim.kati_app.kati.domain.temp.editData.allergy.model.ReadUserAllergyResponse;

import java.util.Vector;

import retrofit2.Response;

import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.ALLERGY_EXPANDABLE_LIST_TITLE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.ALLERGY_LIST_NO_SUCH_ITEM_MESSAGE_SUFFIX;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.ALLERGY_LIST_NO_SUCH_ITEM_TITLE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.ALLERGY_MODIFY_SUCCESS_DIALOG_MESSAGE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.ALLERGY_MODIFY_SUCCESS_DIALOG_TITLE;


public class UserAllergyListFragment extends AbstractExpandableItemList {


    @Override
    protected void katiEntityUpdatedAndLogin() {
        this.loadAllergy(this.dataset.get(KatiEntity.EKatiData.AUTHORIZATION));
    }

    @Override
    protected void katiEntityUpdatedAndNoLogin() {
        this.notLoginDialog();
    }

    @Override
    protected void addItem(String editText) {
        for (EAllergyList allergy : EAllergyList.values()) {
            if (editText.equals(allergy.name())) {
                this.addItemToAdapter(editText);
                this.resetEditText();
                return;
            }
        }
        this.showDialog(
                ALLERGY_LIST_NO_SUCH_ITEM_TITLE,
                editText + ALLERGY_LIST_NO_SUCH_ITEM_MESSAGE_SUFFIX,
                (dialog, which) -> this.resetEditText()
        );
    }

    @Override
    protected void onSave() {
        this.saveAllergy(this.dataset.get(KatiEntity.EKatiData.AUTHORIZATION));
    }

    private void saveAllergy(String token) {
        CreateUserAllergyRequest request = new CreateUserAllergyRequest();
        request.setAllergyList(getItemsFromAdapter());
        KatiRetrofitTool.getAPIWithAuthorizationToken(token).createUserAllergy(request).enqueue(JSHRetrofitTool.getCallback(new ModifyUserAllergyCallBack(this.getActivity())));
    }


    private void loadAllergy(String token) {
        KatiRetrofitTool.getAPIWithAuthorizationToken(token).readUserAllergy().enqueue(JSHRetrofitTool.getCallback(new ReadUserAllergyCallBack(this.getActivity())));
    }

    private class ReadUserAllergyCallBack extends SimpleRetrofitCallBack<ReadUserAllergyResponse> {
        public ReadUserAllergyCallBack(Activity activity) {
            super(activity);
        }

        @Override
        public void onSuccessResponse(Response<ReadUserAllergyResponse> response) {
            Vector<String> vector = new Vector<>();
            vector.addAll(response.body().getUserAllergyMaterials());
            setItemValues(new ExpandableListItem(ALLERGY_EXPANDABLE_LIST_TITLE, vector));
        }

        @Override
        public void refreshToken(KatiEntity.EKatiData eKatiData, String authorization) {
            dataset.put(eKatiData, authorization);
        }
    }


    private class ModifyUserAllergyCallBack extends SimpleRetrofitCallBack<CreateUserAllergyResponse> {

        public ModifyUserAllergyCallBack(Activity activity) {
            super(activity);
        }

        @Override
        public void onSuccessResponse(Response<CreateUserAllergyResponse> response) {
            showDialog(
                    ALLERGY_MODIFY_SUCCESS_DIALOG_TITLE,
                    ALLERGY_MODIFY_SUCCESS_DIALOG_MESSAGE,
                    (dialog, which) -> startActivity(TempActivity.class)
            );
        }

        @Override
        public void refreshToken(KatiEntity.EKatiData eKatiData, String authorization) {
            dataset.put(eKatiData, authorization);
        }
    }


}