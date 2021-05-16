package com.plim.kati_app.domain.katiCrossDomain.domain.view.abstractTableFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.plim.kati_app.R;

import java.util.HashMap;

public class TableAdapter extends RecyclerView.Adapter<TableViewHolder> {

    // Component
    private HashMap<Integer, TableInfo.Data> itemMap;

    // Constructor
    public TableAdapter() {
        this.itemMap = new HashMap<>();
    }

    @Override
    public TableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_information_table, parent, false);
        return new TableViewHolder(view);
    }
    @Override
    public void onBindViewHolder(TableViewHolder holder, int position) { holder.setValue(this.itemMap.get(position)); }
    @Override
    public int getItemCount() { return itemMap.entrySet().size(); }

    public void setItems(HashMap<Integer, TableInfo.Data> itemMap) {
        this.itemMap = itemMap;
        this.notifyDataSetChanged();
    }
}