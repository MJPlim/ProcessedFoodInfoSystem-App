package com.plim.kati_app.kati.domain.temp.logout.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.plim.kati_app.R;
import com.plim.kati_app.kati.crossDomain.domain.model.Constant;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.KatiDialog;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.KatiViewModelFragment;
import com.plim.kati_app.kati.domain.TempMainActivity;

public class LogOutFragment extends KatiViewModelFragment {

    public LogOutFragment() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_log_out;
    }

    @Override
    public void katiEntityUpdated() {
        if(this.dataset.containsKey(KatiEntity.EKatiData.AUTHORIZATION)){

            this.dataset.remove(KatiEntity.EKatiData.AUTHORIZATION);
            this.dataset.remove(KatiEntity.EKatiData.EMAIL);
            this.dataset.remove(KatiEntity.EKatiData.PASSWORD);
            this.dataset.put(KatiEntity.EKatiData.AUTO_LOGIN, KatiEntity.EKatiData.FALSE.name());
            this.save();
            Log.d("디버그","외않돼!!"+this.dataset.containsKey(KatiEntity.EKatiData.AUTHORIZATION));
            this.showOkDialog();
        }else{
            this.showNoDialog();
        }
    }
    public void showOkDialog(){ this.showDialog(Constant.LOG_OUT_ACTIVITY_SUCCESSFUL_DIALOG_TITLE, Constant.LOG_OUT_ACTIVITY_SUCCESSFUL_DIALOG_MESSAGE); }
    public void showNoDialog(){ this.showDialog(Constant.LOG_OUT_ACTIVITY_FAILURE_DIALOG_TITLE, Constant.LOG_OUT_ACTIVITY_FAILURE_DIALOG_MESSAGE); }
    public void showDialog(String title, String message){ super.showDialog(title, message, (dialog, which) -> this.startActivity(TempMainActivity.class)); }

    @Override
    protected void associateView(View view) {
    }

    @Override
    protected void initializeView() {
    }
}