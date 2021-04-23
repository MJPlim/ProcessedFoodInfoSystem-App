package com.plim.kati_app.domain.view.user.signOut;

import android.content.Intent;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.plim.kati_app.R;
import com.plim.kati_app.domain.model.room.KatiDatabase;
import com.plim.kati_app.domain.view.MainActivity;
import com.plim.kati_app.domain.asset.AbstractFragment1;
import com.plim.kati_app.domain.model.KatiViewModel;

public class WithdrawalResultFragment extends AbstractFragment1 {

    @Override
    protected void initializeView() { // 화면 초기화
        this.mainTextView.setText(this.getStringOfId(R.string.withdrawalResultFragment_mainTextView_thankYou));
        this.subTextView.setText( this.getStringOfId(R.string.withdrawalResultFragment_subTextView_withdrawalOk));
        this.editText.setVisibility(View.INVISIBLE);
        this.button.setText(this.getStringOfId(R.string.common_ok));
    }

    @Override
    protected void buttonClicked() { // 버튼 눌리면 메인 엑티비티 실행
        this.startActivity(new Intent(this.getContext(), MainActivity.class));
    }
}
