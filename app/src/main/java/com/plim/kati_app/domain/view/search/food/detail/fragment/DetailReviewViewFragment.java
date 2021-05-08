package com.plim.kati_app.domain.view.search.food.detail.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.plim.kati_app.R;
import com.plim.kati_app.constants.Constant;
import com.plim.kati_app.constants.Constant_yun;
import com.plim.kati_app.domain.asset.GetResultFragment;
import com.plim.kati_app.domain.asset.KatiDialog;
import com.plim.kati_app.domain.model.WithdrawResponse;
import com.plim.kati_app.domain.model.dto.ReadReviewDto;
import com.plim.kati_app.domain.model.dto.ReadReviewRequest;
import com.plim.kati_app.domain.model.dto.ReadReviewResponse;
import com.plim.kati_app.domain.model.room.KatiData;
import com.plim.kati_app.domain.model.room.KatiDatabase;
import com.plim.kati_app.domain.view.user.login.RetrofitClient;
import com.plim.kati_app.tech.RestAPI;
import com.plim.kati_app.tech.RestAPIClient;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.plim.kati_app.constants.Constant_yun.FOOD_SEARCH_RESULT_LIST_FRAGMENT_FAILURE_DIALOG_TITLE;

/**
 * 리뷰를 보여주는 fragment
 */
public class DetailReviewViewFragment extends GetResultFragment {

    private int currentPageNum = 1;
    private Long foodId;
    private boolean isLogin = false;

    private TextView reviewTotalCount, pageNum;
    private Button prevPageButton, nextPageButton;

    private ReviewRecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private TabLayout categoryTabLayout;

    public DetailReviewViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_review_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.adapter = new ReviewRecyclerAdapter();

        this.categoryTabLayout = view.findViewById(R.id.reviewViewFragment_tabLayout);
        this.recyclerView = view.findViewById(R.id.reviewViewFragment_recyclerView);

        this.reviewTotalCount = view.findViewById(R.id.reviewViewFragment_reviewTotalCount);
        this.pageNum = view.findViewById(R.id.reviewViewFragment_pageNum);
        this.prevPageButton = view.findViewById(R.id.reviewViewFragment_prevPageButton);
        this.nextPageButton = view.findViewById(R.id.reviewViewFragment_nextPageButton);


        int i = 0;
        for (Constant_yun.EReviewCateGory cateGory : Constant_yun.EReviewCateGory.values()) {
            categoryTabLayout.addTab(categoryTabLayout.newTab().setText(cateGory.getName()), i);
        }

        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        this.recyclerView.setAdapter(this.adapter);

        this.prevPageButton.setOnClickListener(v -> {
            this.currentPageNum--;
            this.getReviews();
        });

        this.nextPageButton.setOnClickListener(v -> {
            this.currentPageNum++;
            this.getReviews();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        new Thread(() -> {
            KatiDatabase database = KatiDatabase.getAppDatabase(getContext());
            String token = database.katiDataDao().getValue(KatiDatabase.AUTHORIZATION);
            if (token != null) this.isLogin = true;
        });

    }

    @Override
    public void setFragmentRequestKey() {
        this.fragmentRequestKey = "reviewBundle";
    }

    @Override
    public void ResultParse(String requestKey, Bundle result) {
        Long foodId = result.getLong("foodId");
        this.foodId = foodId;
        Log.d("리뷰,foodId", foodId + "");
        this.getReviews();
    }

    private void like() {
    }

    /**
     * 리뷰들을 불러온다.
     */
    private void getReviews() {
        new Thread(() -> {

            Call<ReadReviewDto> listCall;
            if (!this.isLogin) {
                Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(Constant.URL)
                        .build();
                RestAPI service = retrofit.create(RestAPI.class);
                listCall = service.readReview(this.foodId, this.currentPageNum);
            } else {
                KatiDatabase database = KatiDatabase.getAppDatabase(getContext());
                String token = database.katiDataDao().getValue(KatiDatabase.AUTHORIZATION);

                listCall = RestAPIClient.getApiService2(token).readReviewByUser(this.foodId, this.currentPageNum);

            }
            listCall.enqueue(new Callback<ReadReviewDto>() {
                @Override
                public void onResponse(Call<ReadReviewDto> call, Response<ReadReviewDto> response) {
                    if (!response.isSuccessful()) {
                        KatiDialog.showRetrofitNotSuccessDialog(getContext(), Integer.toString(response.code()), null);
                    } else {
                        if (response.body() == null) {
                            Log.d("디버그", "널임");
                        } else {

                            ReadReviewDto reviewDto = response.body();

                            int findReviewCount = reviewDto.getReviewCount().getFindReviewCount();
                            reviewTotalCount.setText("리뷰 총 " + findReviewCount + "개");

                            int findReviewPageCount = reviewDto.getReviewCount().getFindReviewPageCount();
                            pageNum.setText(currentPageNum + "/" + findReviewPageCount);

                            prevPageButton.setEnabled(currentPageNum == 1 ? false : true);
                            nextPageButton.setEnabled(currentPageNum == findReviewPageCount ? false : true);

                            List<ReadReviewResponse> reviewList = reviewDto.getReadReviewResponse();
                            Vector<ReadReviewResponse> vector = new Vector<>();
                            vector.addAll(reviewList);
                            adapter.setItems(vector);

                        }
                    }


                    if (isLogin) {
                        new Thread(() -> {
                            String token = response.headers().get(KatiDatabase.AUTHORIZATION);
                            KatiDatabase database = KatiDatabase.getAppDatabase(getContext());
                            database.katiDataDao().insert(new KatiData(KatiDatabase.AUTHORIZATION, token));
                        }).start();
                    }
                }

                @Override
                public void onFailure(Call<ReadReviewDto> call, Throwable t) {
                    KatiDialog.showRetrofitFailDialog(getContext(), t.getMessage(), null).showDialog();
                }
            });
        }).start();
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
            private Button editButton;
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
            }

            public void setValue(ReadReviewResponse value) {
                this.productName.setText(value.getUserName());
                this.date.setText(value.getReviewModifiedDate() == null ?
                        value.getReviewCreatedDate().toString() + "작성" :
                        value.getReviewModifiedDate().toString() + "수정");
                this.score.setText(value.getReviewRating() + "");
                this.reviewContent.setText(value.getReviewDescription());
                this.like.setText(value.getLikeCount() + "");


                this.editButton.setEnabled(value.isUserCheck());


                this.like.setOnClickListener((!value.isUserLikeCheck()) && isLogin ? v -> like() : null);
                this.likeImageButton.setOnClickListener((!value.isUserLikeCheck()) && isLogin ? v -> like() : null);


            }
        }
    }
}