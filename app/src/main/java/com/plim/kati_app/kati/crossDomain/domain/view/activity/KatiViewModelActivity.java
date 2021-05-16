package com.plim.kati_app.kati.crossDomain.domain.view.activity;

import android.content.Intent;

import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntityTool;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.jshCrossDomain.domain.model.room.entity.JSHEntity;
import com.plim.kati_app.jshCrossDomain.domain.view.JSHViewModelActivity;

import java.util.ArrayList;
import java.util.Map;

public abstract class KatiViewModelActivity extends JSHViewModelActivity {

    // Associate
        // Model
        protected KatiEntity entity;
        protected Map<KatiEntity.EKatiData, String> dataset;
        protected ArrayList<String> searchWords;

    @Override
    public void onPause() {
        super.onPause();
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
            this.searchWords = this.entity.getSearchWords();

            this.associateView();
            this.initializeView();
            this.katiEntityUpdated();
        }
    }

    protected abstract void associateView();
    protected abstract void initializeView();
    public abstract void katiEntityUpdated();

    public void startActivity(Class<?> cls){ this.startActivity(new Intent(this, cls)); }
}
