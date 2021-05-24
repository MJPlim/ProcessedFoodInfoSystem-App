package com.plim.kati_app.kati.domain.login.emailFind.view;

import android.content.Intent;
import android.view.View;

import com.plim.kati_app.R;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.AbstractFragment_1EditText;
import com.plim.kati_app.kati.domain.TempMainActivity;

public class FindEmailResultFragment extends AbstractFragment_1EditText {

    @Override
    protected void initializeView() {
        super.initializeView();
        this.mainTextView.setText(getString(R.string.find_user_email_dialog_title));
        this.subTextView.setVisibility(View.INVISIBLE);
        this.editText.setVisibility(View.INVISIBLE);
        this.button.setText("확인");
    }

    @Override
    protected void buttonClicked() {
        this.startActivity(TempMainActivity.class);
    }
}