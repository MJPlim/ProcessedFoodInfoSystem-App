package com.plim.kati_app.kati.crossDomain.domain.view.fragment.abstractTableFragment;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.plim.kati_app.R;

public class TableViewHolder extends RecyclerView.ViewHolder {

    // Associate
        // View
        private TextView title, value;

    // Constructor
    public TableViewHolder(View itemView) {
        super(itemView);
        this.title = itemView.findViewById(R.id.detailInformationTableTitle_nameTextView);
        this.value = itemView.findViewById(R.id.detailInformationTableTitle_valueTextView);
    }

    public void setValue(TableInfo.Data data) {
        this.title.setText(data.getTitle());
        this.value.setText(data.getValue());
    }
}