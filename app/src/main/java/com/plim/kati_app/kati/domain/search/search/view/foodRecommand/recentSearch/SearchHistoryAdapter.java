package com.plim.kati_app.kati.domain.search.search.view.foodRecommand.recentSearch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.plim.kati_app.R;
import com.plim.kati_app.kati.domain.search.search.model.SearchViewModel;

import java.util.ArrayList;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SearchHistoryAdapter extends RecyclerView.Adapter<RecentViewHolder> {

    // Associate
    // Model
    private ArrayList<String> values;
    private View.OnLongClickListener onLongClickListener;
    private View.OnClickListener onClickListener;

    @Override
    public RecentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_light_button, parent, false);
        return new RecentViewHolder(view, this.onLongClickListener);
    }

    @Override
    public void onBindViewHolder(RecentViewHolder holder, int position) {
        holder.setValueButton(this.values.get(this.getItemCount()-position-1),this.onClickListener);
    }

    @Override
    public int getItemCount() {
        return this.values.size();
    }

    public void setValueVector(ArrayList<String> recentSearchedWords) {
        this.values = recentSearchedWords;
        this.notifyDataSetChanged();
    }
}
