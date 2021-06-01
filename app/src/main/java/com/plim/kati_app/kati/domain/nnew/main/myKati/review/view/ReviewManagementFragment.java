package com.plim.kati_app.kati.domain.nnew.main.myKati.review.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.plim.kati_app.R;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitCallback;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitTool;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.KatiDialog;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.LoadingDialog;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.KatiLoginCheckViewModelFragment;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.KatiRetrofitTool;
import com.plim.kati_app.kati.domain.nnew.main.myKati.review.adapter.UserReviewRecyclerAdapter;
import com.plim.kati_app.kati.domain.nnew.main.myKati.review.model.ReadReviewResponse;
import com.plim.kati_app.kati.domain.nnew.main.myKati.review.model.ReadUserReviewResponse;
import com.plim.kati_app.kati.domain.old.model.CreateReviewResponse;
import com.plim.kati_app.kati.domain.old.model.DeleteReviewRequest;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.List;
import java.util.Vector;

import retrofit2.Response;

import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.DELETE_REVIEW_RESULT_DIALOG_MESSAGE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.FOOD_SEARCH_RESULT_LIST_FRAGMENT_FAILURE_DIALOG_TITLE;

public class ReviewManagementFragment extends KatiLoginCheckViewModelFragment {
    private RecyclerView foodReviewRecyclerView;
    private UserReviewRecyclerAdapter reviewRecyclerAdapter;
    private TextView reviewNum;
    private Vector<ReadReviewResponse> items;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_review_management;
    }

    @Override
    protected void associateView(View view) {
        this.foodReviewRecyclerView = view.findViewById(R.id.review_list);
        this.reviewNum = view.findViewById(R.id.review_numOfReview);
    }

    @Override
    protected void initializeView() {
        View.OnClickListener listener = v -> {
            this.deleteReview((Long)v.getTag());
        };
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
    protected void katiEntityUpdatedAndNoLogin() {

    }

    private void refresh(){
        this.getUserReview();
    }

    private void getUserReview() {
        KatiRetrofitTool.getAPIWithAuthorizationToken(dataset.get(KatiEntity.EKatiData.AUTHORIZATION)).getUserReview()
                .enqueue(JSHRetrofitTool.getCallback(new ReadReviewResponseCallback()));
    }


    private class ReadReviewResponseCallback implements JSHRetrofitCallback<ReadUserReviewResponse<List<ReadReviewResponse>>> {
        @Override
        public void onSuccessResponse(Response<ReadUserReviewResponse<List<ReadReviewResponse>>> response) {
            items = new Vector<ReadReviewResponse>(response.body().getUserReviewList());
            String temp = "총 "+items.size() + "개";
            SpannableStringBuilder ssb = new SpannableStringBuilder(temp);
            ssb.setSpan(new ForegroundColorSpan(Color.parseColor("#E53154")), temp.length() - 3, temp.length()-1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            reviewNum.setText(ssb);
            reviewRecyclerAdapter.setItems(items);
            foodReviewRecyclerView.setAdapter(reviewRecyclerAdapter);

        }
        @Override
        public void onFailResponse(Response<ReadUserReviewResponse<List<ReadReviewResponse>>> response) {
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

    private void deleteReview(Long reviewId) {
        DeleteReviewRequest request = new DeleteReviewRequest(reviewId);
        KatiRetrofitTool.getAPIWithAuthorizationToken(dataset.get(KatiEntity.EKatiData.AUTHORIZATION)).deleteReview(request)
                .enqueue(JSHRetrofitTool.getCallback(new DeleteReviewRequestCallback()));
    }

    private class DeleteReviewRequestCallback implements JSHRetrofitCallback<CreateReviewResponse> {

        @Override
        public void onSuccessResponse(Response<CreateReviewResponse> response) {
            KatiDialog.simplerAlertDialog(getActivity(),
                    DELETE_REVIEW_RESULT_DIALOG_MESSAGE, response.message(),
                    null
            );refresh();
        }

        @Override
        public void onFailResponse(Response<CreateReviewResponse> response) {
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


}