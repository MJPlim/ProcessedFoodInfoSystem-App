package com.plim.kati_app.kati.domain.mypage.myFavorite.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.plim.kati_app.R;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitCallback;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitTool;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.KatiDialog;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.LoadingDialog;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.KatiViewModelFragment;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.KatiRetrofitTool;
import com.plim.kati_app.kati.domain.mypage.myFavorite.model.UserFavoriteResponse;
import com.plim.kati_app.kati.domain.search.foodInfo.model.FoodDetailResponse;
import com.plim.kati_app.kati.domain.search.foodInfo.view.foodInfo.view.DetailReviewViewFragment;
import com.plim.kati_app.kati.domain.mypage.myFavorite.adapter.UserFavoriteFoodRecyclerAdapter;
import com.plim.kati_app.kati.domain.search.search.view.foodList.searchResult.FoodViewHolder;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.List;
import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.FOOD_SEARCH_RESULT_LIST_FRAGMENT_FAILURE_DIALOG_TITLE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.NEW_DETAIL_ACTIVITY_EXTRA_FOOD_ID;


/**
 * 마이페이지 즐겨찾기 프래그먼트.
 *
 */

public class UserFavoriteFragment extends KatiViewModelFragment {
    private TextView favoriteNum;
    private RecyclerView foodInfoRecyclerView;
    private UserFavoriteFoodRecyclerAdapter foodRecyclerAdapter;
    private LoadingDialog dialog;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mypage_favorite_food_list;
    }

    @Override
    protected void associateView(View view) {
        this.foodInfoRecyclerView = view.findViewById(R.id.myPage_foodInfoRecyclerView);
        this.favoriteNum=view.findViewById(R.id.myPage_favorite_num);
    }

    @Override
    protected void initializeView() {
        this.foodRecyclerAdapter = new UserFavoriteFoodRecyclerAdapter(this.getActivity());
        this.dialog = new LoadingDialog(getContext());
        this.foodInfoRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        this.foodInfoRecyclerView.setAdapter(this.foodRecyclerAdapter);
        this.getUserFavorite();

    }

    @Override
    protected void katiEntityUpdated() {

    }

    private void getUserFavorite() {
        KatiRetrofitTool.getAPIWithAuthorizationToken(dataset.get(KatiEntity.EKatiData.AUTHORIZATION)).getUserFavorite()
                .enqueue(JSHRetrofitTool.getCallback(new UserFavoriteResponseCallback()));
    }

    private class UserFavoriteResponseCallback implements JSHRetrofitCallback<List<UserFavoriteResponse>> {
        @Override
        public void onSuccessResponse(Response<List<UserFavoriteResponse>> response) {
            Vector<UserFavoriteResponse> items = new Vector<>(response.body());
            dialog.hide();
            foodRecyclerAdapter.setItems(items);
            foodInfoRecyclerView.setAdapter(foodRecyclerAdapter);
            favoriteNum.setText("총 "+items.size()+"개");
        }
        @Override
        public void onFailResponse(Response<List<UserFavoriteResponse>> response) {
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
