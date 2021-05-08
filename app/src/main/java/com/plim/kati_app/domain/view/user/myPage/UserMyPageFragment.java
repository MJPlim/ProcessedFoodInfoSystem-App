package com.plim.kati_app.domain.view.user.myPage;

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

public class UserMyPageFragment extends Fragment {

    private Button logoutButton;
    private TextView userId,favoriteNum,reviewNum,replyNum,postNum,favoriteText,reviewText,replyText,postText,serviceCenterText,modifyUserText,changePasswordText,noticeText,settingText;
    private ImageView userImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mypage, container, false);
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


    }
}
