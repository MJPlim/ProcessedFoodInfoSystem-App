package com.plim.kati_app.domain.view.search2.foodInfo.foodInfo;

import com.plim.kati_app.domain.katiCrossDomain.domain.view.abstractTableFragment.TableInfo;
import com.plim.kati_app.domain.katiCrossDomain.domain.view.AbstractTableFragment;

import java.util.HashMap;

public class DetailReviewStatisticsTableFragment extends AbstractTableFragment {

    @Override
    public void foodModelDataUpdated() {
        HashMap<String,String> hashMap= new HashMap<>();
        hashMap.put("4.7\n(250+)","5점     80%\n4점     11%\n3점     8%\n2점     1%\n1점     1%");
        this.setItemValues(new TableInfo("제품 리뷰",hashMap));
    }
}
