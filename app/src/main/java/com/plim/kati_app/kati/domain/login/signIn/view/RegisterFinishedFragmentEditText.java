package com.plim.kati_app.kati.domain.login.signIn.view;

import android.view.View;

import com.plim.kati_app.R;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.AbstractFragment_1EditText;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.kati.domain.TempMainActivity;

public class RegisterFinishedFragmentEditText extends AbstractFragment_1EditText {

    /**
     * System Life Cycle Callback
     */
    @Override
    protected void initializeView() {
        super.initializeView();
        this.mainTextView.setText(String.format(getString(R.string.welcome_start)+" "+this.dataset.get(KatiEntity.EKatiData.NAME)+getString(R.string.welcome_end)));
        this.subTextView.setText(getString(R.string.register_finish_subtext));
        this.editText.setVisibility(View.INVISIBLE);
        this.button.setText(getString(R.string.button_ok));
    }

    /**
     * Callback
     */
    @Override
    protected void buttonClicked() {
        this.startActivity(TempMainActivity.class);
    }
}