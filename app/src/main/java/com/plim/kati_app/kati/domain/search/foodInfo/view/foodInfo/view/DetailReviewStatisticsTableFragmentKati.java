package com.plim.kati_app.kati.domain.search.foodInfo.view.foodInfo.view;

import android.util.Log;

import com.plim.kati_app.kati.crossDomain.domain.view.fragment.abstractTableFragment.TableInfo;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.abstractTableFragment.KatiAbstractTableFragment;

import java.util.HashMap;

public class DetailReviewStatisticsTableFragmentKati extends KatiAbstractTableFragment {

    @Override
    protected void summaryDataUpdated() {
        super.summaryDataUpdated();
        Log.d("디버그",foodDetailResponse.getFoodId()+ (readSummaryResponse==null?"널임":"널 아님"));
        if(readSummaryResponse==null) return;
        HashMap<String, String> hashMap = new HashMap<>();
        float five=(float)readSummaryResponse.getFiveCount()/readSummaryResponse.getReviewCount();
        float four=(float)readSummaryResponse.getFourCount()/readSummaryResponse.getReviewCount();
        float three=(float)readSummaryResponse.getThreeCount()/readSummaryResponse.getReviewCount();
        float two=(float)readSummaryResponse.getTwoCount()/readSummaryResponse.getReviewCount();
        float one=(float)readSummaryResponse.getOneCount()/readSummaryResponse.getReviewCount();

        Log.d("디버그",five+"");

        hashMap.put(readSummaryResponse.getAvgRating() + "(" + readSummaryResponse.getReviewCount() + ")",
                "5점     " + Math.round( five* 10000) / 100
                        + "%\n4점     " + Math.round(four * 10000) / 100
                        + "%\n3점     " + Math.round(three * 10000) / 100
                        + "%\n2점     " + Math.round(two * 10000) / 100
                        + "%\n1점     " + Math.round(one * 10000) / 100 + "%");
        this.setItemValues(new TableInfo("제품 리뷰", hashMap));


    }

    @Override
    public void foodModelDataUpdated() {

    }
}
