package com.plim.kati_app.domain.view.search.food.list.setting;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.plim.kati_app.R;
import com.plim.kati_app.constants.Constant;
import com.plim.kati_app.constants.Constant_yun;
import com.plim.kati_app.domain.asset.KatiDialog;
import com.plim.kati_app.domain.view.user.dataChange.UserDataChangeActivity;

import java.util.Vector;

/**
 * 알러지 정보를 보여주는 fragment.
 */
public class AllergyViewFragment extends Fragment {
    private Vector<String> data;

    private ChipGroup group;
    private TextView editTextView;

    public AllergyViewFragment(Vector<String> data) {
        this.data = data;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_allergy_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.group = view.findViewById(R.id.allergyViewFragment_allergyChipGroup);
        this.editTextView = view.findViewById(R.id.allergyViewFragment_editTextView);

        this.editTextView.setOnClickListener(v ->
                {
                    KatiDialog katiDialog = new KatiDialog(getContext());
                    katiDialog.setTitle("알레르기 필터를 수정하시겠습니까?");
                    katiDialog.setMessage("수정하기 위해 수정 페이지로 이동합니다.");
                    katiDialog.setPositiveButton(Constant_yun.KATI_DIALOG_YES, (dialog, which) -> getActivity().startActivity(new Intent(getActivity(), UserDataChangeActivity.class)));
                    katiDialog.setNegativeButton(Constant_yun.KATI_DIALOG_NO, null);
                    katiDialog.setColor(getContext().getResources().getColor(R.color.kati_coral, getContext().getTheme()));
                    katiDialog.showDialog();
                }
        );

        if (this.data.size() != 0) {
            view.findViewById(R.id.allergyViewFragment_noTextView).setVisibility(View.GONE);
        }
            for (String string : this.data) {
                Log.d("디버그",string);
                Chip chip = new Chip(view.getContext());
                chip.setText(string);
                chip.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.kati_yellow)));
                chip.setGravity(View.TEXT_ALIGNMENT_CENTER);
                chip.setClickable(false);
                this.group.addView(chip);
            }

    }
}
