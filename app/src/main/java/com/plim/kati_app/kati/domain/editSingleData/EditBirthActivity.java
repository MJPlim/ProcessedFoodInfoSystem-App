package com.plim.kati_app.kati.domain.editSingleData;

import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.EditText;

import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.kati.crossDomain.domain.model.Constant.EEditSingleMode;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.BASIC_DATE_FORMAT;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.EDIT_SINGLE_DATA_BUTTON_TEXT_SUFFIX;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.EDIT_SINGLE_DATA_TITLE_SUFFIX;

public class EditBirthActivity extends EditSingleActivity {


    @Override
    protected void initializeView() {
        super.initializeView();
        this.editText.setHint(editMode.getText() + EDIT_SINGLE_DATA_BUTTON_TEXT_SUFFIX);

        this.editText.setFocusable(EditText.NOT_FOCUSABLE);
        this.editText.setCursorVisible(false);

        this.editText.setOnClickListener(v -> new DatePickerDialog(EditBirthActivity.this, (DatePicker view, int year, int month, int dayOfMonth) -> {
            this.dateSetter(year, month, dayOfMonth);
        }, 1980, 01, 01).show());

        this.submitButton.setEnabled(false);
        this.submitButton.setText(editMode.getText() + EDIT_SINGLE_DATA_TITLE_SUFFIX);

        this.submitButton.setOnClickListener(v -> this.modifyUserData(this.dataset.get(KatiEntity.EKatiData.AUTHORIZATION), this.editMode));

    }

    @Override
    protected EEditSingleMode getEditMode() {
        return EEditSingleMode.birth;
    }

    /**
     * method
     */
    private void dateSetter(int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        Date date = calendar.getTime();
        String tt = new SimpleDateFormat(BASIC_DATE_FORMAT).format(date);
        this.editText.setText(tt);
        this.submitButton.setEnabled(true);
    }

}