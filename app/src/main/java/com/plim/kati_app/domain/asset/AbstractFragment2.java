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

public abstract class AbstractFragment2 extends Fragment {

    // Associate
        // View
        protected TextView mainTextView, subTextView;
        protected EditText Present_Password_editText, New_Password_editText, New_Password_Verify_editText;
        protected Button button;

    /**
     * System Callback
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_change_password, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Associate View
        this.mainTextView = view.findViewById(R.id.abstractRegisterLayout2_mainTextView);
        this.subTextView = view.findViewById(R.id.abstractRegisterLayout2_subTextView);
        this.Present_Password_editText = view.findViewById(R.id.abstractRegisterLayout2_editText);
        this.New_Password_editText = view.findViewById(R.id.abstractRegisterLayout2_editText2);
        this.New_Password_Verify_editText = view.findViewById(R.id.abstractRegisterLayout2_editText3);
        this.button = view.findViewById(R.id.abstractRegisterLayout2_button);

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
