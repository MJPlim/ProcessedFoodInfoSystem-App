package com.plim.kati_app.kati.crossDomain.domain.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import com.plim.kati_app.R;

public class LoadingDialog extends Dialog {

    // Constructor
    public LoadingDialog(Context context) {
        super(context);
        setContentView(R.layout.dialog_loading);
        this.setCancelable(false);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}
