package com.plim.kati_app.kati.crossDomain.domain.view.activity;

import com.plim.kati_app.kati.crossDomain.domain.view.activity.KatiLoginCheckViewModelActivity;
import com.plim.kati_app.R;
import com.plim.kati_app.kati.crossDomain.domain.view.etc.JSHToolBar;

public abstract class KatiHasTitleActivity extends KatiLoginCheckViewModelActivity {

    protected JSHToolBar titleBar;

    @Override
    protected void associateView() {
        this.titleBar = this.findViewById(R.id.toolbar);
    }

    @Override
    protected void initializeView() {
        if (this.getTitleContent() != null)
            this.titleBar.setToolBarTitle(this.getTitleContent());
        this.titleBar.setToolBarOnClickListener(v -> this.getOnBackTitleListener());
    }

    /**
     * method
     */
    protected String getTitleContent() {
        return null;
    }

    private void getOnBackTitleListener() {
        this.onBackPressed();
    }

}
