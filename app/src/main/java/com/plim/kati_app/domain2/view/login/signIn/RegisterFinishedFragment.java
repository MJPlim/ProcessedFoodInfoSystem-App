package com.plim.kati_app.domain2.view.login.signIn;

import android.view.View;

import com.plim.kati_app.R;
import com.plim.kati_app.domain.asset.AbstractFragment1;
import com.plim.kati_app.domain2.katiCrossDomain.domain.model.forFrontend.KatiEntity;
import com.plim.kati_app.domain2.view.TempMainActivity;

public class RegisterFinishedFragment extends AbstractFragment1 {

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