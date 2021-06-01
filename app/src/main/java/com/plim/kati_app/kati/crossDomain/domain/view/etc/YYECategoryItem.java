package com.plim.kati_app.kati.crossDomain.domain.view.etc;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.plim.kati_app.R;

public class YYECategoryItem extends LinearLayout {

    private ImageView imageView;
    private TextView titleTextView;

    private View view;

    public YYECategoryItem(Context context){
        super(context);
// Inflate View
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.view= layoutInflater.inflate(R.layout.fragment_home_category_item, this, false);
        this.addView(this.view);

        this.imageView=this.view.findViewById(R.id.categoryItem_imageView);
        this.titleTextView=this.view.findViewById(R.id.categoryItem_titleTextView);
    }

    // Constructor
    public YYECategoryItem(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Get Attributes
        TypedArray attributeArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.YYECategoryItem, 0, 0);


        // Inflate View
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        this.view= layoutInflater.inflate(R.layout.fragment_home_category_item, this, false);
        this.addView(this.view);

        this.imageView=this.view.findViewById(R.id.categoryItem_imageView);
        this.titleTextView=this.view.findViewById(R.id.categoryItem_titleTextView);

        this.titleTextView.setText(attributeArray.getString(R.styleable.YYECategoryItem_YYECategoryItem_text));
//        Glide.with(categoryItem).load(attributeArray.getDrawable(R.styleable.YYECategoryItem_YYECategoryItem_image)).into(imageView);
        imageView.setImageDrawable(attributeArray.getDrawable(R.styleable.YYECategoryItem_YYECategoryItem_image));
    }

    public void setText(String text){
        this.titleTextView.setText(text);
    }

    public void setImage(Drawable drawable){
//        Glide.with(this.activity).load(foodInfo.getFoodImageAddress()).fitCenter().transform(new CenterCrop(), new CircleCrop()).into(this.foodImageView);
        Glide.with(this.view).load(drawable).fitCenter().transform(new CenterCrop()).into(imageView);
//        imageView.setImageDrawable(drawable);
//        imageView.setColorFilter(getResources().getColor(R.color.kati_gray,getContext().getTheme()));
    }

    @Override
    public void setOnClickListener(OnClickListener listener){
        this.view.setOnClickListener(listener);
    }

}
