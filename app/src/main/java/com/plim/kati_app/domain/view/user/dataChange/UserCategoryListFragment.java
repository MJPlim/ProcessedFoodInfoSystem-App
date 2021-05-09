package com.plim.kati_app.domain.view.user.dataChange;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.plim.kati_app.domain.asset.AbstractExpandableItemList;
import com.plim.kati_app.domain.asset.KatiDialog;
import com.plim.kati_app.domain.model.dto.ReadUserAllergyResponse;
import com.plim.kati_app.domain.model.room.KatiData;
import com.plim.kati_app.domain.model.room.KatiDatabase;
import com.plim.kati_app.tech.RestAPIClient;

import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserCategoryListFragment extends AbstractExpandableItemList {

    public UserCategoryListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.loadCategory();
    }

    private void loadCategory() {
//        new Thread(() -> {
//            KatiDatabase database = KatiDatabase.getAppDatabase(this.getActivity());
//            String token = database.katiDataDao().getValue(KatiDatabase.AUTHORIZATION);
//
//
//        }).start();

        Vector<String> strings= new Vector<>();
        strings.add("임시데이터");
        strings.add("임시데이터");
        strings.add("임시데이터");
        strings.add("임시데이터");

        this.setItemValues(new ExpandableListItem("선호 카테고리",strings));

    }


    @Override
    public void setFragmentRequestKey() {
        this.fragmentRequestKey = "categoryList";
    }

    @Override
    public void ResultParse(String requestKey, Bundle result) {
    }
}