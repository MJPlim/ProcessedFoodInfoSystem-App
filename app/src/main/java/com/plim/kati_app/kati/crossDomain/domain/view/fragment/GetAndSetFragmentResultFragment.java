package com.plim.kati_app.kati.crossDomain.domain.view.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class GetAndSetFragmentResultFragment extends KatiViewModelFragment {

    @Override
    protected void initializeView() {
        this.requireActivity().getSupportFragmentManager().setFragmentResultListener(this.getFragmentRequestKey(),this.requireActivity(),(this::parseFragmentRequestBundle));
    }

    protected abstract String getFragmentRequestKey();
    protected abstract void parseFragmentRequestBundle(String requestKey, Bundle result);

    protected void sendFragmentResult(){
        this.requireActivity().getSupportFragmentManager().setFragmentResult(this.getFragmentResultKey(),this.getFragmentResultBundle());
    }

    protected abstract Bundle getFragmentResultBundle();
    protected abstract String getFragmentResultKey();


}
