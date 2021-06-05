package com.plim.kati_app.kati.domain.nnew.foodDetail.adapter;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.plim.kati_app.R;
import com.plim.kati_app.kati.domain.nnew.main.search.model.ReadReviewResponse;

public class ReviewViewHolder extends RecyclerView.ViewHolder {

    private Activity activity;

    private TextView userNameTextView, dateTextView, reviewValue, reviewLikeItemCountTextView,
    editButton, deleteButton, reportButton;
    private ImageView reviewLikeIcon;
    private RatingBar ratingBar;
    private View reviewLikeButtonBackground;

    private View.OnClickListener deleteListener,unLikeListener,likeListener,updateListener;


    public ReviewViewHolder(@NonNull View itemView, Activity activity, View.OnClickListener deleteListener, View.OnClickListener unLikeListener,View.OnClickListener likeListener, View.OnClickListener updateListener) {
        super(itemView);
        this.userNameTextView=itemView.findViewById(R.id.foodInfoReviewItem_userNameTextView);
        this.dateTextView=itemView.findViewById(R.id.foodInfoReviewItem_dateTextView);
        this.reviewValue=itemView.findViewById(R.id.foodInfoReviewItem_reviewValue);
        this.reviewLikeItemCountTextView=itemView.findViewById(R.id.foodInfoReviewItem_reviewLikeItemCountTextView);


        this.editButton=itemView.findViewById(R.id.foodInfoReviewItem_reviewEditTextView);
        this.deleteButton=itemView.findViewById(R.id.foodInfoReviewItem_reviewDeleteTextView);
        this.reportButton=itemView.findViewById(R.id.foodInfoReviewItem_reportTextView);

        this.reviewLikeButtonBackground=itemView.findViewById(R.id.foodInfoReviewItem_reviewLikeButtonBackground);

        this.reviewLikeIcon=itemView.findViewById(R.id.foodInfoReviewItem_reviewLikeIcon);

        this.ratingBar=itemView.findViewById(R.id.foodInfoReviewItem_ratingBar);

        this.deleteListener=deleteListener;
        this.unLikeListener=unLikeListener;
        this.likeListener=likeListener;
        this.updateListener=updateListener;
        this.activity=activity;

    }

    public void setValue(ReadReviewResponse value) {
        this.userNameTextView.setText(value.getUserName());
        this.dateTextView.setText(value.getReviewModifiedDate() == value.getReviewCreatedDate() ?
                value.getReviewCreatedDate().toString() + "작성" :
                value.getReviewModifiedDate().toString() + "수정");
        this.ratingBar.setRating(value.getReviewRating());
        this.reviewValue.setText(value.getReviewDescription());
        this.reviewLikeItemCountTextView.setText(value.getLikeCount() + "");

        this.deleteButton.setVisibility(value.isUserCheck()? View.VISIBLE:View.INVISIBLE);
        this.editButton.setVisibility(value.isUserCheck()? View.VISIBLE:View.INVISIBLE);
        this.reportButton.setVisibility(!value.isUserCheck()? View.VISIBLE:View.INVISIBLE);

        this.deleteButton.setOnClickListener(deleteListener);
        this.deleteButton.setTag(value.getReviewId());

        this.reviewLikeIcon.clearColorFilter();
        int color = this.activity.getResources().getColor(value.isUserLikeCheck() ? R.color.kati_red : R.color.gray, this.activity.getTheme());
        this.reviewLikeIcon.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        this.reviewLikeButtonBackground.setBackgroundResource(value.isUserLikeCheck() ? R.drawable.thumb_bg_select : R.drawable.thumb_bg_non_select);
//        if (value.isUserLikeCheck()){
//            this.reviewLikeButtonBackground.setBackgroundResource(R.drawable.thumb_bg_select);
//        }else{
//            this.reviewLikeButtonBackground.setBackgroundResource(R.drawable.thumb_bg_non_select);
//        }

        this.reviewLikeItemCountTextView.setOnClickListener(value.isUserCheck()? this.likeListener:this.unLikeListener);
        this.reviewLikeIcon.setOnClickListener(value.isUserCheck()? this.likeListener:this.unLikeListener);
        this.reviewLikeItemCountTextView.setTag(value.getReviewId());
        this.reviewLikeIcon.setTag(value.getReviewId());

        this.editButton.setOnClickListener(this.updateListener);
        this.editButton.setTag(value.getReviewId());
    }
}