package com.plim.kati_app.kati.domain.nnew.login.view;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.plim.kati_app.R;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.KatiDialog;
import com.plim.kati_app.kati.domain.nnew.findId.FindIdActivity;
import com.plim.kati_app.kati.domain.nnew.login.model.LoginResponse;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitCallback;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitTool;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.KatiRetrofitTool;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.KatiViewModelFragment;
import com.plim.kati_app.kati.domain.old.TempMainActivity;
import com.plim.kati_app.kati.domain.old.login.pwFind.view.FindPasswordActivity;
import com.plim.kati_app.kati.domain.old.login.signIn.view.SignInActivity;

import java.util.Vector;


import retrofit2.Response;

public class LoginFragment extends KatiViewModelFragment {

    // Associate
    // View
    private EditText id, pw;
    private Button loginButton, idFindButton, pwFindButton, signInButton, kakaoLoginButton, googleLoginButton;
    private CheckBox autologinCheckBox;

    private Vector<KatiDialog> dialogs;

    public LoginFragment(){
        this.dialogs= new Vector<>();
    }


    /**
     * System Life Cycle Callback
     */
    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_home_layout;
    }

    @Override
    protected void associateView(View view) {
        this.id = view.findViewById(R.id.loginActivity_idText);
        this.pw = view.findViewById(R.id.loginActivity_pwText);
        this.loginButton = view.findViewById(R.id.loginActivity_loginButton);
        this.idFindButton = view.findViewById(R.id.loginActivity_idFindButton);
        this.pwFindButton = view.findViewById(R.id.loginActivity_pwFindButton);
        this.signInButton = view.findViewById(R.id.loginActivity_accountCreateButton);

        this.autologinCheckBox=view.findViewById(R.id.loginActivity_autologinCheckBox);

        this.autologinCheckBox = view.findViewById(R.id.loginActivity_autologinCheckBox);

        this.kakaoLoginButton = view.findViewById(R.id.social_login_button_kakao);
        this.googleLoginButton = view.findViewById(R.id.social_login_button_google);
    }

    @Override
    protected void initializeView() {
        this.autologinCheckBox.setOnClickListener((v -> this.setAutoLogin()));

        this.signInButton.setOnClickListener(v->this.startActivity(new Intent(getContext(), SignInActivity.class)));
        this.pwFindButton.setOnClickListener(v->this.startActivity(new Intent(getContext(), FindPasswordActivity.class)));
        this.idFindButton.setOnClickListener(v->this.startActivity(new Intent(getContext(), FindIdActivity.class)));
        this.loginButton.setOnClickListener(v->this.login());
        this.autologinCheckBox.setChecked(this.dataset.get(KatiEntity.EKatiData.AUTO_LOGIN).equals(KatiEntity.EKatiData.TRUE.name()));
        this.kakaoLoginButton.setOnClickListener(v->this.googleLogin());
        this.googleLoginButton.setOnClickListener(v->this.kakaoLogin());

    }

    @Override
    public void katiEntityUpdated() {
        this.checkLogined();
    }

    @Override
    public void onDestroy() {
        for(KatiDialog dialog: dialogs) {
            dialog.dismiss();
        }
        super.onDestroy();
    }

    /**
     * Callback
     */
    private void kakaoLogin() {
    }

    private void googleLogin() {
//        Intent intent = new Intent(getContext(), GoogleLoginActivity.class);
//        startActivity(intent);
    }

    private void setAutoLogin() {
        this.dataset.put(KatiEntity.EKatiData.AUTO_LOGIN,
                this.autologinCheckBox.isChecked() ? KatiEntity.EKatiData.TRUE.name() : KatiEntity.EKatiData.FALSE.name());
    }

    private void login() {
        LoginResponse loginRequest = new LoginResponse(this.id.getText().toString(), this.pw.getText().toString());
        KatiRetrofitTool.getAPIWithNullConverter().login(loginRequest).enqueue(JSHRetrofitTool.getCallback(new LoginRequestCallback()));
    }

    private void checkLogined() {
        if (this.dataset.get(KatiEntity.EKatiData.AUTHORIZATION) != null)
            if (!this.dataset.get(KatiEntity.EKatiData.AUTHORIZATION).equals(KatiEntity.EKatiData.NULL.name())) {
               this.dialogs.add( KatiDialog.simplerAlertDialog(this.getActivity(),
                        R.string.login_already_signed_dialog, R.string.login_already_signed_content_dialog,
                        (dialog, which) -> startMainActivity()
                ));
            }
    }

    private class LoginRequestCallback implements JSHRetrofitCallback<LoginResponse> {
        @Override
        public void onSuccessResponse(Response<LoginResponse> response) {
            dataset.put(KatiEntity.EKatiData.AUTHORIZATION, response.headers().get("Authorization"));
            dataset.put(KatiEntity.EKatiData.EMAIL, id.getText().toString());
            dataset.put(KatiEntity.EKatiData.PASSWORD, pw.getText().toString());
//            save();
           dialogs.add( KatiDialog.simplerAlertDialog(getActivity(),
                    R.string.login_success_dialog, R.string.login_success_content_dialog,
                    (dialog, which) -> startMainActivity()
            ));
        }

        @Override
        public void onFailResponse(Response<LoginResponse> response) {
            dialogs.add(KatiDialog.simplerAlertDialog(getActivity(),
                    R.string.login_failed_dialog, R.string.login_failed_content_dialog,
                    (dialog, which) -> {
                        id.setText("");
                        pw.setText("");
                    }
            ));
        }

        @Override
        public void onConnectionFail(Throwable t) {
            Log.e("연결실패", t.getMessage());
        }
    }

    /**
     * Method
     */
    public void startMainActivity() {
        this.startActivity(new Intent(this.getContext(), TempMainActivity.class));
    }
}