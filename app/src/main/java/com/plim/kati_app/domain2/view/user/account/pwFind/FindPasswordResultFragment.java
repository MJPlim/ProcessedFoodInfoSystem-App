package com.plim.kati_app.domain2.view.user.account.pwFind;

import android.content.Intent;

import android.view.View;

import com.plim.kati_app.domain.asset.AbstractFragment1;
import com.plim.kati_app.domain2.view.TempMainActivity;

public class FindPasswordResultFragment extends AbstractFragment1 {

    @Override
    protected void initializeView() {
        this.mainTextView.setText("임시 비밀번호가 이메일로 전송되었습니다.");
        this.subTextView.setVisibility(View.INVISIBLE);
        this.editText.setVisibility(View.INVISIBLE);
        this.button.setText("확인");
    }

    @Override
    protected void buttonClicked() {
        this.startActivity(new Intent(getActivity(), TempMainActivity.class));
    }
}