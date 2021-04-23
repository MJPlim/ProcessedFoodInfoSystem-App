package com.plim.kati_app.domain.asset;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;
import android.text.style.StyleSpan;
import android.view.View;
import androidx.annotation.ColorInt;
import com.plim.kati_app.R;
import static android.os.Build.VERSION_CODES.R;


public class KatiDialog extends AlertDialog.Builder{

    // Attribute
    private @ColorInt int color;

    // Constructor
    public KatiDialog(Context context) {
        super(context);
    }

    public void setTitle(String titleContent){ // 제목 설정
        SpannableString title = new SpannableString(titleContent);
        title.setSpan(new StyleSpan(Typeface.BOLD), 0, title.length(),0);
        title.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, title.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        this.setTitle(title);
    }
    public void setColor(@ColorInt int color){ this.color=color; } // 버튼 색 설정

    public void showDialog(){ // 다이얼로그 보이기
        AlertDialog dialog = this.create();
        dialog.setOnShowListener(arg0 -> {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(this.color);
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(this.color);
            dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(this.color);
        });
        dialog.show();
    }

    public static KatiDialog simpleAlertDialog(Context context, String title, String message, DialogInterface.OnClickListener listener, @ColorInt int color){
        KatiDialog kDialog= new KatiDialog(context);
        kDialog.setTitle(title);
        kDialog.setMessage(message);
        kDialog.setPositiveButton("확인",listener);
        kDialog.setColor(color);
        return kDialog;
    }
}