package com.plim.kati_app.kati.domain.nnew.main.myKati;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.plim.kati_app.R;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitCallback;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitTool;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.KatiDialog;
import com.plim.kati_app.kati.crossDomain.domain.view.etc.JSHSelectItem;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.KatiLoginCheckViewModelFragment;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.KatiViewModelFragment;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.KatiRetrofitTool;
import com.plim.kati_app.kati.domain.nnew.login.LoginActivity;
import com.plim.kati_app.kati.domain.nnew.main.myKati.model.UserSummaryResponse;
import com.plim.kati_app.kati.domain.nnew.signUp.SignUpActivity;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import retrofit2.Response;

import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.FOOD_SEARCH_RESULT_LIST_FRAGMENT_FAILURE_DIALOG_TITLE;

public class MyKatiFragment extends KatiLoginCheckViewModelFragment {
    ConstraintLayout myInfoEditSelect,loginLayout;
    JSHSelectItem reviewSelect;
    JSHSelectItem allergySelect;
    Button signUp, login;
    TextView name;
    ImageView myImage;
    private int numOfMyReview;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mykati;
    }

    @Override
    protected void associateView(View view) {
        loginLayout = view.findViewById(R.id.mykati_login_layout);
        name = view.findViewById(R.id.mykati_name_textView);
        myInfoEditSelect = view.findViewById(R.id.select_my_info_edit);
        reviewSelect = view.findViewById(R.id.select_my_review);
        allergySelect = view.findViewById(R.id.select_my_allergy);
        signUp = view.findViewById(R.id.mykati_signUp_button);
        login = view.findViewById(R.id.mykati_login_button);
        myImage = view.findViewById(R.id.mykati_myImage);

    }

    @Override
    protected void initializeView() {
        myInfoEditSelect.setOnClickListener(
                v -> Navigation.findNavController(v).navigate(R.id.action_myKatiFragment_to_myInfoEditFragment)
        );
        reviewSelect.setOnClickListener(
                v -> Navigation.findNavController(v).navigate(R.id.action_myKatiFragment_to_reviewFlagment)
        );
        allergySelect.setOnClickListener(
                v -> Navigation.findNavController(v).navigate(R.id.action_myKatiFragment_to_allergyFragment)
        );
        signUp.setOnClickListener(v -> this.getActivity().startActivity(new Intent(this.getContext(), SignUpActivity.class)));
        login.setOnClickListener(v -> this.getActivity().startActivity(new Intent(this.getContext(), LoginActivity.class)));
    }

    @Override
    protected boolean isLoginNeeded() {
        return false;
    }

    @Override
    protected void katiEntityUpdatedAndLogin() {
        loginLayout.setVisibility(View.GONE);
        myInfoEditSelect.setVisibility(View.VISIBLE);
        allergySelect.setVisibility(View.VISIBLE);
        reviewSelect.setVisibility(View.VISIBLE);
        getUserInfo();
        changeUserImage();

    }

    @Override
    protected void katiEntityUpdatedAndNoLogin() {
        loginLayout.setVisibility(View.VISIBLE);
        myInfoEditSelect.setVisibility(View.GONE);
        allergySelect.setVisibility(View.GONE);
        reviewSelect.setVisibility(View.GONE);
    }

    private void changeUserImage() {

    }

    // 서버에서 사용자 정보를 받아와서 바꿔주는 메서드
    private void getUserInfo() {
        KatiRetrofitTool.getAPIWithAuthorizationToken(dataset.get(KatiEntity.EKatiData.AUTHORIZATION)).getUserSummary().enqueue(JSHRetrofitTool.getCallback(new UserSummaryResponseCallback()));
    }

    private class UserSummaryResponseCallback implements JSHRetrofitCallback<UserSummaryResponse> {
        @Override
        public void onSuccessResponse(Response<UserSummaryResponse> response) {
            name.setText(response.body().getUser_name());
            dataset.put(KatiEntity.EKatiData.NAME,response.body().getUser_name());
            numOfMyReview=Integer.parseInt(response.body().getReview_count());
            if(numOfMyReview>100){
                myImage.setImageResource(R.drawable.gold_icon);
            }else if(numOfMyReview>50){
                myImage.setImageResource(R.drawable.silver_icon);
            }else{
                myImage.setImageResource(R.drawable.bronze_icon);
            }
        }

        @Override
        public void onFailResponse(Response<UserSummaryResponse> response) {
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
            KatiDialog.simplerAlertDialog(getActivity(),FOOD_SEARCH_RESULT_LIST_FRAGMENT_FAILURE_DIALOG_TITLE,t.getMessage(),null);
        }
    }



}