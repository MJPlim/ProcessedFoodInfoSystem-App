package com.plim.kati_app.domain2.view.userAccount.login;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.plim.kati_app.R;
import com.plim.kati_app.domain.asset.KatiDialog;
import com.plim.kati_app.retrofit.dto.LoginRequest;
import com.plim.kati_app.retrofit.RetrofitClient;
import com.plim.kati_app.domain2.model.KatiEntity;
import com.plim.kati_app.domain2.view.KatiViewModelFragment;
import com.plim.kati_app.domain2.view.TempMainActivity;
import com.plim.kati_app.domain2.view.userAccount.pwFind.FindPasswordActivity;
import com.plim.kati_app.domain2.view.userAccount.signIn.SignInActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends KatiViewModelFragment {

    // Associate
        // View
        private EditText id, pw;
        private Button loginButton, idFindButton, pwFindButton, signInButton, kakaoLoginButton, googleLoginButton;
        private CheckBox autologinCheckBox;

    /**
     * System Life Cycle Callback
     */
    @Override
    protected int getLayoutId() { return R.layout.activity_login_home_layout; }
    @Override
    protected void associateView(View view) {
        this.id = view.findViewById(R.id.loginActivity_idText);
        this.pw = view.findViewById(R.id.loginActivity_pwText);
        this.loginButton = view.findViewById(R.id.loginActivity_loginButton);
        this.idFindButton = view.findViewById(R.id.loginActivity_idFindButton);
        this.pwFindButton = view.findViewById(R.id.loginActivity_pwFindButton);
        this.signInButton = view.findViewById(R.id.loginActivity_accountCreateButton);
        this.autologinCheckBox=view.findViewById(R.id.loginActivity_autologinCheckBox);
        this.kakaoLoginButton = view.findViewById(R.id.social_login_button_kakao);
        this.googleLoginButton = view.findViewById(R.id.social_login_button_google);
    }
    @Override
    protected void initializeView() {
        this.autologinCheckBox.setOnClickListener((v -> this.setAutoLogin()));
        this.signInButton.setOnClickListener(v->this.startActivity(new Intent(getContext(), SignInActivity.class)));
        this.pwFindButton.setOnClickListener(v->this.startActivity(new Intent(getContext(), FindPasswordActivity.class)));
        this.loginButton.setOnClickListener(v->this.retrofitLogin());
        this.googleLoginButton.setOnClickListener(v->this.googleLogin());
        this.kakaoLoginButton.setOnClickListener(v-> this.kakaoLogin());
        this.autologinCheckBox.setChecked(this.dataset.get(KatiEntity.EKatiData.AUTO_LOGIN).equals(KatiEntity.EKatiData.TRUE.name()));
    }
    @Override
    public void katiEntityUpdated() {
        this.checkLogined();
    }

    /**
     * Callback
     */
    private void setAutoLogin() {
        this.dataset.put(KatiEntity.EKatiData.AUTO_LOGIN,
                this.autologinCheckBox.isChecked()? KatiEntity.EKatiData.TRUE.name():KatiEntity.EKatiData.FALSE.name());
    }
    private void retrofitLogin(){
        String email = id.getText().toString();
        String password = pw.getText().toString();
        LoginRequest login = new LoginRequest(email, password);

        RetrofitClient retrofitClient = new RetrofitClient();

        Call<LoginRequest> call = retrofitClient.apiService.login(login);
        call.enqueue(new Callback<LoginRequest>() {
            @Override
            public void onResponse(Call<LoginRequest> call, Response<LoginRequest> response) {
                if (response.isSuccessful()) {
                    dataset.put(KatiEntity.EKatiData.AUTHORIZATION, response.headers().get("Authorization"));
                    dataset.put(KatiEntity.EKatiData.EMAIL, email);
                    dataset.put(KatiEntity.EKatiData.PASSWORD, password);
                    KatiDialog.simpleAlertDialog(getContext(),
                        getString(R.string.login_success_dialog),
                        getString(R.string.login_success_content_dialog),
                        (dialog, which) -> {startMainActivity();},
                        getResources().getColor(R.color.kati_coral, getContext().getTheme())
                    ).showDialog();
                } else {
                    KatiDialog.simpleAlertDialog(getContext(),
                        getString(R.string.login_failed_dialog),
                        getString(R.string.login_failed_content_dialog),
                        (dialog, which) -> { id.setText(""); pw.setText(""); },
                        getResources().getColor(R.color.kati_coral, getContext().getTheme())
                    ).showDialog();
                }
            }
            @Override public void onFailure(Call<LoginRequest> call, Throwable t) { Log.e("연결실패", t.getMessage()); }
        });
    }
    private void googleLogin() { }
    private void kakaoLogin() { }
    private void checkLogined() {
        if(this.dataset.containsKey(KatiEntity.EKatiData.AUTHORIZATION)){
            KatiDialog.simpleAlertDialog(getContext(),
                this.getString(R.string.login_already_signed_dialog),
                this.getString(R.string.login_already_signed_content_dialog),
                (dialog, which) -> this.startMainActivity(),
                this.getResources().getColor(R.color.kati_coral, this.getActivity().getTheme())
            ).showDialog();
        }
    }

    public void startMainActivity() { this.startActivity(new Intent(this.getContext(), TempMainActivity.class)); }
}