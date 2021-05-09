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
import com.plim.kati_app.domain.asset.KatiDialog;
import com.plim.kati_app.domain.model.DetailTableItem;
import com.plim.kati_app.domain.model.dto.ReadUserAllergyResponse;
import com.plim.kati_app.domain.model.room.KatiData;
import com.plim.kati_app.domain.model.room.KatiDatabase;
import com.plim.kati_app.tech.RestAPIClient;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserAllergyListFragment extends AbstractExpandableItemList {

    public UserAllergyListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onResume() {
        super.onResume();
        this.loadAllergy();
    }

    private void loadAllergy() {
        new Thread(() -> {
            KatiDatabase database = KatiDatabase.getAppDatabase(this.getActivity());
            String token = database.katiDataDao().getValue(KatiDatabase.AUTHORIZATION);

            RestAPIClient.getApiService2(token).readUserAllergy().enqueue(new Callback<ReadUserAllergyResponse>() {
                @Override
                public void onResponse(Call<ReadUserAllergyResponse> call, Response<ReadUserAllergyResponse> response) {
                    if (!response.isSuccessful()) {
                        KatiDialog.showRetrofitNotSuccessDialog(getContext(),
                                response.code() + "",
                                null
                        ).showDialog();
                    } else {
                        Vector<String> vector = new Vector<>();
                        vector.addAll(response.body().getUserAllergyMaterials());
                        setItemValues(new ExpandableListItem(
                                "알레르기",
                                vector)
                        );
                    }

                    new Thread(() -> {
                        String token = response.headers().get(KatiDatabase.AUTHORIZATION);
                        database.katiDataDao().insert(new KatiData(KatiDatabase.AUTHORIZATION, token));
                    }).start();

                }

                @Override
                public void onFailure(Call<ReadUserAllergyResponse> call, Throwable t) {
                    KatiDialog.showRetrofitFailDialog(getContext(), t.getMessage(), null);
                }
            });


        }).start();

    }


    @Override
    public void setFragmentRequestKey() {
        this.fragmentRequestKey = "allergyList";
    }

    @Override
    public void ResultParse(String requestKey, Bundle result) {
    }
}