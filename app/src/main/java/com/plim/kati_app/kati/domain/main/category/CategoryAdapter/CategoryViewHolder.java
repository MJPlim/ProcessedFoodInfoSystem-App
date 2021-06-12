package com.plim.kati_app.kati.domain.main.category.CategoryAdapter;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.plim.kati_app.R;
import com.plim.kati_app.kati.domain.main.search.model.FoodResponse;

import org.jetbrains.annotations.NotNull;

public class CategoryViewHolder extends RecyclerView.ViewHolder {


    private Activity activity;
    private TextView productNameTextView,ratingTextView,reviewCountTextView;
    private ImageView foodImageView;


    public CategoryViewHolder(@NonNull @NotNull View itemView, Activity activity) {
        super(itemView);
        this.activity=activity;
        this.productNameTextView=itemView.findViewById(R.id.newFoodItem_productNameTextView);

        this.ratingTextView=itemView.findViewById(R.id.newFoodItem_ratingTextView);
        this.reviewCountTextView=itemView.findViewById(R.id.newFoodItem_reviewCountTextView);
        this.foodImageView=itemView.findViewById(R.id.newFoodItem_foodImageView);
    }

    public void setValue(FoodResponse foodResponse) {
        this.itemView.setTag(foodResponse.getFoodId());
        this.productNameTextView.setText(foodResponse.getFoodName());
        this.ratingTextView.setText(foodResponse.getReviewRate());
        this.reviewCountTextView.setText("("+foodResponse.getReviewCount()+")");
        this.foodImageView.setClipToOutline(true);
        Glide.with(this.activity).load(foodResponse.getFoodImageAddress()).transform(new CenterCrop(), new RoundedCorners(50)).into(this.foodImageView);

    }
}