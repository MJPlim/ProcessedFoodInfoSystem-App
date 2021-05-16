package com.plim.kati_app.domain.view.search2.foodInfo.foodInfo;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.plim.kati_app.R;
import com.plim.kati_app.domain.katiCrossDomain.domain.view.KatiFoodFragment;

/**
 * 상세페이지 상단부 사진, 공유버튼, 즐찾버튼.
 */
public class DetailPhotoViewFragment extends KatiFoodFragment {

    // Working Variable
    private boolean isFront = true;

    // Associate
        // View
        private ImageView productImageView, shareButton, likeButton;

    @Override
    protected int getLayoutId() { return R.layout.fragment_detail_photo_view; }
    @Override
    protected void associateView(View view) {
        this.productImageView = view.findViewById(R.id.detailPhotoFragment_productImageView);
        this.shareButton = view.findViewById(R.id.detailPhotoFragment_shareButton);
        this.likeButton = view.findViewById(R.id.detailPhotoFragment_likeButton);
    }
    @Override
    protected void initializeView() {
        this.productImageView.setOnClickListener(v -> this.changeProductImage());
        this.shareButton.setOnClickListener(v -> this.share());
        this.likeButton.setOnClickListener(v -> this.saveLike());
    }
    @Override
    protected void katiEntityUpdated() { }
    @Override
    public void foodModelDataUpdated() { this.changeImage(this.foodDetailResponse.getFoodImageAddress()); }

    private void saveLike() { Toast.makeText(this.getContext(), "즐겨찾기 아직 미구현", Toast.LENGTH_SHORT).show(); }
    private void share() { Toast.makeText(this.getContext(), "공유 아직 미구현", Toast.LENGTH_SHORT).show(); }

    private void changeProductImage() {
        if(!this.isFront){ this.changeImage(this.foodModel.getFoodDetailResponse().getValue().getFoodImageAddress()); }
        else{ this.changeImage(this.foodModel.getFoodDetailResponse().getValue().getFoodMeteImageAddress()); }
        this.isFront = !this.isFront;
    }
    public void changeImage(String address){
        Glide.with(getContext()).load(address).fitCenter().transform(new CenterCrop(), new CircleCrop()).into(this.productImageView);
    }
}