package com.plim.kati_app.kati.domain.search.foodInfo.view.foodInfo.view;

import com.plim.kati_app.kati.crossDomain.domain.view.fragment.abstractTableFragment.TableInfo;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.abstractTableFragment.KatiAbstractTableFragment;

import java.util.HashMap;

import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.DETAIL_PRODUCT_MATERIAL_TABLE_FRAGMENT_INGREDIENT_NAME;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.DETAIL_PRODUCT_MATERIAL_TABLE_FRAGMENT_MATERIAL_NAME;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.DETAIL_PRODUCT_MATERIAL_TABLE_FRAGMENT_TABLE_NAME;

public class DetailProductMaterialTableFragmentKati extends KatiAbstractTableFragment {

    @Override
    public void foodModelDataUpdated() {
        String name = DETAIL_PRODUCT_MATERIAL_TABLE_FRAGMENT_TABLE_NAME;
        HashMap<String, String> infoMap = new HashMap<>();
        infoMap.put(DETAIL_PRODUCT_MATERIAL_TABLE_FRAGMENT_MATERIAL_NAME, this.foodDetailResponse.getMaterials());
        infoMap.put(DETAIL_PRODUCT_MATERIAL_TABLE_FRAGMENT_INGREDIENT_NAME, this.foodDetailResponse.getNutrient());
        this.setItemValues(new TableInfo(name, infoMap));
    }
}
