package com.plim.kati_app.kati.domain.editSingleData;

import com.plim.kati_app.kati.crossDomain.domain.model.Constant.EEditSingleMode;

public class EditNameActivity extends EditSingleActivity {

    @Override
    protected EEditSingleMode getEditMode() {
        return EEditSingleMode.name;
    }
}