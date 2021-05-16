package com.plim.kati_app.domain.view.search2.foodInfo.foodInfo;

import com.plim.kati_app.domain.katiCrossDomain.domain.view.abstractTableFragment.TableInfo;
import com.plim.kati_app.domain.katiCrossDomain.domain.view.AbstractTableFragment;

import java.util.HashMap;

import static com.plim.kati_app.domain.katiCrossDomain.domain.Constant.DETAIL_PRODUCT_MATERIAL_TABLE_FRAGMENT_INGREDIENT_NAME;
import static com.plim.kati_app.domain.katiCrossDomain.domain.Constant.DETAIL_PRODUCT_MATERIAL_TABLE_FRAGMENT_MATERIAL_NAME;
import static com.plim.kati_app.domain.katiCrossDomain.domain.Constant.DETAIL_PRODUCT_MATERIAL_TABLE_FRAGMENT_TABLE_NAME;

public class DetailProductMaterialTableFragment extends AbstractTableFragment {

    @Override
    public void foodModelDataUpdated() {
        String name = DETAIL_PRODUCT_MATERIAL_TABLE_FRAGMENT_TABLE_NAME;
        HashMap<String, String> infoMap = new HashMap<>();
        infoMap.put(DETAIL_PRODUCT_MATERIAL_TABLE_FRAGMENT_MATERIAL_NAME, this.foodDetailResponse.getMaterials());
        infoMap.put(DETAIL_PRODUCT_MATERIAL_TABLE_FRAGMENT_INGREDIENT_NAME, this.foodDetailResponse.getNutrient());
        this.setItemValues(new TableInfo(name, infoMap));
    }
}
