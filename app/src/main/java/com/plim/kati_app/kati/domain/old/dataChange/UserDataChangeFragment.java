package com.plim.kati_app.kati.domain.old.dataChange;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.plim.kati_app.R;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitTool;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.KatiLoginCheckViewModelFragment;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.KatiRetrofitTool;
//import com.plim.kati_app.kati.domain.old.changePW.view.ChangePasswordActivity;
import com.plim.kati_app.kati.domain.old.temp.TempActivity;
import com.plim.kati_app.kati.domain.old.dataChange.model.UserInfoModifyRequest;
import com.plim.kati_app.kati.domain.old.dataChange.model.UserInfoResponse;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import retrofit2.Response;

import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.BASIC_DATE_FORMAT;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.USER_MODIFY_SUCCESS_DIALOG_MESSAGE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.USER_MODIFY_SUCCESS_DIALOG_TITLE;


public class UserDataChangeFragment extends KatiLoginCheckViewModelFragment {

    private ImageView userProfileImageView;
    private TextView userProfileTextView, userEmailDataTextView,
    secondEmailEditButton, allergyEditButton, passwordEditButton,
    editCompleteButton,logoutButton,signOutButton;

    private EditText userNicknameEditText, userBirthEditText, userAddressEditText;



    public UserDataChangeFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_data_change;
    }

    @Override
    protected void associateView(View view) {
        this.userProfileImageView=view.findViewById(R.id.userDataChangeFragment_userProfileImageView);

        this.userProfileTextView=view.findViewById(R.id.userDataChangeFragment_userProfileTextView);
        this.userEmailDataTextView=view.findViewById(R.id.userDataChangeFragment_emailDataTextView);

        this.secondEmailEditButton=view.findViewById(R.id.userDataChangeFragment_secondEmailEditButton);
        this.allergyEditButton=view.findViewById(R.id.userDataChangeFragment_allergyEditButton);
        this.passwordEditButton=view.findViewById(R.id.userDataChangeFragment_passwordEditButton);

        this.editCompleteButton=view.findViewById(R.id.userDataChangeFragment_editCompleteButton);

        this.userNicknameEditText=view.findViewById(R.id.userDataChangeFragment_userNicknameEditTextInput);
        this.userBirthEditText=view.findViewById(R.id.userDataChangeFragment_userBirthEditText);
        this.userAddressEditText=view.findViewById(R.id.userDataChangeFragment_userAddressEditText);


        this.logoutButton=view.findViewById(R.id.userDataChangeFragment_logoutButton);
        this.signOutButton=view.findViewById(R.id.userDataChangeFragment_signoutButton);

    }

    @Override
    protected void initializeView() {
        View.OnClickListener listener= v-> Toast.makeText(getActivity(), "프로필 이미지 미구현", Toast.LENGTH_SHORT).show();
        this.userProfileImageView.setOnClickListener(listener);
        this.userProfileTextView.setOnClickListener(listener);

//        this.logoutButton.setOnClickListener(v -> this.startActivity(LogOutActivity.class));



      //  this.secondEmailEditButton.setOnClickListener(v -> this.startActivity(SetSecondEmailActivity.class));
//        this.allergyEditButton.setOnClickListener(v -> this.startActivity(Allerg.class));
//        this.passwordEditButton.setOnClickListener(v -> this.startActivity(ChangePasswordActivity.class));

        this.editCompleteButton.setOnClickListener(v -> this.modifyUserData(this.dataset.get(KatiEntity.EKatiData.AUTHORIZATION)));
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

    private class ReadUserDataCallBack extends KatiLoginCheckViewModelFragment.SimpleLoginRetrofitCallBack<UserInfoResponse> {
        public ReadUserDataCallBack(Activity activity) {
            super(activity);
        }

        @Override
        public void onSuccessResponse(Response<UserInfoResponse> response) {
            userEmailDataTextView.setText(dataset.get(KatiEntity.EKatiData.EMAIL));
            UserInfoResponse userInfo = response.body();
            userNicknameEditText.setHint(userInfo.getName());
            if (userInfo.getBirth() == null)
                userBirthEditText.setHint(BASIC_DATE_FORMAT);
            else
                userBirthEditText.setHint(userInfo.getBirth());
            userAddressEditText.setHint(userInfo.getAddress() == null ? "" : userInfo.getAddress());
        }
    }

    private class ModifyUserDataCallBack extends KatiLoginCheckViewModelFragment.SimpleLoginRetrofitCallBack<UserInfoResponse> {
        public ModifyUserDataCallBack(Activity activity) {
            super(activity);
        }

        @Override
        public void onSuccessResponse(Response<UserInfoResponse> response) {
            showDialog(USER_MODIFY_SUCCESS_DIALOG_TITLE,USER_MODIFY_SUCCESS_DIALOG_MESSAGE,(dialog, which) -> startActivity(TempActivity.class));
        }
    }


    private void getUserData(String header) {
        KatiRetrofitTool.getAPIWithAuthorizationToken(header).getUserInfo().enqueue(JSHRetrofitTool.getCallback(new ReadUserDataCallBack(getActivity())));
    }

    private void modifyUserData(String header) {
        String name = !userNicknameEditText.getText().toString().equals("") ? userNicknameEditText.getText().toString() : userNicknameEditText.getHint().toString();
        String birth = !userBirthEditText.getText().toString().equals("") ? LocalDate.parse(userBirthEditText.getText().toString()).format(DateTimeFormatter.ISO_DATE) :
                !userBirthEditText.getHint().toString().equals(BASIC_DATE_FORMAT) ? LocalDate.parse(userBirthEditText.getHint().toString()).format(DateTimeFormatter.ISO_DATE) : null;
        String address = !userAddressEditText.getText().toString().equals("") ? userAddressEditText.getText().toString() : userAddressEditText.getHint().toString();
        UserInfoModifyRequest request = new UserInfoModifyRequest(name, birth, address);

        KatiRetrofitTool.getAPIWithAuthorizationToken(header).modifyUserInfo(request).enqueue(JSHRetrofitTool.getCallback(new ModifyUserDataCallBack(getActivity())));
    }


}