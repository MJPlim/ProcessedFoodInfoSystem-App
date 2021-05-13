package com.plim.kati_app.domain2.view.search2.foodInfo.foodInfo;

import com.plim.kati_app.domain.DetailTableItem;
import com.plim.kati_app.domain.asset.AbstractTableFragment;

import java.util.HashMap;

import static com.plim.kati_app.domain.constant.Constant_yun.DETAIL_PRODUCT_INFO_TABLE_FRAGMENT_EXPIRATION_DATE;
import static com.plim.kati_app.domain.constant.Constant_yun.DETAIL_PRODUCT_INFO_TABLE_FRAGMENT_MANUFACTURER_NAME;
import static com.plim.kati_app.domain.constant.Constant_yun.DETAIL_PRODUCT_INFO_TABLE_FRAGMENT_PRODUCT_NAME;
import static com.plim.kati_app.domain.constant.Constant_yun.DETAIL_PRODUCT_INFO_TABLE_FRAGMENT_TABLE_NAME;

public class DetailProductInfoTableFragment extends AbstractTableFragment {

    @Override
    public void foodModelDataUpdated() {
        String name = DETAIL_PRODUCT_INFO_TABLE_FRAGMENT_TABLE_NAME;
        HashMap<String, String> infoMap = new HashMap<>();
        infoMap.put(DETAIL_PRODUCT_INFO_TABLE_FRAGMENT_PRODUCT_NAME, this.foodDetailResponse.getFoodName());
        infoMap.put(DETAIL_PRODUCT_INFO_TABLE_FRAGMENT_MANUFACTURER_NAME, this.foodDetailResponse.getManufacturerName());
        infoMap.put(DETAIL_PRODUCT_INFO_TABLE_FRAGMENT_EXPIRATION_DATE, "-");
        this.setItemValues(new DetailTableItem(name, infoMap));
    }
}
