package com.plim.kati_app.kati.domain.nnew.main.myKati;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.plim.kati_app.R;
import com.plim.kati_app.kati.crossDomain.domain.view.etc.JSHSelectItem;
import com.plim.kati_app.kati.domain.nnew.login.LoginActivity;
import com.plim.kati_app.kati.domain.nnew.signUp.SignUpActivity;

import org.jetbrains.annotations.NotNull;

public class MyKatiFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mykati, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        JSHSelectItem myInfoEditSelect = view.findViewById(R.id.select_my_info_edit);
        myInfoEditSelect.setOnClickListener(
                v-> Navigation.findNavController(view).navigate(R.id.action_myKatiFragment_to_myInfoEditFragment)
        );

        JSHSelectItem reviewSelect = view.findViewById(R.id.select_my_review);
        reviewSelect.setOnClickListener(
                v-> Navigation.findNavController(view).navigate(R.id.action_myKatiFragment_to_reviewFlagment)
        );

        JSHSelectItem allergySelect = view.findViewById(R.id.select_my_allergy);
        allergySelect.setOnClickListener(
                v-> Navigation.findNavController(view).navigate(R.id.action_myKatiFragment_to_allergyFragment)
        );

        Button signUp = view.findViewById(R.id.mykati_signUp_button);
        signUp.setOnClickListener(v->this.getActivity().startActivity(new Intent(this.getContext(), SignUpActivity.class)));

        Button login = view.findViewById(R.id.mykati_login_button);
        login.setOnClickListener(v-> this.getActivity().startActivity(new Intent(this.getContext(), LoginActivity.class)));
    }
}