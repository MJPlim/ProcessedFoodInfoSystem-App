package com.plim.kati_app.domain.view.user.myPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.plim.kati_app.R;
import com.plim.kati_app.domain.asset.KatiDialog;
import com.plim.kati_app.domain.model.room.KatiDatabase;
import com.plim.kati_app.domain.view.MainActivity;
import com.plim.kati_app.domain.view.rank.RankingActivity;
import com.plim.kati_app.domain.view.user.changePW.ChangePasswordActivity;
import com.plim.kati_app.domain.view.user.dataChange.UserDataChangeActivity;
import com.plim.kati_app.domain.view.user.findPW.FindPasswordActivity;
import com.plim.kati_app.domain.view.user.logOut.LogOutActivity;
import com.plim.kati_app.domain.view.user.login.LoginActivity;
import com.plim.kati_app.domain.view.user.register.RegisterActivity;

import static com.plim.kati_app.constants.Constant_yun.LOG_OUT_ACTIVITY_FAILURE_DIALOG_TITLE;

/**
 * 마이페이지 프래그먼트.
 *
 * !로그인이 되어있지 않을때 누르면 로그인페이지로 이동하는데 그상태에서 뒤로가기 누르면 메인으로 돌아가지않고 마이페이지로 돌아가는 이슈
 */

public class UserMyPageFragment extends Fragment {
    KatiDatabase database = KatiDatabase.getAppDatabase(getContext());
    private Button logoutButton;
    private TextView userId,favoriteNum,reviewNum,replyNum,postNum,favoriteText,reviewText,replyText,postText,serviceCenterText,modifyUserText,changePasswordText,noticeText,settingText;
    private ImageView userImage;

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
        this.favoriteText=view.findViewById(R.id.myPage_favorite);
        this.reviewText = view.findViewById(R.id.myPage_review);
        this.replyText = view.findViewById(R.id.myPage_reply);
        this.postText = view.findViewById(R.id.myPage_post);
        this.serviceCenterText = view.findViewById(R.id.myPage_serviceCenter);
        this.modifyUserText = view.findViewById(R.id.myPage_modifyUser);
        this.changePasswordText = view.findViewById(R.id.myPage_changePassword);
        this.noticeText = view.findViewById(R.id.myPage_notice);
        this.settingText = view.findViewById(R.id.myPage_setting);
        this.userImage=view.findViewById(R.id.myPage_imageUser);

        View.OnClickListener onClickListener = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.myPage_logout_button:
                        moveToLogOutActivity();
                        break;
                    case R.id.myPage_changePassword:
                        moveToChangePasswordActivity();
                        break;
                    case R.id.myPage_modifyUser:
                        moveToModifyUserActivity();
                        break;

                }
            }
        };

        this.logoutButton.setOnClickListener(onClickListener);
        this.changePasswordText.setOnClickListener(onClickListener);
        this.modifyUserText.setOnClickListener(onClickListener);

    }

    private void moveToLogOutActivity() {
        Intent intent = new Intent(this.getActivity(), LogOutActivity.class);
        startActivity(intent);
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


}
