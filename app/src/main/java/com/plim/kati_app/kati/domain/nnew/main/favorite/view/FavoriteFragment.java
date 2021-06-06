package com.plim.kati_app.kati.domain.nnew.main.favorite.view;

import android.content.Intent;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.KatiLoginCheckViewModelFragment;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.KatiRetrofitTool;
import com.plim.kati_app.kati.domain.login.LoginActivity;
import com.plim.kati_app.kati.domain.nnew.main.favorite.adapter.UserFavoriteFoodRecyclerAdapter;
import com.plim.kati_app.kati.domain.nnew.main.favorite.model.UserFavoriteResponse;

import org.json.JSONObject;

import java.util.List;
import java.util.Vector;

import retrofit2.Response;

import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.FOOD_SEARCH_RESULT_LIST_FRAGMENT_FAILURE_DIALOG_TITLE;

public class FavoriteFragment extends KatiLoginCheckViewModelFragment {
    private TextView favoriteNum,noLogin,noFavorite;
    private RecyclerView foodInfoRecyclerView;
    private UserFavoriteFoodRecyclerAdapter foodRecyclerAdapter;
    private LoadingDialog dialog;
    private ImageView emptyImage;
    private Button loginButton;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_favorite;
    }

    @Override
    protected void associateView(View view) {
        this.foodInfoRecyclerView = view.findViewById(R.id.favoriteFragment_recyclerView);
        this.favoriteNum=view.findViewById(R.id.favoriteFragment_numOfFavoriteTextView);
        this.noLogin=view.findViewById(R.id.favorite_noLogin);
        this.noFavorite=view.findViewById(R.id.favorite_noFavorite);
        this.emptyImage=view.findViewById(R.id.favorite_emptyImage);
        this.loginButton=view.findViewById(R.id.favorite_login_Button);
    }

    @Override
    protected void initializeView() {
        this.noFavorite.setVisibility(View.GONE);
        this.foodRecyclerAdapter = new UserFavoriteFoodRecyclerAdapter(this.getActivity());
        this.dialog = new LoadingDialog(getContext());
        this.foodInfoRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        this.foodInfoRecyclerView.setAdapter(this.foodRecyclerAdapter);
        this.loginButton.setOnClickListener(v -> this.getActivity().startActivity(new Intent(this.getContext(), LoginActivity.class)));

    }


    @Override
    protected boolean isLoginNeeded() {
        return false;
    }

    @Override
    protected void katiEntityUpdatedAndLogin() {
        favoriteNum.setVisibility(View.VISIBLE);
        this.getUserFavorite();
    }

    @Override
    protected void katiEntityUpdatedAndNoLogin() {
        favoriteNum.setVisibility(View.GONE);
    }


    /**
     * callback
     */
    private class UserFavoriteResponseCallback implements JSHRetrofitCallback<List<UserFavoriteResponse>> {
        @Override
        public void onSuccessResponse(Response<List<UserFavoriteResponse>> response) {
            Vector<UserFavoriteResponse> items = new Vector<>(response.body());
            dialog.hide();
            foodRecyclerAdapter.setItems(items);
            foodInfoRecyclerView.setAdapter(foodRecyclerAdapter);
            String temp = "총 "+items.size() + "개";
            SpannableStringBuilder ssb = new SpannableStringBuilder(temp);
            ssb.setSpan(new ForegroundColorSpan(Color.parseColor("#E53154")), temp.length() - 3, temp.length()-1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            favoriteNum.setText(ssb);
            noLogin.setVisibility(View.GONE);
            emptyImage.setVisibility(View.GONE);
            loginButton.setVisibility(View.GONE);
            if(items.size()==0){
                emptyImage.setVisibility(View.VISIBLE);
                noFavorite.setVisibility(View.VISIBLE);
            }

        }
        @Override
        public void onFailResponse(Response<List<UserFavoriteResponse>> response) {
            try {
                JSONObject jObjError = new JSONObject(response.errorBody().string());
                Toast.makeText(getContext(), jObjError.getString("error-message"), Toast.LENGTH_LONG).show();
//                moveToLogOutActivity();
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

    /**
     * method
     */
    private void getUserFavorite() {

        KatiRetrofitTool.getAPIWithAuthorizationToken(dataset.get(KatiEntity.EKatiData.AUTHORIZATION)).getUserFavorite()
                .enqueue(JSHRetrofitTool.getCallback(new UserFavoriteResponseCallback()));
    }







}