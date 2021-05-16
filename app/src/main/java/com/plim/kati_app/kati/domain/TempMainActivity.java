package com.plim.kati_app.kati.domain;

import android.os.Bundle;
import android.util.Log;

import com.plim.kati_app.R;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.KatiDialog;
import com.plim.kati_app.kati.crossDomain.domain.model.Constant;
import com.plim.kati_app.kati.domain.login.login.model.LoginRequest;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.kati.crossDomain.domain.view.activity.KatiViewModelActivity;
import com.plim.kati_app.kati.domain.search.search.view.FoodSearchActivity;
import com.plim.kati_app.kati.domain.login.login.view.LoginActivity;
import com.plim.kati_app.kati.domain.changePW.view.ChangePasswordActivity;
import com.plim.kati_app.kati.domain.temp.TempActivity;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitCallback;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitTool;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.KatiRetrofitTool;

import retrofit2.Response;

public class TempMainActivity extends KatiViewModelActivity {

    /**
     * System Life Cycle Callback
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override protected void associateView() { }
    @Override
    protected void initializeView() {
        this.findViewById(R.id.mainActivity_tempButton).setOnClickListener(v -> this.startActivity(TempActivity.class));
        this.findViewById(R.id.mainActivity_loginTestButton).setOnClickListener(v -> this.startActivity(LoginActivity.class));
        this.findViewById(R.id.mainActivity_searchTestButton).setOnClickListener(v -> this.startActivity(FoodSearchActivity.class));
        this.findViewById(R.id.mainActivity_changePWButton).setOnClickListener(v -> this.startActivity(ChangePasswordActivity.class));
    }
    @Override public void katiEntityUpdated() { this.autoLogin(); }
    @Override protected void onDestroy() { super.onDestroy(); this.autoLogout(); }

    /**
     * System Callback
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.showSystemOffCheckDialog();
    }
    private void autoLogin() {
        if(this.dataset.get(KatiEntity.EKatiData.AUTO_LOGIN).equals(KatiEntity.EKatiData.TRUE.name())){
            LoginRequest loginRequest = new LoginRequest(this.dataset.get(KatiEntity.EKatiData.EMAIL), this.dataset.get(KatiEntity.EKatiData.PASSWORD));
            KatiRetrofitTool.getAPIWithNullConverter().login(loginRequest).enqueue(JSHRetrofitTool.getCallback(new LoginRequestCallback()));
        }
    }
    private void autoLogout() {
        if(this.dataset.get(KatiEntity.EKatiData.AUTO_LOGIN).equals(KatiEntity.EKatiData.TRUE.name())
                && this.dataset.containsKey(KatiEntity.EKatiData.AUTHORIZATION)){
            this.dataset.remove(KatiEntity.EKatiData.AUTHORIZATION);
        }
    }

    /**
     * Callback
     */
    private class LoginRequestCallback implements JSHRetrofitCallback<LoginRequest> {
        @Override
        public void onSuccessResponse(Response<LoginRequest> response) {
            dataset.put(KatiEntity.EKatiData.AUTHORIZATION, response.headers().get("Authorization"));
        }
        @Override
        public void onFailResponse(Response<LoginRequest> response) {
            showLoginFailDialog();
        }
        @Override
        public void onConnectionFail(Throwable t) {
            Log.e("연결실패", t.getMessage());
        }
    }
    public void showSystemOffCheckDialog(){
        KatiDialog.simplerAlertDialog(this,
            Constant.MAIN_ACTIVITY_FINISH_DIALOG_TITLE, Constant.MAIN_ACTIVITY_FINISH_DIALOG_MESSAGE,
            (dialog, which) -> { this.finish(); this.finishAffinity();}
        );
    }

    private void showLoginFailDialog() {
        KatiDialog.simplerAlertDialog(this,
            Constant.AUTO_LOGIN_SERVICE_FAIL_DIALOG_TITLE, Constant.AUTO_LOGIN_SERVICE_FAIL_DIALOG_MESSAGE,
            null
        );
    }
}