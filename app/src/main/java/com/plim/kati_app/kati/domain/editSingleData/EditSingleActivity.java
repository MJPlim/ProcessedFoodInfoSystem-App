package com.plim.kati_app.kati.domain.editSingleData;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import com.plim.kati_app.R;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitTool;
import com.plim.kati_app.kati.crossDomain.domain.model.Constant;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.kati.crossDomain.domain.view.activity.KatiHasTitleActivity;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.KatiRetrofitTool;
import com.plim.kati_app.kati.domain.nnew.main.MainActivity;
import com.plim.kati_app.kati.domain.nnew.main.myKati.myInfoEdit.model.UserInfoModifyRequest;
import com.plim.kati_app.kati.domain.nnew.main.myKati.myInfoEdit.model.UserInfoResponse;

import com.plim.kati_app.kati.crossDomain.domain.model.Constant.EEditSingleMode;

import retrofit2.Response;

import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.EDIT_SINGLE_DATA_BUTTON_TEXT_SUFFIX;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.EDIT_SINGLE_DATA_EXTRA_ADDRESS;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.EDIT_SINGLE_DATA_EXTRA_BIRTH;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.EDIT_SINGLE_DATA_EXTRA_NAME;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.EDIT_SINGLE_DATA_TITLE_SUFFIX;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.USER_MODIFY_SUCCESS_DIALOG_MESSAGE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.USER_MODIFY_SUCCESS_DIALOG_TITLE;

public abstract class EditSingleActivity extends KatiHasTitleActivity {

    //associate
    //view
    protected EditText editText;
    protected Button submitButton;

    //attribute
    protected EEditSingleMode editMode;
    protected String name, address, birth;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_name_edit;
    }

    @Override
    protected void associateView() {
        super.associateView();
        this.editMode = getEditMode();
        Intent intent = this.getIntent();
        this.name = intent.getStringExtra(EDIT_SINGLE_DATA_EXTRA_NAME);
        this.address = intent.getStringExtra(EDIT_SINGLE_DATA_EXTRA_ADDRESS);
        this.birth = intent.getStringExtra(EDIT_SINGLE_DATA_EXTRA_BIRTH);

        this.editText = findViewById(R.id.nameEditActivity_editText);
        this.submitButton = findViewById(R.id.nameEditActivity_submitButton);
    }

    @Override
    protected void initializeView() {
        super.initializeView();
        this.editText.setHint(editMode.getText() + EDIT_SINGLE_DATA_BUTTON_TEXT_SUFFIX);
        this.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editText.length() > 0) submitButton.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        this.submitButton.setEnabled(false);
        this.submitButton.setText(editMode.getText() + EDIT_SINGLE_DATA_TITLE_SUFFIX);
        this.submitButton.setOnClickListener(v -> this.modifyUserData(this.dataset.get(KatiEntity.EKatiData.AUTHORIZATION), this.editMode));
    }

    @Override
    protected String getTitleContent() {
        return editMode.getText() + EDIT_SINGLE_DATA_TITLE_SUFFIX;
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

    /**
     * callback
     */
    private class ModifyUserDataCallBack extends SimpleLoginRetrofitCallBack<UserInfoResponse> {
        public ModifyUserDataCallBack(Activity activity) {
            super(activity);
        }

        @Override
        public void onSuccessResponse(Response<UserInfoResponse> response) {
            dialogVector.add(showDialog(USER_MODIFY_SUCCESS_DIALOG_TITLE, USER_MODIFY_SUCCESS_DIALOG_MESSAGE, (dialog, which) -> onBackPressed()));
        }
    }

    /**
     * method
     **/
    protected abstract EEditSingleMode getEditMode();

    protected void modifyUserData(String token, Constant.EEditSingleMode editMode) {
        UserInfoModifyRequest request;
        if (this.editText.length() != 0) {
            switch (editMode) {
                case name:
                    this.name = this.editText.getText().toString();
                    break;
                case birth:
                    this.birth = this.editText.getText().toString();
                    break;
                case address:
                    this.address = this.editText.getText().toString();
                    break;
            }
        }
        request = new UserInfoModifyRequest(name, birth, address==null? KatiEntity.EKatiData.NULL.name(): address);
        KatiRetrofitTool.getAPIWithAuthorizationToken(token).modifyUserInfo(request).enqueue(JSHRetrofitTool.getCallback(new ModifyUserDataCallBack(this)));
    }


}