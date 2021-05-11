package com.plim.kati_app.domain.asset;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.ProgressBar;

import com.plim.kati_app.R;

/**
 * 로딩할 때 보여주는 로딩 다이얼로그 클래스.
 */
public class LoadingDialog extends Dialog {
    private ProgressBar progressBar;

    /**
     * 생성자
     */
    public LoadingDialog(Context context) {
        super(context);
        setContentView(R.layout.dialog_loading);
        this.setCancelable(false);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

}
