package com.plim.kati_app.jshCrossDomain.domain.view;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.plim.kati_app.jshCrossDomain.domain.model.viewModel.JSHViewModelTool;
import com.plim.kati_app.jshCrossDomain.domain.model.viewModel.ViewModelToolCallback;

public abstract class JSHViewModelFragment<T> extends Fragment implements ViewModelToolCallback {

    // Component
    private JSHViewModelTool<T> viewModelTool;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Create Component
        this.viewModelTool = new JSHViewModelTool<T>(this.getActivity(), this);
    }
    @Override
    public void onResume() {
        super.onResume();
        this.viewModelTool.startObserve();
    }
    @Override
    public void onPause() {
        super.onPause();
        this.viewModelTool.stopObserve();
    }
}
