package com.plim.kati_app.kati.domain.mypage.myReview.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.plim.kati_app.R;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitCallback;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitTool;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.KatiDialog;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.LoadingDialog;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.KatiViewModelFragment;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.KatiRetrofitTool;
import com.plim.kati_app.kati.domain.mypage.myFavorite.adapter.UserFavoriteFoodRecyclerAdapter;
import com.plim.kati_app.kati.domain.mypage.myFavorite.model.UserFavoriteResponse;
import com.plim.kati_app.kati.domain.mypage.myFavorite.view.UserFavoriteFragment;
import com.plim.kati_app.kati.domain.mypage.myReview.adapter.UserReviewRecyclerAdapter;
import com.plim.kati_app.kati.domain.mypage.myReview.model.ReadReviewResponse;


import org.json.JSONObject;

import java.util.List;
import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.FOOD_SEARCH_RESULT_LIST_FRAGMENT_FAILURE_DIALOG_TITLE;


/**
 * 마이페이지 리뷰 프래그먼트.
 *
 */

public class UserReviewFragment extends KatiViewModelFragment {
    private TextView reviewNum;
    private RecyclerView foodReviewRecyclerView;
    private UserReviewRecyclerAdapter reviewRecyclerAdapter;
    private LoadingDialog dialog;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mypage_review_food_list;
    }

    @Override
    protected void associateView(View view) {
        this.foodReviewRecyclerView = view.findViewById(R.id.myPage_foodReviewRecyclerView);
        this.reviewNum = view.findViewById(R.id.myPage_review_num);

    }

    @Override
    protected void initializeView() {
        this.reviewRecyclerAdapter = new UserReviewRecyclerAdapter(this.getActivity());
        this.dialog = new LoadingDialog(getContext());
        this.foodReviewRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        this.foodReviewRecyclerView.setAdapter(this.reviewRecyclerAdapter);
        this.getUserReview();
    }

    @Override
    protected void katiEntityUpdated() {

    }

    private void getUserReview() {
        KatiRetrofitTool.getAPIWithAuthorizationToken(dataset.get(KatiEntity.EKatiData.AUTHORIZATION)).getUserReview()
                .enqueue(JSHRetrofitTool.getCallback(new ReadReviewResponseCallback()));
    }

    private class ReadReviewResponseCallback implements JSHRetrofitCallback<List<ReadReviewResponse>> {
        @Override
        public void onSuccessResponse(Response<List<ReadReviewResponse>> response) {
            Vector<ReadReviewResponse> items = new Vector<>(response.body());
            dialog.hide();
            reviewRecyclerAdapter.setItems(items);
            foodReviewRecyclerView.setAdapter(reviewRecyclerAdapter);
            reviewNum.setText("총 "+items.size()+"개");
        }
        @Override
        public void onFailResponse(Response<List<ReadReviewResponse>> response) {
            try {
                JSONObject jObjError = new JSONObject(response.errorBody().string());
                Toast.makeText(getContext(), jObjError.getString("error-message"), Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        @Override
        public void onConnectionFail(Throwable t) {
            KatiDialog.simplerAlertDialog(getActivity(),
                    FOOD_SEARCH_RESULT_LIST_FRAGMENT_FAILURE_DIALOG_TITLE, t.getMessage(),
                    null
            );
        }
    }


//    KatiDatabase database = KatiDatabase.getAppDatabase(getContext());
//    private TextView reviewNum;
//    private String token;
//    private com.plim.kati_app.domain.view.user.myPage.UserReviewFragment.ReviewRecyclerAdapter adapter;
//    private RecyclerView recyclerView;
//    private boolean isLogin = true;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_mypage_review_food_list, container, false);
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        new Thread(() -> {
//            // 토큰값 저장
//            token=database.katiDataDao().getValue(KatiDatabase.AUTHORIZATION);
//            getReviewFoods();
//        }).start();
//    }
//
//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        this.reviewNum = view.findViewById(R.id.myPage_review_num);
//        this.adapter = new com.plim.kati_app.domain.view.user.myPage.UserReviewFragment.ReviewRecyclerAdapter();
//        this.recyclerView = view.findViewById(R.id.myPage_foodReviewRecyclerView);
//        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
//        this.recyclerView.setAdapter(this.adapter);
//    }
//
//    private void refresh(){
//        this.getReviewFoods();
//    }
//
//    private void deleteReview(Long reviewId){
//        new Thread(()->{
//
//            DeleteReviewRequest request= new DeleteReviewRequest();
//            request.setReviewId(reviewId);
//
//
//            KatiDatabase database= KatiDatabase.getAppDatabase(getContext());
//            String token = database.katiDataDao().getValue(KatiDatabase.AUTHORIZATION);
//
//            Call<CreateReviewResponse> call =RestAPIClient.getApiService2(token).deleteReview(request);
//            call.enqueue(new Callback<CreateReviewResponse>() {
//                @Override
//                public void onResponse(Call<CreateReviewResponse> call, Response<CreateReviewResponse> response) {
//
//                    if(!response.isSuccessful()){
//                        KatiDialog.showRetrofitNotSuccessDialog(getContext(),response.code()+"",null).showDialog();
//                    }
//                    else
//                        KatiDialog.simpleAlertDialog(
//                                getContext(),
//                                "리뷰 삭제",
//                                "리뷰를 성공적으로 삭제하였습니다.",
//                                (dialog, which)->{
//                                    refresh();
//                                },
//                                getContext().getResources().getColor(R.color.kati_coral,getContext().getTheme())
//                        ).showDialog();
//                }
//
//                @Override
//                public void onFailure(Call<CreateReviewResponse> call, Throwable t) {
//                    KatiDialog.showRetrofitFailDialog(getContext(),t.getMessage(),null);
//                }
//            });
//        }).start();
//    }
//
//    private void like(Long reviewId, boolean likeCheck) {
//        new Thread(()->{
//            UpdateReviewLikeRequest request= new UpdateReviewLikeRequest();
//            request.setReviewId(reviewId);
//            request.setLikeCheck(likeCheck);
//
//            KatiDatabase database= KatiDatabase.getAppDatabase(getContext());
//            String token = database.katiDataDao().getValue(KatiDatabase.AUTHORIZATION);
//
//            Call<UpdateReviewLikeResponse> call = RestAPIClient.getApiService2(token).likeReview(request);
//            call.enqueue(new Callback<UpdateReviewLikeResponse>() {
//                @Override
//                public void onResponse(Call<UpdateReviewLikeResponse> call, Response<UpdateReviewLikeResponse> response) {
//
//                    if(!response.isSuccessful()){
//                        KatiDialog.showRetrofitNotSuccessDialog(getContext(),response.code()+"",null).showDialog();
//                    }
//                    else
//                        KatiDialog.simpleAlertDialog(
//                                getContext(),
//                                "좋아요를 눌렀습니다.",
//                                !likeCheck?"좋아요를 저장하였습니다.":"좋아요를 취소하였습니다.",
//                                (dialog, which)->{
//                                    refresh();
//                                },
//                                getContext().getResources().getColor(R.color.kati_coral,getContext().getTheme())
//                        ).showDialog();
//                }
//
//                @Override
//                public void onFailure(Call<UpdateReviewLikeResponse> call, Throwable t) {
//                    KatiDialog.showRetrofitFailDialog(getContext(),t.getMessage(),null);
//                }
//            });
//        }).start();
//    }
//
//    // 서버에서 사용자 정보를 받아와서 이름을 바꿔주는 메서드
//    private void getReviewFoods() {
//        Call<List<ReadReviewResponse>> call = getApiService2(token).getUserReview();
//        Callback<List<ReadReviewResponse>> callback = new Callback<List<ReadReviewResponse>>() {
//            @Override
//            public void onResponse(Call<List<ReadReviewResponse>> call, Response<List<ReadReviewResponse>> response) {
//                Vector<ReadReviewResponse> items = new Vector<>();
//                if (!response.isSuccessful()) {
//                    try {
//                        JSONObject jObjError = new JSONObject(response.errorBody().string());
//                        Toast.makeText(getContext(), jObjError.getString("error-message"), Toast.LENGTH_LONG).show();
//                    } catch (Exception e) {
//                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//                    }
//                } else {
//                    getActivity().runOnUiThread(() -> {
//                        List<ReadReviewResponse> list = response.body();
//                        items.addAll(list);
//                        adapter.setItems(items);
//                        recyclerView.setAdapter(adapter);
//                        reviewNum.setText("총 "+items.size()+"개");
//
//
//                    });
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<ReadReviewResponse>> call, Throwable t) {
//            }
//        };
//        call.enqueue(callback);
//    }
//
//    private void updateReviews(Long foodId,Long reviewId, String foodName, String value, int score){
//        Intent intent= new Intent(this.getActivity(), WriteReviewActivity.class);
//
//        intent.putExtra("score",score);
//        intent.putExtra("foodId",foodId);
//        intent.putExtra("reviewId",reviewId);
//        intent.putExtra("product",foodName);
//        intent.putExtra("value",value);
//        startActivity(intent);
//    }
//
//    private class ReviewRecyclerAdapter extends RecyclerView.Adapter<ReviewRecyclerAdapter.ReviewViewHolder> {
//
//        private Vector<ReadReviewResponse> vector;
//
//        private ReviewRecyclerAdapter() {
//            this.vector = new Vector<>();
//        }
//
//        @NonNull
//        @Override
//        public com.plim.kati_app.domain.view.user.myPage.UserReviewFragment.ReviewRecyclerAdapter.ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            Context context = parent.getContext();
//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View view = inflater.inflate(R.layout.item_review, parent, false);
//            com.plim.kati_app.domain.view.user.myPage.UserReviewFragment.ReviewRecyclerAdapter.ReviewViewHolder viewHolder = new com.plim.kati_app.domain.view.user.myPage.UserReviewFragment.ReviewRecyclerAdapter.ReviewViewHolder(view);
//            return viewHolder;
//        }
//
//
//
//        @Override
//        public void onBindViewHolder(@NonNull com.plim.kati_app.domain.view.user.myPage.UserReviewFragment.ReviewRecyclerAdapter.ReviewViewHolder holder, int position) {
//            holder.setValue(vector.get(position));
//        }
//
//        @Override
//        public int getItemCount() {
//            return this.vector.size();
//        }
//
//        public void setItems(Vector<ReadReviewResponse> vector) {
//            this.vector = vector;
//            this.notifyDataSetChanged();
//            Log.d("벡터길이", vector.size() + "");
//        }
//
//    /**
//     * 뷰 홀더
//     */
//    private class ReviewViewHolder extends RecyclerView.ViewHolder {
//
//
//        private TextView productName, date, score, reviewContent, like;
//        private Button editButton, deleteButton;
//        private ImageView likeImageButton;
//
//
//        public ReviewViewHolder(@NonNull View itemView) {
//            super(itemView);
//            this.productName = itemView.findViewById(R.id.reviewItem_ProductNameTextView);
//            this.date = itemView.findViewById(R.id.reviewItem_EditDateTextView);
//            this.score = itemView.findViewById(R.id.reviewItem_reviewScoreTextView);
//            this.reviewContent = itemView.findViewById(R.id.reviewItem_reviewTextTextView);
//            this.like = itemView.findViewById(R.id.reviewItem_reviewLikeTextView);
//            this.editButton = itemView.findViewById(R.id.reviewItem_editButton);
//            this.likeImageButton = itemView.findViewById(R.id.reviewItem_reviewLikeImageView);
//            this.deleteButton= itemView.findViewById(R.id.reviewItem_deleteButton);
//        }
//
//        public void setValue(ReadReviewResponse value) {
//            this.productName.setText(value.getFoodName());
//            this.date.setText(value.getReviewModifiedDate() == null ?
//                    value.getReviewCreatedDate().toString() + "작성" :
//                    value.getReviewModifiedDate().toString() + "수정");
//            this.score.setText(value.getReviewRating() + "");
//            this.reviewContent.setText(value.getReviewDescription());
//            this.like.setText(value.getLikeCount() + "");
//
//            this.deleteButton.setEnabled(value.isUserCheck());
//            this.editButton.setEnabled(value.isUserCheck());
//
//            this.deleteButton.setOnClickListener(v->deleteReview(value.getReviewId()));
//
//            this.likeImageButton.clearColorFilter();
//            int color= getResources().getColor(value.isUserLikeCheck()? R.color.kati_orange: R.color.gray,getContext().getTheme());
//            this.likeImageButton.setColorFilter(color, PorterDuff.Mode.SRC_IN);
//
//
//            this.like.setOnClickListener(isLogin ? v -> like(value.getReviewId(),value.isUserLikeCheck()) : null);
//            this.likeImageButton.setOnClickListener(isLogin ? v -> like(value.getReviewId(),value.isUserLikeCheck()) : null);
//
//            this.editButton.setOnClickListener(v->{
//                updateReviews(value.getFoodId(),value.getReviewId(),value.getFoodName(),value.getReviewDescription(),(int)value.getReviewRating());
//
//            });
//
//        }
//    }
//
//
//
//
//
//
//
//}
}
