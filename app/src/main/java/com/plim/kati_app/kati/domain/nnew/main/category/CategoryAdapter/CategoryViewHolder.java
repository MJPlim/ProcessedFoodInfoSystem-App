package com.plim.kati_app.kati.domain.nnew.main.category.CategoryAdapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.plim.kati_app.R;
import com.plim.kati_app.kati.domain.nnew.main.search.model.FoodResponse;

import org.jetbrains.annotations.NotNull;

public class CategoryViewHolder extends RecyclerView.ViewHolder {

    private TextView productNameTextView,ratingTextView,reviewCountTextView;
    private ImageView foodImageView;


    public CategoryViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        this.productNameTextView=itemView.findViewById(R.id.newFoodItem_productNameTextView);

        this.ratingTextView=itemView.findViewById(R.id.newFoodItem_ratingTextView);
        this.reviewCountTextView=itemView.findViewById(R.id.newFoodItem_reviewCountTextView);
        this.foodImageView=itemView.findViewById(R.id.newFoodItem_foodImageView);
    }

    public void setValue(FoodResponse foodResponse) {
        this.itemView.setTag(foodResponse.getFoodId());
        this.productNameTextView.setText(foodResponse.getFoodName());
        this.ratingTextView.setText(foodResponse.getReviewRate());
        this.reviewCountTextView.setText(foodResponse.getReviewCount()+"");
        Glide.with(itemView.getContext()).load(foodResponse.getFoodImageAddress()).fitCenter().transform(new CenterCrop()).into(foodImageView);

    }
}