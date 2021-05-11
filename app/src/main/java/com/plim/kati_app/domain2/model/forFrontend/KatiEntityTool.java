package com.plim.kati_app.domain2.model.forFrontend;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.plim.kati_app.jshCrossDomain.domain.model.room.entity.JSHEntity;
import com.plim.kati_app.jshCrossDomain.domain.model.viewModel.JSHViewModelTool;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class KatiEntityTool {

    public static KatiEntity fromStringToKatiEntity(String value) {
        Type listType = new TypeToken<KatiEntity>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    public static  String fromKatiEntityToString(KatiEntity type) {
        Gson gson = new Gson();
        String json = gson.toJson(type);
        return json;
    }
    public static ArrayList<KatiEntity> convertJSHEntityArrayToDomainArray(ArrayList<JSHEntity> jshEntities) {
        ArrayList<KatiEntity> katiEntityArray = new ArrayList<>();
        for(JSHEntity jshEntity : jshEntities) katiEntityArray.add(KatiEntityTool.fromStringToKatiEntity(jshEntity.getEntityString()));
        return katiEntityArray;
    }
    public static void save(JSHViewModelTool viewModelTool, KatiEntity entity) {
        ArrayList<JSHEntity> jshEntityArray = viewModelTool.getJSHEntities();
        if(jshEntityArray.size()!=0){
            JSHEntity jshEntity = jshEntityArray.get(0);
            jshEntity.setEntityString(KatiEntityTool.fromKatiEntityToString(entity));
            viewModelTool.getModel().update(jshEntity);
        }
    }
}
