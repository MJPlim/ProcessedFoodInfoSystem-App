package com.plim.kati_app.domain2.view.search2.search.foodRecommand.ranking;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.plim.kati_app.R;

public class FoodRankViewHolder extends RecyclerView.ViewHolder{

    // Associate
        // View
        private TextView rankNumber, rankValue;

    // Constructor
    public FoodRankViewHolder(View itemView) {
        super(itemView);
        this.rankNumber=itemView.findViewById(R.id.item_simpleRankNumber);
        this.rankValue=itemView.findViewById(R.id.item_simpleRankValue);
    }
    public void setValueButton(int position, String value){
        this.rankNumber.setText(Integer.toString(position+1));
        this.rankValue.setText(value);
    }
}