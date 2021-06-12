package com.plim.kati_app.kati.domain.main.category.CategoryAdapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.plim.kati_app.R;
import com.plim.kati_app.kati.domain.main.search.model.FoodResponse;

import org.jetbrains.annotations.NotNull;

import java.util.Vector;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder> {

    private Vector<FoodResponse> items;
    private View.OnClickListener listener;
    private Activity activity;

    public CategoryAdapter(View.OnClickListener listener, Activity activity) {
        this.activity = activity;
        items = new Vector<>();
        this.listener = listener;
    }

    @NonNull
    @NotNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view;
        view = ((LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_food_old, parent, false);
        view.setOnClickListener(listener);
        return new CategoryViewHolder(view, this.activity);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CategoryViewHolder holder, int position) {
            holder.setValue(items.get(position));
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public void setItems(Vector<FoodResponse> items) {
        this.items = items;
        this.notifyDataSetChanged();
    }


}
