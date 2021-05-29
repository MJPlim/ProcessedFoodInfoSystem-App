package com.plim.kati_app.kati.domain.nnew.main.search.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.plim.kati_app.R;
import com.plim.kati_app.kati.domain.nnew.main.search.model.FoodResponse;
import com.varunest.sparkbutton.SparkButton;

public class FoodViewHolder extends RecyclerView.ViewHolder {

    // Associate
    // View

    private ImageView foodImageView;
    private TextView productNameTextView, reviewCountTextView, ratingTextView;

    // ETC
    private Activity activity;

    // Constructor
    public FoodViewHolder(View itemView, View.OnClickListener onClickListener, Activity activity) {
        super(itemView);

        // Associate
        this.activity = activity;

        // Associate View

        this.foodImageView = itemView.findViewById(R.id.newFoodItem_foodImageView);
        this.productNameTextView = itemView.findViewById(R.id.newFoodItem_productNameTextView);
        this.reviewCountTextView = itemView.findViewById(R.id.newFoodItem_reviewCountTextView);
        this.ratingTextView = itemView.findViewById(R.id.newFoodItem_ratingTextView);

        // Initialize View
        itemView.setOnClickListener(onClickListener);
    }

    public void setValue(FoodResponse foodInfo) {
        this.itemView.setTag(foodInfo.getFoodId());
        this.productNameTextView.setText(foodInfo.getFoodName());
        Glide.with(this.activity).load(foodInfo.getFoodImageAddress()).fitCenter().transform(new CenterCrop(), new CircleCrop()).into(this.foodImageView);
        this.ratingTextView.setText(foodInfo.getReviewRate() == null ? " ㅡ " : foodInfo.getReviewRate());
        this.reviewCountTextView.setText("(" + foodInfo.getReviewCount() + ")");
    }
}