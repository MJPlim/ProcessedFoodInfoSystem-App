package com.plim.kati_app.kati.crossDomain.domain.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.navigation.Navigation;

import com.plim.kati_app.R;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntityTool;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.jshCrossDomain.domain.model.room.entity.JSHEntity;
import com.plim.kati_app.jshCrossDomain.domain.view.JSHViewModelActivity;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.KatiDialog;

import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

public abstract class KatiViewModelActivity extends JSHViewModelActivity {

    // Associate
        // Model
        protected KatiEntity entity;
        protected Map<KatiEntity.EKatiData, String> dataset;
        protected ArrayList<String> searchWords;

        protected Vector<KatiDialog> dialogVector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(this.getLayoutId());
        this.dialogVector= new Vector<>();
    }

    public void save() {
        KatiEntityTool.save(this.viewModelTool, this.entity);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for(KatiDialog dialog:dialogVector)
            dialog.dismiss();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.save();
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
            this.searchWords = this.entity.getSearchWords();

            this.associateView();
            this.initializeView();
            this.katiEntityUpdated();
        }
    }

    protected abstract int getLayoutId();

    protected abstract void associateView();
    protected abstract void initializeView();
    public abstract void katiEntityUpdated();

    protected String getStringOfId(int id) {
        return this.getResources().getString(id);
    }


    public void startActivity(Class<?> cls){ this.startActivity(new Intent(this, cls)); }
    protected KatiDialog showDialog(String title, String message, DialogInterface.OnClickListener listener){
        return KatiDialog.simplerAlertDialog(this,title,message,listener);
    }


}
