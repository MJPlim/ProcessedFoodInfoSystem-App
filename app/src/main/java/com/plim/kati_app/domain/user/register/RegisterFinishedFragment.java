package com.plim.kati_app.domain.user.register;

import android.content.Intent;
import android.view.View;

import com.plim.kati_app.domain.MainActivity;
import com.plim.kati_app.domain.asset.AbstractFragment1;

public class RegisterFinishedFragment extends AbstractFragment1 {

    @Override
    protected void initializeView() {
        String userName = this.getArguments().getString("userName");
        this.mainTextView.setText("환영합니다 "+userName+"님!");
        this.subTextView.setText("가입 절차가 끝났어요!");
        this.editText.setVisibility(View.INVISIBLE);
        this.button.setText("확인");
    }

    @Override
    protected void buttonClicked() {
        // 뭔진 몰라도 어디로 가든가 하것제
        this.startActivity(new Intent(this.getContext(), MainActivity.class));
    }
}