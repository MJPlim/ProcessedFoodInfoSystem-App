package com.plim.kati_app.domain.view.login.pwFind;

import android.view.View;

import com.plim.kati_app.domain.katiCrossDomain.domain.view.AbstractFragment1;
import com.plim.kati_app.domain.view.TempMainActivity;

public class FindPasswordResultFragment extends AbstractFragment1 {

    /**
     * System Life Cycle Callback
     */
    @Override
    protected void initializeView() {
        super.initializeView();
        this.mainTextView.setText("임시 비밀번호가 이메일로 전송되었습니다.");
        this.subTextView.setVisibility(View.INVISIBLE);
        this.editText.setVisibility(View.INVISIBLE);
        this.button.setText("확인");
    }

    /**
     * Callback
     */
    @Override
    protected void buttonClicked() {
        this.startActivity(TempMainActivity.class);
    }
}