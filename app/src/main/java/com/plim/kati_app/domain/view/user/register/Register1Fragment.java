package com.plim.kati_app.domain.view.user.register;

import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.plim.kati_app.R;
import com.plim.kati_app.domain.asset.AbstractFragment1;
import com.plim.kati_app.domain.model.RegisterActivityViewModel;

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
        RegisterActivityViewModel registerActivityViewModel = new ViewModelProvider(this.requireActivity()).get(RegisterActivityViewModel.class);
        registerActivityViewModel.getUser().setEmail(this.editText.getText().toString());

        Navigation.findNavController(this.getView()).navigate(R.id.action_register1Fragment_to_register2Fragment);
    }
}