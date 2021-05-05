package com.plim.kati_app.domain2.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.plim.kati_app.domain2.model.KatiEntityTool;
import com.plim.kati_app.domain2.model.KatiEntity;
import com.plim.kati_app.jshCrossDomain.domain.model.room.entity.JSHEntity;
import com.plim.kati_app.jshCrossDomain.domain.view.JSHViewModelFragment;

import java.util.ArrayList;
import java.util.Map;

public abstract class KatiViewModelFragment extends JSHViewModelFragment {

    // Associate
        // Model
        protected KatiEntity entity;
        protected Map<String, String> dataset;
        protected ArrayList<String> searchWords;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(this.getLayoutId(), container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.associateView(view);
    }
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

            this.initializeView();
            this.katiEntityUpdated();
        }
    }

    protected abstract int getLayoutId();
    protected abstract void associateView(View view);
    protected abstract void initializeView();
    protected abstract void katiEntityUpdated();
}
