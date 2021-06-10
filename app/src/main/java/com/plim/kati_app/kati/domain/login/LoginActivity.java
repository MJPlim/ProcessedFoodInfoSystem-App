package com.plim.kati_app.kati.domain.login;

import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.plim.kati_app.R;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitCallback;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitTool;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.kati.crossDomain.domain.view.activity.KatiHasTitleActivity;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.KatiDialog;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.KatiRetrofitTool;
import com.plim.kati_app.kati.domain.findId.FindIdActivity;
import com.plim.kati_app.kati.domain.findPassword.FindPasswordActivity;
import com.plim.kati_app.kati.domain.login.model.LoginResponse;
import com.plim.kati_app.kati.domain.signUp.SignUpActivity;

import java.util.Vector;

import retrofit2.Response;

import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.FOOD_SEARCH_RESULT_LIST_FRAGMENT_FAILURE_DIALOG_TITLE;

/**
 * 로그인 액티비티.
 */
public class LoginActivity extends KatiHasTitleActivity {

    //associate
    //view
    private TextView findId, findPw, signIn;
    private EditText emailAddress, password;
    private Button loginButton;
    private CheckBox autologinCheckBox;
    private Vector<KatiDialog> dialogs;

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        if (!(emailAddress.length() == 0) && !(password.length() == 0)) {
            this.loginButton.setEnabled(true);
        } else {
            this.loginButton.setEnabled(false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (KatiDialog dialog : dialogs)
            dialog.dismiss();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void associateView() {
        super.associateView();
        this.dialogs = new Vector<>();
        this.emailAddress = this.findViewById(R.id.loginActivity_emailTextView);
        this.password = this.findViewById(R.id.loginActivity_passwordTextView);
        this.loginButton = this.findViewById(R.id.loginActivity_loginButton);
        this.autologinCheckBox = this.findViewById(R.id.loginActivity_autoLoginCheckBox);
        this.findId = this.findViewById(R.id.loginActivity_findIdTextView);
        this.findPw = this.findViewById(R.id.loginActivity_findPasswordTextView);
        this.signIn = this.findViewById(R.id.loginActivity_registerTextView);
    }

    @Override
    protected void initializeView() {
        super.initializeView();
        this.findId.setOnClickListener(v -> this.startActivity(new Intent(this, FindIdActivity.class)));
        this.findPw.setOnClickListener(v -> this.startActivity(new Intent(this, FindPasswordActivity.class)));
        this.signIn.setOnClickListener(v -> this.startActivity(new Intent(this, SignUpActivity.class)));
        this.autologinCheckBox.setOnClickListener((v -> this.setAutoLogin()));
        this.loginButton.setOnClickListener(v -> this.login());
    }


    @Override
    protected boolean isLoginNeeded() {
        return false;
    }

    @Override
    protected void katiEntityUpdatedAndLogin() {

    }

    @Override
    protected void katiEntityUpdatedAndNoLogin() {

    }

    @Override
    protected String getTitleContent() {
        return "";
    }


    /**
     * callback
     */
    private class LoginRequestCallback implements JSHRetrofitCallback<LoginResponse> {
        @Override
        public void onSuccessResponse(Response<LoginResponse> response) {
            saveLoginData(response.headers().get("Authorization"));
            onBackPressed();
        }

        @Override
        public void onFailResponse(Response<LoginResponse> response) {
            dialogs.add(KatiDialog.simplerAlertDialog(LoginActivity.this,
                    R.string.login_failed_dialog, R.string.login_failed_content_dialog,
                    (dialog, which) -> {
                        emailAddress.setText("");
                        password.setText("");
                    }
            ));
        }

        @Override
        public void onConnectionFail(Throwable t) {
            Log.e(FOOD_SEARCH_RESULT_LIST_FRAGMENT_FAILURE_DIALOG_TITLE, t.getMessage());
        }
    }

    /**
     * method
     */
    private void login() {
        LoginResponse loginRequest = new LoginResponse(this.emailAddress.getText().toString(), this.password.getText().toString());
        KatiRetrofitTool.getAPIWithNullConverter().login(loginRequest).enqueue(JSHRetrofitTool.getCallback(new LoginRequestCallback()));
    }

    private void setAutoLogin() {
        String autoLogin = this.autologinCheckBox.isChecked() ? KatiEntity.EKatiData.TRUE.name() : KatiEntity.EKatiData.FALSE.name();
        this.dataset.put(KatiEntity.EKatiData.AUTO_LOGIN, autoLogin);
    }

    public void saveLoginData(String authorization) {
        this.dataset.put(KatiEntity.EKatiData.AUTHORIZATION, authorization);
        this.dataset.put(KatiEntity.EKatiData.EMAIL, emailAddress.getText().toString());
        this.dataset.put(KatiEntity.EKatiData.PASSWORD, password.getText().toString());
        save();
    }
}