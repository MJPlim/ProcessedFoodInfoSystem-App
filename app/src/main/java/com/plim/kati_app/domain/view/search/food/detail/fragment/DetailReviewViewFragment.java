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
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.plim.kati_app.R;
import com.plim.kati_app.constants.Constant;
import com.plim.kati_app.constants.Constant_yun;
import com.plim.kati_app.domain.asset.GetResultFragment;
import com.plim.kati_app.domain.asset.KatiDialog;
import com.plim.kati_app.domain.model.dto.ReadReviewRequest;
import com.plim.kati_app.domain.model.dto.ReadReviewResponse;
import com.plim.kati_app.tech.RestAPI;

import java.util.List;
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

        int i = 0;
        for (Constant_yun.EReviewCateGory cateGory : Constant_yun.EReviewCateGory.values()) {
            categoryTabLayout.addTab(categoryTabLayout.newTab().setText(cateGory.getName()), i);
        }

        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        this.recyclerView.setAdapter(this.adapter);
    }

    @Override
    public void setFragmentRequestKey() {
        this.fragmentRequestKey = "reviewBundle";
    }

    @Override
    public void ResultParse(String requestKey, Bundle result) {
        Long foodId = result.getLong("foodId");
        Log.d("리뷰,foodId",foodId+"");
        this.getReviews(foodId);
    }

    /**
     * 리뷰들을 불러온다.
     *
     * @param foodId
     */
    private void getReviews(Long foodId) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constant.URL)
                .build();
        RestAPI service = retrofit.create(RestAPI.class);
        Call<List<ReadReviewResponse>> listCall = service.readReview(new ReadReviewRequest(foodId));
        listCall.enqueue(new Callback<List<ReadReviewResponse>>() {
            @Override
            public void onResponse(Call<List<ReadReviewResponse>> call, Response<List<ReadReviewResponse>> response) {
                Vector<ReadReviewResponse> vector = new Vector<>();
                if(response.body()!=null)vector.addAll(response.body());
                adapter.setItems(vector);
            }

            @Override
            public void onFailure(Call<List<ReadReviewResponse>> call, Throwable t) {
                Log.d("여기야","리뷰");
                KatiDialog.simpleAlertDialog(getContext(),
                        FOOD_SEARCH_RESULT_LIST_FRAGMENT_FAILURE_DIALOG_TITLE,
                        t.getMessage(), null,
                        getContext().getResources().getColor(R.color.kati_coral, getContext().getTheme())
                ).showDialog();
            }
        });

    }


    private class ReviewRecyclerAdapter extends RecyclerView.Adapter<ReviewRecyclerAdapter.ReviewViewHolder> {

        private Vector<ReadReviewResponse> vector;

        private ReviewRecyclerAdapter(){
            this.vector=new Vector<>();
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
            Log.d("벡터길이",vector.size()+"");
        }

        /**
         * 뷰 홀더
         */
        private class ReviewViewHolder extends RecyclerView.ViewHolder {

            private TextView productName, date, score, reviewContent, like;
            private Button editButton;

            public ReviewViewHolder(@NonNull View itemView) {
                super(itemView);
                this.productName = itemView.findViewById(R.id.reviewItem_ProductNameTextView);
                this.date = itemView.findViewById(R.id.reviewItem_EditDateTextView);
                this.score = itemView.findViewById(R.id.reviewItem_reviewScoreTextView);
                this.reviewContent = itemView.findViewById(R.id.reviewItem_reviewTextTextView);
                this.like = itemView.findViewById(R.id.reviewItem_reviewLikeTextView);
                this.editButton = itemView.findViewById(R.id.reviewItem_editButton);
            }

            public void setValue(ReadReviewResponse value) {
                this.productName.setText(value.getUserName());
                this.date.setText(value.getReviewModifiedDate() == null ?
                        value.getReviewCreatedDate().toString()+ "작성" :
                        value.getReviewModifiedDate().toString() + "수정");
                this.score.setText(value.getReviewRating()+"");
                this.reviewContent.setText(value.getReviewDescription());
                this.like.setText("좋아요가 없음??");
//                this.like.setText(value.get)
                this.editButton.setVisibility(View.INVISIBLE);


            }
        }
    }
}