package com.plim.kati_app.domain2.view.search.food.list.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.plim.kati_app.R;

/**
 * 음식 정보 리사이클러뷰 어댑터
 */
public class FoodInfoRecyclerViewAdapter extends RecyclerView.Adapter {
    //attribute
    int size ;

    public FoodInfoRecyclerViewAdapter(int size) {
        this.size=size;
    }

    @NonNull
    @Override
    public FoodInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FoodInfoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        
    }

    @Override
    public int getItemCount() {
        return this.size;
    }


    /**
     * 뷰홀더
     */
    private class FoodInfoViewHolder extends RecyclerView.ViewHolder {

        // Working Variable
        private boolean favorite = false;

        // Associate
        // View
        private ImageView favoriteImageView;

        @SuppressLint("ResourceAsColor")
        public FoodInfoViewHolder(View itemView) {
            super(itemView);

            // Associate View
            this.favoriteImageView = itemView.findViewById(R.id.foodItem_favoriteImageView);

            // Set View Callback
            this.favoriteImageView.setOnClickListener(v -> {
                this.favorite = !this.favorite;
                int newImageId = this.favorite ? R.drawable.ic_baseline_favorite_24 : R.drawable.ic_baseline_favorite_border_24;
                int newTint = this.favorite ? R.color.kati_red : R.color.kati_yellow;
                this.favoriteImageView.setImageResource(newImageId);
                this.favoriteImageView.setColorFilter(ContextCompat.getColor(v.getContext(), newTint), android.graphics.PorterDuff.Mode.SRC_IN);
            });
        }
    }
}


