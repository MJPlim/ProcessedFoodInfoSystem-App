package com.plim.kati_app.kati.domain.old;

import android.os.Bundle;

import com.plim.kati_app.R;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.KatiDialog;
import com.plim.kati_app.kati.crossDomain.domain.model.Constant;
import com.plim.kati_app.kati.crossDomain.domain.view.activity.KatiViewModelActivity;

public class TempMainActivity extends KatiViewModelActivity { // 이게 끝나긴 하네 3

    private KatiDialog dialog;

    /**
     * System Life Cycle Callback
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_old);
    }

    @Override
    protected void associateView() {
    }

    @Override
    protected void initializeView() {

    }

    @Override
    public void katiEntityUpdated() {
    }

    @Override
    public void onBackPressed() {
        this.showSystemOffCheckDialog();
    }

    public void showSystemOffCheckDialog() {
        this.dialog = KatiDialog.simplerAlertDialog(this,
                Constant.MAIN_ACTIVITY_FINISH_DIALOG_TITLE, Constant.MAIN_ACTIVITY_FINISH_DIALOG_MESSAGE,
                (dialog, which) -> {
                    this.finish();
                    this.finishAffinity();
                }
        );
    }
}