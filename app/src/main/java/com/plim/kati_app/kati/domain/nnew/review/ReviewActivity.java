package com.plim.kati_app.kati.domain.nnew.review;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.plim.kati_app.R;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitTool;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.kati.crossDomain.domain.view.activity.KatiLoginCheckViewModelActivity;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.KatiDialog;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.KatiRetrofitTool;
import com.plim.kati_app.kati.domain.nnew.review.model.CreateAndUpdateReviewRequest;
import com.plim.kati_app.kati.domain.nnew.review.model.CreateReviewResponse;

import retrofit2.Response;


public class ReviewActivity extends KatiLoginCheckViewModelActivity {

    private Button submitButton;
    private EditText reviewEditText;
    private RatingBar ratingBar;

    private Long foodId;
    private boolean isUpdate;

    private float ratingScore;
    private String reviewText;
    private Long reviewId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_review;
    }

    @Override
    protected void associateView() {
        this.ratingBar = findViewById(R.id.reviewActivity_ratingBar);
        this.submitButton = findViewById(R.id.reviewActivity_submitButton);
        this.reviewEditText = findViewById(R.id.reviewActivity_reviewEditText);
    }

    @Override
    protected void initializeView() {
        LayerDrawable stars = (LayerDrawable) this.ratingBar.getProgressDrawable();
        int unfilledColor = getResources().getColor(R.color.kati_middle_gray, this.getTheme());
        stars.getDrawable(0).setTint(unfilledColor);

        Intent intent = this.getIntent();
        this.foodId = intent.getLongExtra("foodId", 0L);
        this.isUpdate = intent.getBooleanExtra("isUpdate",false);
        if(isUpdate){
            Log.d("리뷰 작성 디버그","업데이트모드");
            this.reviewText=intent.getStringExtra("reviewText");
            this.ratingScore=intent.getIntExtra("ratingScore",0);

            this.reviewId=intent.getLongExtra("reviewId",0L);

            this.reviewEditText.setText(this.reviewText);
            this.ratingBar.setRating(this.ratingScore);
            this.submitButton.setOnClickListener(v -> this.updateReview());
        }else
        this.submitButton.setOnClickListener(v -> this.saveReview());
    }

    @Override
    protected boolean isLoginNeeded() {
        return true;
    }

    @Override
    protected void katiEntityUpdatedAndLogin() {

    }

    @Override
    protected void katiEntityUpdatedAndNoLogin() {
    }

    private class CreateAndUpdateReviewCallback extends SimpleLoginRetrofitCallBack<CreateReviewResponse> {

        public CreateAndUpdateReviewCallback(Activity activity) {
            super(activity);
        }

        @Override
        public void onSuccessResponse(Response<CreateReviewResponse> response) {
            KatiDialog.simplerAlertDialog(
                    ReviewActivity.this,
                    "리뷰 저장 완료",
                    "성공적으로 리뷰를 저장하였습니다.",
                    (dialog, which) -> {
                        onBackPressed();
                    }
            );
        }
    }


    private void saveReview() {
        String token= this.dataset.get(KatiEntity.EKatiData.AUTHORIZATION);

        CreateAndUpdateReviewRequest request= new CreateAndUpdateReviewRequest();
        request.setReviewDescription(this.reviewEditText.getText().toString());
        request.setReviewRating((int) this.ratingBar.getRating());
        request.setFoodId(this.foodId);

        KatiRetrofitTool.getAPIWithAuthorizationToken(token).createReview(request).enqueue(JSHRetrofitTool.getCallback(new CreateAndUpdateReviewCallback(this)));
    }

    private void updateReview() {
        String token= this.dataset.get(KatiEntity.EKatiData.AUTHORIZATION);

        CreateAndUpdateReviewRequest request= new CreateAndUpdateReviewRequest();
        request.setReviewDescription(this.reviewEditText.getText().toString());
        request.setReviewRating((int) this.ratingBar.getRating());
        request.setReviewId(this.reviewId);

        KatiRetrofitTool.getAPIWithAuthorizationToken(token).updateReview(request).enqueue(JSHRetrofitTool.getCallback(new CreateAndUpdateReviewCallback(this)));
    }


}