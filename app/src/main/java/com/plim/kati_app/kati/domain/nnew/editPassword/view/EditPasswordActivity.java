package com.plim.kati_app.kati.domain.nnew.editPassword.view;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.plim.kati_app.R;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitCallback;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitTool;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.kati.crossDomain.domain.view.activity.KatiViewModelActivity;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.KatiDialog;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.LoadingDialog;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.KatiRetrofitTool;
import com.plim.kati_app.kati.domain.nnew.editPassword.model.ModifyPasswordRequest;
import com.plim.kati_app.kati.domain.nnew.editPassword.model.ModifyPasswordResponse;

import org.json.JSONObject;

import retrofit2.Response;

import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.CHANGE_PASSWORD_DIFF_ERROR;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.COMPLETE_CHANGE_PASSWORD_MESSAGE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.COMPLETE_CHANGE_PASSWORD_TITLE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.LOG_OUT_ACTIVITY_FAILURE_DIALOG_TITLE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.RETROFIT_FAIL_CONNECTION_MESSAGE;

public class EditPasswordActivity extends KatiViewModelActivity {

    private LoadingDialog loadingDialog;
    private TextView mainTextView;
    private EditText editText, editText2, editText3;
    private Button submitButton;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_password_edit;
    }

    @Override
    protected void associateView() {
        this.mainTextView = findViewById(R.id.editPasswordActivity_mainTextView);
        this.editText = findViewById(R.id.editPasswordActivity_beforePassword_editText);
        this.editText2 = findViewById(R.id.editPasswordActivity_afterPassword_editText);
        this.editText3 = findViewById(R.id.editPasswordActivity_afterPasswordConfirm_editText);
        this.submitButton = findViewById(R.id.editPasswordActivity_submitButton);

    }

    @Override
    protected void initializeView() {
        this.submitButton.setOnClickListener(v -> this.buttonClicked());


    }

    private void buttonClicked() {
        if (this.editText2.getText().toString().equals(this.editText3.getText().toString())) {
            changePassword();
        } else {
            Toast.makeText(this, CHANGE_PASSWORD_DIFF_ERROR, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void katiEntityUpdated() {
        if (!this.dataset.containsKey(KatiEntity.EKatiData.AUTHORIZATION)) {
            this.showNotLoginedDialog();
        }
    }

    private void changePassword() {
        ModifyPasswordRequest request = new ModifyPasswordRequest();
        request.setBeforePassword(this.editText.getText().toString());
        request.setAfterPassword(this.editText2.getText().toString());
        KatiRetrofitTool.getAPIWithAuthorizationToken(this.dataset.get(KatiEntity.EKatiData.AUTHORIZATION)).modifyPassword(request)
                .enqueue(JSHRetrofitTool.getCallback(new ChangePasswordRequestCallback()));

    }

    private class ChangePasswordRequestCallback implements JSHRetrofitCallback<ModifyPasswordResponse> {
        @Override
        public void onSuccessResponse(Response<ModifyPasswordResponse> response) {
            showCompletedDialog();
        }

        @Override
        public void onFailResponse(Response<ModifyPasswordResponse> response) {
            try {
                JSONObject jObjError = new JSONObject(response.errorBody().string());
                Toast.makeText(getApplicationContext(), jObjError.getString("error-message"), Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }

        }

        @Override
        public void onConnectionFail(Throwable t) {
            Toast.makeText(getApplicationContext(),RETROFIT_FAIL_CONNECTION_MESSAGE , Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Method
     */
    private void showNotLoginedDialog() {
        Toast.makeText(getApplicationContext(),LOG_OUT_ACTIVITY_FAILURE_DIALOG_TITLE , Toast.LENGTH_LONG).show();
    }

    private void showCompletedDialog() {
        Toast.makeText(getApplicationContext(),COMPLETE_CHANGE_PASSWORD_MESSAGE , Toast.LENGTH_LONG).show();
    }


}