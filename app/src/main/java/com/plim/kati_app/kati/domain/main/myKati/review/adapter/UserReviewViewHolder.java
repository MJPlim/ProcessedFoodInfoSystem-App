package com.plim.kati_app.kati.domain.main.myKati.review.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.plim.kati_app.R;
import com.plim.kati_app.kati.domain.main.myKati.review.model.ReadReviewResponse;
import com.plim.kati_app.kati.domain.review.ReviewActivity;

import java.text.SimpleDateFormat;

public class UserReviewViewHolder extends RecyclerView.ViewHolder {

    // Attribute

    // Associate
    // View
    private TextView productName, date,  reviewContent;
    private TextView editButton, deleteButton;
    private RatingBar score;




    // ETC
    private Activity activity;

    // Constructor
    public UserReviewViewHolder(View itemView, View.OnClickListener onClickListener, Activity activity) {
        super(itemView);
        // Associate
        this.activity = activity;
        // Associate View
        this.productName = itemView.findViewById(R.id.review_name);
        this.date = itemView.findViewById(R.id.review_date);
        this.score = itemView.findViewById(R.id.ratingBar);
        this.reviewContent = itemView.findViewById(R.id.textView12);
        this.editButton = itemView.findViewById(R.id.review_edit);
        this.deleteButton = itemView.findViewById(R.id.review_delete);
        // Initialize View
        itemView.setOnClickListener(onClickListener);

    }

    public void setValue(ReadReviewResponse item, View.OnClickListener listener) {
        String pattern = "yyyy.MM.dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String createdDate = simpleDateFormat.format(item.getReviewCreatedDate());
        String modifiedDate = simpleDateFormat.format(item.getReviewModifiedDate());
        this.productName.setText(item.getFoodName());
        this.date.setText(item.getReviewModifiedDate() == null ?
                createdDate + " 작성" :
                modifiedDate + " 수정");
        this.score.setRating(item.getReviewRating());
        this.reviewContent.setText(item.getReviewDescription());
        this.deleteButton.setEnabled(item.isUserCheck());
        this.editButton.setEnabled(item.isUserCheck());

        this.deleteButton.setTag(item.getReviewId());
        this.deleteButton.setOnClickListener(listener);


        this.editButton.setOnClickListener(v -> {
            Intent intent = new Intent(this.activity, ReviewActivity.class);
            intent.putExtra("reviewId", item.getReviewId());
            intent.putExtra("isUpdate",true);
            intent.putExtra("foodName", item.getFoodName());
            intent.putExtra("reviewText",item.getReviewDescription());
            intent.putExtra("ratingScore",item.getReviewRating());
            this.activity.startActivity(intent);
        });
    }
    }