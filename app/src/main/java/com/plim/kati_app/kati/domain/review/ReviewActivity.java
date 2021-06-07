package com.plim.kati_app.kati.domain.review;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.LayerDrawable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.plim.kati_app.R;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitTool;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.kati.crossDomain.domain.view.activity.KatiHasTitleActivity;
import com.plim.kati_app.kati.crossDomain.domain.view.etc.JSHToolBar;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.KatiRetrofitTool;
import com.plim.kati_app.kati.domain.review.model.CreateAndUpdateReviewRequest;
import com.plim.kati_app.kati.domain.review.model.CreateReviewResponse;

import retrofit2.Response;

import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.REVIEW_ACTIVITY_TOO_LONG_MESSAGE;


public class ReviewActivity extends KatiHasTitleActivity {

    private JSHToolBar toolBar;

    private Button submitButton;
    private EditText reviewEditText;
    private RatingBar ratingBar;

    private Long foodId;
    private boolean isUpdate;
    private String foodName;

    private float ratingScore;
    private String reviewText;
    private Long reviewId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_review;
    }

    @Override
    protected void associateView() {
        super.associateView();
        this.toolBar = findViewById(R.id.toolbar);
        this.ratingBar = findViewById(R.id.reviewActivity_ratingBar);
        this.submitButton = findViewById(R.id.reviewActivity_submitButton);
        this.reviewEditText = findViewById(R.id.reviewActivity_reviewEditText);
    }

    @Override
    protected void initializeView() {
        super.initializeView();
        LayerDrawable stars = (LayerDrawable) this.ratingBar.getProgressDrawable();
        int unfilledColor = getResources().getColor(R.color.kati_middle_gray, this.getTheme());
        stars.getDrawable(0).setTint(unfilledColor);

        Intent intent = this.getIntent();
        this.foodId = intent.getLongExtra("foodId", 0L);
        this.isUpdate = intent.getBooleanExtra("isUpdate", false);
        this.foodName = intent.getStringExtra("foodName");
        this.toolBar.setToolBarTitle(this.foodName);
        if (isUpdate) {
            this.reviewText = intent.getStringExtra("reviewText");
            this.ratingScore = intent.getIntExtra("ratingScore", 0);
            this.reviewId = intent.getLongExtra("reviewId", 0L);
            this.reviewEditText.setText(this.reviewText);
            this.ratingBar.setRating(this.ratingScore);
            this.submitButton.setOnClickListener(v -> this.updateReview());
        } else
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


    /**
     * callback
     */
    private class CreateAndUpdateReviewCallback extends SimpleLoginRetrofitCallBack<CreateReviewResponse> {
        public CreateAndUpdateReviewCallback(Activity activity) {
            super(activity);
        }

        @Override
        public void onSuccessResponse(Response<CreateReviewResponse> response) {
            onBackPressed();
        }
    }

    /**
     * method
     */
    private void saveReview() {
        String token = this.dataset.get(KatiEntity.EKatiData.AUTHORIZATION);

        CreateAndUpdateReviewRequest request = new CreateAndUpdateReviewRequest();
        String text = this.reviewEditText.getText().toString();
        if (text.length() > 500) {
            Toast.makeText(this,REVIEW_ACTIVITY_TOO_LONG_MESSAGE,Toast.LENGTH_SHORT);
            return;
        }
        request.setReviewDescription(this.reviewEditText.getText().toString());
        request.setReviewRating((int) this.ratingBar.getRating());
        request.setFoodId(this.foodId);

        KatiRetrofitTool.getAPIWithAuthorizationToken(token).createReview(request).enqueue(JSHRetrofitTool.getCallback(new CreateAndUpdateReviewCallback(this)));
    }

    private void updateReview() {
        String token = this.dataset.get(KatiEntity.EKatiData.AUTHORIZATION);

        CreateAndUpdateReviewRequest request = new CreateAndUpdateReviewRequest();
        request.setReviewDescription(this.reviewEditText.getText().toString());
        request.setReviewRating((int) this.ratingBar.getRating());
        request.setReviewId(this.reviewId);

        KatiRetrofitTool.getAPIWithAuthorizationToken(token).updateReview(request).enqueue(JSHRetrofitTool.getCallback(new CreateAndUpdateReviewCallback(this)));
    }


}