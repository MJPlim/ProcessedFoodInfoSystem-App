package com.plim.kati_app.kati.domain.old.temp.setSecondEmail.view;

import android.view.View;

import com.plim.kati_app.kati.crossDomain.domain.view.fragment.AbstractFragment_1EditText;
import com.plim.kati_app.kati.domain.old.TempMainActivity;

public class SetSecondEmailResult extends AbstractFragment_1EditText {
    @Override
    protected void initializeView() {
        super.initializeView();
        this.mainTextView.setText("복구 이메일 등록이 완료되었습니다.");
        this.subTextView.setVisibility(View.INVISIBLE);
        this.editText.setVisibility(View.INVISIBLE);
        this.button.setText("확인");
    }

    @Override
    protected void buttonClicked() {
        this.startActivity(TempMainActivity.class);
    }
}
