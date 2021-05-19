package com.plim.kati_app.jshCrossDomain.domain.view;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.plim.kati_app.jshCrossDomain.domain.model.room.entity.JSHEntity;
import com.plim.kati_app.jshCrossDomain.domain.model.viewModel.JSHViewModelTool;
import com.plim.kati_app.jshCrossDomain.domain.model.viewModel.ViewModelToolCallback;

import java.util.ArrayList;

public abstract class JSHViewModelFragment extends Fragment implements ViewModelToolCallback {

    // Component
    protected JSHViewModelTool viewModelTool;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Create Component
        this.viewModelTool = new JSHViewModelTool(this.getActivity(), this, this);
    }

    @Override
    public void onStart() {
        super.onStart();
        this.viewModelTool.startObserve();
    }

    @Override
    public void onResume() {
        super.onResume();
//        this.viewModelTool.startObserve();
    }
    @Override
    public void onPause() {
        super.onPause();
//        this.viewModelTool.stopObserve();
    }

    @Override
    public void onStop() {
        super.onStop();
        this.viewModelTool.stopObserve();
    }
}
