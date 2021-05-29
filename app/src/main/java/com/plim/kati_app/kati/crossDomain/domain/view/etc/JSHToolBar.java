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

public class JSHToolBar extends LinearLayout {

    // Associate
        // View
        private ImageView button;
        private TextView title;

    // Constructor
    public JSHToolBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Get Attributes
        TypedArray attributeArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.JSHToolBar, 0, 0);
        String title = attributeArray.getString(R.styleable.JSHToolBar_title);
        int buttonImageSource = attributeArray.getResourceId(R.styleable.JSHToolBar_button_image, 0);

        // Inflate View
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View jshToolbar = layoutInflater.inflate(R.layout.widget_toolbar, this, false);
        this.addView(jshToolbar);

        // Associate View
        this.button = jshToolbar.findViewById(R.id.button_imageView);
        this.title = jshToolbar.findViewById(R.id.title_textView);

        // Initialize View
        this.button.setImageResource(buttonImageSource);
        this.title.setText(title);
    }

    public void setToolBarOnClickListener(OnClickListener listener){
        this.button.setOnClickListener(listener);
    }

    public void setToolBarTitle(String title){
        this.title.setText(title);
    }
}
