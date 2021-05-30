package com.plim.kati_app.kati.domain.nnew.login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.plim.kati_app.R;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitCallback;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitTool;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.kati.crossDomain.domain.view.activity.KatiViewModelActivity;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.KatiDialog;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.KatiRetrofitTool;
import com.plim.kati_app.kati.domain.nnew.findId.FindIdActivity;
import com.plim.kati_app.kati.domain.nnew.findPassword.FindPasswordActivity;
import com.plim.kati_app.kati.domain.nnew.login.model.LoginResponse;
import com.plim.kati_app.kati.domain.nnew.main.MainActivity;
import com.plim.kati_app.kati.domain.nnew.signUp.SignUpActivity;

import java.util.Map;
import java.util.Vector;

import retrofit2.Response;

public class LoginActivity extends KatiViewModelActivity {

    TextView findId, findPw, signIn;
    private EditText emailAddress, password;
    private Button loginButton;
    private CheckBox autologinCheckBox;
    private Vector<KatiDialog> dialogs;

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        if (!(emailAddress.getText().toString().equals("")) && !(password.getText().toString().equals(""))){
            this.loginButton.setEnabled(true);
        }else{
            this.loginButton.setEnabled(false);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void associateView() {
        this.dialogs= new Vector<>();
        this.emailAddress = findViewById(R.id.editTextTextEmailAddress);
        this.password = findViewById(R.id.editTextTextPassword4);
        this.loginButton = findViewById(R.id.loginButton);
        this.autologinCheckBox= findViewById(R.id.loginActivity_autologinCheckBox);
        this.findId = this.findViewById(R.id.login_findId_textView);
        this.findPw = this.findViewById(R.id.login_findPW_textView);
        this.signIn = this.findViewById(R.id.login_signIn_textView);
    }

    @Override
    protected void initializeView() {
        findId.setOnClickListener(v->this.startActivity(new Intent(this, FindIdActivity.class)));
        findPw.setOnClickListener(v->this.startActivity(new Intent(this, FindPasswordActivity.class)));
        signIn.setOnClickListener(v->this.startActivity(new Intent(this, SignUpActivity.class)));
//        this.autologinCheckBox.setOnClickListener((v -> this.setAutoLogin()));
        this.loginButton.setOnClickListener(v->this.login());
        //      this.autologinCheckBox.setChecked(this.dataset.get(KatiEntity.EKatiData.AUTO_LOGIN).equals(KatiEntity.EKatiData.TRUE.name()));
    }

    @Override public void katiEntityUpdated() { }

    private void setAutoLogin() {
        this.dataset.put(KatiEntity.EKatiData.AUTO_LOGIN,
                this.autologinCheckBox.isChecked() ? KatiEntity.EKatiData.TRUE.name() : KatiEntity.EKatiData.FALSE.name());
    }

    private void login() {
        LoginResponse loginRequest = new LoginResponse(this.emailAddress.getText().toString(), this.password.getText().toString());
        KatiRetrofitTool.getAPIWithNullConverter().login(loginRequest).enqueue(JSHRetrofitTool.getCallback(new LoginRequestCallback()));
    }

    private class LoginRequestCallback implements JSHRetrofitCallback<LoginResponse> {
        @Override
        public void onSuccessResponse(Response<LoginResponse> response) {
            dataset.put(KatiEntity.EKatiData.AUTHORIZATION, response.headers().get("Authorization"));
            dataset.put(KatiEntity.EKatiData.EMAIL, emailAddress.getText().toString());
            dataset.put(KatiEntity.EKatiData.PASSWORD, password.getText().toString());
            save();
            dialogs.add( KatiDialog.simplerAlertDialog(LoginActivity.this,
                    R.string.login_success_dialog, R.string.login_success_content_dialog,
                    (dialog, which) -> startMainActivity()
            ));
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
            Log.e("연결실패", t.getMessage());
        }
    }

    public void startMainActivity() {
        this.startActivity(new Intent(this, MainActivity.class));
    }
}