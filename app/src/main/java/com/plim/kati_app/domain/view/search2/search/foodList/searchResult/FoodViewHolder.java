package com.plim.kati_app.domain.view.search2.search.foodList.searchResult;

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
import com.plim.kati_app.domain.katiCrossDomain.domain.model.forBackend.searchFood.FoodResponse;

public class FoodViewHolder extends RecyclerView.ViewHolder {

    // Attribute
    private String imageAddress;

    // Associate
        // View
        private ImageView imageView;
        private TextView productName, companyName;
        // ETC
        private Activity activity;

    // Constructor
    public FoodViewHolder(View itemView, View.OnClickListener onClickListener, Activity activity) {
        super(itemView);

        // Associate
        this.activity=activity;

        // Associate View
        this.productName = itemView.findViewById(R.id.foodItem_productName);
        this.companyName = itemView.findViewById(R.id.foodItem_companyName);
        this.imageView = itemView.findViewById(R.id.foodItem_foodImageView);

        // Initialize View
        itemView.setOnClickListener(onClickListener);
    }

    public void setValue(FoodResponse foodInfo) {
        this.itemView.setTag(foodInfo.getFoodId());
        this.productName.setText(foodInfo.getFoodName());
        this.companyName.setText(foodInfo.getManufacturerName());
        this.imageAddress = foodInfo.getFoodImageAddress();
        Glide.with(this.activity).load(this.imageAddress).fitCenter().transform(new CenterCrop(), new CircleCrop()).into(this.imageView);
        this.imageView.setOnClickListener(v -> this.activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(this.imageAddress))));
    }
}