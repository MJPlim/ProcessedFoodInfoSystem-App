package com.plim.kati_app.kati.domain;

import android.os.Bundle;
import android.util.Log;

import com.plim.kati_app.R;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.KatiDialog;
import com.plim.kati_app.kati.crossDomain.domain.model.Constant;
import com.plim.kati_app.kati.domain.login.login.model.LoginRequest;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.kati.crossDomain.domain.view.activity.KatiViewModelActivity;
import com.plim.kati_app.kati.domain.mypage.main.view.UserMyPageActivity;
import com.plim.kati_app.kati.domain.search.search.view.FoodSearchActivity;
import com.plim.kati_app.kati.domain.login.login.view.LoginActivity;
import com.plim.kati_app.kati.domain.changePW.view.ChangePasswordActivity;
import com.plim.kati_app.kati.domain.temp.TempActivity;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitCallback;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitTool;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.KatiRetrofitTool;
import com.plim.kati_app.kati.domain.temp.itemRank.view.RankingActivity;

import retrofit2.Response;

public class TempMainActivity extends KatiViewModelActivity { // 이게 끝나긴 하네 3

    private KatiDialog dialog;

    /**
     * System Life Cycle Callback
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void associateView() {
    }

    @Override
    protected void initializeView() {

    }

    @Override
    public void katiEntityUpdated() {
    }

    @Override
    public void onBackPressed() {
        this.showSystemOffCheckDialog();
    }

    public void showSystemOffCheckDialog() {
        this.dialog = KatiDialog.simplerAlertDialog(this,
                Constant.MAIN_ACTIVITY_FINISH_DIALOG_TITLE, Constant.MAIN_ACTIVITY_FINISH_DIALOG_MESSAGE,
                (dialog, which) -> {
                    this.finish();
                    this.finishAffinity();
                }
        );
    }
}