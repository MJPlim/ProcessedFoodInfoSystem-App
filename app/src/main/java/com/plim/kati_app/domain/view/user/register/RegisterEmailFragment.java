package com.plim.kati_app.domain.view.user.register;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.plim.kati_app.R;
import com.plim.kati_app.domain.asset.AbstractFragment1;
import com.plim.kati_app.domain.asset.KatiDialog;
import com.plim.kati_app.domain.model.RegisterActivityViewModel;
import com.plim.kati_app.domain.model.room.KatiDatabase;
import com.plim.kati_app.domain.view.MainActivity;
import com.plim.kati_app.domain.view.user.signOut.NewWithdrawalActivity;
import com.plim.kati_app.domain.view.user.signOut.TempActivity;

public class RegisterEmailFragment extends AbstractFragment1 {

    @Override
    protected void initializeView() {
        this.mainTextView.setText(getString(R.string.register_email_maintext));
        this.subTextView.setVisibility(View.INVISIBLE);
        this.editText.setHint(getString(R.string.register_email_hint));
        this.button.setText(getString(R.string.button_ok));
    }

    @Override
    public void onResume() {
        super.onResume();
        new Thread(()->{
            KatiDatabase database= KatiDatabase.getAppDatabase(getContext());
            if(database.katiDataDao().getValue(KatiDatabase.AUTHORIZATION)!=null) {
                getActivity().runOnUiThread(()->showNotLoginedDialog());
            }
        }).start();
    }

    private void showNotLoginedDialog(){
        KatiDialog.simpleAlertDialog(getContext(),
                getString(R.string.login_already_signed_dialog),
                getString(R.string.login_already_signed_content_dialog),
                (dialog, which)->{
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }, getResources().getColor(R.color.kati_coral,getContext().getTheme())).showDialog();
    }

    @Override
    protected void buttonClicked() {
        RegisterActivityViewModel registerActivityViewModel = new ViewModelProvider(this.requireActivity()).get(RegisterActivityViewModel.class);
        registerActivityViewModel.getUser().setEmail(this.editText.getText().toString());

        Navigation.findNavController(this.getView()).navigate(R.id.action_register1Fragment_to_register2Fragment);
    }
}