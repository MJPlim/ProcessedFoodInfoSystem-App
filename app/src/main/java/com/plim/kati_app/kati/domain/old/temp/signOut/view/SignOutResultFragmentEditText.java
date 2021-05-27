package com.plim.kati_app.kati.domain.old.temp.signOut.view;

import android.view.View;

import com.plim.kati_app.R;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.AbstractFragment_1EditText;
import com.plim.kati_app.kati.domain.old.TempMainActivity;

public class SignOutResultFragmentEditText extends AbstractFragment_1EditText {

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
