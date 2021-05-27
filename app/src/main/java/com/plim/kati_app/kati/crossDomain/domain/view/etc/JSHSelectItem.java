package com.plim.kati_app.kati.crossDomain.domain.view.etc;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.plim.kati_app.R;

public class JSHSelectItem extends LinearLayout {

    // Associate
        // View
        private TextView title;

    // Constructor
    public JSHSelectItem(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Get Attributes
        TypedArray attributeArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.JSHSelectItem, 0, 0);
        String title = attributeArray.getString(R.styleable.JSHSelectItem_JSHSelectItem_title);

        // Inflate View
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View jshToolbar = layoutInflater.inflate(R.layout.item_select, this, false);
        this.addView(jshToolbar);

        // Associate View
        this.title = jshToolbar.findViewById(R.id.title);

        // Initialize View
        this.title.setText(title);
    }
}
