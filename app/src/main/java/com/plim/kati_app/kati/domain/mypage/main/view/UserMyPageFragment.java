package com.plim.kati_app.kati.domain.mypage.main.view;

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

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.plim.kati_app.R;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitCallback;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitTool;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.KatiDialog;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.LoadingDialog;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.KatiViewModelFragment;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.KatiRetrofitTool;
import com.plim.kati_app.kati.domain.TempMainActivity;
import com.plim.kati_app.kati.domain.changePW.view.ChangePasswordActivity;
import com.plim.kati_app.kati.domain.mypage.main.model.UserSummaryResponse;
import com.plim.kati_app.kati.domain.temp.logout.view.LogOutActivity;
import com.plim.kati_app.kati.domain.temp.setSecondEmail.view.SetSecondEmailActivity;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.FOOD_SEARCH_RESULT_LIST_FRAGMENT_FAILURE_DIALOG_TITLE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.LOG_OUT_ACTIVITY_FAILURE_DIALOG_MESSAGE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.LOG_OUT_ACTIVITY_FAILURE_DIALOG_TITLE;


/**
 * 마이페이지 프래그먼트.
 * <p>
 * !로그인이 되어있지 않을때 누르면 로그인페이지로 이동하는데 그상태에서 뒤로가기 누르면 메인으로 돌아가지않고 마이페이지로 돌아가는 이슈
 * ! 화면이 생성되고 나서 이름이 바뀌는 이슈
 */

public class UserMyPageFragment extends KatiViewModelFragment {


    // Associate
    // View
    private Button logoutButton;
    private TextView userId, favoriteNum, reviewNum, replyNum, postNum, favoriteText, reviewText, replyText, postText, customerCenterText, restoreEmailText, modifyUserText, changePasswordText, noticeText, settingText;
    private ImageView userImage;
    private String token;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mypage;
    }

    @Override
    protected void associateView(View view) {
        this.logoutButton = view.findViewById(R.id.myPage_logout_button);
        this.userId = view.findViewById(R.id.myPage_UserId);
        this.favoriteNum = view.findViewById(R.id.myPage_favorite_num);
        this.reviewNum = view.findViewById(R.id.myPage_review_num);
        this.replyNum = view.findViewById(R.id.myPage_reply_num);
        this.postNum = view.findViewById(R.id.myPage_post_num);
        this.favoriteText = view.findViewById(R.id.myPage_favorite);
        this.reviewText = view.findViewById(R.id.myPage_review);
        this.replyText = view.findViewById(R.id.myPage_reply);
        this.postText = view.findViewById(R.id.myPage_post);
        this.restoreEmailText = view.findViewById(R.id.myPage_restoreEmail);
        this.modifyUserText = view.findViewById(R.id.myPage_modifyUser);
        this.changePasswordText = view.findViewById(R.id.myPage_changePassword);
        this.noticeText = view.findViewById(R.id.myPage_notice);
        this.settingText = view.findViewById(R.id.myPage_setting);
        this.userImage = view.findViewById(R.id.myPage_imageUser);
        this.customerCenterText = view.findViewById(R.id.userPageFragment_customerCenter);

    }

    @Override
    protected void initializeView() {
        this.logoutButton.setOnClickListener(v -> this.moveToLogOutActivity());
        this.favoriteNum.setOnClickListener(v -> this.navigateTo(R.id.action_myPageFragment_to_myPageFavoriteFragment));
        this.reviewNum.setOnClickListener(v -> this.navigateTo(R.id.action_myPageFragment_to_myPageReviewFragment));
        this.restoreEmailText.setOnClickListener(v -> this.moveToRestoreEmailActivity());
        this.modifyUserText.setOnClickListener(v -> Toast.makeText(getContext(), "미구현", Toast.LENGTH_LONG).show());
        this.changePasswordText.setOnClickListener(v -> this.moveToChangePasswordActivity());
        this.getUserInfo();
    }

    @Override
    protected void katiEntityUpdated() {
        if (!this.dataset.containsKey(KatiEntity.EKatiData.AUTHORIZATION)) {
            this.showNotLoginedDialog();
        }
    }

    /**
     * Method
     */


    private void moveToRestoreEmailActivity() {
        Intent intent = new Intent(this.getActivity(), SetSecondEmailActivity.class);
        startActivity(intent);
    }

    private void showNotLoginedDialog() {
        KatiDialog.simplerAlertDialog(this.getActivity(),
                LOG_OUT_ACTIVITY_FAILURE_DIALOG_TITLE, LOG_OUT_ACTIVITY_FAILURE_DIALOG_TITLE,
                (dialog, which) -> this.startActivity(TempMainActivity.class)
        );
    }

    private void moveToLogOutActivity() {
        Intent intent = new Intent(this.getActivity(), LogOutActivity.class);
        startActivity(intent);
        getActivity().finish();
    }


    private void moveToChangePasswordActivity() {
        Intent intent = new Intent(this.getActivity(), ChangePasswordActivity.class);
        startActivity(intent);
    }



    // 서버에서 사용자 정보를 받아와서 바꿔주는 메서드
    private void getUserInfo() {
        KatiRetrofitTool.getAPIWithAuthorizationToken(dataset.get(KatiEntity.EKatiData.AUTHORIZATION)).getUserSummary().enqueue(JSHRetrofitTool.getCallback(new UserSummaryResponseCallback()));
    }

    /**
     * Callback
     */


    private class UserSummaryResponseCallback implements JSHRetrofitCallback<UserSummaryResponse> {
        @Override
        public void onSuccessResponse(Response<UserSummaryResponse> response) {
            String temp = response.body().getUser_name() + " 님";
            SpannableStringBuilder ssb = new SpannableStringBuilder(temp);
            ssb.setSpan(new ForegroundColorSpan(Color.parseColor("#FFFFFFFF")), temp.length() - 1, temp.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            userId.setText(ssb);
            favoriteNum.setText(response.body().getFavorite_count());
            reviewNum.setText(response.body().getReview_count());
        }

        @Override
        public void onFailResponse(Response<UserSummaryResponse> response) {
            try {
                JSONObject jObjError = new JSONObject(response.errorBody().string());
                Toast.makeText(getContext(), jObjError.getString("error-message"), Toast.LENGTH_LONG).show();
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
