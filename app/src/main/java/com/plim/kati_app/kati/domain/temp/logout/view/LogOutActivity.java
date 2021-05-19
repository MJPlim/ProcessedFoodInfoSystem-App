package com.plim.kati_app.kati.domain.temp.logout.view;

import android.os.Bundle;

import com.plim.kati_app.R;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.KatiDialog;
import com.plim.kati_app.kati.crossDomain.domain.model.Constant;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.kati.crossDomain.domain.view.activity.KatiViewModelActivity;
import com.plim.kati_app.kati.domain.TempMainActivity;

public class LogOutActivity extends KatiViewModelActivity {

    /**
     * System Life Cycle Callback
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_log_out);
    }
    @Override
    public void katiEntityUpdated() { }

    @Override protected void associateView() { }
    @Override protected void initializeView() { }
}