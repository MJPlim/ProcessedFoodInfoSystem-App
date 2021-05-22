package com.plim.kati_app.kati.crossDomain.domain.view.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.navigation.Navigation;

import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntityTool;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.jshCrossDomain.domain.model.room.entity.JSHEntity;
import com.plim.kati_app.jshCrossDomain.domain.view.JSHViewModelFragment;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.KatiDialog;
import com.plim.kati_app.kati.domain.login.login.view.LoginActivity;

import java.util.ArrayList;
import java.util.Map;

public abstract class KatiViewModelFragment extends JSHViewModelFragment {

    // Associate
        // Model
        protected KatiEntity entity;
        protected Map<KatiEntity.EKatiData, String> dataset;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(this.getLayoutId(), container, false);
    }
    @Override
    public void onPause() {
        super.onPause();
        KatiEntityTool.save(this.viewModelTool, this.entity);
    }
    public void save(){
        KatiEntityTool.save(this.viewModelTool, this.entity);
    }
    @Override
    public void viewModelDataUpdated() {
        ArrayList<KatiEntity> katiEntityArray = KatiEntityTool.convertJSHEntityArrayToDomainArray(this.viewModelTool.getJSHEntities());

        if(katiEntityArray.size()==0){
            JSHEntity jshEntity = new JSHEntity();
            jshEntity.setEntityString(KatiEntityTool.fromKatiEntityToString(new KatiEntity()));
            this.viewModelTool.getModel().insert(jshEntity);
        }else{
            this.entity = katiEntityArray.get(0);
            this.dataset = this.entity.getDataset();

            this.associateView(this.getView());
            this.initializeView();
            this.katiEntityUpdated();
        }
    }

    protected abstract int getLayoutId();
    protected abstract void associateView(View view);
    protected abstract void initializeView();
    protected abstract void katiEntityUpdated();

    public void startActivity(Class<?> cls){ this.startActivity(new Intent(this.getContext(), cls)); }
    protected void navigateTo(int id){ Navigation.findNavController(this.getView()).navigate(id); }
    protected String getStringOfId(int id){ return this.getResources().getString(id); }


    protected void showDialog(String title, String message, DialogInterface.OnClickListener listener){
        KatiDialog.simplerAlertDialog(this.getActivity(),title,message,listener);
    }

}
