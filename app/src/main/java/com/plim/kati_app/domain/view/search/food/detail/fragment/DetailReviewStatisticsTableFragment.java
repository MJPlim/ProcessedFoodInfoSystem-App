package com.plim.kati_app.domain.view.search.food.detail.fragment;

import android.os.Bundle;

import com.plim.kati_app.domain.asset.AbstractTableFragment;
import com.plim.kati_app.domain.model.DetailTableItem;

import java.util.HashMap;

/**
 * 리뷰 수 통계를 보여주는 테이블 fragment.
 */
public class DetailReviewStatisticsTableFragment extends AbstractTableFragment {

    /**
     * !임시!! 원래 번들 키를 설정하는 메소드인데, 불러오는 것이 없으므로, 일단 이렇게 해 놓았음.
     */
    @Override
    public void setFragmentRequestKey() {
        this.fragmentRequestKey="temp";
        HashMap<String,String> hashMap= new HashMap<>();
        hashMap.put("4.7\n(250+)","5점     80%\n4점     11%\n3점     8%\n2점     1%\n1점     1%");
        this.setItemValues(new DetailTableItem("제품 리뷰",hashMap));
    }

    @Override
    public void ResultParse(String requestKey, Bundle result) {

    }
}
