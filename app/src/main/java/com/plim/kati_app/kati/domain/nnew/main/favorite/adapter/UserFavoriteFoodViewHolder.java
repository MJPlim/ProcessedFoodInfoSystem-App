package com.plim.kati_app.kati.domain.nnew.main.favorite.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.plim.kati_app.R;
import com.plim.kati_app.kati.domain.nnew.main.favorite.model.UserFavoriteResponse;

public class UserFavoriteFoodViewHolder extends RecyclerView.ViewHolder {

    // Attribute
    private String imageAddress;

    // Associate
        // View
        private ImageView imageView,starImage;
        private TextView productName,rate,rateNum;
        // ETC
        private Activity activity;

    // Constructor
    public UserFavoriteFoodViewHolder(View itemView, View.OnClickListener onClickListener, Activity activity) {
        super(itemView);

        // Associate
        this.activity=activity;

        // Associate View
        this.imageView = itemView.findViewById(R.id.newFoodItem_foodImageView);
        this.starImage = itemView.findViewById(R.id.newFoodItem_starIcon);
        this.productName= itemView.findViewById(R.id.newFoodItem_productNameTextView);
        this.rate=itemView.findViewById(R.id.newFoodItem_ratingTextView);
        this.rateNum=itemView.findViewById(R.id.newFoodItem_reviewCountTextView);

        // Initialize View
        itemView.setOnClickListener(onClickListener);
    }

    public void setValue(UserFavoriteResponse item) {
        this.itemView.setTag(item.getFood().getFoodId());
        this.productName.setText(item.getFood().getFoodName());
        this.rate.setText(item.getFood().getReviewRate());
        this.rateNum.setText("("+item.getFood().getReviewCount().toString()+")");
        this.imageAddress = item.getFood().getFoodImageAddress();
        Glide.with(this.activity).load(this.imageAddress).fitCenter().transform(new CenterCrop(), new CircleCrop()).into(this.imageView);
        this.imageView.setOnClickListener(v -> this.activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(this.imageAddress))));
    }
}