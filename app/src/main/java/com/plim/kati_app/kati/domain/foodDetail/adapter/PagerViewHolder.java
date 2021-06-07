package com.plim.kati_app.kati.domain.foodDetail.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.plim.kati_app.R;

public class PagerViewHolder extends RecyclerView.ViewHolder {

    public PagerViewHolder(View itemView) {
        super(itemView);
    }

    public void setText(int position) {
        TextView tv = this.itemView.findViewById(R.id.textView);
        tv.setText("임시 광고 "+position);
    }
}
