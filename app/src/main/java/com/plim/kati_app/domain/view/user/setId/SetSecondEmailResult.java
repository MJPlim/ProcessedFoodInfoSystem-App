package com.plim.kati_app.domain.view.user.setId;

import android.app.Fragment;
import android.content.Intent;
import android.view.View;

import com.plim.kati_app.R;
import com.plim.kati_app.domain.asset.AbstractFragment1;
import com.plim.kati_app.domain.view.MainActivity;

public class SetSecondEmailResult extends AbstractFragment1 {
    @Override
    protected void initializeView() {
        this.mainTextView.setText("복구 이메일 등록이 완료되었습니다.");
        this.subTextView.setVisibility(View.INVISIBLE);
        this.editText.setVisibility(View.INVISIBLE);
        this.button.setText("확인");
    }

    @Override
    protected void buttonClicked() {
        this.startActivity(new Intent(this.getContext(), MainActivity.class));
    }
}
