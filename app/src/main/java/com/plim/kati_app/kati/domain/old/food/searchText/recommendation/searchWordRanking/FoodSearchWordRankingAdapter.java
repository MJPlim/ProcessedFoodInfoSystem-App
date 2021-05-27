package com.plim.kati_app.kati.domain.old.food.searchText.recommendation.searchWordRanking;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;
import com.plim.kati_app.R;

import java.util.Vector;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FoodSearchWordRankingAdapter extends RecyclerView.Adapter<FoodSearchWordRankingViewHolder> {

    // Component
    private Vector<String> recentValues;
    private View.OnClickListener listener;

    @Override
    public FoodSearchWordRankingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_simple_rank, parent, false);
        return new FoodSearchWordRankingViewHolder(view);
    }
    @Override
    public void onBindViewHolder(FoodSearchWordRankingViewHolder holder, int position) {
        holder.setValueButton(position, this.recentValues.get(position),listener);
    }
    @Override
    public int getItemCount() { return this.recentValues.size(); }
}
