package com.plim.kati_app.domain.view.user.myPage;
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
import com.plim.kati_app.domain.model.UserInfoResponse;
import com.plim.kati_app.R;
import com.plim.kati_app.domain.asset.KatiDialog;
import com.plim.kati_app.domain.model.UserSummaryResponse;
import com.plim.kati_app.domain.model.room.KatiData;
import com.plim.kati_app.domain.model.room.KatiDatabase;
import com.plim.kati_app.domain.view.user.changePW.ChangePasswordActivity;
import com.plim.kati_app.domain.view.user.dataChange.UserDataChangeActivity;
import com.plim.kati_app.domain.view.user.logOut.LogOutActivity;
import com.plim.kati_app.domain.view.user.login.LoginActivity;
import com.plim.kati_app.domain.view.user.setId.SetSecondEmailActivity;

import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.plim.kati_app.constants.Constant_yun.LOG_OUT_ACTIVITY_FAILURE_DIALOG_TITLE;
import static com.plim.kati_app.tech.RestAPIClient.getApiService2;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

/**
 * 마이페이지 프래그먼트.
 * <p>
 * !로그인이 되어있지 않을때 누르면 로그인페이지로 이동하는데 그상태에서 뒤로가기 누르면 메인으로 돌아가지않고 마이페이지로 돌아가는 이슈
 * ! 화면이 생성되고 나서 이름이 바뀌는 이슈
 */

public class UserMyPageFragment extends Fragment {
    KatiDatabase database = KatiDatabase.getAppDatabase(getContext());
    private Button logoutButton;
    private TextView userId, favoriteNum, reviewNum, replyNum, postNum, favoriteText, reviewText, replyText, postText,customerCenterText, restoreEmailText, modifyUserText, changePasswordText, noticeText, settingText;
    private ImageView userImage;
    private String token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mypage, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        new Thread(() -> {
            if (database.katiDataDao().getValue(KatiDatabase.AUTHORIZATION) == null) {
                getActivity().runOnUiThread(() -> showNotLoginedDialog());
            }else{
                // 토큰값 저장
                token=database.katiDataDao().getValue(KatiDatabase.AUTHORIZATION);
                changeId();
            }

        }).start();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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


        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.myPage_logout_button:
                        moveToLogOutActivity();
                        break;
                    case R.id.myPage_changePassword:
                        moveToChangePasswordActivity();
                        break;
                    case R.id.myPage_modifyUser:
                        moveToModifyUserActivity();
                        break;
                    case R.id.myPage_review_num:
                        Navigation.findNavController(view).navigate(R.id.action_myPageFragment_to_myPageReviewFragment);
                        break;
                    case R.id.myPage_favorite_num:
                        Navigation.findNavController(view).navigate(R.id.action_myPageFragment_to_myPageFavoriteFragment);
                        break;
                    case R.id.myPage_restoreEmail:
                        moveToRestoreEmailActivity();
                        break;

                }
            }
        };
        this.reviewNum.setOnClickListener(onClickListener);
        this.logoutButton.setOnClickListener(onClickListener);
        this.changePasswordText.setOnClickListener(onClickListener);
        this.modifyUserText.setOnClickListener(onClickListener);
        this.favoriteNum.setOnClickListener(onClickListener);
        this.restoreEmailText.setOnClickListener(onClickListener);

    }

    private void moveToRestoreEmailActivity() {
        Intent intent = new Intent(this.getActivity(), SetSecondEmailActivity.class);
        startActivity(intent);
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

    private void moveToModifyUserActivity() {
        Intent intent = new Intent(this.getActivity(), UserDataChangeActivity.class);
        startActivity(intent);
    }

    private void showNotLoginedDialog() {
        KatiDialog.simpleAlertDialog(getContext(),
                LOG_OUT_ACTIVITY_FAILURE_DIALOG_TITLE,
                LOG_OUT_ACTIVITY_FAILURE_DIALOG_TITLE,
                (dialog, which) -> {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }, getResources().getColor(R.color.kati_coral, getContext().getTheme())).showDialog();
    }


    // 서버에서 사용자 정보를 받아와서 이름을 바꿔주는 메서드
    private void changeId() {
        Call<UserSummaryResponse> call = getApiService2(token).getUserSummary();
        Callback<UserSummaryResponse> callback = new Callback<UserSummaryResponse>() {
            @Override
            public void onResponse(retrofit2.Call<UserSummaryResponse> call, Response<UserSummaryResponse> response) {
                if (!response.isSuccessful()) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(getContext(), jObjError.getString("error-message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                } else {
                    new Thread(() -> {
                        String token = response.headers().get(KatiDatabase.AUTHORIZATION);
                        database.katiDataDao().insert(new KatiData(KatiDatabase.AUTHORIZATION, token));
                    }).start();
                    getActivity().runOnUiThread(() -> {
                        //  "님" 색상을 바꿔주는 메서드
                        String temp=response.body().getUser_name()+" 님";
                        SpannableStringBuilder ssb = new SpannableStringBuilder(temp);
                        ssb.setSpan(new ForegroundColorSpan(Color.parseColor("#FFFFFFFF")), temp.length()-1, temp.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        userId.setText(ssb);
                        favoriteNum.setText(response.body().getFavorite_count());
                        reviewNum.setText(response.body().getReview_count());
                    });

                }

            }

            @Override
            public void onFailure(Call<UserSummaryResponse> call, Throwable t) {

            }


        };
        call.enqueue(callback);


    }




}
