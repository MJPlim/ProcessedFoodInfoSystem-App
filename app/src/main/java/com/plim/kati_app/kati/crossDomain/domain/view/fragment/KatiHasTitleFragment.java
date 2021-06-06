package com.plim.kati_app.kati.crossDomain.domain.view.fragment;

import android.view.View;

import com.plim.kati_app.R;
import com.plim.kati_app.kati.crossDomain.domain.view.activity.KatiViewModelActivity;
import com.plim.kati_app.kati.crossDomain.domain.view.etc.JSHToolBar;

public abstract class KatiHasTitleFragment extends KatiViewModelFragment {

    protected JSHToolBar titleBar;

    @Override
    protected void associateView(View view) {
        this.titleBar = view.findViewById(R.id.toolbar);
    }

    @Override
    protected void initializeView() {
        if (this.getTitleContent() != null)
            this.titleBar.setToolBarTitle(this.getTitleContent());
        this.titleBar.setToolBarOnClickListener(v -> this.getOnBackTitleListener());
    }

    @Override
    public void katiEntityUpdated() {

    }

    /**
     * method
     */
    protected String getTitleContent() {
        return null;
    }

    private void getOnBackTitleListener() {
        this.getActivity().onBackPressed();
    }

    ;
}
