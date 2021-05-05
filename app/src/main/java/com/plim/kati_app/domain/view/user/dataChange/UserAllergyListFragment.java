package com.plim.kati_app.domain.view.user.dataChange;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.plim.kati_app.R;
import com.plim.kati_app.domain.asset.AbstractExpandableItemList;
import com.plim.kati_app.domain.model.DetailTableItem;


public class UserAllergyListFragment extends AbstractExpandableItemList {

    public UserAllergyListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void setFragmentRequestKey() {
        this.fragmentRequestKey="";
    }

    @Override
    public void ResultParse(String requestKey, Bundle result) {
//        this.setItemValues(new DetailTableItem());

    }
}