package com.plim.kati_app.kati.domain.search.search.view.foodRecommand.ranking;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;
import com.plim.kati_app.R;
import com.plim.kati_app.kati.domain.search.search.model.SearchViewModel;

import java.util.Vector;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FoodRankAdapter extends RecyclerView.Adapter<FoodRankViewHolder> {

    // Component
    private Vector<String> recentValues;
    private View.OnClickListener listener;

    @Override
    public FoodRankViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_simple_rank, parent, false);
        return new FoodRankViewHolder(view);
    }
    @Override
    public void onBindViewHolder(FoodRankViewHolder holder, int position) {
        holder.setValueButton(position, this.recentValues.get(position),listener);
    }
    @Override
    public int getItemCount() { return this.recentValues.size(); }
}
