package com.plim.kati_app.kati.crossDomain.domain.view.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;
import android.text.style.StyleSpan;

import androidx.annotation.ColorInt;

import com.plim.kati_app.R;
import com.plim.kati_app.kati.domain.nnew.review.ReviewActivity;

import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.DIALOG_CONFIRM;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.KATI_DIALOG_CANCEL;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.KATI_DIALOG_CONFIRM;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.LOG_OUT_ACTIVITY_FAILURE_DIALOG_MESSAGE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.LOG_OUT_ACTIVITY_FAILURE_DIALOG_TITLE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.RETROFIT_FAIL_CONNECTION_MESSAGE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.RETROFIT_NOT_SUCCESS_TITLE_PREFIX;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.RETROFIT_FAIL_CONNECTION_TITLE;


public class KatiDialog extends AlertDialog.Builder {

    //component
    private AlertDialog dialog;

    // Attribute
    private @ColorInt
    int color;

    // Constructor
    public KatiDialog(Context context) {
        super(context);
    }



    public void setTitle(String titleContent) { // 제목 설정
        SpannableString title = new SpannableString(titleContent);
        title.setSpan(new StyleSpan(Typeface.BOLD), 0, title.length(), 0);
        title.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, title.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        this.setTitle(title);
    }

    public void setColor(@ColorInt int color) {
        this.color = color;
    } // 버튼 색 설정

    public void showDialog() { // 다이얼로그 보이기
        this.dialog = this.create();
        this.dialog.setOnShowListener(arg0 -> {
            this.dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(this.color);
            this.dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(this.color);
            this.dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(this.color);
        });
        this.dialog.show();
    }

    public static KatiDialog simplerAlertDialog(Activity activity, int titleId, int messageId, DialogInterface.OnClickListener listener) {
        KatiDialog kDialog = new KatiDialog(activity);
        kDialog.setTitle(activity.getString(titleId));
        kDialog.setMessage(activity.getString(messageId));
        kDialog.setPositiveButton(KATI_DIALOG_CONFIRM, listener);
        kDialog.setColor(activity.getResources().getColor(R.color.kati_coral, activity.getTheme()));
        kDialog.showDialog();
        return kDialog;
    }

    public static KatiDialog simplerAlertDialog(Activity activity, String title, String message, DialogInterface.OnClickListener listener) {
        KatiDialog kDialog = new KatiDialog(activity);
        kDialog.setTitle(title);
        kDialog.setMessage(message);
        kDialog.setPositiveButton(KATI_DIALOG_CONFIRM, listener);
        kDialog.setColor(activity.getResources().getColor(R.color.kati_coral, activity.getTheme()));
        kDialog.showDialog();
        return kDialog;
    }

    public static void NotLogInDialog(Activity context, DialogInterface.OnClickListener listener) {
        KatiDialog kDialog = new KatiDialog(context);
        kDialog.setTitle(LOG_OUT_ACTIVITY_FAILURE_DIALOG_TITLE);
        kDialog.setMessage(LOG_OUT_ACTIVITY_FAILURE_DIALOG_MESSAGE);
        kDialog.setPositiveButton(DIALOG_CONFIRM, listener);
        kDialog.setColor(context.getResources().getColor(R.color.kati_coral, context.getTheme()));
        kDialog.showDialog();
    }
    public static void NotLogInDialog(Activity context, DialogInterface.OnClickListener listener, DialogInterface.OnClickListener cancelListener) {
        KatiDialog kDialog = new KatiDialog(context);
        kDialog.setTitle(LOG_OUT_ACTIVITY_FAILURE_DIALOG_TITLE);
        kDialog.setMessage(LOG_OUT_ACTIVITY_FAILURE_DIALOG_MESSAGE);
        kDialog.setPositiveButton(DIALOG_CONFIRM, listener);
        kDialog.setNegativeButton(KATI_DIALOG_CANCEL, cancelListener);
        kDialog.setColor(context.getResources().getColor(R.color.kati_coral, context.getTheme()));
        kDialog.showDialog();
    }

    public static void RetrofitFailDialog(Activity activity, DialogInterface.OnClickListener listener) {
        KatiDialog kDialog = new KatiDialog(activity);
        kDialog.setTitle(RETROFIT_FAIL_CONNECTION_TITLE);
        kDialog.setMessage(RETROFIT_FAIL_CONNECTION_MESSAGE);
        kDialog.setPositiveButton(KATI_DIALOG_CONFIRM, listener);
        kDialog.setColor(activity.getResources().getColor(R.color.kati_coral, activity.getTheme()));
        kDialog.showDialog();
    }

    public static void RetrofitNotSuccessDialog(Activity context, String message, int code, DialogInterface.OnClickListener listener) {
        KatiDialog kDialog = new KatiDialog(context);
        kDialog.setTitle(RETROFIT_NOT_SUCCESS_TITLE_PREFIX + "(" + code + ")");
        kDialog.setMessage(message);
        kDialog.setPositiveButton(DIALOG_CONFIRM, listener);
        kDialog.setColor(context.getResources().getColor(R.color.kati_coral, context.getTheme()));
        kDialog.showDialog();
    }

    public void dismiss() {
        this.dialog.dismiss();
    }


}