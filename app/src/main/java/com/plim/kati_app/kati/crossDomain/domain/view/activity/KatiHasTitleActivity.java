package com.plim.kati_app.kati.crossDomain.domain.view.activity;

import com.plim.kati_app.R;
import com.plim.kati_app.kati.crossDomain.domain.view.etc.JSHToolBar;

public abstract class KatiHasTitleActivity extends KatiViewModelActivity{

    protected JSHToolBar titleBar;

    @Override
    protected void associateView() {
        this.titleBar=this.findViewById(R.id.toolbar);
    }

    @Override
    protected void initializeView() {
        this.titleBar.setToolBarTitle(this.getTitleContent());
        this.titleBar.setToolBarOnClickListener(v -> this.getOnBackTitleListener());
    }

    @Override
    public void katiEntityUpdated() {

    }

    /**
     * method
     */
    protected abstract String getTitleContent();

    private void getOnBackTitleListener(){this.onBackPressed();};
}
