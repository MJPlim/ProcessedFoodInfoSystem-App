package com.plim.kati_app.kati.domain.old.mypage.myReview.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.plim.kati_app.R;
import com.plim.kati_app.kati.domain.old.mypage.myReview.model.ReadReviewResponse;
import com.plim.kati_app.kati.domain.old.search.foodInfo.view.review.UpdateReviewActivity;

public class UserReviewViewHolder extends RecyclerView.ViewHolder {

    // Attribute

    // Associate
    // View
    private TextView productName, date, score, reviewContent, like;
    private Button editButton, deleteButton;
    private ImageView likeImageButton;

    // ETC
    private Activity activity;

    // Constructor
    public UserReviewViewHolder(View itemView, View.OnClickListener onClickListener, Activity activity) {
        super(itemView);
        // Associate
        this.activity = activity;
        // Associate View
        this.productName = itemView.findViewById(R.id.reviewItem_ProductNameTextView);
        this.date = itemView.findViewById(R.id.reviewItem_EditDateTextView);
        this.score = itemView.findViewById(R.id.reviewItem_reviewScoreTextView);
        this.reviewContent = itemView.findViewById(R.id.reviewItem_reviewTextTextView);
        this.like = itemView.findViewById(R.id.reviewItem_reviewLikeTextView);
        this.editButton = itemView.findViewById(R.id.reviewItem_editButton);
        this.likeImageButton = itemView.findViewById(R.id.reviewItem_reviewLikeImageView);
        this.deleteButton = itemView.findViewById(R.id.reviewItem_deleteButton);
        // Initialize View
        itemView.setOnClickListener(onClickListener);

    }

    public void setValue(ReadReviewResponse item,View.OnClickListener listener) {
        this.productName.setText(item.getFoodName());
        this.date.setText(item.getReviewModifiedDate() == null ?
                item.getReviewCreatedDate().toString() + "작성" :
                item.getReviewModifiedDate().toString() + "수정");
        this.score.setText(item.getReviewRating() + "");
        this.reviewContent.setText(item.getReviewDescription());
        this.like.setText(item.getLikeCount() + "");


        this.deleteButton.setEnabled(item.isUserCheck());
        this.editButton.setEnabled(item.isUserCheck());

        this.deleteButton.setTag(item.getReviewId());
        this.deleteButton.setOnClickListener(listener);


        this.likeImageButton.clearColorFilter();
//        int color = getResources().getColor(item.isUserLikeCheck() ? R.color.kati_orange : R.color.gray, getContext().getTheme());
//        this.likeImageButton.setColorFilter(color, PorterDuff.Mode.SRC_IN);
//        this.like.setOnClickListener(isLogin ? v -> like(value.getReviewId(), value.isUserLikeCheck()) : null);
//        this.likeImageButton.setOnClickListener(isLogin ? v -> like(value.getReviewId(), value.isUserLikeCheck()) : null);
        this.editButton.setOnClickListener(v -> {
            Intent intent = new Intent(this.activity, UpdateReviewActivity.class);
            intent.putExtra("reviewId", item.getReviewId());
            intent.putExtra("foodId", item.getFoodId());
            this.activity.startActivity(intent);
        });
    }
    }