package com.plim.kati_app.kati.crossDomain.domain.view.etc;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.plim.kati_app.R;

public class YYECategoryItem extends LinearLayout {

    private ImageView imageView;
    private TextView titleTextView;

    public YYECategoryItem(Context context){
        super(context);
// Inflate View
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View categoryItem= layoutInflater.inflate(R.layout.fragment_home_category_item, this, false);
        this.addView(categoryItem);

        this.imageView=categoryItem.findViewById(R.id.categoryItem_imageView);
        this.titleTextView=categoryItem.findViewById(R.id.categoryItem_titleTextView);
    }

    // Constructor
    public YYECategoryItem(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Get Attributes
        TypedArray attributeArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.YYECategoryItem, 0, 0);


        // Inflate View
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        View categoryItem= layoutInflater.inflate(R.layout.fragment_home_category_item, this, false);
        this.addView(categoryItem);

        this.imageView=categoryItem.findViewById(R.id.categoryItem_imageView);
        this.titleTextView=categoryItem.findViewById(R.id.categoryItem_titleTextView);

        this.titleTextView.setText(attributeArray.getString(R.styleable.YYECategoryItem_YYECategoryItem_text));
//        Glide.with(categoryItem).load(attributeArray.getDrawable(R.styleable.YYECategoryItem_YYECategoryItem_image)).into(imageView);
        imageView.setImageDrawable(attributeArray.getDrawable(R.styleable.YYECategoryItem_YYECategoryItem_image));
    }

    public void setText(String text){
        this.titleTextView.setText(text);
    }

    public void setImage(Drawable drawable){
        imageView.setImageDrawable(drawable);
        imageView.setColorFilter(getResources().getColor(R.color.kati_gray,getContext().getTheme()));
    }

}