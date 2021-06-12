package com.plim.kati_app.kati.domain.editPassword.view;

import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.plim.kati_app.R;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitTool;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.kati.crossDomain.domain.view.activity.KatiHasTitleActivity;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.KatiDialog;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.KatiRetrofitTool;
import com.plim.kati_app.kati.domain.editPassword.model.ModifyPasswordRequest;
import com.plim.kati_app.kati.domain.editPassword.model.ModifyPasswordResponse;
import com.plim.kati_app.kati.domain.main.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Response;

import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.CHANGE_PASSWORD_DIFF_ERROR;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.EDIT_PASSWORD_ACTIVITY_LOG_OUT_DIALOG_MESSAGE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.EDIT_PASSWORD_ACTIVITY_LOG_OUT_DIALOG_TITLE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.JSONOBJECT_AFTER_PASSWORD_MESSAGE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.JSONOBJECT_BEFORE_PASSWORD_MESSAGE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.JSONOBJECT_ERROR_MESSAGE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.RETROFIT_FAIL_CONNECTION_MESSAGE;

public class EditPasswordActivity extends KatiHasTitleActivity {

    //associate
    //view
    private EditText beforePassword, afterPassword, afterPasswordOneMore;
    private Button submitButton;
    private boolean loginNeed = true;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_password_edit;
    }

    @Override
    protected void associateView() {
        super.associateView();
        this.beforePassword = findViewById(R.id.editPasswordActivity_beforePassword_editText);
        this.afterPassword = findViewById(R.id.editPasswordActivity_afterPassword_editText);
        this.afterPasswordOneMore = findViewById(R.id.editPasswordActivity_afterPasswordConfirm_editText);
        this.submitButton = findViewById(R.id.editPasswordActivity_submitButton);
    }

    @Override
    protected void initializeView() {
        super.initializeView();
        this.submitButton.setOnClickListener(v -> this.buttonClicked());
    }

    @Override
    protected boolean isLoginNeeded() {
        return this.loginNeed;
    }

    @Override
    protected void katiEntityUpdatedAndLogin() {

    }

    @Override
    protected void katiEntityUpdatedAndNoLogin() {

    }

    /**
     * callback
     */
    private class ChangePasswordRequestCallback extends SimpleLoginRetrofitCallBack<ModifyPasswordResponse> {
        public ChangePasswordRequestCallback(Activity activity) {
            super(activity);
        }

        @Override
        public void onSuccessResponse(Response<ModifyPasswordResponse> response) {
            showCompletedDialog();
        }

        @Override
        protected String getFailMessage(JSONObject object) throws JSONException {
            if (object.optJSONObject(JSONOBJECT_ERROR_MESSAGE) != null) {
                JSONObject miniObject = object.getJSONObject(JSONOBJECT_ERROR_MESSAGE);
                return miniObject.has(JSONOBJECT_BEFORE_PASSWORD_MESSAGE) ? miniObject.getString(JSONOBJECT_BEFORE_PASSWORD_MESSAGE) :
                        miniObject.has(JSONOBJECT_AFTER_PASSWORD_MESSAGE) ? miniObject.getString(JSONOBJECT_AFTER_PASSWORD_MESSAGE) :
                                miniObject.toString();
            }
            return super.getFailMessage(object);
        }

        @Override
        public void onConnectionFail(Throwable t) {
            Toast.makeText(getApplicationContext(), RETROFIT_FAIL_CONNECTION_MESSAGE, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Method
     */
    private void showCompletedDialog() {
        KatiDialog.simplerTwoOptionAlertDialog(
                this,
                EDIT_PASSWORD_ACTIVITY_LOG_OUT_DIALOG_TITLE,
                EDIT_PASSWORD_ACTIVITY_LOG_OUT_DIALOG_MESSAGE,
                (dialog, which) -> {
                    this.removeLoginData();
                    startActivity(MainActivity.class);
                },
                null
        );
    }

    private void changePassword() {
        ModifyPasswordRequest request = new ModifyPasswordRequest();
        request.setBeforePassword(this.beforePassword.getText().toString());
        request.setAfterPassword(this.afterPassword.getText().toString());
        KatiRetrofitTool.getAPIWithAuthorizationToken(this.dataset.get(KatiEntity.EKatiData.AUTHORIZATION)).modifyPassword(request)
                .enqueue(JSHRetrofitTool.getCallback(new ChangePasswordRequestCallback(this)));

    }

    private void buttonClicked() {
        if (this.afterPassword.getText().toString().equals(this.afterPasswordOneMore.getText().toString())) {
            changePassword();
        } else {
            Toast.makeText(this, CHANGE_PASSWORD_DIFF_ERROR, Toast.LENGTH_LONG).show();
        }
    }

    private void removeLoginData() {
        this.loginNeed = false;
        this.dataset.put(KatiEntity.EKatiData.EMAIL, KatiEntity.EKatiData.NULL.name());
        this.dataset.put(KatiEntity.EKatiData.PASSWORD, KatiEntity.EKatiData.NULL.name());
        this.dataset.put(KatiEntity.EKatiData.NAME, KatiEntity.EKatiData.NULL.name());
        this.dataset.put(KatiEntity.EKatiData.AUTO_LOGIN, KatiEntity.EKatiData.FALSE.name());
        this.dataset.put(KatiEntity.EKatiData.AUTHORIZATION, KatiEntity.EKatiData.NULL.name());
        this.save();
    }


}