package com.plim.kati_app.kati.domain.temp.editData.userData.view;

import android.os.Bundle;

import com.plim.kati_app.R;
import com.plim.kati_app.kati.crossDomain.domain.view.activity.KatiViewModelActivity;

public class EditDataActivity extends KatiViewModelActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);
    }

    @Override
    protected void associateView() {
    }

    @Override
    protected void initializeView() {
    }

    @Override
    public void katiEntityUpdated() {
    }
}