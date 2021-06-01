package com.plim.kati_app.kati.domain.nnew.editName;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.plim.kati_app.R;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class EditBirthActivity extends EditSingleActivity {


    @Override
    protected void initializeView() {
        this.toolBar.setToolBarTitle(editMode.getText()+" 변경");
        this.editText.setHint(editMode.getText()+" 입력");

        this.editText.setFocusable(EditText.NOT_FOCUSABLE);
        this.editText.setCursorVisible(false);


        editText.setOnClickListener(v -> new DatePickerDialog(EditBirthActivity.this, (DatePicker view, int year, int month, int dayOfMonth)-> {
        Calendar calendar= Calendar.getInstance();
            calendar.set(Calendar.YEAR,year);
            calendar.set(Calendar.MONTH,month);
            calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
            Date date =calendar.getTime();
            LocalDate day= LocalDate.of(year,month,dayOfMonth);
            String tt= new SimpleDateFormat("yyyy-MM-dd").format(date);
            this.editText.setText(tt);
            this.submitButton.setEnabled(true);

        }, 1990, 01, 01).show());

        this.submitButton.setEnabled(false);
        this.submitButton.setText(editMode.getText()+" 변경");

        this.submitButton.setOnClickListener(v -> this.modifyUserData(this.dataset.get(KatiEntity.EKatiData.AUTHORIZATION),this.editMode));

    }

    @Override
    protected EditMode getEditMode() {
        return EditMode.birth;
    }
}