package com.plim.kati_app.domain2.view.search.list.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.plim.kati_app.R;

import java.util.Vector;

/**
 * 어두운 색의 버튼들로 이루어진 리사이클러 뷰의 뷰 어댑터.
 */
public class DarkToggleButtonRecyclerViewAdapter extends LightButtonRecyclerViewAdapter {

    public DarkToggleButtonRecyclerViewAdapter(Vector<String> values){
        super(values);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context= parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_dark_button,parent,false);
        ToggleButtonRecyclerviewViewHolder toggleButtonRecyclerviewViewHolder = new ToggleButtonRecyclerviewViewHolder(view);

        return toggleButtonRecyclerviewViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String value=this.values.get(position);
        ((ToggleButtonRecyclerviewViewHolder)holder).setValueButton(value);
    }

    @Override
    public int getItemCount() {
        return this.values.size();
    }

    /**
     * 뷰 홀더
     */
    private class ToggleButtonRecyclerviewViewHolder extends RecyclerView.ViewHolder{
        private ToggleButton valueButton;

        public ToggleButtonRecyclerviewViewHolder(@NonNull View itemView) {
            super(itemView);
            this.valueButton=itemView.findViewById(R.id.item_button);
        }
        public void setValueButton(String value){
            this.valueButton.setText(value);
            this.valueButton.setTextOff(value);
            this.valueButton.setTextOn(value);
        }
    }
}
