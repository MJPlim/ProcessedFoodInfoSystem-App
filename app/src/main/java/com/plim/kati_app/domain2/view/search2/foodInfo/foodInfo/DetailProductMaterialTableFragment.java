package com.plim.kati_app.domain2.view.search2.foodInfo.foodInfo;

import com.plim.kati_app.domain.DetailTableItem;
import com.plim.kati_app.domain.asset.AbstractTableFragment;

import java.util.HashMap;

import static com.plim.kati_app.domain.constant.Constant_yun.DETAIL_PRODUCT_MATERIAL_TABLE_FRAGMENT_INGREDIENT_NAME;
import static com.plim.kati_app.domain.constant.Constant_yun.DETAIL_PRODUCT_MATERIAL_TABLE_FRAGMENT_MATERIAL_NAME;
import static com.plim.kati_app.domain.constant.Constant_yun.DETAIL_PRODUCT_MATERIAL_TABLE_FRAGMENT_TABLE_NAME;

public class DetailProductMaterialTableFragment extends AbstractTableFragment {

    @Override
    public void foodModelDataUpdated() {
        String name = DETAIL_PRODUCT_MATERIAL_TABLE_FRAGMENT_TABLE_NAME;
        HashMap<String, String> infoMap = new HashMap<>();
        infoMap.put(DETAIL_PRODUCT_MATERIAL_TABLE_FRAGMENT_MATERIAL_NAME, this.foodDetailResponse.getMaterials());
        infoMap.put(DETAIL_PRODUCT_MATERIAL_TABLE_FRAGMENT_INGREDIENT_NAME, this.foodDetailResponse.getNutrient());
        this.setItemValues(new DetailTableItem(name, infoMap));
    }
}
