package com.plim.kati_app.kati.domain.old.changePW.view;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.plim.kati_app.R;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.KatiDialog;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.LoadingDialog;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.KatiViewModelFragment;
import com.plim.kati_app.kati.domain.old.changePW.model.ModifyPasswordRequest;
import com.plim.kati_app.kati.domain.old.changePW.model.ModifyPasswordResponse;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitCallback;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitTool;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.KatiRetrofitTool;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.kati.domain.old.TempMainActivity;

import org.json.JSONObject;

import retrofit2.Response;

import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.AFTER_PASSWORD_HINT;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.AFTER_PASSWORD_HINT2;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.BEFORE_PASSWORD_HINT;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.CHANGE_PASSWORD_DIFF_ERROR;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.CHANGE_PASSWORD_TITLE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.COMPLETE_CHANGE_PASSWORD_MESSAGE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.COMPLETE_CHANGE_PASSWORD_TITLE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.DIALOG_CONFIRM;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.LOG_OUT_ACTIVITY_FAILURE_DIALOG_TITLE;

public class ChangePasswordInputFragment extends KatiViewModelFragment {

    // Component
        // View
        private LoadingDialog loadingDialog;
        private TextView mainTextView,subTextView;
        private EditText editText,editText2,editText3;
        private Button submitButton;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_change_password;
    }

    @Override
    protected void associateView(View view) {
        this.mainTextView=view.findViewById(R.id.changePassword_mainTextView);
        this.subTextView=view.findViewById(R.id.changePassword_subTextView);
        this.editText=view.findViewById(R.id.changePassword_oldPassWordEditText);
        this.editText2=view.findViewById(R.id.changePassword_newPassWordEditText);
        this.editText3=view.findViewById(R.id.changePassword_confirmOldPassWordEditText);
        this.submitButton=view.findViewById(R.id.changePassword_submit_button);

    }

    /**
     * System Life Cycle Callback
     */
    @Override
    protected void initializeView() {
        this.mainTextView.setText(CHANGE_PASSWORD_TITLE);
        this.subTextView.setVisibility(View.INVISIBLE);
        this.editText.setHint(BEFORE_PASSWORD_HINT);
        this.editText2.setHint(AFTER_PASSWORD_HINT);
        this.editText3.setHint(AFTER_PASSWORD_HINT2);
        this.submitButton.setText(DIALOG_CONFIRM);
        this.submitButton.setOnClickListener(v->this.buttonClicked());
    }
    @Override
    protected void katiEntityUpdated() {
        if(!this.dataset.containsKey(KatiEntity.EKatiData.AUTHORIZATION)){ this.showNotLoginedDialog(); }
    }

    /**
     * Callback
     */

    protected void buttonClicked() {
        if (this.editText2.getText().toString().equals(this.editText3.getText().toString())) {
            this.loadingDialog = new LoadingDialog(this.getContext());
            this.loadingDialog.show();
            changePassword();
                    } else {
            Toast.makeText(getContext(), CHANGE_PASSWORD_DIFF_ERROR, Toast.LENGTH_LONG).show();
        }
    }

    private void changePassword(){
        ModifyPasswordRequest request = new ModifyPasswordRequest();
        request.setBeforePassword(this.editText.getText().toString());
        request.setAfterPassword(this.editText2.getText().toString());
        KatiRetrofitTool.getAPIWithAuthorizationToken(this.dataset.get(KatiEntity.EKatiData.AUTHORIZATION)).modifyPassword(request).enqueue(JSHRetrofitTool.getCallback(new ChangePasswordRequestCallback()));

    }

    private class ChangePasswordRequestCallback implements JSHRetrofitCallback<ModifyPasswordResponse> {
        @Override
        public void onSuccessResponse(Response<ModifyPasswordResponse> response) {
            loadingDialog.hide();
            showCompletedDialog();
        }
        @Override
        public void onFailResponse(Response<ModifyPasswordResponse> response) {
            loadingDialog.hide();
            try {
                JSONObject jObjError = new JSONObject(response.errorBody().string());
                Toast.makeText(getContext(), jObjError.getString("error-message"), Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        @Override
        public void onConnectionFail(Throwable t) { loadingDialog.hide(); }
    }

    /**
     * Method
     */
    private void showNotLoginedDialog() {
        KatiDialog.simplerAlertDialog(this.getActivity(),
                LOG_OUT_ACTIVITY_FAILURE_DIALOG_TITLE, LOG_OUT_ACTIVITY_FAILURE_DIALOG_TITLE,
                (dialog, which) -> this.startActivity(TempMainActivity.class)
        );
    }
    private void showCompletedDialog() {
        KatiDialog.simplerAlertDialog(this.getActivity(),
                COMPLETE_CHANGE_PASSWORD_TITLE, COMPLETE_CHANGE_PASSWORD_MESSAGE,
//                (dialog, which) -> this.startActivity(LogOutActivity.class)
        null
        );
    }
}