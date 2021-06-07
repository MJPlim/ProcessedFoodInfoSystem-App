package com.plim.kati_app.kati.domain.foodDetail;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.plim.kati_app.R;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitTool;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.KatiFoodFragment;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.KatiRetrofitTool;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.SimpleRetrofitCallBackImpl;
import com.plim.kati_app.kati.domain.foodDetail.adapter.ReviewRecyclerAdapter;
import com.plim.kati_app.kati.domain.review.ReviewActivity;
import com.plim.kati_app.kati.domain.review.model.CreateReviewResponse;
import com.plim.kati_app.kati.domain.nnew.main.search.model.DeleteReviewRequest;
import com.plim.kati_app.kati.domain.nnew.main.search.model.ReadReviewDto;
import com.plim.kati_app.kati.domain.nnew.main.search.model.ReadReviewResponse;
import com.plim.kati_app.kati.domain.nnew.main.search.model.ReadSummaryResponse;
import com.plim.kati_app.kati.domain.nnew.main.search.model.UpdateReviewLikeRequest;
import com.plim.kati_app.kati.domain.nnew.main.search.model.UpdateReviewLikeResponse;

import java.util.List;
import java.util.Vector;

import retrofit2.Response;

import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.REVIEW_ACTIVITY_EXTRA_FOOD_ID;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.REVIEW_ACTIVITY_EXTRA_FOOD_NAME;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.REVIEW_ACTIVITY_EXTRA_RATING;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.REVIEW_ACTIVITY_EXTRA_REVIEW_ID;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.REVIEW_ACTIVITY_EXTRA_REVIEW_TEXT;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.REVIEW_ACTIVITY_EXTRA_UPDATE;

public class ReviewFragment extends KatiFoodFragment {

    //associate
    //view
    private Button writeReviewButton;
    private RecyclerView recyclerView;

    private ProgressBar firstProgressBar, secondProgressBar, thirdProgressBar, fourthProgressBar, fifthProgressBar;
    private TextView firstValueTextView, secondValueTextView, thirdValueTextView, fourthValueTextView, fifthValueTextView,
            ratingTextView, noRereviewTextView;
    private RatingBar ratingBar;

    private NestedScrollView nestedScrollView;

    //working variable
    private int currentPageNum = 1;
    private boolean hasNext = false, clear = false;

    //model
    private Vector<ReadReviewResponse> vector;

    //listener
    private View.OnClickListener updateListener, likeListener, unLikeListener, deleteListener;

    //adapter
    private ReviewRecyclerAdapter adapter;

    public ReviewFragment() {
        this.vector = new Vector<>();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_review;
    }

    @Override
    protected void associateView(View view) {
        this.noRereviewTextView = view.findViewById(R.id.reviewFragment_noReview);
        this.firstProgressBar = view.findViewById(R.id.reviewSummaryWidget_firstProgressBar);
        this.secondProgressBar = view.findViewById(R.id.reviewSummaryWidget_secondProgressBar);
        this.thirdProgressBar = view.findViewById(R.id.reviewSummaryWidget_thirdProgressBar);
        this.fourthProgressBar = view.findViewById(R.id.reviewSummaryWidget_fourthProgressBar);
        this.fifthProgressBar = view.findViewById(R.id.reviewSummaryWidget_fifthProgressBar);

        this.ratingTextView = view.findViewById(R.id.reviewSummaryWidget_ratingTextView);
        this.ratingBar = view.findViewById(R.id.reviewSummaryWidget_ratingBar);

        this.firstValueTextView = view.findViewById(R.id.reviewSummaryWidget_firstValueTextView);
        this.secondValueTextView = view.findViewById(R.id.reviewSummaryWidget_secondValueTextView);
        this.thirdValueTextView = view.findViewById(R.id.reviewSummaryWidget_thirdValueTextView);
        this.fourthValueTextView = view.findViewById(R.id.reviewSummaryWidget_fourthValueTextView);
        this.fifthValueTextView = view.findViewById(R.id.reviewSummaryWidget_fifthValueTextView);

        this.writeReviewButton = view.findViewById(R.id.reviewSummaryWidget_writeReviewButton);
        this.recyclerView = view.findViewById(R.id.reviewFragment_recyclerView);

        this.nestedScrollView = view.findViewById(R.id.reviewFragment_nestedScrollView);
    }

    @Override
    protected void initializeView() {
        LinearLayoutManager manager = new LinearLayoutManager(this.getContext());
        this.writeReviewButton.setOnClickListener(v -> this.moveToReviewActivity());
        this.recyclerView.setLayoutManager(manager);
        this.recyclerView.setNestedScrollingEnabled(false);

        this.nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (v.getChildAt(v.getChildCount() - 1) != null) {
                if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) &&
                        scrollY > oldScrollY) {
                    loadMore();
                }
            }
        });


    }

    @Override
    protected void katiEntityUpdatedAndLogin() {
        super.katiEntityUpdatedAndLogin();
        this.writeReviewButton.setVisibility(View.VISIBLE);

        this.deleteListener = v -> deleteReview(dataset.get(KatiEntity.EKatiData.AUTHORIZATION), (Long) v.getTag());
        this.likeListener = v -> like(dataset.get(KatiEntity.EKatiData.AUTHORIZATION), (Long) v.getTag(), true);
        this.unLikeListener = v -> like(dataset.get(KatiEntity.EKatiData.AUTHORIZATION), (Long) v.getTag(), false);
        this.updateListener = v -> updateReviews((Long) v.getTag());

        this.adapter = new ReviewRecyclerAdapter(this.getActivity(), deleteListener, unLikeListener, likeListener, updateListener);
        this.recyclerView.setAdapter(this.adapter);
    }

    @Override
    protected void katiEntityUpdatedAndNoLogin() {
        this.writeReviewButton.setVisibility(View.GONE);

        this.adapter = new ReviewRecyclerAdapter(this.getActivity(), null, null, null, null);
        this.recyclerView.setAdapter(this.adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.vector.clear();
    }

    @Override
    public void foodModelDataUpdated() {
        this.refresh();
    }

    @Override
    protected void summaryDataUpdated() {
        setProgressBar(this.firstProgressBar, (float) readSummaryResponse.getOneCount() / readSummaryResponse.getReviewCount());
        setProgressBar(this.secondProgressBar, (float) readSummaryResponse.getTwoCount() / readSummaryResponse.getReviewCount());
        setProgressBar(this.thirdProgressBar, (float) readSummaryResponse.getThreeCount() / readSummaryResponse.getReviewCount());
        setProgressBar(this.fourthProgressBar, (float) readSummaryResponse.getFourCount() / readSummaryResponse.getReviewCount());
        setProgressBar(this.fifthProgressBar, (float) readSummaryResponse.getFiveCount() / readSummaryResponse.getReviewCount());

        this.firstValueTextView.setText(String.valueOf(readSummaryResponse.getOneCount()));
        this.secondValueTextView.setText(String.valueOf(readSummaryResponse.getTwoCount()));
        this.thirdValueTextView.setText(String.valueOf(readSummaryResponse.getThreeCount()));
        this.fourthValueTextView.setText(String.valueOf(readSummaryResponse.getFourCount()));
        this.fifthValueTextView.setText(String.valueOf(readSummaryResponse.getFiveCount()));

        this.ratingTextView.setText(String.valueOf(readSummaryResponse.getAvgRating()));
        this.ratingBar.setRating(readSummaryResponse.getAvgRating());

        this.noRereviewTextView.setVisibility(readSummaryResponse.getAvgRating() > 0 ? View.INVISIBLE : View.VISIBLE);
    }

    /**
     * callback
     */
    private class ReadReviewCallback extends SimpleRetrofitCallBackImpl<ReadReviewDto> {
        public ReadReviewCallback(Activity activity) {
            super(activity);
        }

        @Override
        public void onSuccessResponse(Response<ReadReviewDto> response) {
            onReadReviewResponse(response);
        }

    }

    private class ReadUserReviewCallback extends SimpleLoginRetrofitCallBack<ReadReviewDto> {
        public ReadUserReviewCallback(Activity activity) {
            super(activity);
        }

        @Override
        public void onSuccessResponse(Response<ReadReviewDto> response) {
            onReadReviewResponse(response);
        }

    }

    private class LikeReviewCallback extends SimpleLoginRetrofitCallBack<UpdateReviewLikeResponse> {
        private boolean likeCheck;

        public LikeReviewCallback(Activity activity, boolean likeCheck) {
            super(activity);
            this.likeCheck = likeCheck;
        }

        @Override
        public void onSuccessResponse(Response<UpdateReviewLikeResponse> response) {
            refresh();
        }
    }

    private class DeleteReviewCallback extends SimpleLoginRetrofitCallBack<CreateReviewResponse> {
        public DeleteReviewCallback(Activity activity) {
            super(activity);
        }

        @Override
        public void onSuccessResponse(Response<CreateReviewResponse> response) {
            refresh();
        }
    }

    /**
     * method
     */
    private void getReviews(String token) {
        KatiRetrofitTool.getAPIWithAuthorizationToken(token).readReviewByUser(this.foodDetailResponse.getFoodId(), this.currentPageNum)
                .enqueue(JSHRetrofitTool.getCallback(new ReadUserReviewCallback(this.getActivity())));
    }

    private void getReviews() {
        KatiRetrofitTool.getAPI().readReview(this.foodDetailResponse.getFoodId(), this.currentPageNum)
                .enqueue(JSHRetrofitTool.getCallback(new ReadReviewCallback(this.getActivity())));
    }

    private void deleteReview(String token, Long reviewId) {
        DeleteReviewRequest request = new DeleteReviewRequest(reviewId);
        KatiRetrofitTool.getAPIWithAuthorizationToken(token).deleteReview(request).enqueue(JSHRetrofitTool.getCallback(new DeleteReviewCallback(getActivity())));
    }

    private void like(String token, Long reviewId, boolean likeCheck) {
        UpdateReviewLikeRequest request = new UpdateReviewLikeRequest();
        request.setReviewId(reviewId);
        request.setLikeCheck(likeCheck);
        KatiRetrofitTool.getAPIWithAuthorizationToken(token).likeReview(request).enqueue(JSHRetrofitTool.getCallback(new LikeReviewCallback(this.getActivity(), likeCheck)));
    }

    private void load() {
        if (!this.dataset.get(KatiEntity.EKatiData.AUTHORIZATION).equals(KatiEntity.EKatiData.NULL.name())) {
            this.getReviews(this.dataset.get(KatiEntity.EKatiData.AUTHORIZATION));
        } else {
            this.getReviews();
        }
    }

    private void refresh() {
        nestedScrollView.scrollTo(0, 0);
        this.currentPageNum = 1;
        this.clear = true;
        this.load();
    }

    private void loadMore() {
        this.currentPageNum++;
        this.clear = false;
        this.load();
    }

    private void onReadReviewResponse(Response<ReadReviewDto> response) {
        ReadReviewDto reviewDto = response.body();

        ReadSummaryResponse readSummaryResponse = reviewDto.getReadSummaryResponse();
        int findReviewPageCount = readSummaryResponse.getReviewPageCount();
        hasNext = (currentPageNum < findReviewPageCount);
        foodModel.getReadSummaryResponse().setValue(readSummaryResponse);
        saveReadSummary();

        List<ReadReviewResponse> reviewList = reviewDto.getReadReviewResponse();

        if (clear) {
            vector.clear();
        }
        vector.addAll(reviewList);
        adapter.setItems(vector);
    }


    private void updateReviews(Long reviewId) {
        //리뷰수정
        for (ReadReviewResponse review : this.vector) {
            if (review.getReviewId().equals(reviewId)) {
                Intent intent = new Intent(this.getActivity(), ReviewActivity.class);
                intent.putExtra(REVIEW_ACTIVITY_EXTRA_REVIEW_ID, reviewId);
                intent.putExtra(REVIEW_ACTIVITY_EXTRA_FOOD_ID, this.foodDetailResponse.getFoodId());
                intent.putExtra(REVIEW_ACTIVITY_EXTRA_FOOD_NAME, foodDetailResponse.getFoodName());
                intent.putExtra(REVIEW_ACTIVITY_EXTRA_UPDATE, true);
                intent.putExtra(REVIEW_ACTIVITY_EXTRA_RATING, (int) review.getReviewRating());
                intent.putExtra(REVIEW_ACTIVITY_EXTRA_REVIEW_TEXT, review.getReviewDescription());
                startActivity(intent);
            }
        }
    }

    public void moveToReviewActivity() {
        Intent intent = new Intent(this.getActivity(), ReviewActivity.class);
        intent.putExtra(REVIEW_ACTIVITY_EXTRA_UPDATE, false);
        intent.putExtra(REVIEW_ACTIVITY_EXTRA_FOOD_ID, foodDetailResponse.getFoodId());
        intent.putExtra(REVIEW_ACTIVITY_EXTRA_FOOD_NAME, foodDetailResponse.getFoodName());
        this.startActivity(intent);
    }

    public void setProgressBar(ProgressBar progressBar, float percent) {
        progressBar.setMax(100);
        progressBar.setMin(0);
        progressBar.setProgress((int) (percent * 100));
    }
}