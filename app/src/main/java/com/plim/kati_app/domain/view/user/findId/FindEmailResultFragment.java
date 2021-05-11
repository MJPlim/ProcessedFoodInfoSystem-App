package com.plim.kati_app.domain.view.user.findId;

import android.content.Intent;
import android.view.View;

import com.plim.kati_app.R;
import com.plim.kati_app.domain.asset.AbstractFragment1;
import com.plim.kati_app.domain.view.MainActivity;

public class FindEmailResultFragment extends AbstractFragment1 {

    @Override
    protected void initializeView() {
        this.mainTextView.setText(getString(R.string.find_user_email_dialog_title));
        this.subTextView.setVisibility(View.INVISIBLE);
        this.editText.setVisibility(View.INVISIBLE);
        this.button.setText("확인");
    }

    @Override
    protected void buttonClicked() {
        this.startActivity(new Intent(getActivity(), MainActivity.class));
    }
}