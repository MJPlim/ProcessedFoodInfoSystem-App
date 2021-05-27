package com.plim.kati_app.kati.domain.old.search.foodInfo.view.foodInfo.view;

import com.plim.kati_app.kati.crossDomain.domain.view.fragment.abstractTableFragment.TableInfo;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.abstractTableFragment.KatiAbstractTableFragment;

import java.util.HashMap;

import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.DETAIL_PRODUCT_INFO_TABLE_FRAGMENT_EXPIRATION_DATE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.DETAIL_PRODUCT_INFO_TABLE_FRAGMENT_MANUFACTURER_NAME;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.DETAIL_PRODUCT_INFO_TABLE_FRAGMENT_PRODUCT_NAME;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.DETAIL_PRODUCT_INFO_TABLE_FRAGMENT_TABLE_NAME;

public class DetailProductInfoTableFragmentKati extends KatiAbstractTableFragment {

    @Override
    public void foodModelDataUpdated() {
        String name = DETAIL_PRODUCT_INFO_TABLE_FRAGMENT_TABLE_NAME;
        HashMap<String, String> infoMap = new HashMap<>();
        infoMap.put(DETAIL_PRODUCT_INFO_TABLE_FRAGMENT_PRODUCT_NAME, this.foodDetailResponse.getFoodName());
        infoMap.put(DETAIL_PRODUCT_INFO_TABLE_FRAGMENT_MANUFACTURER_NAME, this.foodDetailResponse.getManufacturerName());
        infoMap.put(DETAIL_PRODUCT_INFO_TABLE_FRAGMENT_EXPIRATION_DATE, "-");
        this.setItemValues(new TableInfo(name, infoMap));
    }
}
