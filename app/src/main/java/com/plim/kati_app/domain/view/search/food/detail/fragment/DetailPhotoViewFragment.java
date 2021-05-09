package com.plim.kati_app.domain.view.search.food.detail.fragment;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.plim.kati_app.R;
import com.plim.kati_app.domain.asset.GetResultFragment;
import com.plim.kati_app.domain.asset.KatiDialog;
import com.plim.kati_app.domain.model.room.KatiData;
import com.plim.kati_app.domain.model.room.KatiDatabase;
import com.plim.kati_app.tech.RestAPIClient;

import static com.plim.kati_app.constants.Constant_yun.DETAIL_PHOTO_VIEW_FRAGMENT_BUNDLE_BACK_IMAGE;
import static com.plim.kati_app.constants.Constant_yun.DETAIL_PHOTO_VIEW_FRAGMENT_BUNDLE_FRONT_IMAGE;
import static com.plim.kati_app.constants.Constant_yun.DETAIL_PHOTO_VIEW_FRAGMENT_BUNDLE_KEY;

/**
 * 상세페이지 상단부 사진, 공유버튼, 즐찾버튼.
 */
public class DetailPhotoViewFragment extends GetResultFragment {

    private Long foodId;
    private boolean isLogin = false;
    private boolean isFavorite = false;

    //이미지 주소
    private String imageFront, imageBack;

    //working variable
    private boolean isFront = true;

    //associate view
    private ImageView productImageView, shareButton, likeButton;

    public DetailPhotoViewFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();



    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_photo_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //find view
        this.productImageView = getView().findViewById(R.id.detailPhotoFragment_productImageView);
        this.shareButton = getView().findViewById(R.id.detailPhotoFragment_shareButton);
        this.likeButton = getView().findViewById(R.id.detailPhotoFragment_likeButton);

        //set view
        this.productImageView.setOnClickListener(v -> this.changeProductImage(!this.isFront));
        this.shareButton.setOnClickListener(v -> this.share());
        this.likeButton.setOnClickListener(v -> this.saveLike());

        //set image
        this.changeProductImage(this.isFront);
    }

    @Override
    public void setFragmentRequestKey() {
        this.fragmentRequestKey = DETAIL_PHOTO_VIEW_FRAGMENT_BUNDLE_KEY;
    }

    @Override
    public void ResultParse(String requestKey, Bundle result) {
        this.imageFront = result.getString(DETAIL_PHOTO_VIEW_FRAGMENT_BUNDLE_FRONT_IMAGE);
        this.imageBack = result.getString(DETAIL_PHOTO_VIEW_FRAGMENT_BUNDLE_BACK_IMAGE);
        this.foodId = result.getLong("foodId");
        this.changeProductImage(this.isFront);
        this.checkFavorite();
    }

    private void setIsFavorite(boolean flag) {
        this.isFavorite = flag;
        likeButton.setImageResource(
                this.isFavorite ?
                        R.drawable.ic_baseline_favorite_24 :
                        R.drawable.ic_baseline_favorite_border_24
        );
        likeButton.setColorFilter(
                this.isFavorite ?
                        R.color.kati_orange :
                        R.color.kati_yellow,
                PorterDuff.Mode.SRC_IN
        );



    }

    private void checkFavorite() {
        new Thread(() -> {
            KatiDatabase database = KatiDatabase.getAppDatabase(getContext());
            String token = database.katiDataDao().getValue(KatiDatabase.AUTHORIZATION);
            if (token != null) {
                this.isLogin = true;
                Log.d("로그인", "되어있음");
                Call<Boolean> call = RestAPIClient.getApiService2(token).getFavoriteStateForFood(foodId);
                call.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if (!response.isSuccessful()) {
                            KatiDialog.showRetrofitNotSuccessDialog(getContext(), response.toString() , null).showDialog();

                        } else {
                            Log.d("디버그", response.body() + "");
                            setIsFavorite(response.body().booleanValue());


                        }

                        new Thread(() -> {
                            String token = response.headers().get(KatiDatabase.AUTHORIZATION);
                            KatiDatabase database = KatiDatabase.getAppDatabase(getContext());
                            database.katiDataDao().insert(new KatiData(KatiDatabase.AUTHORIZATION, token));
                        }).start();

                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {

                    }
                });
            }

        }).start();
    }


    /**
     * !!임시 :해당 제품을 즐겨찾기로 등록한다.
     */
    private void saveLike() {
        if (!isLogin) return;

        new Thread(() -> {
            KatiDatabase database = KatiDatabase.getAppDatabase(getContext());
            String token = database.katiDataDao().getValue(KatiDatabase.AUTHORIZATION);

            if (!isFavorite) {
                Call<Boolean> call = RestAPIClient.getApiService2(token).addFavoriteFood(foodId);
                call.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if (!response.isSuccessful())
                            KatiDialog.showRetrofitNotSuccessDialog(getContext(), response.toString() , null).showDialog();
                        else {
                            KatiDialog.simpleAlertDialog(
                                    getContext(),
                                    "즐겨찾기 설정 하였습니다.",
                                    "즐겨찾기 목록에 해당 제품이 추가되었습니다.",
                                    null,
                                    getContext().getResources().getColor(R.color.kati_coral, getContext().getTheme())
                            ).showDialog();
                            setIsFavorite(true);
                        }

                        new Thread(() -> {
                            String token = response.headers().get(KatiDatabase.AUTHORIZATION);
                            KatiDatabase database = KatiDatabase.getAppDatabase(getContext());
                            database.katiDataDao().insert(new KatiData(KatiDatabase.AUTHORIZATION, token));
                        }).start();
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        KatiDialog.showRetrofitFailDialog(getContext(), t.getMessage(), null).showDialog();
                    }
                });
            } else {
                Log.d("디버그","좋아요 삭제");
                Call<Void> call = RestAPIClient.getApiService2(token).deleteFavoriteFood(foodId);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (!response.isSuccessful())
                            KatiDialog.showRetrofitNotSuccessDialog(getContext(), response.toString() , null).showDialog();
                        else {
                            KatiDialog.simpleAlertDialog(
                                    getContext(),
                                    "즐겨찾기를 해제하였습니다.",
                                    "즐겨찾기 목록에서 해당 제품이 삭제되었습니다.",
                                    null,
                                    getContext().getResources().getColor(R.color.kati_coral, getContext().getTheme())
                            ).showDialog();
                            setIsFavorite(false);
                        }


                        new Thread(() -> {
                            String token = response.headers().get(KatiDatabase.AUTHORIZATION);
                            KatiDatabase database = KatiDatabase.getAppDatabase(getContext());
                            database.katiDataDao().insert(new KatiData(KatiDatabase.AUTHORIZATION, token));
                        }).start();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        KatiDialog.showRetrofitFailDialog(getContext(), t.getMessage(), null).showDialog();
                    }
                });
            }
        }).start();
    }


    /**
     * !!임시 :해당 제품을 다른 사람에게 공유한다.
     */
    private void share() {
        Toast.makeText(this.getContext(), "공유 아직 미구현", Toast.LENGTH_SHORT).show();
    }

    /**
     * 해당 제품의 이미지를 앞뒷면으로 토글한다.
     */
    private void changeProductImage(boolean isFront) {
        this.isFront = isFront;
        Glide.with(getContext()).load(this.isFront ? this.imageFront : this.imageBack).fitCenter().transform(new CenterCrop(), new CircleCrop()).into(this.productImageView);
    }
}