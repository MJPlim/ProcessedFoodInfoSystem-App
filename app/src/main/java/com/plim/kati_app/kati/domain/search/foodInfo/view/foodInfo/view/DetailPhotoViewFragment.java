package com.plim.kati_app.kati.domain.search.foodInfo.view.foodInfo.view;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.plim.kati_app.R;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitTool;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.KatiDialog;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.KatiFoodFragment;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.KatiRetrofitTool;

import retrofit2.Response;

import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.ADD_FAVORITE_RESULT_DIALOG_MESSAGE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.ADD_FAVORITE_RESULT_DIALOG_TITLE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.DELETE_FAVORITE_RESULT_DIALOG_MESSAGE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.DELETE_FAVORITE_RESULT_DIALOG_TITLE;

/**
 * 상세페이지 상단부 사진, 공유버튼, 즐찾버튼.
 */
public class DetailPhotoViewFragment extends KatiFoodFragment {

    //attribute
    private Long foodId;
    private boolean isFavorite = false;

    // Working Variable
    private boolean isFront = true;

    // Associate
    // View
    private ImageView productImageView, shareButton, likeButton;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_detail_photo_view;
    }

    @Override
    protected void associateView(View view) {
        this.productImageView = view.findViewById(R.id.detailPhotoFragment_productImageView);
        this.shareButton = view.findViewById(R.id.detailPhotoFragment_shareButton);
        this.likeButton = view.findViewById(R.id.detailPhotoFragment_likeButton);
    }

    @Override
    protected void initializeView() {
        this.productImageView.setOnClickListener(v -> this.changeProductImage(this.isFront));
        this.shareButton.setOnClickListener(v -> this.share());
        this.likeButton.setOnClickListener(v -> this.saveLike());
    }


    @Override
    public void foodModelDataUpdated() {
        if(this.dataset.containsKey(KatiEntity.EKatiData.AUTHORIZATION)) {
            String token = this.dataset.get(KatiEntity.EKatiData.AUTHORIZATION);
            Log.d("즐찾 확인", token + "-");
            this.foodId = this.foodDetailResponse.getFoodId();
            this.checkFavorite(token);
        }
        this.changeImage(this.foodDetailResponse.getFoodImageAddress());
    }

    private class AddFavoriteCallBack extends SimpleLoginRetrofitCallBack<Boolean> {

        public AddFavoriteCallBack(Activity activity) {
            super(activity);
        }

        @Override
        public void onSuccessResponse(Response<Boolean> response) {
            KatiDialog.simplerAlertDialog(
                    getActivity(),
                    ADD_FAVORITE_RESULT_DIALOG_TITLE,
                    ADD_FAVORITE_RESULT_DIALOG_MESSAGE,
                    null
            );
            setIsFavorite(true);
        }
    }

    private class DeleteFavoriteCallBack extends SimpleLoginRetrofitCallBack<Void> {

        public DeleteFavoriteCallBack(Activity activity) {
            super(activity);
        }

        @Override
        public void onSuccessResponse(Response<Void> response) {
            KatiDialog.simplerAlertDialog(
                    getActivity(),
                    DELETE_FAVORITE_RESULT_DIALOG_TITLE,
                    DELETE_FAVORITE_RESULT_DIALOG_MESSAGE,
                    null
            );
            setIsFavorite(false);
        }
    }

    private class CheckFavoriteCallBack extends SimpleLoginRetrofitCallBack<Boolean> {

        public CheckFavoriteCallBack(Activity activity) {
            super(activity);
        }

        @Override
        public void onSuccessResponse(Response<Boolean> response) {
            setIsFavorite(response.body().booleanValue());
        }
    }

    private void share() {
        Toast.makeText(this.getContext(), "공유 아직 미구현", Toast.LENGTH_SHORT).show();
    }

    private void changeProductImage(boolean isFront) {
        this.isFront = !isFront;
        if (!this.isFront) {
            this.changeImage(this.foodModel.getFoodDetailResponse().getValue().getFoodImageAddress());
        } else {
            this.changeImage(this.foodModel.getFoodDetailResponse().getValue().getFoodMeteImageAddress());
        }
    }

    public void changeImage(String address) {
        Glide.with(getContext()).load(address).fitCenter().transform(new CenterCrop(), new CircleCrop()).into(this.productImageView);
    }

    private void setIsFavorite(boolean flag) {
        this.isFavorite = flag;
        this.likeButton.setImageDrawable(getResources().getDrawable(
                this.isFavorite ?
                        R.drawable.ic_baseline_favorite_24 :
                        R.drawable.ic_baseline_favorite_border_24
                , getContext().getTheme()
        ));
        this.likeButton.clearColorFilter();
        this.likeButton.setColorFilter(
                getResources().getColor(
                        this.isFavorite ?
                                R.color.kati_red :
                                R.color.gray, getContext().getTheme()
                ),
                PorterDuff.Mode.SRC_IN
        );
    }

    private void saveLike() {
        if (this.dataset.containsKey(KatiEntity.EKatiData.AUTHORIZATION)) {
            String token = this.dataset.get(KatiEntity.EKatiData.AUTHORIZATION);

            if (!isFavorite) {
                KatiRetrofitTool.getAPIWithAuthorizationToken(token).addFavoriteFood(this.foodId).enqueue(JSHRetrofitTool.getCallback(new AddFavoriteCallBack(this.getActivity())));
            } else {
                KatiRetrofitTool.getAPIWithAuthorizationToken(token).deleteFavoriteFood(this.foodId).enqueue(JSHRetrofitTool.getCallback(new DeleteFavoriteCallBack(this.getActivity())));
            }
        }
    }

    private void checkFavorite(String token) {
        Log.d("음식아이디?",this.foodId+"");
        KatiRetrofitTool.getAPIWithAuthorizationToken(token).getFavoriteStateForFood(this.foodId).enqueue(JSHRetrofitTool.getCallback(new CheckFavoriteCallBack(this.getActivity())));
    }
}