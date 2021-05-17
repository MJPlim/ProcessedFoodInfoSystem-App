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
        HashMap<String,String> hashMap= new HashMap<>();
        hashMap.put("0.00(0)",
                "5점     0.00%\n4점     0.00%\n3점     0.00%\n2점     0.00%\n1점     0.00%");
        this.setItemValues(new DetailTableItem("제품 리뷰",hashMap,new HashMap<String,String>()));
        this.fragmentRequestKey="reviewStatistics";
       }

    @Override
    public void ResultParse(String requestKey, Bundle result) {
        Long count=result.getLong("count");
        HashMap<String,Float>map= (HashMap<String, Float>) result.getSerializable("map");

        HashMap<String,String> hashMap= new HashMap<>();
        hashMap.put(map.get("avgRating")+"("+count+")",
                            "5점     "+Math.round(map.get("oneCount")*10000)/100
                        +"%\n4점     "+Math.round(map.get("twoCount")*10000)/100
                        +"%\n3점     "+Math.round(map.get("threeCount")*10000)/100
                        +"%\n2점     "+Math.round(map.get("fourCount")*10000)/100
                        +"%\n1점     "+Math.round(map.get("fiveCount")*10000)/100+"%");
        this.setItemValues(new DetailTableItem("제품 리뷰",hashMap,new HashMap<String, String>()));
    }
}
