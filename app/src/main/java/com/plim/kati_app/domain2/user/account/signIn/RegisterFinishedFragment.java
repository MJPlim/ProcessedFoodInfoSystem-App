package com.plim.kati_app.domain2.user.account.signIn;

import android.content.Intent;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.plim.kati_app.R;
import com.plim.kati_app.domain.model.RegisterActivityViewModel;
import com.plim.kati_app.domain2.MainActivity;
import com.plim.kati_app.domain.asset.AbstractFragment1;

public class RegisterFinishedFragment extends AbstractFragment1 {

    @Override
    protected void initializeView() {
        RegisterActivityViewModel registerActivityViewModel = new ViewModelProvider(this.requireActivity()).get(RegisterActivityViewModel.class);
        this.mainTextView.setText(String.format("%s %s%s", getString(R.string.welcome_start), registerActivityViewModel.getUser().getName(), getString(R.string.welcome_end)));
        this.subTextView.setText(getString(R.string.register_finish_subtext));
        this.editText.setVisibility(View.INVISIBLE);
        this.button.setText(getString(R.string.button_ok));
    }

    @Override
    protected void buttonClicked() {
        // 뭔진 몰라도 어디로 가든가 하것제
        this.startActivity(new Intent(this.getContext(), MainActivity.class));
    }
}