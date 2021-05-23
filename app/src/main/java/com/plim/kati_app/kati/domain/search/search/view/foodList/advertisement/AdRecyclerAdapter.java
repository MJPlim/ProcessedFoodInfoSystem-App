package com.plim.kati_app.kati.domain.search.search.view.foodList.advertisement;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.plim.kati_app.R;
import com.plim.kati_app.kati.domain.search.foodInfo.model.FoodDetailResponse;
import com.plim.kati_app.kati.domain.search.search.model.AdvertisementResponse;
import com.plim.kati_app.kati.domain.search.foodInfo.view.FoodInfoActivity;
import com.plim.kati_app.kati.domain.search.search.model.FoodResponse;
import com.plim.kati_app.kati.domain.search.search.view.foodList.searchResult.FoodViewHolder;

import java.util.Vector;

import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.NEW_DETAIL_ACTIVITY_EXTRA_FOOD_ID;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.NEW_DETAIL_ACTIVITY_EXTRA_IS_AD;

public class AdRecyclerAdapter extends RecyclerView.Adapter<FoodViewHolder> {

    // Associate
        // ETC
        private Activity activity;
        private Vector<AdvertisementResponse> items;

    // Constructor
    public AdRecyclerAdapter(Activity activity) {
        this.items = new Vector<>();
        this.activity=activity;
    }

    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new FoodViewHolder(view, v->this.startNewDetailActivity((Long) v.getTag()), this.activity);
    }
    @Override
    public void onBindViewHolder(FoodViewHolder holder, int position) {
        FoodResponse response= items.get(position).getFood();
        response.setFoodId(items.get(position).getId());

        holder.setValue(items.get(position).getFood()); }
    @Override
    public int getItemCount() { return items.size(); }

    public void setItems(Vector<AdvertisementResponse> items) { this.items=items; }
    private void startNewDetailActivity(Long adId) {
        Intent intent = new Intent(this.activity, FoodInfoActivity.class);
        intent.putExtra(NEW_DETAIL_ACTIVITY_EXTRA_FOOD_ID, adId);
        intent.putExtra(NEW_DETAIL_ACTIVITY_EXTRA_IS_AD, true);
        this.activity.startActivity(intent);
    }


}
