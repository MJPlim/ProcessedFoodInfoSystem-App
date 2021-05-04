package com.plim.kati_app.domain2.view.search.food.list.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.plim.kati_app.R;

import java.util.Vector;

/**
 * 밝은 색 버튼들로 이루어진 리사이클러 뷰의 어댑터.
 */
public class LightButtonRecyclerViewAdapter extends RecyclerView.Adapter {

    public Vector<String> values;

    public LightButtonRecyclerViewAdapter(Vector<String> values){
        //create component
        this.values= new Vector<>();

        //set components
        this.values.addAll(values);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context= parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_light_button,parent,false);
        ButtonRecyclerviewViewHolder lightButtonRecyclerviewViewHolder= new ButtonRecyclerviewViewHolder(view);

        return lightButtonRecyclerviewViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String value=this.values.get(position);
        ((ButtonRecyclerviewViewHolder)holder).setValueButton(value);
    }

    @Override
    public int getItemCount() {
        return this.values.size();
    }

    /**
     * 뷰 홀더
     */
    private class ButtonRecyclerviewViewHolder extends RecyclerView.ViewHolder{
        protected Button valueButton;

        public ButtonRecyclerviewViewHolder(@NonNull View itemView) {
            super(itemView);
            this.valueButton=itemView.findViewById(R.id.item_button);
        }
        public void setValueButton(String value){
            this.valueButton.setText(value);
        }
    }
}
