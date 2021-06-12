package com.plim.kati_app.kati.domain.main.home.advertisement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.plim.kati_app.R;
import com.plim.kati_app.kati.domain.main.home.HomeFragment;

public class AdvertisementViewPagerAdapter extends RecyclerView.Adapter<AdvertisementViewHolder> {

    private final HomeFragment.EAdImage[] values;
    private final Context context;

    public AdvertisementViewPagerAdapter(HomeFragment.EAdImage[] values, Context context) {
   this.values=values;
   this.context=context;
    }

    @Override
    public AdvertisementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_advertisement, parent, false);
        return new AdvertisementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdvertisementViewHolder holder, int position) {
//        holder.setText(position);
        holder.setImage(this.context.getDrawable(values[position].getImageId()));
    }

    @Override public int getItemCount() { return this.values.length; }
}