package com.plim.kati_app.domain.view.user.myPage;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.plim.kati_app.R;
import com.plim.kati_app.domain.model.UserInfoResponse;
import com.plim.kati_app.domain.model.room.KatiDatabase;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.plim.kati_app.tech.RestAPIClient.getApiService2;

/**
 * 마이페이지 즐겨찾기 프래그먼트.
 */

public class UserFavoriteFragment extends Fragment {
    KatiDatabase database = KatiDatabase.getAppDatabase(getContext());
    private TextView favoriteNum;
    private String token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mypage_favorite_food_list, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        new Thread(() -> {
            // 토큰값 저장
            token = database.katiDataDao().getValue(KatiDatabase.AUTHORIZATION);
            getFavoriteFoods();
        }).start();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.favoriteNum = view.findViewById(R.id.myPage_favorite_num);


    }


    // 서버에서 사용자 정보를 받아와서 이름을 바꿔주는 메서드
    private void getFavoriteFoods() {
        Call<UserInfoResponse> call = getApiService2(token).getUserInfo();
        Callback<UserInfoResponse> callback = new Callback<UserInfoResponse>() {
            @Override
            public void onResponse(Call<UserInfoResponse> call, Response<UserInfoResponse> response) {
                if (!response.isSuccessful()) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(getContext(), jObjError.getString("error-message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                } else {
                    getActivity().runOnUiThread(() -> {
                        //  "님" 색상을 바꿔주는 메서드
                        String temp = response.body().getName() + " 님";
                        SpannableStringBuilder ssb = new SpannableStringBuilder(temp);
                        ssb.setSpan(new ForegroundColorSpan(Color.parseColor("#FFFFFFFF")), temp.length() - 1, temp.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


                    });

                }

            }

            @Override
            public void onFailure(Call<UserInfoResponse> call, Throwable t) {

            }
        };
        call.enqueue(callback);


    }

}