package com.plim.kati_app.kati.crossDomain.domain.view.fragment.expandableFragment.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.plim.kati_app.R;

/**
 * 뷰 홀더.
 */
public class AbstractExpandableViewHolder extends RecyclerView.ViewHolder {

    private Activity activity;
    private View.OnClickListener listener;

    private TextView textView;
    private ImageView delView;

    public AbstractExpandableViewHolder(@NonNull View itemView, Activity activity, View.OnClickListener listener) {
        super(itemView);
        this.activity=activity;
        this.listener=listener;
        this.textView = itemView.findViewById(R.id.allergyItem_value);
        this.delView = itemView.findViewById(R.id.allergyItem_delete);
    }

    public void setValue(String value) {
        this.textView.setText(value);
        this.delView.setColorFilter(this.activity.getResources().getColor(R.color.kati_orange,this.activity.getTheme()));
        this.delView.setTag(value);
        this.delView.setOnClickListener(this.listener);
    }

}