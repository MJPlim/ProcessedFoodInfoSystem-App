package com.plim.kati_app.kati.domain.nnew.foodDetail.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.plim.kati_app.R;
import com.plim.kati_app.kati.domain.old.model.ReadReviewResponse;

import java.util.Vector;

public class ReviewRecyclerAdapter extends RecyclerView.Adapter<ReviewViewHolder> {

    private Activity activity;
    private Vector<ReadReviewResponse> vector;
    private View.OnClickListener deleteListener, unLikeListener, likeListener, updateListener;

    public ReviewRecyclerAdapter(Activity activity, View.OnClickListener deleteListener, View.OnClickListener unLikeListener, View.OnClickListener likeListener, View.OnClickListener updateListener) {
        this.vector = new Vector<>();
        this.activity=activity;
        this.deleteListener=deleteListener;
        this.unLikeListener=unLikeListener;
        this.likeListener=likeListener;
        this.updateListener=updateListener;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_review_see_at_food_info, parent, false);
        ReviewViewHolder viewHolder = new ReviewViewHolder(view,activity,deleteListener,unLikeListener,likeListener,updateListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        holder.setValue(vector.get(position));
    }

    @Override
    public int getItemCount() {
        return this.vector.size();
    }

    public void setItems(Vector<ReadReviewResponse> vector) {
        this.vector = vector;
        this.notifyDataSetChanged();
        Log.d("벡터길이", vector.size() + "");
    }

}