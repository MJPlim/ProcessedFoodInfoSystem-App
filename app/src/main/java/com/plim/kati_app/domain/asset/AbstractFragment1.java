package com.plim.kati_app.domain.asset;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.plim.kati_app.R;

public abstract class AbstractFragment1 extends Fragment {

    // Associate
        // View
        protected TextView mainTextView, subTextView;
        protected EditText editText;
        protected Button button;

    /**
     * System Callback
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.abstract_layout_1, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Associate View
        this.mainTextView = view.findViewById(R.id.abstractRegisterLayout_mainTextView);
        this.subTextView = view.findViewById(R.id.abstractRegisterLayout_subTextView);
        this.editText = view.findViewById(R.id.abstractRegisterLayout_editText);
        this.button = view.findViewById(R.id.abstractRegisterLayout_button);

        // Initialize View
        this.button.setOnClickListener(v->this.buttonClicked());
        this.initializeView();
    }

    /**
     * Initialize View
     */
    protected abstract void initializeView();

    /**
     * Button Callback
     */
    protected abstract void buttonClicked();

    protected String getStringOfId(int id){
        return this.getResources().getString(id);
    }
}
