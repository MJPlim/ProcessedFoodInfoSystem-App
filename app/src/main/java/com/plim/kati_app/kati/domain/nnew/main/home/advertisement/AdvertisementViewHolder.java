package com.plim.kati_app.kati.domain.nnew.main.home.advertisement;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.plim.kati_app.R;

public class AdvertisementViewHolder extends RecyclerView.ViewHolder {

    public AdvertisementViewHolder(View itemView) {
        super(itemView);
    }

    public void setText(int position) {
        TextView tv = this.itemView.findViewById(R.id.textView);
        tv.setText("임시 광고 "+position);
    }
}
