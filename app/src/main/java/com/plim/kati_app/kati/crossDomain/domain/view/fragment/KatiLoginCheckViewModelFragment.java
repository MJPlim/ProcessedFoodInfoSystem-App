package com.plim.kati_app.kati.crossDomain.domain.view.fragment;

import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.KatiDialog;
import com.plim.kati_app.kati.domain.login.login.view.LoginActivity;

public abstract class KatiLoginCheckViewModelFragment extends KatiViewModelFragment {
    @Override
    protected void katiEntityUpdated() {
        if (dataset.containsKey(KatiEntity.EKatiData.AUTHORIZATION))this.katiEntityUpdatedAndLogin();
        else this.katiEntityUpdatedAndNoLogin();
    }

    protected abstract void katiEntityUpdatedAndLogin();
    protected abstract void katiEntityUpdatedAndNoLogin();

    protected void notLoginDialog() {
        KatiDialog.NotLogInDialog(this.getActivity(), (dialog, which) ->{
            if(dataset.containsKey(KatiEntity.EKatiData.AUTHORIZATION)){
                dataset.remove(KatiEntity.EKatiData.AUTHORIZATION);
            }
            this.startActivity(LoginActivity.class);
        });
    }
}
