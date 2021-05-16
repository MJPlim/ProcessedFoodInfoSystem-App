package com.plim.kati_app.domain.view.search2.search.foodList.searchResult;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.plim.kati_app.R;
import com.plim.kati_app.domain.katiCrossDomain.domain.model.forBackend.searchFood.FoodResponse;
import com.plim.kati_app.domain.view.search2.foodInfo.FoodInfoActivity;

import java.util.Vector;

import static com.plim.kati_app.domain.katiCrossDomain.domain.Constant.NEW_DETAIL_ACTIVITY_EXTRA_FOOD_ID;
import static com.plim.kati_app.domain.katiCrossDomain.domain.Constant.NEW_DETAIL_ACTIVITY_EXTRA_IS_AD;

public class FoodRecyclerAdapter extends RecyclerView.Adapter<FoodViewHolder> {

    // Associate
        // ETC
        private Activity activity;
        private Vector<FoodResponse> items;

    // Constructor
    public FoodRecyclerAdapter(Activity activity) {
        this.items = new Vector<>();
        this.activity=activity;
    }

    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new FoodViewHolder(view, v->this.startNewDetailActivity((Long) v.getTag()), this.activity);
    }
    @Override
    public void onBindViewHolder(FoodViewHolder holder, int position) { holder.setValue(items.get(position)); }
    @Override
    public int getItemCount() { return items.size(); }

    public void setItems(Vector<FoodResponse> items) { this.items=items; }
    private void startNewDetailActivity(Long foodId) {
        Intent intent = new Intent(this.activity, FoodInfoActivity.class);
        intent.putExtra(NEW_DETAIL_ACTIVITY_EXTRA_FOOD_ID, foodId);
        intent.putExtra(NEW_DETAIL_ACTIVITY_EXTRA_IS_AD, false);
        this.activity.startActivity(intent);
    }
}
