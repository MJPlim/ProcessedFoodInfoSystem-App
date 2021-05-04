package com.plim.kati_app.jshCrossDomain.domain.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.plim.kati_app.jshCrossDomain.domain.model.viewModel.JSHViewModelTool;
import com.plim.kati_app.jshCrossDomain.domain.model.viewModel.ViewModelToolCallback;

import java.util.ArrayList;

public abstract class JSHViewModelActivity<T> extends AppCompatActivity implements ViewModelToolCallback {

    // Component
    private JSHViewModelTool<T> viewModelTool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create Component
        this.viewModelTool = new JSHViewModelTool<>(this, this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        this.viewModelTool.startObserve();
    }
    @Override
    protected void onPause() {
        super.onPause();
        this.viewModelTool.stopObserve();
    }
    public ArrayList<T> getDomainEntities(){
        return this.viewModelTool.getDomainEntities();
    }
}
