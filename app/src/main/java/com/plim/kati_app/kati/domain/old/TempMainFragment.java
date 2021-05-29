package com.plim.kati_app.kati.domain.old;

import android.app.Activity;

import android.view.View;

import com.plim.kati_app.R;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitTool;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.KatiViewModelFragment;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.KatiRetrofitTool;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.SimpleRetrofitCallBackImpl;
import com.plim.kati_app.kati.domain.nnew.login.LoginActivity;
//import com.plim.kati_app.kati.domain.old.changePW.view.ChangePasswordActivity;
import com.plim.kati_app.kati.domain.nnew.login.model.LoginResponse;

import com.plim.kati_app.kati.domain.old.temp.TempActivity;
import com.plim.kati_app.kati.domain.nnew.itemRank.view.RankingActivity;

import retrofit2.Response;

public class TempMainFragment extends KatiViewModelFragment {


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_temp_main;
    }

    @Override
    protected void associateView(View view) {
        view.findViewById(R.id.mainActivity_tempButton).setOnClickListener(v -> this.startActivity(TempActivity.class));
        view.findViewById(R.id.mainActivity_loginTestButton).setOnClickListener(v -> this.startActivity(LoginActivity.class));
//        view.findViewById(R.id.mainActivity_searchTestButton).setOnClickListener(v -> this.startActivity(FoodSearchTextActivity.class));
//        view.findViewById(R.id.mainActivity_changePWButton).setOnClickListener(v -> this.startActivity(ChangePasswordActivity.class));
        view.findViewById(R.id.mainActivity_RankingTestButton).setOnClickListener(v -> this.startActivity(RankingActivity.class));


    }

    @Override
    protected void initializeView() {}

    @Override
    protected void katiEntityUpdated() {
        this.autoLogin();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        this.autoLogout();
        this.save();
        super.onDestroy();
    }

    /**
     * Callback
     */
    private class LoginRequestCallback extends SimpleRetrofitCallBackImpl<LoginResponse> {
        public LoginRequestCallback(Activity activity) {
            super(activity);
        }

        @Override
        public void onSuccessResponse(Response<LoginResponse> response) {
        }

        @Override
        public void onResponse(Response<LoginResponse> response) {
            dataset.put(KatiEntity.EKatiData.AUTHORIZATION, response.headers().get("Authorization"));
        }
    }

    /**
     * method
     */
    private void autoLogin() {
        if (this.dataset.get(KatiEntity.EKatiData.AUTO_LOGIN).equals(KatiEntity.EKatiData.TRUE.name())) {
//            Log.d("디버그 자동로그인", "설정O");
            LoginResponse loginRequest = new LoginResponse(this.dataset.get(KatiEntity.EKatiData.EMAIL), this.dataset.get(KatiEntity.EKatiData.PASSWORD));
            KatiRetrofitTool.getAPIWithNullConverter().login(loginRequest).enqueue(JSHRetrofitTool.getCallback(new LoginRequestCallback(this.getActivity())));
        }
    }

    private void autoLogout() {
        this.dataset.put(KatiEntity.EKatiData.AUTHORIZATION, KatiEntity.EKatiData.NULL.name());

        if (this.dataset.get(KatiEntity.EKatiData.AUTO_LOGIN).equals(KatiEntity.EKatiData.FALSE.name())) {
//            Log.d("디버그 자동로그아웃", "시작");
            this.dataset.put(KatiEntity.EKatiData.EMAIL, KatiEntity.EKatiData.NULL.name());
            this.dataset.put(KatiEntity.EKatiData.PASSWORD, KatiEntity.EKatiData.NULL.name());
        }
    }

}