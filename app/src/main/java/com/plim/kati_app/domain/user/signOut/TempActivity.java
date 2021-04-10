package com.plim.kati_app.domain.user.signOut;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.plim.kati_app.R;
import com.plim.kati_app.domain.asset.KatiDialog;

public class TempActivity extends AppCompatActivity {

    // Associate
        // View
        private Button signOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        // Associate View
        this.signOutButton = this.findViewById(R.id.button);

        // Initialize View
        this.signOutButton.setOnClickListener(v->this.showSignOutAskDialog());
    }

    /**
     * show Sign Out Ask Dialog
     */
    private void showSignOutAskDialog(){
        KatiDialog signOutAskDialog = new KatiDialog(this);
        signOutAskDialog.setTitle("정말 탈퇴하시겠습니까?");
        signOutAskDialog.setMessage("회원을 탈퇴하시면 그동안 쌓여왔던 모든 자료와 데이터가 삭제됩니다.");
        signOutAskDialog.setPositiveButton("예", (dialog, which) -> this.showSignOutCompleteDialog());
        signOutAskDialog.setNegativeButton("아니오", null);
        signOutAskDialog.setColor(this.getResources().getColor(R.color.kati_coral, this.getTheme()));
        signOutAskDialog.showDialog();
    }

    /**
     * show Sign Out Complete Dialog
     */
    private void showSignOutCompleteDialog(){
        KatiDialog signOutCompleteDialog = new KatiDialog(this);
        signOutCompleteDialog.setTitle("회원 탈퇴가 완료되었습니다.");
        signOutCompleteDialog.setPositiveButton("확인", (dialog, which) -> this.startActivity(new Intent(this, WithdrawalActivity.class)));
        signOutCompleteDialog.setColor(this.getResources().getColor(R.color.kati_coral, this.getTheme()));
        signOutCompleteDialog.showDialog();
    }
}