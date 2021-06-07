package com.plim.kati_app.kati.crossDomain.domain.view.etc;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.plim.kati_app.R;

public class JSHInfoItem extends LinearLayout {

    private TextView titleTextView, contentTextView;
    private View view;

    // Constructor
    public JSHInfoItem(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Get Attributes
        TypedArray attributeArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.JSHInfoItem, 0, 0);
        String title = attributeArray.getString(R.styleable.JSHInfoItem_JSHInfoItem_title);
        String content = attributeArray.getString(R.styleable.JSHInfoItem_JSHInfoItem_content);

        // Inflate View
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.view = layoutInflater.inflate(R.layout.item_info, this, false);
        this.addView(this.view);

        // Associate View
        this.titleTextView = this.view.findViewById(R.id.title);
        this.contentTextView = this.view.findViewById(R.id.content);

        // Initialize View
        this.titleTextView.setText(title);
        this.contentTextView.setText(content);

    }


    public void setContentText(String text) {
        if (text != null)
            this.contentTextView.setText(text);
        else
            this.view.setVisibility(View.GONE);
    }
}
