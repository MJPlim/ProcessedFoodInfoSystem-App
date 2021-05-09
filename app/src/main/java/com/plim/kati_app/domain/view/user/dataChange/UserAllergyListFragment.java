package com.plim.kati_app.domain.view.user.dataChange;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.plim.kati_app.R;
import com.plim.kati_app.domain.asset.AbstractExpandableItemList;
import com.plim.kati_app.domain.asset.KatiDialog;
import com.plim.kati_app.domain.model.DetailTableItem;
import com.plim.kati_app.domain.model.UserInfoResponse;
import com.plim.kati_app.domain.model.dto.CreateUserAllergyRequest;
import com.plim.kati_app.domain.model.dto.ReadUserAllergyResponse;
import com.plim.kati_app.domain.model.room.KatiData;
import com.plim.kati_app.domain.model.room.KatiDatabase;
import com.plim.kati_app.tech.RestAPIClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserAllergyListFragment extends AbstractExpandableItemList {

    private enum EAllergyList{
        새우,게,우유,아몬드,잣,호두,땅콩,대두,밀,메밀,메추리알,난류,계란,소고기,닭고기,쇠고기,돼지고기,오징어,조개류,복숭아,토마토,아황산류;
    }

    public UserAllergyListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    protected void addItem(String editText) {
        for(EAllergyList allergy: EAllergyList.values()){
            if(editText.equals(allergy.name())) {this.addItemToAdapter(editText);
            resetEditText();
            return;}
        }
        KatiDialog.simpleAlertDialog(
                getContext(),
                "해당하는 아이템 없음",
                editText+"(이)라는 아이템이 존재하지 않습니다.",
                (dialog,which)->resetEditText(),
                getContext().getResources().getColor(R.color.kati_coral,getContext().getTheme())
        ).showDialog();
        Log.d(editText,"디버그");
    }

    @Override
    public void onResume() {
        super.onResume();
        this.loadAllergy();
    }

    private void saveAllergy() {
        new Thread(() -> {
            KatiDatabase database = KatiDatabase.getAppDatabase(this.getActivity());
            String token = database.katiDataDao().getValue(KatiDatabase.AUTHORIZATION);

            CreateUserAllergyRequest request= new CreateUserAllergyRequest();
            request.setAllergyList(getItemsFromAdapter());

            RestAPIClient.getApiService2(token).createUserAllergy(request).enqueue(new Callback<UserInfoResponse>() {
                @Override
                public void onResponse(Call<UserInfoResponse> call, Response<UserInfoResponse> response) {
                    if (!response.isSuccessful()) {
                        KatiDialog.showRetrofitNotSuccessDialog(getContext(),
                                response.code() + "",
                                null
                        ).showDialog();
                    } else {
                        KatiDialog.simpleAlertDialog(
                                getContext(),
                                "알레르기 정보 저장 성공",
                                "알레르기 정보를 성공적으로 저장하였습니다.",
                                null,
                                getContext().getResources().getColor(R.color.kati_coral,getContext().getTheme())
                        ).showDialog();

                    }

                    new Thread(() -> {
                        String token = response.headers().get(KatiDatabase.AUTHORIZATION);
                        database.katiDataDao().insert(new KatiData(KatiDatabase.AUTHORIZATION, token));
                    }).start();

                }

                @Override
                public void onFailure(Call<UserInfoResponse> call, Throwable t) {
                    KatiDialog.showRetrofitFailDialog(getContext(), t.getMessage(), null);
                }
            });


        }).start();
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
        this.fragmentRequestKey = "saveAllergyList";
    }

    @Override
    public void ResultParse(String requestKey, Bundle result) {
        this.saveAllergy();
    }


}