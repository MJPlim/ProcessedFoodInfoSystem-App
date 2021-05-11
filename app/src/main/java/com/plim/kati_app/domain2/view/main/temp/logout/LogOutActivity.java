package com.plim.kati_app.domain2.view.main.temp.logout;

import android.os.Bundle;

import com.plim.kati_app.R;
import com.plim.kati_app.domain.asset.KatiDialog;
import com.plim.kati_app.domain.constant.Constant_yun;
import com.plim.kati_app.domain2.model.forFrontend.KatiEntity;
import com.plim.kati_app.domain2.view.KatiViewModelActivity;
import com.plim.kati_app.domain2.view.main.TempMainActivity;

public class LogOutActivity extends KatiViewModelActivity {

    /**
     * System Life Cycle Callback
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_log_out);
    }
    @Override
    public void katiEntityUpdated() {
        if(this.dataset.containsKey(KatiEntity.EKatiData.AUTHORIZATION)){
            this.dataset.remove(KatiEntity.EKatiData.AUTHORIZATION);
            this.dataset.remove(KatiEntity.EKatiData.EMAIL);
            this.dataset.remove(KatiEntity.EKatiData.PASSWORD);
            this.dataset.put(KatiEntity.EKatiData.AUTO_LOGIN, KatiEntity.EKatiData.FALSE.name());
            this.showOkDialog();
        }else{
            this.showNoDialog();
        }
    }
    public void showOkDialog(){ this.showDialog(Constant_yun.LOG_OUT_ACTIVITY_SUCCESSFUL_DIALOG_TITLE, Constant_yun.LOG_OUT_ACTIVITY_SUCCESSFUL_DIALOG_MESSAGE); }
    public void showNoDialog(){ this.showDialog(Constant_yun.LOG_OUT_ACTIVITY_FAILURE_DIALOG_TITLE, Constant_yun.LOG_OUT_ACTIVITY_FAILURE_DIALOG_MESSAGE); }
    public void showDialog(String title, String message){ KatiDialog.simplerAlertDialog(this, title, message, (dialog, which) -> this.startActivity(TempMainActivity.class)); }

    @Override protected void associateView() { }
    @Override protected void initializeView() { }
}