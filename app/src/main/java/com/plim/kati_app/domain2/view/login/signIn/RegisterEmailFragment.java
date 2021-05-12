package com.plim.kati_app.domain2.view.login.signIn;

import android.view.View;

import com.plim.kati_app.R;
import com.plim.kati_app.domain.asset.AbstractFragment1;
import com.plim.kati_app.domain.asset.KatiDialog;
import com.plim.kati_app.domain2.katiCrossDomain.domain.model.forFrontend.KatiEntity;
import com.plim.kati_app.domain2.view.TempMainActivity;

public class RegisterEmailFragment extends AbstractFragment1 {

    /**
     * System Life Cycle Callback
     */
    @Override
    protected void initializeView() {
        super.initializeView();
        this.mainTextView.setText(getString(R.string.register_email_maintext));
        this.subTextView.setVisibility(View.INVISIBLE);
        this.editText.setHint(getString(R.string.register_email_hint));
        this.button.setText(getString(R.string.button_ok));
    }
    @Override
    protected void katiEntityUpdated() {
        if(this.dataset.containsKey(KatiEntity.EKatiData.AUTHORIZATION)){ this.showLoginedDialog(); }
    }

    /**
     * Callback
     */
    @Override
    protected void buttonClicked() {
        this.dataset.put(KatiEntity.EKatiData.EMAIL, this.editText.getText().toString());
        this.navigateTo(R.id.action_register1Fragment_to_register2Fragment);
    }

    /**
     * Method
     */
    private void showLoginedDialog(){
         KatiDialog.simplerAlertDialog(this.getActivity(),
            R.string.login_already_signed_dialog, R.string.login_already_signed_content_dialog,
            (dialog, which) -> this.startActivity(TempMainActivity.class)
        );
    }
}