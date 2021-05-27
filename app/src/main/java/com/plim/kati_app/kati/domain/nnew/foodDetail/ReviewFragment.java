package com.plim.kati_app.kati.domain.nnew.foodDetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.plim.kati_app.R;
import com.plim.kati_app.kati.domain.nnew.review.ReviewActivity;

import org.jetbrains.annotations.NotNull;

public class ReviewFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review, container, false);
        RecyclerView rv = view.findViewById(R.id.review_reviewList);
        rv.setAdapter(new Adapter2());
        rv.setNestedScrollingEnabled(false);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rv = view.findViewById(R.id.review_reviewList);
        rv.setAdapter(new Adapter2());
        rv.setNestedScrollingEnabled(false);

        Button reviewWrite = view.findViewById(R.id.review_writeButton);
        reviewWrite.setOnClickListener(v->this.getActivity().startActivity(new Intent(this.getContext(), ReviewActivity.class)));
    }

    private class Adapter2 extends RecyclerView.Adapter<ViewHolder>{

        int i=0;
        @NonNull
        @NotNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
            View view;
            Log.d("TEST2", i+++"");
            view =  ((LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_review_see_at_food_info, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
            Log.d("TEST", position+"");
        }

        @Override
        public int getItemCount() {
            return 10;
        }
    }
    private class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
        }
    }
}