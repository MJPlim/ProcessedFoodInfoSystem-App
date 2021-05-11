package com.plim.kati_app.domain2.view.main.search.list.adapter;

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
 * 정렬버튼 어댑터.
 */
public class SortButtonRecyclerViewAdapter extends RecyclerView.Adapter {
    View.OnClickListener listener;

    public SortButtonRecyclerViewAdapter(Vector<String> val, View.OnClickListener listener) {
        //create component
        this.values= new Vector<>();

        //set components
        this.values.addAll(val);
        this.listener=listener;
    }
    protected Vector<String> values;


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context= parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_light_button,parent,false);
        SortButtonRecyclerViewAdapter.ButtonRecyclerviewViewHolder lightButtonRecyclerviewViewHolder= new SortButtonRecyclerViewAdapter.ButtonRecyclerviewViewHolder(view);

        return lightButtonRecyclerviewViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String value=this.values.get(position);
        ((SortButtonRecyclerViewAdapter.ButtonRecyclerviewViewHolder)holder).setValueButton(value);
    }

    @Override
    public int getItemCount() {
        return this.values.size();
    }

    /**
     * 뷰 홀더
     */
    private class ButtonRecyclerviewViewHolder extends RecyclerView.ViewHolder{
        private Button valueButton;

        public ButtonRecyclerviewViewHolder(@NonNull View itemView) {
            super(itemView);
            this.valueButton=itemView.findViewById(R.id.item_button);
            this.valueButton.setOnClickListener(listener);
        }
        public void setValueButton(String value){
            this.valueButton.setText(value);
        }
    }
}
