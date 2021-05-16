package com.plim.kati_app.domain.view.search.food.list.setting;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.chip.Chip;
import com.google.android.material.tabs.TabLayout;
import com.plim.kati_app.R;
import com.plim.kati_app.constants.Constant_yun;
import com.plim.kati_app.domain.asset.AbstractExpandableItemList;
import com.plim.kati_app.domain.asset.BlankFragment;
import com.plim.kati_app.domain.asset.KatiDialog;
import com.plim.kati_app.domain.model.dto.ReadUserAllergyResponse;
import com.plim.kati_app.domain.model.room.KatiData;
import com.plim.kati_app.domain.model.room.KatiDatabase;
import com.plim.kati_app.tech.RestAPIClient;

import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.plim.kati_app.constants.Constant_yun.FOOD_SEARCH_FIELD_FRAGMENT_BUNDLE_INDEX;
import static com.plim.kati_app.constants.Constant_yun.FOOD_SEARCH_FIELD_FRAGMENT_BUNDLE_KEY;
import static com.plim.kati_app.constants.Constant_yun.FOOD_SEARCH_FIELD_FRAGMENT_BUNDLE_MODE;
import static com.plim.kati_app.constants.Constant_yun.FOOD_SEARCH_FIELD_FRAGMENT_BUNDLE_SORT;


public class SearchSettingFragment extends Fragment {
    //working variable
    private boolean isFiltered = false;
    private boolean showAllergy = false;

    // Associate
    // View
    //알레르기 필터링
    private ImageView allergyImageView;
    private TextView allergyTextView;

    private Constant_yun.SortElement sortElement;

    private Chip rankChip, manufacturerChip, reviewChip;

    //component
    private Fragment allergyViewFragment;
    private BlankFragment blankFragment;
    private Vector<String> allergyVector;


    public SearchSettingFragment() {
        // Required empty public constructor
        this.allergyVector= new Vector<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_setting_fragment, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Associate View
        this.allergyImageView = view.findViewById(R.id.searchFragment_allergyImageView);
        this.allergyTextView = view.findViewById(R.id.searchFragment_allergyTextView);

        this.manufacturerChip = view.findViewById(R.id.searchSettingFragment_manufacturerChip);
        this.rankChip = view.findViewById(R.id.searchSettingFragment_rankingChip);
        this.reviewChip = view.findViewById(R.id.searchSettingFragment_reviewChip);

        this.manufacturerChip.setOnCheckedChangeListener((buttonView, isChecked) -> this.doSort(isChecked,Constant_yun.SortElement.MANUFACTURER));
        this.rankChip.setOnCheckedChangeListener((buttonView, isChecked) -> this.doSort(isChecked,Constant_yun.SortElement.RANK));
        this.reviewChip.setOnCheckedChangeListener((buttonView, isChecked) -> this.doSort(isChecked,Constant_yun.SortElement.REVIEW_COUNT));

        this.getData();
    }


    private void doSort(boolean isChecked, Constant_yun.SortElement element){
        if (isChecked) {
            this.sortElement = element;
            Bundle bundle = new Bundle();
            bundle.putString(FOOD_SEARCH_FIELD_FRAGMENT_BUNDLE_SORT, this.sortElement.getMessage());
            this.getActivity().getSupportFragmentManager().setFragmentResult(FOOD_SEARCH_FIELD_FRAGMENT_BUNDLE_KEY, bundle);
        }
    }


    private void setVector() {

        this.allergyViewFragment = new AllergyViewFragment(this.allergyVector);

        this.blankFragment = new BlankFragment();
        this.allergyTextView.setOnClickListener(v -> {
            this.showAllergy = !showAllergy;
            if (showAllergy)
                getChildFragmentManager().beginTransaction().replace(R.id.searchFragment_allergyFrameLayout, allergyViewFragment).commit();
            else
                getChildFragmentManager().beginTransaction().replace(R.id.searchFragment_allergyFrameLayout, blankFragment).commit();
        });

        this.allergyImageView.setOnClickListener((v) -> {
            this.setAllergyFilter(!this.isFiltered);
            this.setColor();
        });
        this.setColor();
    }

    private void setAllergyFilter(boolean isFiltered){
        this.isFiltered=isFiltered;
            Bundle bundle = new Bundle();
            bundle.putBoolean("flag",this.isFiltered);
            bundle.putSerializable("allergy",this.allergyVector);
            this.getActivity().getSupportFragmentManager().setFragmentResult("allergyFilter", bundle);

    }


    public void setColor() {
        int newTint = this.isFiltered ? R.color.kati_red : R.color.kati_yellow;
        this.allergyImageView.setColorFilter(ContextCompat.getColor(getContext(), newTint), android.graphics.PorterDuff.Mode.SRC_IN);
    }

    private void getData() {

        new Thread(() -> {

            KatiDatabase database = KatiDatabase.getAppDatabase(this.getActivity());
            String token = database.katiDataDao().getValue(KatiDatabase.AUTHORIZATION);

            if (token == null) {
                this.allergyTextView.setVisibility(View.GONE);
                this.allergyImageView.setVisibility(View.GONE);
            }
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
                        allergyVector=vector;
                        setVector();
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



}