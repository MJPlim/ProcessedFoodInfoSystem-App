package com.plim.kati_app.kati.domain.nnew.main.search.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.plim.kati_app.R;
import com.plim.kati_app.kati.domain.old.search.foodInfo.view.FoodInfoActivity;
import com.plim.kati_app.kati.domain.nnew.main.search.model.FoodResponse;

import java.util.Vector;

import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.NEW_DETAIL_ACTIVITY_EXTRA_FOOD_ID;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.NEW_DETAIL_ACTIVITY_EXTRA_IS_AD;

public class FoodRecyclerAdapter extends RecyclerView.Adapter<FoodViewHolder> {

    // Associate
        // ETC
        private Activity activity;
        private Vector<FoodResponse> items;
    private View.OnClickListener listener;

    // Constructor
    public FoodRecyclerAdapter(Activity activity, View.OnClickListener listener) {
        this.items = new Vector<>();
        this.activity=activity;
        this.listener=listener;
    }

    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food_old, parent, false);
        return new FoodViewHolder(view, this.listener, this.activity);
    }
    @Override
    public void onBindViewHolder(FoodViewHolder holder, int position) { holder.setValue(items.get(position)); }
    @Override
    public int getItemCount() { return items.size(); }

    public void setItems(Vector<FoodResponse> items) { this.items=items; }

}
