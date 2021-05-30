package com.plim.kati_app.kati.domain.nnew.main.myKati;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.plim.kati_app.R;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.kati.crossDomain.domain.view.etc.JSHSelectItem;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.KatiLoginCheckViewModelFragment;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.KatiViewModelFragment;
import com.plim.kati_app.kati.domain.nnew.login.LoginActivity;
import com.plim.kati_app.kati.domain.nnew.signUp.SignUpActivity;

import org.jetbrains.annotations.NotNull;

public class MyKatiFragment extends KatiLoginCheckViewModelFragment {
    ConstraintLayout myInfoEditSelect,loginLayout;
    JSHSelectItem reviewSelect;
    JSHSelectItem allergySelect;
    Button signUp, login;
    TextView name;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mykati;
    }

    @Override
    protected void associateView(View view) {
        loginLayout = view.findViewById(R.id.mykati_login_layout);
        name = view.findViewById(R.id.mykati_name_textView);
        myInfoEditSelect = view.findViewById(R.id.select_my_info_edit);
        reviewSelect = view.findViewById(R.id.select_my_review);
        allergySelect = view.findViewById(R.id.select_my_allergy);
        signUp = view.findViewById(R.id.mykati_signUp_button);
        login = view.findViewById(R.id.mykati_login_button);
    }

    @Override
    protected void initializeView() {

        myInfoEditSelect.setOnClickListener(
                v -> Navigation.findNavController(v).navigate(R.id.action_myKatiFragment_to_myInfoEditFragment)
        );
        reviewSelect.setOnClickListener(
                v -> Navigation.findNavController(v).navigate(R.id.action_myKatiFragment_to_reviewFlagment)
        );
        allergySelect.setOnClickListener(
                v -> Navigation.findNavController(v).navigate(R.id.action_myKatiFragment_to_allergyFragment)
        );
        signUp.setOnClickListener(v -> this.getActivity().startActivity(new Intent(this.getContext(), SignUpActivity.class)));
        login.setOnClickListener(v -> this.getActivity().startActivity(new Intent(this.getContext(), LoginActivity.class)));
    }

    @Override
    protected boolean isLoginNeeded() {
        return false;
    }

    @Override
    protected void katiEntityUpdatedAndLogin() {
        loginLayout.setVisibility(View.GONE);
        myInfoEditSelect.setVisibility(View.VISIBLE);
        allergySelect.setVisibility(View.VISIBLE);
        reviewSelect.setVisibility(View.VISIBLE);
        name.setText(dataset.get(KatiEntity.EKatiData.NAME));

    }

    @Override
    protected void katiEntityUpdatedAndNoLogin() {
        loginLayout.setVisibility(View.VISIBLE);
        myInfoEditSelect.setVisibility(View.GONE);
        allergySelect.setVisibility(View.GONE);
        reviewSelect.setVisibility(View.GONE);
    }




}