package com.plim.kati_app.kati.domain.old.temp.editData.userData.view;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.plim.kati_app.R;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitTool;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.KatiLoginCheckViewModelFragment;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.KatiRetrofitTool;
import com.plim.kati_app.kati.domain.old.temp.TempActivity;
import com.plim.kati_app.kati.domain.old.dataChange.model.UserInfoModifyRequest;
import com.plim.kati_app.kati.domain.old.dataChange.model.UserInfoResponse;
import com.plim.kati_app.kati.domain.old.temp.signOut.view.SignOutActivity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import retrofit2.Response;

import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.BASIC_DATE_FORMAT;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.USER_MODIFY_SUCCESS_DIALOG_MESSAGE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.USER_MODIFY_SUCCESS_DIALOG_TITLE;

public class EditDataFragment extends KatiLoginCheckViewModelFragment {

    //associate
    //view
    private ImageView addImageButton;
    private Button finalEditButton;

    private EditText nameEditText, birthEditText, addressEditText;
    private TextView withdrawalTextView;

    //working variable
    private boolean successful = false;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_edit_data;
    }

    @Override
    protected void associateView(View view) {
        this.addImageButton = view.findViewById(R.id.editDataFragment_addImageButton);
        this.finalEditButton = view.findViewById(R.id.editDataFragment_finalEditButton);

        this.nameEditText = view.findViewById(R.id.editDataFragment_nameEditText);
        this.birthEditText = view.findViewById(R.id.editDataFragment_birthEditText);
        this.addressEditText = view.findViewById(R.id.editDataFragment_addressEditText);
        this.withdrawalTextView = view.findViewById(R.id.editDataFragment_withdrawalTextView);
    }

    @Override
    protected void initializeView() {
        this.withdrawalTextView.setOnClickListener(v -> this.startActivity(SignOutActivity.class));
        this.finalEditButton.setOnClickListener(v -> this.modifyUserData(this.dataset.get(KatiEntity.EKatiData.AUTHORIZATION)));

    }

    @Override
    protected boolean isLoginNeeded() {
        return true;
    }

    @Override
    protected void katiEntityUpdatedAndLogin() {
        this.getUserData(this.dataset.get(KatiEntity.EKatiData.AUTHORIZATION));
    }

    @Override
    protected void katiEntityUpdatedAndNoLogin() {

    }




    private class ReadUserDataCallBack extends SimpleLoginRetrofitCallBack<UserInfoResponse> {
        public ReadUserDataCallBack(Activity activity) {
            super(activity);
        }

        @Override
        public void onSuccessResponse(Response<UserInfoResponse> response) {
            UserInfoResponse userInfo = response.body();
            nameEditText.setHint(userInfo.getName());
            if (userInfo.getBirth() == null)
                birthEditText.setHint(BASIC_DATE_FORMAT);
            else
                birthEditText.setHint(userInfo.getBirth());
            addressEditText.setHint(userInfo.getAddress() == null ? "" : userInfo.getAddress());
        }
    }

    private class ModifyUserDataCallBack extends SimpleLoginRetrofitCallBack<UserInfoResponse> {
        public ModifyUserDataCallBack(Activity activity) {
            super(activity);
        }

        @Override
        public void onSuccessResponse(Response<UserInfoResponse> response) {
            successful = true;
            showDialog(USER_MODIFY_SUCCESS_DIALOG_TITLE,USER_MODIFY_SUCCESS_DIALOG_MESSAGE,(dialog, which) -> startActivity(TempActivity.class));
        }
    }

    private void getUserData(String header) {
        KatiRetrofitTool.getAPIWithAuthorizationToken(header).getUserInfo().enqueue(JSHRetrofitTool.getCallback(new ReadUserDataCallBack(getActivity())));
    }

    private void modifyUserData(String header) {
        String name = !nameEditText.getText().toString().equals("") ? nameEditText.getText().toString() : nameEditText.getHint().toString();
        String birth = !birthEditText.getText().toString().equals("") ? LocalDate.parse(birthEditText.getText().toString()).format(DateTimeFormatter.ISO_DATE) :
                !birthEditText.getHint().toString().equals(BASIC_DATE_FORMAT) ? LocalDate.parse(birthEditText.getHint().toString()).format(DateTimeFormatter.ISO_DATE) : null;
        String address = !addressEditText.getText().toString().equals("") ? addressEditText.getText().toString() : addressEditText.getHint().toString();
        UserInfoModifyRequest request = new UserInfoModifyRequest(name, birth, address);

        KatiRetrofitTool.getAPIWithAuthorizationToken(header).modifyUserInfo(request).enqueue(JSHRetrofitTool.getCallback(new ModifyUserDataCallBack(getActivity())));
    }
}

