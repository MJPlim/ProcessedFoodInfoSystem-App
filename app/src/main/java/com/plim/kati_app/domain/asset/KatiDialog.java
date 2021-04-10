package com.plim.kati_app.domain.asset;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;
import android.text.style.StyleSpan;

import androidx.annotation.ColorInt;

public class KatiDialog extends AlertDialog.Builder{

    // Attribute
    private @ColorInt int color;

    // Constructor
    public KatiDialog(Context context) {
        super(context);
    }

    public void setTitle(String titleContent){
        SpannableString title = new SpannableString(titleContent);
        title.setSpan(new StyleSpan(Typeface.BOLD), 0, title.length(),0);
        title.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, title.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        this.setTitle(title);
    }
    public void setColor(@ColorInt int color){ this.color=color; }

    public void showDialog(){
        AlertDialog dialog = this.create();
        dialog.setOnShowListener(arg0 -> {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(this.color);
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(this.color);
            dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(this.color);
        });
        dialog.show();
    }
}