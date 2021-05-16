package com.plim.kati_app.domain.view.search2.search.foodRecommand.ranking;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;
import com.plim.kati_app.R;

import java.util.Vector;

public class FoodRankAdapter extends RecyclerView.Adapter<FoodRankViewHolder> {

    // Component
    private Vector<String> recentValues;

    // Constructor
    public FoodRankAdapter(Vector<String> recentValues){
        this.recentValues = recentValues;
    }

    @Override
    public FoodRankViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_simple_rank, parent, false);
        return new FoodRankViewHolder(view);
    }
    @Override
    public void onBindViewHolder(FoodRankViewHolder holder, int position) {
        holder.setValueButton(position, this.recentValues.get(position));
    }
    @Override
    public int getItemCount() { return this.recentValues.size(); }
}
