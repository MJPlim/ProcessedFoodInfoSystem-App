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
import com.plim.kati_app.kati.domain.mypage.myReview.adapter.UserReviewViewHolder;
import com.plim.kati_app.kati.domain.mypage.myReview.model.ReadReviewResponse;
import com.plim.kati_app.kati.domain.search.foodInfo.view.foodInfo.model.CreateAndUpdateReviewRequest;
import com.plim.kati_app.kati.domain.search.foodInfo.view.foodInfo.model.CreateReviewResponse;
import com.plim.kati_app.kati.domain.search.foodInfo.view.foodInfo.model.DeleteReviewRequest;
import com.plim.kati_app.kati.domain.search.foodInfo.view.foodInfo.model.UpdateReviewLikeRequest;
import com.plim.kati_app.kati.domain.search.foodInfo.view.foodInfo.model.UpdateReviewLikeResponse;
import com.plim.kati_app.kati.domain.search.foodInfo.view.foodInfo.view.DetailReviewViewFragment;


import org.json.JSONObject;

import java.util.List;
import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.DELETE_REVIEW_RESULT_DIALOG_MESSAGE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.FOOD_SEARCH_RESULT_LIST_FRAGMENT_FAILURE_DIALOG_TITLE;


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
        View.OnClickListener listener = v -> {
            this.deleteReview((Long)v.getTag());
        };
        this.reviewRecyclerAdapter = new UserReviewRecyclerAdapter(this.getActivity(),listener);
        this.dialog = new LoadingDialog(getContext());
        this.foodReviewRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        this.foodReviewRecyclerView.setAdapter(this.reviewRecyclerAdapter);
        this.getUserReview();
    }



    @Override
    protected void katiEntityUpdated() {

    }

    private void refresh(){
        this.getUserReview();
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

    private void deleteReview(Long reviewId) {
        DeleteReviewRequest request = new DeleteReviewRequest(reviewId);
        KatiRetrofitTool.getAPIWithAuthorizationToken(dataset.get(KatiEntity.EKatiData.AUTHORIZATION)).deleteReview(request)
                .enqueue(JSHRetrofitTool.getCallback(new DeleteReviewRequestCallback()));
    }

    private class DeleteReviewRequestCallback implements JSHRetrofitCallback<CreateReviewResponse> {

        @Override
        public void onSuccessResponse(Response<CreateReviewResponse> response) {
            dialog.hide();
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

    private void likeReview(Long reviewId, boolean likeCheck) {
        UpdateReviewLikeRequest request= new UpdateReviewLikeRequest();
        request.setReviewId(reviewId);
        request.setLikeCheck(likeCheck);

        KatiRetrofitTool.getAPIWithAuthorizationToken(dataset.get(KatiEntity.EKatiData.AUTHORIZATION)).likeReview(request)
                .enqueue(JSHRetrofitTool.getCallback(new LikeReviewRequestCallback()));
    }

    private class LikeReviewRequestCallback implements JSHRetrofitCallback<UpdateReviewLikeResponse>{
        @Override
        public void onSuccessResponse(Response<UpdateReviewLikeResponse> response) {

        }

        @Override
        public void onFailResponse(Response<UpdateReviewLikeResponse> response) {
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
