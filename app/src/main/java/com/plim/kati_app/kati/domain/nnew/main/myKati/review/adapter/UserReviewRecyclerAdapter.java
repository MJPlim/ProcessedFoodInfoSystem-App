package com.plim.kati_app.kati.domain.nnew.main.myKati.review.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.plim.kati_app.R;
import com.plim.kati_app.kati.domain.nnew.main.myKati.review.model.ReadReviewResponse;


import java.util.Vector;

public class UserReviewRecyclerAdapter extends RecyclerView.Adapter<UserReviewViewHolder> {

    // Associate
        // ETC
        private Activity activity;
        private Vector<ReadReviewResponse> vector;
        private View.OnClickListener onClickListener;

    // Constructor
    public UserReviewRecyclerAdapter(Activity activity, View.OnClickListener listener) {
        this.vector = new Vector<>();
        this.activity=activity;
        this.onClickListener=listener;
    }



    @Override
    public UserReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review_see_at_mykati, parent, false);
        return new UserReviewViewHolder(view, v->this.startNewDetailActivity((Long) v.getTag()), this.activity);
    }
    @Override
    public void onBindViewHolder(UserReviewViewHolder holder, int position) {
        holder.setValue(vector.get(position),this.onClickListener);
    }
    @Override
    public int getItemCount() { return vector.size(); }

    public void setItems(Vector<ReadReviewResponse> vector) { this.vector=vector; }
    private void startNewDetailActivity(Long reviewId) {
    }
}
