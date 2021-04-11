package com.plim.kati_app.domain.view.search.fragment;

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
            button.setHeight(80);
            button.setWidth(150);
            button.setGravity(TextView.TEXT_ALIGNMENT_CENTER);
            button.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            button.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.shape_light_button));
            newTableRow.addView(button);
        }
        newTableRow.setPadding(30,30,30,30);
        this.allergyTableRows.add(newTableRow);

        for(TableRow tableRow: allergyTableRows){
            this.allergyTableLayout.addView(tableRow);
        }
    }
}
