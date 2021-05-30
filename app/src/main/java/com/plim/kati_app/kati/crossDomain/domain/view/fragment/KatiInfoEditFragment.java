package com.plim.kati_app.kati.crossDomain.domain.view.fragment;

import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.plim.kati_app.kati.domain.nnew.main.myKati.myInfoEdit.model.InfoEditViewModel;
import com.plim.kati_app.kati.domain.nnew.main.myKati.myInfoEdit.model.UserInfoResponse;

public abstract class KatiInfoEditFragment extends KatiLoginCheckViewModelFragment{

    public InfoEditViewModel infoEditViewModel;
    protected UserInfoResponse userInfoResponse;

    private InfoObserver observer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.infoEditViewModel = new ViewModelProvider(this.getActivity()).get(InfoEditViewModel.class);
        this.userInfoResponse = infoEditViewModel.getUserInfoResponse().getValue();
        this.observer= new InfoObserver();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.infoEditViewModel.getUserInfoResponse().observe(this.getViewLifecycleOwner(),observer);
    }

    @Override
    public void onPause() {
        super.onPause();
        this.infoEditViewModel.getUserInfoResponse().removeObserver(observer);
        this.saveInfo();
    }

    public void saveInfo(){
        this.infoEditViewModel.getUserInfoResponse().setValue(this.userInfoResponse);
    }

    @Override
    protected boolean isLoginNeeded() {
        return true;
    }

    @Override
    protected void katiEntityUpdatedAndNoLogin() {
}

    public abstract void infoModelDataUpdated();


    private class InfoObserver implements Observer{


        @Override
        public void onChanged(Object o) {
            userInfoResponse=infoEditViewModel.getUserInfoResponse().getValue();
            if(dataset!=null)
            infoModelDataUpdated();
        }
    }

}
