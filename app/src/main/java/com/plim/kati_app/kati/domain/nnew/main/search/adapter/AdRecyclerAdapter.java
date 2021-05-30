package com.plim.kati_app.kati.domain.nnew.main.search.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.plim.kati_app.R;
import com.plim.kati_app.kati.domain.nnew.main.search.model.AdvertisementResponse;
import com.plim.kati_app.kati.domain.nnew.main.search.model.FoodResponse;

import java.util.Vector;

public class AdRecyclerAdapter extends RecyclerView.Adapter<adViewHolder> {

    // Associate
        // ETC
        private Activity activity;
        private Vector<AdvertisementResponse> items;
        private View.OnClickListener listener;

    // Constructor
    public AdRecyclerAdapter(Activity activity, View.OnClickListener listener) {
        this.items = new Vector<>();
        this.activity=activity;
        this.listener=listener;
    }

    @Override
    public adViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food_vertical, parent, false);
        return new adViewHolder(view, this.listener, this.activity);
    }
    @Override
    public void onBindViewHolder(adViewHolder holder, int position) {
        FoodResponse response= items.get(position).getFood();
        response.setFoodId(items.get(position).getId());

        holder.setValue(items.get(position).getFood()); }
    @Override
    public int getItemCount() { return items.size(); }

    public void setItems(Vector<AdvertisementResponse> items) { this.items=items; }



}
