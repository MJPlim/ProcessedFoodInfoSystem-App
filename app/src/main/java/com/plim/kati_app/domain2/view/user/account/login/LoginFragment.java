package com.plim.kati_app.domain2.view.user.account.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.plim.kati_app.R;
import com.plim.kati_app.domain.asset.KatiDialog;
import com.plim.kati_app.domain.model.room.KatiData;
import com.plim.kati_app.domain.model.room.KatiDatabase;
import com.plim.kati_app.domain2.view.TempMainActivity;
import com.plim.kati_app.domain2.view.user.account.pwFind.FindPasswordActivity;
import com.plim.kati_app.domain2.view.user.account.signIn.RegisterActivity;
import com.plim.kati_app.jshCrossDomain.domain.view.JSHViewModelFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends JSHViewModelFragment {

    // Associate
        // View
        private EditText id, pw;
        private Button loginButton, idFindButton, pwFindButton, signInButton, kakaoLoginButton, googleLoginButton;
        private CheckBox autologinCheckBox;

    /**
     * System Callback
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_login_home_layout, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Associate View
        this.id = view.findViewById(R.id.loginActivity_idText);
        this.pw = view.findViewById(R.id.loginActivity_pwText);
        this.loginButton = view.findViewById(R.id.loginActivity_loginButton);
        this.idFindButton = view.findViewById(R.id.loginActivity_idFindButton);
        this.pwFindButton = view.findViewById(R.id.loginActivity_pwFindButton);
        this.signInButton = view.findViewById(R.id.loginActivity_accountCreateButton);
        this.autologinCheckBox=view.findViewById(R.id.loginActivity_autologinCheckBox);
        this.kakaoLoginButton = view.findViewById(R.id.social_login_button_kakao);
        this.googleLoginButton = view.findViewById(R.id.social_login_button_google);

        new Thread(() -> {
            KatiDatabase database = KatiDatabase.getAppDatabase(this.getContext());
            if (database.katiDataDao().getValue(KatiDatabase.AUTHORIZATION) != null) {
                getActivity().runOnUiThread(() ->
                        KatiDialog.simpleAlertDialog(getContext(),getString(R.string.login_already_signed_dialog),getString(R.string.login_already_signed_content_dialog),(dialog, which) -> startMainActivity(),
                                this.getResources().getColor(R.color.kati_coral, getActivity().getTheme())).showDialog()
                );
            }
        }).start();

        this.autologinCheckBox.setOnClickListener((v -> {
            this.setAutoLogin();
        }));
         /*
         * 로그인 버튼 클릭
         * 401 -> 인증 실패
         * 500 -> 서버 오류
         * 200 -> 인증 성공
         * */
        View.OnClickListener onClickListener = v -> {
            switch (v.getId()){
                case R.id.loginActivity_accountCreateButton:
                    startActivity(new Intent(getContext(), RegisterActivity.class));
                    break;
                case R.id.loginActivity_pwFindButton:
                    startActivity(new Intent(getContext(), FindPasswordActivity.class));
                case R.id.loginActivity_loginButton:
                    retrofitLogin();
                    break;
                case R.id.social_login_button_google:
                    googleLogin();
                    break;
                case R.id.social_login_button_kakao:
                    kakaoLogin();
                    break;
            }
        };

        this.signInButton.setOnClickListener(onClickListener);
        this.pwFindButton.setOnClickListener(onClickListener);
        this.loginButton.setOnClickListener(onClickListener);
        this.googleLoginButton.setOnClickListener(onClickListener);
        this.kakaoLoginButton.setOnClickListener(onClickListener);
    }

    private void kakaoLogin() {
    }

    private void googleLogin() {
        Log.d("로그인", "구글 로그인 시작");
        Intent intent = new Intent(getContext(), GoogleLoginActivity.class);
        startActivity(intent);
    }

    /**
     * 로그인 요청.
     */
    private void retrofitLogin(){

        String email = id.getText().toString();
        String password = pw.getText().toString();

        LoginRequest login = new LoginRequest(email, password);
        RetrofitClient retrofitClient = new RetrofitClient();
        Call<LoginRequest> call = retrofitClient.apiService.postRetrofitData(login);
        call.enqueue(new Callback<LoginRequest>() {
            @Override
            public void onResponse(Call<LoginRequest> call, Response<LoginRequest> response) {
                if (response.isSuccessful()) {
                    Log.d("연결 성공적 : ", "code : " + response.code());

                    KatiDialog.simpleAlertDialog(
                            getContext(),
                            getString(R.string.login_success_dialog),
                            getString(R.string.login_success_content_dialog),
                            (dialog, which) -> {
                                //헤더값에서 토큰값을 꺼내서 넣기.
                                KatiDatabase database = KatiDatabase.getAppDatabase(getContext());
                                new Thread(() -> {

                                    database.katiDataDao().insert(new KatiData(KatiDatabase.AUTHORIZATION, response.headers().get(KatiDatabase.AUTHORIZATION)));
                                    if(autologinCheckBox.isChecked()){
                                        database.katiDataDao().insert(new KatiData(KatiDatabase.EMAIL, id.getText().toString()));
                                        database.katiDataDao().insert(new KatiData(KatiDatabase.PASSWORD, pw.getText().toString()));
                                    }

                                }).start();
                                startMainActivity();
                            }, getResources().getColor(R.color.kati_coral, getContext().getTheme())).showDialog();

                } else {
                    Log.e("연결 비정상적 : ", "error code : " + response.code());
                    KatiDialog.simpleAlertDialog(getContext(),
                            getString(R.string.login_failed_dialog),
                            getString(R.string.login_failed_content_dialog),
                            (dialog, which) -> {
                                id.setText("");
                                pw.setText("");
                            }
                            , getResources().getColor(R.color.kati_coral, getContext().getTheme())).showDialog();
                    return;
                }
            }

            @Override
            public void onFailure(Call<LoginRequest> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });
    }

    /**
     * 오토로그인을 하도록 혹은 안하도록 설정값을 저장한다.
     */
    private void setAutoLogin() {
        new Thread(()->{
            KatiDatabase katiDatabase=KatiDatabase.getAppDatabase(getContext());
            String data=this.autologinCheckBox.isChecked()?"1":"0";
            katiDatabase.katiDataDao().insert(new KatiData(KatiDatabase.AUTO_LOGIN,data));
            Log.d("카티 데이터 오토 로그인",data);
        }).start();
    }

    /**
     * 메인 액티비티를 시작한다.
     */
    public void startMainActivity() {
        Intent intent = new Intent(getContext(), TempMainActivity.class);
        startActivity(intent);
    }

    @Override
    public void viewModelDataUpdated() {

    }
}