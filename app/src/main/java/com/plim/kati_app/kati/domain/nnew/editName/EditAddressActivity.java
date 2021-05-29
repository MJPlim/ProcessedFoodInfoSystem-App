package com.plim.kati_app.kati.domain.nnew.editName;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.plim.kati_app.R;

public class EditAddressActivity extends EditSingleActivity {

    @Override
    protected EditMode getEditMode() {
        return EditMode.address;
    }
}