package com.plim.kati_app.domain2.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.plim.kati_app.domain2.model.KatiEntityTool;
import com.plim.kati_app.domain2.model.KatiEntity;
import com.plim.kati_app.jshCrossDomain.domain.model.room.entity.JSHEntity;
import com.plim.kati_app.jshCrossDomain.domain.view.JSHViewModelActivity;

import java.util.ArrayList;
import java.util.Map;

public abstract class KatiViewModelActivity extends JSHViewModelActivity {

    // Associate
        // Model
        protected KatiEntity entity;
        protected Map<String, String> dataset;
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

            this.katiEntityUpdated();
        }
    }

    public abstract void katiEntityUpdated();
}
