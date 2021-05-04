package com.plim.kati_app.domain2.view.user.account.signIn;

import android.text.method.PasswordTransformationMethod;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.plim.kati_app.R;
import com.plim.kati_app.domain.asset.AbstractFragment1;
import com.plim.kati_app.domain.model.RegisterActivityViewModel;

public class RegisterPasswordFragment extends AbstractFragment1 {

    @Override
    protected void initializeView() {
        this.editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        this.mainTextView.setText(getString(R.string.register_password_maintext));
        this.subTextView.setVisibility(View.INVISIBLE);
        this.editText.setHint(getString(R.string.register_password_hint));
        this.button.setText(getString(R.string.button_ok));
    }

    @Override
    protected void buttonClicked() {
        RegisterActivityViewModel registerActivityViewModel = new ViewModelProvider(this.requireActivity()).get(RegisterActivityViewModel.class);
        registerActivityViewModel.getUser().setPassword(this.editText.getText().toString());

        Navigation.findNavController(this.getView()).navigate(R.id.action_register2Fragment_to_register3Fragment);
    }
}