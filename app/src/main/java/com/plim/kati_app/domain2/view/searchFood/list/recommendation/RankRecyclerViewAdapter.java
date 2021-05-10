package com.plim.kati_app.domain2.view.searchFood.list.recommendation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.plim.kati_app.R;
import java.util.Vector;

/**
 * 추천 랭크를 보여주는 리사이클러뷰의 어댑터.
 */
public class RankRecyclerViewAdapter extends RecyclerView.Adapter {

    private Vector<String> recentValues;

    public RankRecyclerViewAdapter(Vector<String> recentValues){
        //create &set components
        this.recentValues= new Vector<>();
        this.recentValues.addAll(recentValues);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context= parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_simple_rank,parent,false);
        RankRecyclerViewViewHolder rankRecyclerViewViewHolder= new RankRecyclerViewViewHolder(view);

        return rankRecyclerViewViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String value=this.recentValues.get(position);
        ((RankRecyclerViewViewHolder)holder).setValueButton(position,value);
    }

    @Override
    public int getItemCount() {
        return this.recentValues.size();
    }


    /**
     * 뷰 홀더.
     */
    private class RankRecyclerViewViewHolder extends RecyclerView.ViewHolder{
            private TextView rankNumber, rankValue;

        public RankRecyclerViewViewHolder(@NonNull View itemView) {
            super(itemView);
            this.rankNumber=itemView.findViewById(R.id.item_simpleRankNumber);
            this.rankValue=itemView.findViewById(R.id.item_simpleRankValue);
        }
        public void setValueButton(int position,String value){
            this.rankNumber.setText(position+1+"");
            this.rankValue.setText(value);
        }
    }
}
