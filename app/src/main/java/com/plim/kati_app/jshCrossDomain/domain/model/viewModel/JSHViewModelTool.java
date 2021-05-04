package com.plim.kati_app.jshCrossDomain.domain.model.viewModel;

import android.app.Activity;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.plim.kati_app.jshCrossDomain.domain.model.room.entity.JSHEntity;

import java.util.ArrayList;
import java.util.List;

public class JSHViewModelTool<T> implements Observer {

    // Associate
        // View
        private Activity activity;
        // Model
        protected JSHViewModel model;
        private ViewModelToolCallback callback;
        private ArrayList<T> domainEntities;

    // Constructor
    public JSHViewModelTool(Activity activity, ViewModelToolCallback callback) {
        // Associate View
        this.activity=activity;

        // Associate Model
        this.model = new ViewModelProvider((ViewModelStoreOwner) activity,
                ViewModelProvider.AndroidViewModelFactory.getInstance(activity.getApplication())).get(JSHViewModel.class);
        this.callback=callback;
    }
    public void startObserve(){ this.model.getDataset().observe((LifecycleOwner) this.activity, this); }
    public void stopObserve(){ this.model.getDataset().removeObservers((LifecycleOwner) this.activity); }

    @Override
    public void onChanged(Object o) {
        this.convertJSHEntityToDomainEntity();
        this.callback.viewModelDataUpdated();
    }
    private void convertJSHEntityToDomainEntity(){
        this.domainEntities.clear();
        List<JSHEntity> jshEntities = this.model.getDataset().getValue();
        for(JSHEntity jshEntity : jshEntities){
            this.domainEntities.add(jshEntity.getEntity());
        }
    }

    public ArrayList<T> getDomainEntities(){
        return this.domainEntities;
    }
}

// JSH View Model 이 Android View Model 이 아니게 될 수 있는 경우 사용 // type=Class<T> type
    //        if(type.isAssignableFrom(AndroidViewModel.class)){
    //            this.model = (T) new ViewModelProvider((ViewModelStoreOwner) activity,
    //                    ViewModelProvider.AndroidViewModelFactory.getInstance(activity.getApplication())).get(type);
    //        }else{ // View Model
    //            this.model = (T) new ViewModelProvider((ViewModelStoreOwner) activity).get(type);
    //        }