package com.plim.kati_app.domain2.view.main.search.list.setting;

import android.os.Bundle;
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

import com.plim.kati_app.R;
import com.plim.kati_app.domain.constant.Constant_yun;

import java.util.Vector;

/**
 * 알러지 정보를 보여주는 fragment.
 */
public class AllergyViewFragment extends Fragment {
    private Vector<String> data;

    private TableLayout allergyTableLayout;
    private Vector<TableRow> allergyTableRows;

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

        this.allergyTableLayout= view.findViewById(R.id.allergyViewFragment_allergyTableLayout);

        this.allergyTableRows= new Vector<>();

        TableRow newTableRow =new TableRow(view.getContext());
        for (String data : data) {
            TextView button = new TextView(view.getContext());
            button.setText(data);
            button.setHeight(Constant_yun.ALLERGY_VIEW_FRAGMENT_BUTTON_ITEM_HEIGHT);
            button.setWidth(Constant_yun.ALLERGY_VIEW_FRAGMENT_BUTTON_ITEM_WIDTH);
            button.setGravity(TextView.TEXT_ALIGNMENT_CENTER);
            button.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            button.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.shape_light_button));



            newTableRow.addView(button);
        }
        newTableRow.setPadding(Constant_yun.ALLERGY_VIEW_FRAGMENT_BUTTON_PADDING,
                Constant_yun.ALLERGY_VIEW_FRAGMENT_BUTTON_PADDING,
                Constant_yun.ALLERGY_VIEW_FRAGMENT_BUTTON_PADDING,
                Constant_yun.ALLERGY_VIEW_FRAGMENT_BUTTON_PADDING);
        this.allergyTableRows.add(newTableRow);

        for(TableRow tableRow: allergyTableRows){
            this.allergyTableLayout.addView(tableRow);
        }
    }
}
