package com.plim.kati_app.kati.domain.old.search.search.view.foodList.searchSetting;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.plim.kati_app.R;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.KatiDialog;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.KatiSearchFragment;
import com.plim.kati_app.kati.domain.old.temp.editData.userData.view.EditDataActivity;

import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.ALLERGY_FILTER_INTENT_DIALOG_MESSAGE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.ALLERGY_FILTER_INTENT_DIALOG_TITLE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.KATI_DIALOG_NO;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.KATI_DIALOG_YES;

/**
 * 알러지 정보를 보여주는 fragment.
 */
public class AllergyViewFragment extends KatiSearchFragment {

    private ChipGroup group;
    private TextView editTextView,noTextView;

    public AllergyViewFragment() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_allergy_view;
    }


    @Override
    protected void associateView(View view) {
        this.group = view.findViewById(R.id.allergyViewFragment_allergyChipGroup);
        this.editTextView = view.findViewById(R.id.allergyViewFragment_editTextView);
        this.noTextView=view.findViewById(R.id.allergyViewFragment_noTextView);
    }

    @Override
    protected void initializeView() {
        this.editTextView.setOnClickListener(v ->
                {
                    KatiDialog katiDialog = new KatiDialog(getContext());
                    katiDialog.setTitle(ALLERGY_FILTER_INTENT_DIALOG_TITLE);
                    katiDialog.setMessage(ALLERGY_FILTER_INTENT_DIALOG_MESSAGE);
                    katiDialog.setPositiveButton(KATI_DIALOG_YES, (dialog, which) -> getActivity().startActivity(new Intent(getActivity(), EditDataActivity.class)));
                    katiDialog.setNegativeButton(KATI_DIALOG_NO, null);
                    katiDialog.setColor(getContext().getResources().getColor(R.color.kati_coral, getContext().getTheme()));
                    katiDialog.showDialog();
                }
        );

    }

    @Override
    protected void katiEntityUpdated() {
        if(this.searchModel.getAllergyList()!=null) {
            this.group.removeAllViews();
            for (String string : this.searchModel.getAllergyList()) {
                Chip chip = new Chip(getView().getContext());
                chip.setText(string);
                chip.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.kati_yellow)));
                chip.setClickable(false);
                this.group.addView(chip);
            }
            this.noTextView.setVisibility(this.searchModel.getAllergyList().size() != 0 ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    protected void searchModelDataUpdated() {

    }
}
