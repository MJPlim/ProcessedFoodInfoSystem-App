package com.plim.kati_app.kati.domain.nnew.main.myKati.review.view;

import android.graphics.Color;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.plim.kati_app.R;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitCallback;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitTool;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.KatiHasTitleFragment;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.KatiRetrofitTool;
import com.plim.kati_app.kati.domain.nnew.main.myKati.review.adapter.UserReviewRecyclerAdapter;
import com.plim.kati_app.kati.domain.nnew.main.myKati.review.model.ReadReviewResponse;
import com.plim.kati_app.kati.domain.nnew.main.myKati.review.model.ReadUserReviewResponse;
import com.plim.kati_app.kati.domain.nnew.main.search.model.DeleteReviewRequest;
import com.plim.kati_app.kati.domain.review.model.CreateReviewResponse;

import org.json.JSONObject;

import java.util.List;
import java.util.Vector;

import retrofit2.Response;

import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.DELETE_REVIEW_RESULT_DIALOG_MESSAGE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.FAVORITE_ITEM_SIZE_PREFIX;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.FAVORITE_ITEM_SIZE_SUFFIX;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.FOOD_SEARCH_RESULT_LIST_FRAGMENT_FAILURE_DIALOG_TITLE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.JSONOBJECT_ERROR_MESSAGE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.REVIEW_ITEN_COLOR_STRING;

public class ReviewManagementFragment extends KatiHasTitleFragment {

    //associate
    //view
    private RecyclerView foodReviewRecyclerView;
    private UserReviewRecyclerAdapter reviewRecyclerAdapter;
    private TextView reviewNum;

    //data
    private Vector<ReadReviewResponse> items;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_review_management;
    }

    @Override
    protected void associateView(View view) {
        super.associateView(view);
        this.foodReviewRecyclerView = view.findViewById(R.id.reviewManagementFragment_recyclerView);
        this.reviewNum = view.findViewById(R.id.reviewManagementFragment_numOfReviewTextView);
    }

    @Override
    protected void initializeView() {
        super.initializeView();
        View.OnClickListener listener = v -> this.deleteReview((Long)v.getTag());
        this.reviewRecyclerAdapter = new UserReviewRecyclerAdapter(this.getActivity(),listener);
        this.foodReviewRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        this.foodReviewRecyclerView.setAdapter(this.reviewRecyclerAdapter);
    }

    @Override
    protected boolean isLoginNeeded() {
        return false;
    }

    @Override
    protected void katiEntityUpdatedAndLogin() {
        this.getUserReview();
    }

    @Override
    protected void katiEntityUpdatedAndNoLogin() {}


    /**
     * callback
     */
    private class ReadReviewResponseCallback implements JSHRetrofitCallback<ReadUserReviewResponse<List<ReadReviewResponse>>> {
        @Override
        public void onSuccessResponse(Response<ReadUserReviewResponse<List<ReadReviewResponse>>> response) {
            items = new Vector<>(response.body().getUserReviewList());
            String temp = FAVORITE_ITEM_SIZE_PREFIX+items.size() + FAVORITE_ITEM_SIZE_SUFFIX;
            SpannableStringBuilder ssb = new SpannableStringBuilder(temp);
            ssb.setSpan(new ForegroundColorSpan(Color.parseColor(REVIEW_ITEN_COLOR_STRING)), temp.length() - 3, temp.length()-1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            reviewNum.setText(ssb);
            reviewRecyclerAdapter.setItems(items);
            foodReviewRecyclerView.setAdapter(reviewRecyclerAdapter);
        }
        @Override
        public void onFailResponse(Response<ReadUserReviewResponse<List<ReadReviewResponse>>> response) {
            try {
                JSONObject jObjError = new JSONObject(response.errorBody().string());
                Toast.makeText(getContext(), jObjError.getString(JSONOBJECT_ERROR_MESSAGE), Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        @Override
        public void onConnectionFail(Throwable t) {
            Toast.makeText(getContext(), FOOD_SEARCH_RESULT_LIST_FRAGMENT_FAILURE_DIALOG_TITLE, Toast.LENGTH_LONG).show();
        }
    }

    private class DeleteReviewRequestCallback implements JSHRetrofitCallback<CreateReviewResponse> {

        @Override
        public void onSuccessResponse(Response<CreateReviewResponse> response) {
            Toast.makeText(getContext(), DELETE_REVIEW_RESULT_DIALOG_MESSAGE, Toast.LENGTH_LONG).show();
            refresh();
        }

        @Override
        public void onFailResponse(Response<CreateReviewResponse> response) {
            try {
                JSONObject jObjError = new JSONObject(response.errorBody().string());
                Toast.makeText(getContext(), jObjError.getString(JSONOBJECT_ERROR_MESSAGE), Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onConnectionFail(Throwable t) {
            Toast.makeText(getContext(), FOOD_SEARCH_RESULT_LIST_FRAGMENT_FAILURE_DIALOG_TITLE, Toast.LENGTH_LONG).show();

        }
    }


    /**
     * method
     */
    private void refresh(){
        this.getUserReview();
    }

    private void getUserReview() {
        KatiRetrofitTool.getAPIWithAuthorizationToken(dataset.get(KatiEntity.EKatiData.AUTHORIZATION)).getUserReview()
                .enqueue(JSHRetrofitTool.getCallback(new ReadReviewResponseCallback()));
    }

    private void deleteReview(Long reviewId) {
        DeleteReviewRequest request = new DeleteReviewRequest(reviewId);
        KatiRetrofitTool.getAPIWithAuthorizationToken(dataset.get(KatiEntity.EKatiData.AUTHORIZATION)).deleteReview(request)
                .enqueue(JSHRetrofitTool.getCallback(new DeleteReviewRequestCallback()));
    }




}