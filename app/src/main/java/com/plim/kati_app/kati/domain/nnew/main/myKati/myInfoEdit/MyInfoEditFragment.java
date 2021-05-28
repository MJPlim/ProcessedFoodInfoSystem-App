package com.plim.kati_app.kati.domain.nnew.main.myKati.myInfoEdit;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.plim.kati_app.R;
import com.plim.kati_app.kati.crossDomain.domain.model.Constant;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.KatiDialog;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.KatiViewModelFragment;
import com.plim.kati_app.kati.domain.nnew.editName.EditNameActivity;
import com.plim.kati_app.kati.domain.nnew.editPassword.EditPasswordActivity;
import com.plim.kati_app.kati.domain.nnew.setRestoreEmail.SetRestoreEmailActivity;
import com.plim.kati_app.kati.domain.nnew.signOut.SignOutActivity;

import org.jetbrains.annotations.NotNull;

import java.util.Vector;

import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.LOG_OUT_ACTIVITY_FAILURE_DIALOG_MESSAGE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.LOG_OUT_ACTIVITY_FAILURE_DIALOG_TITLE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.LOG_OUT_ACTIVITY_SUCCESSFUL_DIALOG_MESSAGE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.LOG_OUT_ACTIVITY_SUCCESSFUL_DIALOG_TITLE;

public class MyInfoEditFragment extends KatiViewModelFragment {

    //component
    //view
    private Button editPasswordButton, editNameButton, editRestoreEmailButton;
    private TextView logOut, signOut;

    private Vector<KatiDialog> dialogs;

    public MyInfoEditFragment() {
        this.dialogs= new Vector<>();
    }

    @Override
    public void onDestroy() {
        for(KatiDialog dialog:dialogs){
            dialog.dismiss();
            dialogs.remove(dialog);
        }
        super.onDestroy();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_info_edit;
    }

    @Override
    protected void associateView(View view) {
        this.editPasswordButton = view.findViewById(R.id.myInfoEditFragment_editPasswordButton);
        this.editNameButton = view.findViewById(R.id.myInfoEditFragment_editNameButton);
        this.editRestoreEmailButton = view.findViewById(R.id.myInfoEditFragment_restoreEmailButton);

        this.logOut = view.findViewById(R.id.myInfoEditFragment_logOutTextView);
        this.signOut = view.findViewById(R.id.myInfoEditFragment_signOutTextView);
    }

    @Override
    protected void initializeView() {
        editPasswordButton.setOnClickListener(v -> this.getActivity().startActivity(new Intent(this.getContext(), EditPasswordActivity.class)));
        editNameButton.setOnClickListener(v -> this.getActivity().startActivity(new Intent(this.getContext(), EditNameActivity.class)));
        editRestoreEmailButton.setOnClickListener(v -> this.getActivity().startActivity(new Intent(this.getContext(), SetRestoreEmailActivity.class)));

        this.logOut.setOnClickListener(v -> this.logOut());
        this.signOut.setOnClickListener(v -> this.getActivity().startActivity(new Intent(this.getContext(), SignOutActivity.class)));
    }

    @Override
    protected void katiEntityUpdated() {

    }

    private void logOut() {
        if (!this.dataset.get(KatiEntity.EKatiData.AUTHORIZATION).equals(KatiEntity.EKatiData.NULL.name())) {
            this.dataset.put(KatiEntity.EKatiData.AUTHORIZATION, KatiEntity.EKatiData.NULL.name());
            this.dataset.put(KatiEntity.EKatiData.EMAIL, KatiEntity.EKatiData.NULL.name());
            this.dataset.put(KatiEntity.EKatiData.PASSWORD, KatiEntity.EKatiData.NULL.name());
            this.dataset.put(KatiEntity.EKatiData.AUTO_LOGIN, KatiEntity.EKatiData.FALSE.name());
            this.showOkDialog();
        } else {
            this.showNoDialog();
        }
    }

    public void showOkDialog() {
        this.dialogs.add(this.showDialog(LOG_OUT_ACTIVITY_SUCCESSFUL_DIALOG_TITLE, LOG_OUT_ACTIVITY_SUCCESSFUL_DIALOG_MESSAGE,null));
    }

    public void showNoDialog() {
        this.dialogs.add(this.showDialog(LOG_OUT_ACTIVITY_FAILURE_DIALOG_TITLE, LOG_OUT_ACTIVITY_FAILURE_DIALOG_MESSAGE,null));
    }


}