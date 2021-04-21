package com.plim.kati_app.domain.view.user.register;

import android.content.Intent;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.plim.kati_app.domain.model.RegisterActivityViewModel;
import com.plim.kati_app.domain.view.MainActivity;
import com.plim.kati_app.domain.asset.AbstractFragment1;
import com.plim.kati_app.domain.model.KatiViewModel;

public class RegisterFinishedFragment extends AbstractFragment1 {

    @Override
    protected void initializeView() {
        RegisterActivityViewModel registerActivityViewModel = new ViewModelProvider(this.requireActivity()).get(RegisterActivityViewModel.class);
        this.mainTextView.setText("환영합니다 "+registerActivityViewModel.getUser().getName()+"님!");
        this.subTextView.setText("메일을 확인하면 가입 절차가 끝납니다!");
        this.editText.setVisibility(View.INVISIBLE);
        this.button.setText("확인");
    }

    @Override
    protected void buttonClicked() {
        // 뭔진 몰라도 어디로 가든가 하것제
        this.startActivity(new Intent(this.getContext(), MainActivity.class));
    }
}