package com.plim.kati_app.domain.view.search.food.detail.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
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
import com.plim.kati_app.domain.model.RegisterActivityViewModel;
import com.plim.kati_app.domain.model.WithdrawResponse;
import com.plim.kati_app.domain.model.dto.CreateReviewResponse;
import com.plim.kati_app.domain.model.dto.DeleteReviewRequest;
import com.plim.kati_app.domain.model.dto.ReadReviewDto;
import com.plim.kati_app.domain.model.dto.ReadReviewRequest;
import com.plim.kati_app.domain.model.dto.ReadReviewResponse;
import com.plim.kati_app.domain.model.dto.ReviewViewmodel;
import com.plim.kati_app.domain.model.dto.UpdateReviewLikeRequest;
import com.plim.kati_app.domain.model.dto.UpdateReviewLikeResponse;
import com.plim.kati_app.domain.model.room.KatiData;
import com.plim.kati_app.domain.model.room.KatiDatabase;
import com.plim.kati_app.domain.view.search.food.review.WriteReviewActivity;
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
    private String manufacturer;
    private String foodName;
    private String image;

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
    public void setFragmentRequestKey() {
        this.fragmentRequestKey = "reviewBundle";
    }

    @Override
    public void ResultParse(String requestKey, Bundle result) {
        Long foodId = result.getLong("foodId");
        this.foodName=result.getString("foodName");
        this.manufacturer=result.getString("manufacturer");
        this.image=result.getString("image");
        this.foodId = foodId;
        Log.d("리뷰,foodId", foodId + "");
        this.getReviews();
    }

    private void refresh(){
        this.getReviews();
    }

    private void deleteReview(Long reviewId){
        new Thread(()->{

            DeleteReviewRequest request= new DeleteReviewRequest();
            request.setReviewId(reviewId);


            KatiDatabase database= KatiDatabase.getAppDatabase(getContext());
            String token = database.katiDataDao().getValue(KatiDatabase.AUTHORIZATION);

            Call<CreateReviewResponse> call =RestAPIClient.getApiService2(token).deleteReview(request);
            call.enqueue(new Callback<CreateReviewResponse>() {
                @Override
                public void onResponse(Call<CreateReviewResponse> call, Response<CreateReviewResponse> response) {

                    if(!response.isSuccessful()){
                        KatiDialog.showRetrofitNotSuccessDialog(getContext(),response.code()+"",null).showDialog();
                    }
                    else
                        KatiDialog.simpleAlertDialog(
                                getContext(),
                                "리뷰 삭제",
                                "리뷰를 성공적으로 삭제하였습니다.",
                                (dialog, which)->{
                                    refresh();
                                },
                                getContext().getResources().getColor(R.color.kati_coral,getContext().getTheme())
                        ).showDialog();
                }

                @Override
                public void onFailure(Call<CreateReviewResponse> call, Throwable t) {
                    KatiDialog.showRetrofitFailDialog(getContext(),t.getMessage(),null);
                }
            });
        }).start();
    }

    private void like(Long reviewId, boolean likeCheck) {
        new Thread(()->{
        UpdateReviewLikeRequest request= new UpdateReviewLikeRequest();
        request.setReviewId(reviewId);
        request.setLikeCheck(likeCheck);

        KatiDatabase database= KatiDatabase.getAppDatabase(getContext());
        String token = database.katiDataDao().getValue(KatiDatabase.AUTHORIZATION);

    Call<UpdateReviewLikeResponse> call =RestAPIClient.getApiService2(token).likeReview(request);
    call.enqueue(new Callback<UpdateReviewLikeResponse>() {
        @Override
        public void onResponse(Call<UpdateReviewLikeResponse> call, Response<UpdateReviewLikeResponse> response) {

            if(!response.isSuccessful()){
                KatiDialog.showRetrofitNotSuccessDialog(getContext(),response.code()+"",null).showDialog();
            }
            else
                KatiDialog.simpleAlertDialog(
                        getContext(),
                        "좋아요를 눌렀습니다.",
                        !likeCheck?"좋아요를 저장하였습니다.":"좋아요를 취소하였습니다.",
                        (dialog, which)->{
                            refresh();
                        },
                        getContext().getResources().getColor(R.color.kati_coral,getContext().getTheme())
                ).showDialog();
        }

        @Override
        public void onFailure(Call<UpdateReviewLikeResponse> call, Throwable t) {
KatiDialog.showRetrofitFailDialog(getContext(),t.getMessage(),null);
        }
    });
        }).start();
    }






    /**
     * 리뷰들을 불러온다.
     */
    private void getReviews() {
        new Thread(() -> {
                KatiDatabase database = KatiDatabase.getAppDatabase(getContext());
                String token = database.katiDataDao().getValue(KatiDatabase.AUTHORIZATION);
                if(token!=null)this.isLogin=true;
                Log.d("토큰토ㅌ큰",token+"");

            Call<ReadReviewDto> listCall;
            if (!this.isLogin) {
                listCall = RestAPIClient.getApiService().readReview(this.foodId, this.currentPageNum);
            } else {
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

                            prevPageButton.setEnabled(currentPageNum <= 1 ? false : true);
                            nextPageButton.setEnabled(currentPageNum == findReviewPageCount||findReviewPageCount==0 ? false : true);

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
    private void updateReviews(Long foodId,Long reviewId,String image,String manufacturer, String foodName, String value, int score){
        Intent intent= new Intent(this.getActivity(), WriteReviewActivity.class);

        intent.putExtra("score",score);
        intent.putExtra("foodId",foodId);
        intent.putExtra("reviewId",reviewId);
        intent.putExtra("image",image);

        intent.putExtra("manufacturer",manufacturer);
        intent.putExtra("product",foodName);
        intent.putExtra("value",value);
        startActivity(intent);
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
                this.deleteButton= itemView.findViewById(R.id.reviewItem_deleteButton);
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

                this.deleteButton.setOnClickListener(v->deleteReview(value.getReviewId()));

                this.likeImageButton.clearColorFilter();
                int color= getResources().getColor(value.isUserLikeCheck()?R.color.kati_orange:R.color.gray,getContext().getTheme());
                this.likeImageButton.setColorFilter(color, PorterDuff.Mode.SRC_IN);


                this.like.setOnClickListener(isLogin ? v -> like(value.getReviewId(),value.isUserLikeCheck()) : null);
                this.likeImageButton.setOnClickListener(isLogin ? v -> like(value.getReviewId(),value.isUserLikeCheck()) : null);

                this.editButton.setOnClickListener(v->{
                    updateReviews(value.getFoodId(),value.getReviewId(),image,manufacturer,foodName,value.getReviewDescription(),(int)value.getReviewRating());

                });

            }
        }
    }
}