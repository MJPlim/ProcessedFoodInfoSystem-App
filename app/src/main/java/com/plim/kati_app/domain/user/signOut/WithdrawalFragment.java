package com.plim.kati_app.domain.user.signOut;

import android.content.Intent;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.plim.kati_app.domain.MainActivity;
import com.plim.kati_app.domain.asset.AbstractFragment1;
import com.plim.kati_app.domain.model.KatiViewModel;

public class WithdrawalFragment extends AbstractFragment1 {

    @Override
    protected void initializeView() {
        KatiViewModel katiViewModel = new ViewModelProvider(this.requireActivity()).get(KatiViewModel.class);
        String userName =  katiViewModel.getHeader();
        this.mainTextView.setText("그동안 고마웠어요, "+userName+"님!");
        this.subTextView.setText("성공적으로 탈퇴하였습니다");
        this.editText.setVisibility(View.INVISIBLE);
        this.button.setText("확인");
    }

    @Override
    protected void buttonClicked() {
        this.startActivity(new Intent(this.getContext(), MainActivity.class));
    }
}
