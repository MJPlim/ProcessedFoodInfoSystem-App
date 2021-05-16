package com.plim.kati_app.domain.katiCrossDomain.domain.view;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.plim.kati_app.R;

public abstract class AbstractFragment2 extends KatiViewModelFragment {

    // Associate
        // View
        protected TextView mainTextView, subTextView;
        protected EditText editText,editText2,editText3;
        protected Button button;

    /**
     * System Callback
     */
    @Override
    protected int getLayoutId() {
        return R.layout.abstract_layout2;
    }

    @Override
    protected void associateView(View view) {
        this.mainTextView = view.findViewById(R.id.abstractRegisterLayout2_mainTextView);
        this.subTextView = view.findViewById(R.id.abstractRegisterLayout2_subTextView);
        this.editText = view.findViewById(R.id.abstractRegisterLayout2_editText);
        this.editText2 = view.findViewById(R.id.abstractRegisterLayout2_editText2);
        this.editText3 = view.findViewById(R.id.abstractRegisterLayout2_editText3);
        this.button = view.findViewById(R.id.abstractRegisterLayout2_button);
    }

    @Override
    protected void initializeView() {
        this.button.setOnClickListener(v->this.buttonClicked());
    }

    /**
     * Button Callback
     */
    protected abstract void buttonClicked();
}
