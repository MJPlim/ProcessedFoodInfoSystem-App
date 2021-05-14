package com.plim.kati_app.domain.view.search.food.detail.fragment;

import android.os.Bundle;
import android.util.Log;

import com.plim.kati_app.domain.asset.AbstractTableFragment;
import com.plim.kati_app.domain.model.DetailTableItem;

import java.util.HashMap;

import static com.plim.kati_app.constants.Constant_yun.ABSTRACT_TABLE_FRAGMENT_BUNDLE_TABLE_HASH_MAP;
import static com.plim.kati_app.constants.Constant_yun.ABSTRACT_TABLE_FRAGMENT_BUNDLE_TABLE_LINK_MAP;
import static com.plim.kati_app.constants.Constant_yun.ABSTRACT_TABLE_FRAGMENT_BUNDLE_TABLE_NAME;
import static com.plim.kati_app.constants.Constant_yun.DETAIL_PRODUCT_INFO_TABLE_FRAGMENT_BUNDLE_KEY;

/**
 * 제품명, 회사명 등 기본적인 정보가 들어있는 fragment
 */
public class DetailProductInfoTableFragment extends AbstractTableFragment {

    @Override
    public void setFragmentRequestKey() {
        this.fragmentRequestKey=DETAIL_PRODUCT_INFO_TABLE_FRAGMENT_BUNDLE_KEY;
    }

    @Override
    public void ResultParse(String requestKey, Bundle result) {




        String name= result.getString(ABSTRACT_TABLE_FRAGMENT_BUNDLE_TABLE_NAME);
        HashMap<String, String> valueMap= (HashMap<String, String>) result.getSerializable(ABSTRACT_TABLE_FRAGMENT_BUNDLE_TABLE_HASH_MAP);
        HashMap<String, String> linkMap= (HashMap<String, String>) result.getSerializable(ABSTRACT_TABLE_FRAGMENT_BUNDLE_TABLE_LINK_MAP);
        if(valueMap!=null){
            DetailTableItem item = new DetailTableItem(name, valueMap, linkMap);
            this.setItemValues(item);
        }
    }
}
