package com.plim.kati_app.domain2.view.search.food.detail.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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

import static com.plim.kati_app.domain.constant.Constant_yun.DETAIL_PHOTO_VIEW_FRAGMENT_BUNDLE_BACK_IMAGE;
import static com.plim.kati_app.domain.constant.Constant_yun.DETAIL_PHOTO_VIEW_FRAGMENT_BUNDLE_FRONT_IMAGE;
import static com.plim.kati_app.domain.constant.Constant_yun.DETAIL_PHOTO_VIEW_FRAGMENT_BUNDLE_KEY;

/**
 * 상세페이지 상단부 사진, 공유버튼, 즐찾버튼.
 */
public class DetailPhotoViewFragment extends GetResultFragment {

    //이미지 주소
    private String imageFront, imageBack;

    //working variable
    private boolean isFront = true;

    //associate view
    private ImageView productImageView, shareButton, likeButton;

    public DetailPhotoViewFragment() {}

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
        this.fragmentRequestKey=DETAIL_PHOTO_VIEW_FRAGMENT_BUNDLE_KEY;
    }

    @Override
    public void ResultParse(String requestKey, Bundle result) {
        this.imageFront=result.getString(DETAIL_PHOTO_VIEW_FRAGMENT_BUNDLE_FRONT_IMAGE);
        this.imageBack=result.getString(DETAIL_PHOTO_VIEW_FRAGMENT_BUNDLE_BACK_IMAGE);
        this.changeProductImage(this.isFront);
    }


    /**
     * !!임시 :해당 제품을 즐겨찾기로 등록한다.
     */
    private void saveLike() {
        Toast.makeText(this.getContext(), "즐겨찾기 아직 미구현", Toast.LENGTH_SHORT).show();
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
        Glide.with(getContext()).load(this.isFront? this.imageFront:this.imageBack).fitCenter().transform(new CenterCrop(), new CircleCrop()).into(this.productImageView);
    }
}