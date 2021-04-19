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
        this.mainTextView.setText("이메일을 입력해주세요");
        this.subTextView.setVisibility(View.INVISIBLE);
        this.editText.setHint("example@plim.com");
        this.button.setText("확인");
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
                "이미 로그인 된 상태입니다.",
                "이미 로그인 된 상태입니다.",
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