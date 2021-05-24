package com.plim.kati_app.kati.domain.mypage.myReview.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.plim.kati_app.R;
import com.plim.kati_app.kati.domain.mypage.myReview.model.ReadReviewResponse;
import java.util.Vector;

public class UserReviewRecyclerAdapter extends RecyclerView.Adapter<UserReviewViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(View v, int position) ;
    }

    // Associate
        // ETC
        private Activity activity;
        private Vector<ReadReviewResponse> vector;

    // Constructor
    public UserReviewRecyclerAdapter(Activity activity) {
        this.vector = new Vector<>();
        this.activity=activity;
    }

    private OnItemClickListener mListener=null;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener=listener;
    }

    @Override
    public UserReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
        return new UserReviewViewHolder(view, v->this.startNewDetailActivity((Long) v.getTag()), this.activity);
    }
    @Override
    public void onBindViewHolder(UserReviewViewHolder holder, int position) {
        (holder).setValue(vector.get(position));}
    @Override
    public int getItemCount() { return vector.size(); }

    public void setItems(Vector<ReadReviewResponse> vector) { this.vector=vector; }
    private void startNewDetailActivity(Long reviewId) {
        Log.d("디버그",reviewId.toString());
    }
}
