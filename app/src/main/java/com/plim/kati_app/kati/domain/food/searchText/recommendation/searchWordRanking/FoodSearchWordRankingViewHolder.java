package com.plim.kati_app.kati.domain.food.searchText.recommendation.searchWordRanking;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.plim.kati_app.R;

public class FoodSearchWordRankingViewHolder extends RecyclerView.ViewHolder{

    // Associate
        // View
        private TextView rankNumber, rankValue;

    // Constructor
    public FoodSearchWordRankingViewHolder(View itemView) {
        super(itemView);
        this.rankNumber=itemView.findViewById(R.id.item_simpleRankNumber);
        this.rankValue=itemView.findViewById(R.id.item_simpleRankValue);
    }
    public void setValueButton(int position, String value, View.OnClickListener listener){
        this.rankNumber.setText(Integer.toString(position+1));
        this.rankValue.setText(value);
        this.rankValue.setTag(value);
        this.rankValue.setOnClickListener(listener);
    }
}