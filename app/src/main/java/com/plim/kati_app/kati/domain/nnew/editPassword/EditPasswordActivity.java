package com.plim.kati_app.kati.domain.nnew.editPassword;

import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.plim.kati_app.R;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitTool;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.kati.crossDomain.domain.view.activity.KatiLoginCheckViewModelActivity;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.KatiDialog;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.KatiRetrofitTool;
import com.plim.kati_app.kati.domain.nnew.editPassword.model.ModifyPasswordRequest;
import com.plim.kati_app.kati.domain.nnew.editPassword.model.ModifyPasswordResponse;

import retrofit2.Response;

import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.CHANGE_PASSWORD_DIFF_ERROR;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.COMPLETE_CHANGE_PASSWORD_MESSAGE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.COMPLETE_CHANGE_PASSWORD_TITLE;

public class EditPasswordActivity extends KatiLoginCheckViewModelActivity {

    public EditText currentPasswordEditText, newPasswordEditText, newPasswordRemindEditText;
    public Button submitButton;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_password_edit;
    }

    @Override
    protected void associateView() {
        this.currentPasswordEditText= findViewById(R.id.passwordEditActivity_currentPasswordEditText);
        this.newPasswordEditText= findViewById(R.id.passwordEditActivity_newPasswordEditText);
        this.newPasswordRemindEditText= findViewById(R.id.passwordEditActivity_newPasswordRemindEditText);
        this.submitButton= findViewById(R.id.passwordEditActivity_submitButton);
    }

    @Override
    protected void initializeView() {
        this.submitButton.setOnClickListener(v->this.buttonClicked());
    }

    @Override
    protected boolean isLoginNeeded() {
        return true;
    }

    @Override
    protected void katiEntityUpdatedAndLogin() {

    }

    @Override
    protected void katiEntityUpdatedAndNoLogin() {

    }

    protected void buttonClicked() {
        if (this.newPasswordEditText.getText().toString().equals(this.newPasswordRemindEditText.getText().toString())) {
            changePassword();
        } else {
            Toast.makeText(this, CHANGE_PASSWORD_DIFF_ERROR, Toast.LENGTH_LONG).show();
        }
    }

    private void changePassword(){
        ModifyPasswordRequest request = new ModifyPasswordRequest();
        request.setBeforePassword(this.currentPasswordEditText.getText().toString());
        request.setAfterPassword(this.newPasswordEditText.getText().toString());
        KatiRetrofitTool.getAPIWithAuthorizationToken(this.dataset.get(KatiEntity.EKatiData.AUTHORIZATION)).modifyPassword(request).enqueue(JSHRetrofitTool.getCallback(new ChangePasswordRequestCallback(this)));

    }

    private class ChangePasswordRequestCallback extends SimpleLoginRetrofitCallBack<ModifyPasswordResponse> {
        public ChangePasswordRequestCallback(Activity activity) {
            super(activity);
        }

        @Override
        public void onSuccessResponse(Response<ModifyPasswordResponse> response) {
//            loadingDialog.hide();
            showCompletedDialog();
        }
    }

    private void showCompletedDialog() {
        KatiDialog.simplerAlertDialog(this,
                COMPLETE_CHANGE_PASSWORD_TITLE, COMPLETE_CHANGE_PASSWORD_MESSAGE,
//                (dialog, which) -> this.startActivity(LogOutActivity.class)
                null
        );
    }
}