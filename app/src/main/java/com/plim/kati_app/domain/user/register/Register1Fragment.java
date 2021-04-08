package com.plim.kati_app.domain.user.register;

import android.view.View;

import androidx.navigation.Navigation;

import com.plim.kati_app.R;
import com.plim.kati_app.domain.asset.AbstractFragment1;

public class Register1Fragment extends AbstractFragment1 {

    @Override
    protected void initializeView() {
        this.mainTextView.setText("이메일을 입력해주세요");
        this.subTextView.setVisibility(View.INVISIBLE);
        this.editText.setHint("example@plim.com");
        this.button.setText("확인");
    }

    @Override
    protected void buttonClicked() {
        Navigation.findNavController(this.getView()).navigate(R.id.action_register1Fragment_to_register2Fragment);
    }
}