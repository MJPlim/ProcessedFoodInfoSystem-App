package com.plim.kati_app.kati.domain.nnew.main.favorite.view;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
import com.plim.kati_app.kati.domain.nnew.login.LoginActivity;
import com.plim.kati_app.kati.domain.nnew.main.favorite.adapter.UserFavoriteFoodRecyclerAdapter;
import com.plim.kati_app.kati.domain.nnew.main.favorite.model.UserFavoriteResponse;

import org.json.JSONObject;

import java.util.List;
import java.util.Vector;

import retrofit2.Response;

import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.FOOD_SEARCH_RESULT_LIST_FRAGMENT_FAILURE_DIALOG_TITLE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.LOG_OUT_ACTIVITY_FAILURE_DIALOG_TITLE;

public class FavoriteFragment extends KatiViewModelFragment {
    private TextView favoriteNum;
    private RecyclerView foodInfoRecyclerView;
    private UserFavoriteFoodRecyclerAdapter foodRecyclerAdapter;
    private LoadingDialog dialog;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_favorite;
    }

    @Override
    protected void associateView(View view) {
        this.foodInfoRecyclerView = view.findViewById(R.id.favorite_list);
        this.favoriteNum=view.findViewById(R.id.favorite_count);
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
            favoriteNum.setText(String.valueOf(items.size()));
        }
        @Override
        public void onFailResponse(Response<List<UserFavoriteResponse>> response) {
            try {
                JSONObject jObjError = new JSONObject(response.errorBody().string());
                Toast.makeText(getContext(), jObjError.getString("error-message"), Toast.LENGTH_LONG).show();
                showNotLoginedDialog();
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



    private void showNotLoginedDialog() {
        navigateTo(R.id.action_global_mainFragment);
        this.getActivity().startActivity(new Intent(this.getContext(), LoginActivity.class));

    }



}