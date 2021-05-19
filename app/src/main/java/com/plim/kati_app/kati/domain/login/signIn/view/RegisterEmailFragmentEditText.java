package com.plim.kati_app.kati.domain.login.signIn.view;

import android.view.View;

import com.plim.kati_app.R;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.KatiDialog;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.AbstractFragment_1EditText;
import com.plim.kati_app.kati.domain.TempMainActivity;

public class RegisterEmailFragmentEditText extends AbstractFragment_1EditText {

    // Working Variable
    boolean valueInput = false;

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
        super.katiEntityUpdated();
        if(this.dataset.containsKey(KatiEntity.EKatiData.AUTHORIZATION)){ this.showLoginedDialog(); }
        if(valueInput){this.navigateTo(R.id.action_register1Fragment_to_register2Fragment);}
    }

    /**
     * Callback
     */
    @Override
    protected void buttonClicked() {
        this.dataset.put(KatiEntity.EKatiData.EMAIL, this.editText.getText().toString());
        this.valueInput=true;
        this.save();
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