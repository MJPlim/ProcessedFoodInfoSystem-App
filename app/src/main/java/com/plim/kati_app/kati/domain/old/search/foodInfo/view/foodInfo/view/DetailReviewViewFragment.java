package com.plim.kati_app.kati.domain.old.search.foodInfo.view.foodInfo.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.plim.kati_app.R;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitTool;
import com.plim.kati_app.kati.crossDomain.domain.model.Constant;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.KatiDialog;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.KatiFoodFragment;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.KatiRetrofitTool;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.SimpleRetrofitCallBackImpl;
import com.plim.kati_app.kati.domain.old.search.foodInfo.view.foodInfo.model.CreateReviewResponse;
import com.plim.kati_app.kati.domain.old.search.foodInfo.view.foodInfo.model.DeleteReviewRequest;
import com.plim.kati_app.kati.domain.old.search.foodInfo.view.foodInfo.model.ReadReviewDto;
import com.plim.kati_app.kati.domain.old.search.foodInfo.view.foodInfo.model.ReadReviewResponse;
import com.plim.kati_app.kati.domain.old.search.foodInfo.view.foodInfo.model.ReadSummaryResponse;
import com.plim.kati_app.kati.domain.old.search.foodInfo.view.foodInfo.model.UpdateReviewLikeRequest;
import com.plim.kati_app.kati.domain.old.search.foodInfo.view.foodInfo.model.UpdateReviewLikeResponse;
import com.plim.kati_app.kati.domain.old.search.foodInfo.view.review.UpdateReviewActivity;

import java.util.List;
import java.util.Vector;

import retrofit2.Response;

public class DetailReviewViewFragment extends KatiFoodFragment {

    //attribute
    private int currentPageNum = 1;

    // Associate
    // View
    private ReviewRecyclerAdapter adapter;
    private TextView reviewTotalCount, pageNum;
    private Button prevPageButton, nextPageButton;
    private RecyclerView recyclerView;
    private TabLayout categoryTabLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_detail_review_view;
    }

    @Override
    protected void associateView(View view) {
        this.adapter = new ReviewRecyclerAdapter();
        this.categoryTabLayout = view.findViewById(R.id.reviewViewFragment_tabLayout);
        this.recyclerView = view.findViewById(R.id.reviewViewFragment_recyclerView);
        this.reviewTotalCount = view.findViewById(R.id.reviewViewFragment_reviewTotalCount);
        this.pageNum = view.findViewById(R.id.reviewViewFragment_pageNum);
        this.prevPageButton = view.findViewById(R.id.reviewViewFragment_prevPageButton);
        this.nextPageButton = view.findViewById(R.id.reviewViewFragment_nextPageButton);
    }

    @Override
    protected void initializeView() {
        int i = 0;
        for (Constant.EReviewCateGory cateGory : Constant.EReviewCateGory.values()) {
            this.categoryTabLayout.addTab(this.categoryTabLayout.newTab().setText(cateGory.getName()), i);
        }
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        this.recyclerView.setAdapter(this.adapter);


        this.prevPageButton.setOnClickListener(v -> {
            this.currentPageNum--;
            this.saveFoodDetail();
        });

        this.nextPageButton.setOnClickListener(v -> {
            this.currentPageNum++;
            this.saveFoodDetail();
        });
    }



    @Override
    protected void katiEntityUpdated() {

    }

    @Override
    protected boolean isLoginNeeded() {
        return false;
    }

    @Override
    protected void katiEntityUpdatedAndLogin() {

    }

    @Override
    protected void katiEntityUpdatedAndNoLogin() {

    }

    @Override
    public void foodModelDataUpdated() {
        if (this.dataset.containsKey(KatiEntity.EKatiData.AUTHORIZATION)) {
            this.getReviews(this.dataset.get(KatiEntity.EKatiData.AUTHORIZATION));
        } else this.getReviews();
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


    /**
     * 리뷰들을 불러온다.
     */
    private void refresh() {
        if (this.dataset.containsKey(KatiEntity.EKatiData.AUTHORIZATION))
            this.getReviews(this.dataset.get(KatiEntity.EKatiData.AUTHORIZATION));
        else this.getReviews();
    }

    private void getReviews(String token) {
        KatiRetrofitTool.getAPIWithAuthorizationToken(token).readReviewByUser(this.foodDetailResponse.getFoodId(), this.currentPageNum)
                .enqueue(JSHRetrofitTool.getCallback(new ReadUserReviewCallback(this.getActivity())));
    }

    private void getReviews() {
        KatiRetrofitTool.getAPI().readReview(this.foodDetailResponse.getFoodId(), this.currentPageNum)
                .enqueue(JSHRetrofitTool.getCallback(new ReadReviewCallback(this.getActivity())));
    }


    private void updateReviews(Long reviewId) {
        //리뷰수정
        Intent intent = new Intent(this.getActivity(), UpdateReviewActivity.class);
        intent.putExtra("reviewId", reviewId);
        intent.putExtra("foodId", this.foodDetailResponse.getFoodId());
        startActivity(intent);
    }

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

    private void onReadReviewResponse(Response<ReadReviewDto> response){
        ReadReviewDto reviewDto = response.body();

        ReadSummaryResponse readSummaryResponse = reviewDto.getReadSummaryResponse();
        int findReviewCount = readSummaryResponse.getReviewCount();
        reviewTotalCount.setText("리뷰 총 " + findReviewCount + "개");
        int findReviewPageCount = readSummaryResponse.getReviewPageCount();
        pageNum.setText(currentPageNum + "/" + findReviewPageCount);
        prevPageButton.setEnabled(currentPageNum <= 1 ? false : true);
        nextPageButton.setEnabled(currentPageNum == findReviewPageCount || findReviewPageCount == 0 ? false : true);


        foodModel.getReadSummaryResponse().setValue(readSummaryResponse);
        saveReadSummary();

        List<ReadReviewResponse> reviewList = reviewDto.getReadReviewResponse();
        Vector<ReadReviewResponse> vector = new Vector<>();
        vector.addAll(reviewList);
        adapter.setItems(vector);
    }

    private class LikeReviewCallback extends SimpleLoginRetrofitCallBack<UpdateReviewLikeResponse> {
        private boolean likeCheck;

        public LikeReviewCallback(Activity activity, boolean likeCheck) {
            super(activity);
            this.likeCheck = likeCheck;
        }

        @Override
        public void onSuccessResponse(Response<UpdateReviewLikeResponse> response) {
            KatiDialog.simplerAlertDialog(
                    getActivity(),
                    "좋아요를 눌렀습니다.",
                    !likeCheck ? "좋아요를 저장하였습니다." : "좋아요를 취소하였습니다.",
                    (dialog, which) -> {
                        refresh();
                    }
            );
        }
    }

    private class DeleteReviewCallback extends SimpleLoginRetrofitCallBack<CreateReviewResponse> {

        public DeleteReviewCallback(Activity activity) {
            super(activity);
        }

        @Override
        public void onSuccessResponse(Response<CreateReviewResponse> response) {
            KatiDialog.simplerAlertDialog(
                    getActivity(),
                    "리뷰 삭제",
                    "리뷰를 성공적으로 삭제하였습니다.",
                    (dialog, which) -> {
                        getReviews();
                    }
            );
        }
    }

    private class ReviewRecyclerAdapter extends RecyclerView.Adapter<ReviewRecyclerAdapter.ReviewViewHolder> {

        private Vector<ReadReviewResponse> vector;

        private ReviewRecyclerAdapter() {
            this.vector = new Vector<>();
        }

        @NonNull
        @Override
        public ReviewRecyclerAdapter.ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.item_review, parent, false);
            ReviewViewHolder viewHolder = new ReviewViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ReviewRecyclerAdapter.ReviewViewHolder holder, int position) {
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

        /**
         * 뷰 홀더
         */
        private class ReviewViewHolder extends RecyclerView.ViewHolder {


            private TextView productName, date, score, reviewContent, like;
            private Button editButton, deleteButton;
            private ImageView likeImageButton;


            public ReviewViewHolder(@NonNull View itemView) {
                super(itemView);
                this.productName = itemView.findViewById(R.id.reviewItem_ProductNameTextView);
                this.date = itemView.findViewById(R.id.reviewItem_EditDateTextView);
                this.score = itemView.findViewById(R.id.reviewItem_reviewScoreTextView);
                this.reviewContent = itemView.findViewById(R.id.reviewItem_reviewTextTextView);
                this.like = itemView.findViewById(R.id.reviewItem_reviewLikeTextView);
                this.editButton = itemView.findViewById(R.id.reviewItem_editButton);
                this.likeImageButton = itemView.findViewById(R.id.reviewItem_reviewLikeImageView);
                this.deleteButton = itemView.findViewById(R.id.reviewItem_deleteButton);
            }

            public void setValue(ReadReviewResponse value) {
                this.productName.setText(value.getUserName());
                this.date.setText(value.getReviewModifiedDate() == null ?
                        value.getReviewCreatedDate().toString() + "작성" :
                        value.getReviewModifiedDate().toString() + "수정");
                this.score.setText(value.getReviewRating() + "");
                this.reviewContent.setText(value.getReviewDescription());
                this.like.setText(value.getLikeCount() + "");

                this.deleteButton.setEnabled(value.isUserCheck());
                this.editButton.setEnabled(value.isUserCheck());

                this.deleteButton.setOnClickListener(v -> deleteReview(dataset.get(KatiEntity.EKatiData.AUTHORIZATION), value.getReviewId()));

                this.likeImageButton.clearColorFilter();
                int color = getResources().getColor(value.isUserLikeCheck() ? R.color.kati_orange : R.color.gray, getContext().getTheme());
                this.likeImageButton.setColorFilter(color, PorterDuff.Mode.SRC_IN);


                this.like.setOnClickListener(dataset.containsKey(KatiEntity.EKatiData.AUTHORIZATION) ? v -> like(dataset.get(KatiEntity.EKatiData.AUTHORIZATION), value.getReviewId(), value.isUserLikeCheck()) : null);
                this.likeImageButton.setOnClickListener(dataset.containsKey(KatiEntity.EKatiData.AUTHORIZATION) ? v -> like(dataset.get(KatiEntity.EKatiData.AUTHORIZATION), value.getReviewId(), value.isUserLikeCheck()) : null);

                this.editButton.setOnClickListener(v -> {
                    updateReviews(value.getReviewId());
                });

            }
        }
    }
}