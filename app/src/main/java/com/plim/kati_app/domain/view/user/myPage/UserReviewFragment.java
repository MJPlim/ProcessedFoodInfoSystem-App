package com.plim.kati_app.domain.view.user.myPage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
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
import com.plim.kati_app.domain.asset.KatiDialog;
import com.plim.kati_app.domain.model.UserInfoResponse;
import com.plim.kati_app.domain.model.dto.CreateReviewResponse;
import com.plim.kati_app.domain.model.dto.DeleteReviewRequest;
import com.plim.kati_app.domain.model.dto.ReadReviewDto;
import com.plim.kati_app.domain.model.dto.ReadReviewResponse;
import com.plim.kati_app.domain.model.dto.UpdateReviewLikeRequest;
import com.plim.kati_app.domain.model.dto.UpdateReviewLikeResponse;
import com.plim.kati_app.domain.model.room.KatiDatabase;
import com.plim.kati_app.domain.view.search.food.detail.fragment.DetailReviewViewFragment;
import com.plim.kati_app.domain.view.search.food.review.WriteReviewActivity;
import com.plim.kati_app.domain.view.user.changePW.ChangePasswordActivity;
import com.plim.kati_app.domain.view.user.dataChange.UserDataChangeActivity;
import com.plim.kati_app.domain.view.user.logOut.LogOutActivity;
import com.plim.kati_app.domain.view.user.login.LoginActivity;
import com.plim.kati_app.tech.RestAPIClient;

import org.json.JSONObject;

import java.util.List;
import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.plim.kati_app.constants.Constant_yun.LOG_OUT_ACTIVITY_FAILURE_DIALOG_TITLE;
import static com.plim.kati_app.tech.RestAPIClient.getApiService2;

/**
 * 마이페이지 리뷰 프래그먼트.
 *
 */

public class UserReviewFragment extends Fragment {
    KatiDatabase database = KatiDatabase.getAppDatabase(getContext());
    private TextView reviewNum;
    private String token;
    private UserReviewFragment.ReviewRecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private String manufacturer;
    private String foodName;
    private String image;
    private Long foodId;
    private boolean isLogin = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mypage_review_food_list, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        new Thread(() -> {
            // 토큰값 저장
            token=database.katiDataDao().getValue(KatiDatabase.AUTHORIZATION);
            getReviewFoods();
        }).start();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.reviewNum = view.findViewById(R.id.myPage_review_num);
        this.adapter = new UserReviewFragment.ReviewRecyclerAdapter();
        this.recyclerView = view.findViewById(R.id.myPage_foodReviewRecyclerView);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        this.recyclerView.setAdapter(this.adapter);
    }

    private void refresh(){
        this.getReviewFoods();
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

            Call<UpdateReviewLikeResponse> call = RestAPIClient.getApiService2(token).likeReview(request);
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

    // 서버에서 사용자 정보를 받아와서 이름을 바꿔주는 메서드
    private void getReviewFoods() {
        Call<List<ReadReviewResponse>> call = getApiService2(token).getUserReview();
        Callback<List<ReadReviewResponse>> callback = new Callback<List<ReadReviewResponse>>() {
            @Override
            public void onResponse(Call<List<ReadReviewResponse>> call, Response<List<ReadReviewResponse>> response) {
                Vector<ReadReviewResponse> items = new Vector<>();
                if (!response.isSuccessful()) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(getContext(), jObjError.getString("error-message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    getActivity().runOnUiThread(() -> {
                        List<ReadReviewResponse> list = response.body();
                        items.addAll(list);
                        adapter.setItems(items);
                        recyclerView.setAdapter(adapter);
                        reviewNum.setText("총 "+items.size()+"개");


                    });
                }
            }

            @Override
            public void onFailure(Call<List<ReadReviewResponse>> call, Throwable t) {
            }
        };
        call.enqueue(callback);
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

    private class ReviewRecyclerAdapter extends RecyclerView.Adapter<UserReviewFragment.ReviewRecyclerAdapter.ReviewViewHolder> {

        private Vector<ReadReviewResponse> vector;

        private ReviewRecyclerAdapter() {
            this.vector = new Vector<>();
        }

        @NonNull
        @Override
        public UserReviewFragment.ReviewRecyclerAdapter.ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.item_review, parent, false);
            UserReviewFragment.ReviewRecyclerAdapter.ReviewViewHolder viewHolder = new UserReviewFragment.ReviewRecyclerAdapter.ReviewViewHolder(view);
            return viewHolder;
        }



        @Override
        public void onBindViewHolder(@NonNull UserReviewFragment.ReviewRecyclerAdapter.ReviewViewHolder holder, int position) {
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
