package com.plim.kati_app.kati.domain.login.signIn.view;

import android.text.method.PasswordTransformationMethod;
import android.view.View;

import com.plim.kati_app.R;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.AbstractFragment_1EditText;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;

public class RegisterPasswordFragmentEditText extends AbstractFragment_1EditText {

    /**
     * System Life Cycle Callback
     */
    @Override
    protected void initializeView() {
        super.initializeView();
        this.editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        this.mainTextView.setText(getString(R.string.register_password_maintext));
        this.subTextView.setVisibility(View.INVISIBLE);
        this.editText.setHint(getString(R.string.register_password_hint));
        this.button.setText(getString(R.string.button_ok));
    }

    /**
     * Callback
     */
    @Override
    protected void buttonClicked() {
        this.dataset.put(KatiEntity.EKatiData.PASSWORD, this.editText.getText().toString());
        this.navigateTo(R.id.action_register2Fragment_to_register3Fragment);
    }
}