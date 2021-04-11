package com.plim.kati_app.domain.view.user.register;

import android.text.method.PasswordTransformationMethod;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.plim.kati_app.R;
import com.plim.kati_app.domain.asset.AbstractFragment1;
import com.plim.kati_app.domain.model.RegisterActivityViewModel;

public class Register2Fragment extends AbstractFragment1 {

    @Override
    protected void initializeView() {
        this.editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        this.mainTextView.setText("비밀번호를 입력해주세요");
        this.subTextView.setVisibility(View.INVISIBLE);
        this.editText.setHint("6자리 이상, 숫자+알파벳 조합");
        this.button.setText("확인");
    }

    @Override
    protected void buttonClicked() {
        RegisterActivityViewModel registerActivityViewModel = new ViewModelProvider(this.requireActivity()).get(RegisterActivityViewModel.class);
        registerActivityViewModel.getUser().setPassword(this.editText.getText().toString());

        Navigation.findNavController(this.getView()).navigate(R.id.action_register2Fragment_to_register3Fragment);
    }
}