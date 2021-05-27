package com.plim.kati_app.kati.domain.old.temp.logout.view;

import android.view.View;

import com.plim.kati_app.R;
import com.plim.kati_app.kati.crossDomain.domain.model.Constant;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.KatiDialog;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.KatiViewModelFragment;
import com.plim.kati_app.kati.domain.old.TempMainActivity;

import java.util.Vector;

public class LogOutFragment extends KatiViewModelFragment {

    private Vector<KatiDialog> dialogs;

    public LogOutFragment() {
        this.dialogs= new Vector<>();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_log_out;
    }

    @Override
    protected void associateView(View view) {
    }

    @Override
    protected void initializeView() {

    }

    @Override
    public void onDestroy() {
        for(KatiDialog dialog:dialogs){
            dialog.dismiss();
            dialogs.remove(dialog);
        }
        super.onDestroy();
    }

    @Override
    public void katiEntityUpdated() {
        if (!this.dataset.get(KatiEntity.EKatiData.AUTHORIZATION).equals(KatiEntity.EKatiData.NULL.name())) {
            this.dataset.put(KatiEntity.EKatiData.AUTHORIZATION, KatiEntity.EKatiData.NULL.name());
            this.dataset.put(KatiEntity.EKatiData.EMAIL, KatiEntity.EKatiData.NULL.name());
            this.dataset.put(KatiEntity.EKatiData.PASSWORD, KatiEntity.EKatiData.NULL.name());
            this.dataset.put(KatiEntity.EKatiData.AUTO_LOGIN, KatiEntity.EKatiData.FALSE.name());
            this.showOkDialog();
        } else {
            this.showNoDialog();
        }
    }


    public void showOkDialog() {
        this.dialogs.add(this.showDialog(Constant.LOG_OUT_ACTIVITY_SUCCESSFUL_DIALOG_TITLE, Constant.LOG_OUT_ACTIVITY_SUCCESSFUL_DIALOG_MESSAGE));
    }

    public void showNoDialog() {
        this.dialogs.add(this.showDialog(Constant.LOG_OUT_ACTIVITY_FAILURE_DIALOG_TITLE, Constant.LOG_OUT_ACTIVITY_FAILURE_DIALOG_MESSAGE));
    }

    public KatiDialog showDialog(String title, String message) {
       return super.showDialog(title, message, (dialog, which) -> this.startActivity(TempMainActivity.class));
    }


}