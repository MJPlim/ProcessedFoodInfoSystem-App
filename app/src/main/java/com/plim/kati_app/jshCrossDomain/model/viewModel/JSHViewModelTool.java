package com.plim.kati_app.jshCrossDomain.model.viewModel;

import android.app.Activity;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

public class JSHViewModelTool {

    // Associate
        // View
        private Activity activity;
        // Model
        private JSHViewModel model;

    // Constructor
    public JSHViewModelTool(Activity activity) {
        // Associate View
        this.activity=activity;

        // Associate Model
        this.model = new ViewModelProvider((ViewModelStoreOwner) activity,
                ViewModelProvider.AndroidViewModelFactory.getInstance(activity.getApplication())).get(JSHViewModel.class);
    }
    public void startObserve(Observer observer){ this.model.getDataset().observe((LifecycleOwner) this.activity, observer); }
    public void stopObserve(){ this.model.getDataset().removeObservers((LifecycleOwner) this.activity); }
}

// JSH View Model 이 Android View Model 이 아니게 될 수 있는 경우 사용 // type=Class<T> type
    //        if(type.isAssignableFrom(AndroidViewModel.class)){
    //            this.model = (T) new ViewModelProvider((ViewModelStoreOwner) activity,
    //                    ViewModelProvider.AndroidViewModelFactory.getInstance(activity.getApplication())).get(type);
    //        }else{ // View Model
    //            this.model = (T) new ViewModelProvider((ViewModelStoreOwner) activity).get(type);
    //        }