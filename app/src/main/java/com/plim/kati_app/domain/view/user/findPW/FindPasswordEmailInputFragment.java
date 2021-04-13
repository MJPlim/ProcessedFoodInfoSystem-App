package com.plim.kati_app.domain.view.user.findPW;

import android.content.Intent;
import android.view.View;

import androidx.navigation.Navigation;

import com.plim.kati_app.R;
import com.plim.kati_app.domain.asset.AbstractFragment1;
import com.plim.kati_app.domain.asset.KatiDialog;
import com.plim.kati_app.domain.model.room.KatiDatabase;
import com.plim.kati_app.domain.view.MainActivity;

public class FindPasswordEmailInputFragment extends AbstractFragment1 {

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
        new Thread(() -> {
            KatiDatabase database = KatiDatabase.getAppDatabase(getContext());
            if (database.katiDataDao().getValue(KatiDatabase.AUTHORIZATION) != null) {
                getActivity().runOnUiThread(() -> showNotLoginedDialog());
            }
        }).start();
    }

    private void showNotLoginedDialog() {
        KatiDialog.simpleAlertDialog(getContext(),
                "이미 로그인 된 상태입니다.",
                "이미 로그인 된 상태입니다.",
                (dialog, which) -> { Intent intent = new Intent(getActivity(), MainActivity.class);startActivity(intent);
                }, getResources().getColor(R.color.kati_coral, getContext().getTheme())).showDialog();
    }

    @Override
    protected void buttonClicked() {
        this.showCompletedDialog();
        // 서버로 요청
        // 잘되면
        // this.showNoUserDialog();
        // 안되면
        // this.showCompletedDialog();
    }

    private void showNoUserDialog() {
        KatiDialog signOutAskDialog = new KatiDialog(this.getContext());
        signOutAskDialog.setTitle("해당하는 유저가 없습니다.");
        signOutAskDialog.setMessage("잘못 입력하였거나 해당하는 유저를 찾을 수 없습니다.");
        signOutAskDialog.setPositiveButton("확인", null);
        signOutAskDialog.setColor(this.getResources().getColor(R.color.kati_coral, this.getActivity().getTheme()));
        signOutAskDialog.showDialog();
    }

    private void showCompletedDialog() {
        KatiDialog signOutAskDialog = new KatiDialog(this.getContext());
        signOutAskDialog.setTitle("임시 비밀번호가 발급되었습니다.");
        signOutAskDialog.setMessage("메일함에서 임시 비밀번호 메일을 확인해 주세요.");
        signOutAskDialog.setPositiveButton("예", (dialog, which) -> Navigation.findNavController(this.getView()).navigate(R.id.action_findPasswordEmailInputFragment_to_findPasswordResultFragment));
        signOutAskDialog.setColor(this.getResources().getColor(R.color.kati_coral, this.getActivity().getTheme()));
        signOutAskDialog.showDialog();
    }
}