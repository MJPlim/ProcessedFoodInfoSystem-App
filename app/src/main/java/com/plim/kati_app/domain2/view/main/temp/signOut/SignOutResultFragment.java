package com.plim.kati_app.domain2.view.main.temp.signOut;

import android.view.View;

import com.plim.kati_app.R;
import com.plim.kati_app.domain.asset.AbstractFragment1;
import com.plim.kati_app.domain2.view.main.TempMainActivity;

public class SignOutResultFragment extends AbstractFragment1 {

    /**
     * System Life Cycle Callback
     */
    @Override
    protected void initializeView() {
        super.initializeView();
        this.mainTextView.setText(this.getStringOfId(R.string.withdrawalResultFragment_mainTextView_thankYou));
        this.subTextView.setText( this.getStringOfId(R.string.withdrawalResultFragment_subTextView_withdrawalOk));
        this.editText.setVisibility(View.INVISIBLE);
        this.button.setText(this.getStringOfId(R.string.common_ok));
    }

    /**
     * Callback
     */
    @Override
    protected void buttonClicked() {
        this.startActivity(TempMainActivity.class);
    }
}
