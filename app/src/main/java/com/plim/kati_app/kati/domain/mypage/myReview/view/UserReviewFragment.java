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


}
