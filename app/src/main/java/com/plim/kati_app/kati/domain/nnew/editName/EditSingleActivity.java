package com.plim.kati_app.kati.domain.nnew.editName;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.plim.kati_app.R;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitTool;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.kati.crossDomain.domain.view.activity.KatiLoginCheckViewModelActivity;
import com.plim.kati_app.kati.crossDomain.domain.view.etc.JSHToolBar;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.KatiRetrofitTool;
import com.plim.kati_app.kati.domain.nnew.main.MainActivity;
import com.plim.kati_app.kati.domain.nnew.main.myKati.myInfoEdit.model.UserInfoModifyRequest;
import com.plim.kati_app.kati.domain.nnew.main.myKati.myInfoEdit.model.UserInfoResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import retrofit2.Response;

import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.USER_MODIFY_SUCCESS_DIALOG_MESSAGE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.USER_MODIFY_SUCCESS_DIALOG_TITLE;

public abstract class EditSingleActivity extends KatiLoginCheckViewModelActivity {

    protected JSHToolBar toolBar;
    protected EditText editText;
    protected Button submitButton;

    protected EditMode editMode;

    protected String name, address, birth;

    protected abstract EditMode getEditMode();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_name_edit;
    }

    @Override
    protected void associateView() {
        this.editMode=getEditMode();
        Intent intent = this.getIntent();
        this.name = intent.getStringExtra("name");
        this.address = intent.getStringExtra("address");
        this.birth = intent.getStringExtra("birth");

        this.toolBar = findViewById(R.id.nameEditActivity_toolbar);
        this.editText = findViewById(R.id.nameEditActivity_editText);
        this.submitButton = findViewById(R.id.nameEditActivity_submitButton);
    }

    @Override
    protected void initializeView() {
        this.toolBar.setToolBarTitle(editMode.getText()+" 변경");
        this.editText.setHint(editMode.getText()+" 입력");
     this.editText.addTextChangedListener(new TextWatcher() {
         @Override
         public void beforeTextChanged(CharSequence s, int start, int count, int after) {

         }

         @Override
         public void onTextChanged(CharSequence s, int start, int before, int count) {
             if(editText.length()>0) submitButton.setEnabled(true);
         }

         @Override
         public void afterTextChanged(Editable s) {

         }
     });
        this.submitButton.setEnabled(false);
        this.submitButton.setText(editMode.getText()+" 변경");
        this.submitButton.setOnClickListener(v -> this.modifyUserData(this.dataset.get(KatiEntity.EKatiData.AUTHORIZATION),this.editMode));
    }

    @Override
    protected boolean isLoginNeeded() {
        return true;
    }

    @Override
    protected void katiEntityUpdatedAndLogin() {

    }

    @Override
    protected void katiEntityUpdatedAndNoLogin() {

    }

    private class ModifyUserDataCallBack extends SimpleLoginRetrofitCallBack<UserInfoResponse> {
        public ModifyUserDataCallBack(Activity activity) {
            super(activity);
        }

        @Override
        public void onSuccessResponse(Response<UserInfoResponse> response) {
            showDialog(USER_MODIFY_SUCCESS_DIALOG_TITLE, USER_MODIFY_SUCCESS_DIALOG_MESSAGE, (dialog, which) -> startActivity(MainActivity.class));
        }
    }

    protected void modifyUserData(String token,EditMode editMode) {
        Log.d("디버그","유저 정보 수정");
        UserInfoModifyRequest request;
        if (this.editText.length() != 0) {
            switch (editMode){
                case name:
                    this.name=this.editText.getText().toString();
                    break;
                case birth:
                    this.birth=this.editText.getText().toString();
                    break;
                case address:
                    this.address=this.editText.getText().toString();
                    break;
            }
        }
            request = new UserInfoModifyRequest(name, birth, address);

//        String name = !nameEditText.getText().toString().equals("") ? nameEditText.getText().toString() : nameEditText.getHint().toString();
//        String birth = !birthEditText.getText().toString().equals("") ? LocalDate.parse(birthEditText.getText().toString()).format(DateTimeFormatter.ISO_DATE) :
//                !birthEditText.getHint().toString().equals(BASIC_DATE_FORMAT) ? LocalDate.parse(birthEditText.getHint().toString()).format(DateTimeFormatter.ISO_DATE) : null;
//        String address = !addressEditText.getText().toString().equals("") ? addressEditText.getText().toString() : addressEditText.getHint().toString();
//        UserInfoModifyRequest request = new UserInfoModifyRequest(name, birth, address);

        KatiRetrofitTool.getAPIWithAuthorizationToken(token).modifyUserInfo(request).enqueue(JSHRetrofitTool.getCallback(new ModifyUserDataCallBack(this)));
    }

    @Getter
    @AllArgsConstructor
    protected enum EditMode {
        name("닉네임"), address("주소"), birth("생일");
        private String text;
    }

}