package com.plim.kati_app.domain.view.user.dataChange;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.plim.kati_app.R;
import com.plim.kati_app.domain.asset.KatiDialog;
import com.plim.kati_app.domain.model.dto.UserInfoModifyRequest;
import com.plim.kati_app.domain.model.dto.UserInfoResponse;
import com.plim.kati_app.domain.model.room.KatiData;
import com.plim.kati_app.domain.model.room.KatiDatabase;
import com.plim.kati_app.domain.view.MainActivity;
import com.plim.kati_app.domain.view.user.login.LoginActivity;
import com.plim.kati_app.tech.RestAPIClient;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import lombok.SneakyThrows;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.plim.kati_app.constants.Constant_yun.BASIC_DATE_FORMAT;
import static com.plim.kati_app.constants.Constant_yun.USER_DATA_CHANGE_SUCCESSFUL_DIALOG_MESSAGE;
import static com.plim.kati_app.constants.Constant_yun.USER_DATA_CHANGE_SUCCESSFUL_DIALOG_TITLE;


public class UserDataChangeFragment extends Fragment {

    private ImageView addImageButton;
    private Button finalEditButton;

    private EditText nameEditText, birthEditText, addressEditText;


    public UserDataChangeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_data_change, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.addImageButton = view.findViewById(R.id.userDataChangeFragment_addImageButton);
        this.finalEditButton = view.findViewById(R.id.userDataChangeFragment_finalEditButton);

        this.nameEditText = view.findViewById(R.id.userDataChangeFragment_nameEditText);
        this.birthEditText = view.findViewById(R.id.userDataChangeFragment_birthEditText);
        this.addressEditText = view.findViewById(R.id.userDataChangeFragment_addressEditText);

        KatiDatabase database = KatiDatabase.getAppDatabase(this.getActivity());
        new Thread(() ->
                this.getUserData(database.katiDataDao().getValue(KatiDatabase.AUTHORIZATION))
        ).start();
        this.finalEditButton.setOnClickListener(v -> this.modifyUserData());
    }

    @Override
    public void onResume() {
        super.onResume();
        new Thread(() -> {
            KatiDatabase database = KatiDatabase.getAppDatabase(this.getActivity());
            String token = database.katiDataDao().getValue(KatiDatabase.AUTHORIZATION);
            if (token != null)
                this.getUserData(token);
            else
                KatiDialog.notLoginAlertDialog(
                        this.getContext(),
                        (dialog, which) -> startActivity(new Intent(this.getActivity(), LoginActivity.class))
                ).showDialog();
        }).start();
    }


    private void getUserData(String header) {
        Call<UserInfoResponse> listCall = RestAPIClient.getApiService2(header).getUserInfo();
        listCall.enqueue(new Callback<UserInfoResponse>() {
            @SneakyThrows
            @Override
            public void onResponse(Call<UserInfoResponse> call, Response<UserInfoResponse> response) {
                if (!response.isSuccessful()) {
                    KatiDialog.showRetrofitNotSuccessDialog(
                            getContext(),
                            Integer.toString(response.code()),
                           null
                    ).showDialog();
                } else {
                    UserInfoResponse userInfo = response.body();
                    nameEditText.setHint(userInfo.getName());
                    if (userInfo.getBirth() == null)
                        birthEditText.setHint(BASIC_DATE_FORMAT);
                    else
                        birthEditText.setHint(userInfo.getBirth());
                    addressEditText.setHint(userInfo.getAddress() == null ? "" : userInfo.getAddress());
                }

                String token = response.headers().get(KatiDatabase.AUTHORIZATION);
                KatiDatabase database = KatiDatabase.getAppDatabase(getContext());
                new Thread(() ->
                        database.katiDataDao().insert(new KatiData(KatiDatabase.AUTHORIZATION, token))
                ).start();

            }

            @Override
            public void onFailure(Call<UserInfoResponse> call, Throwable t) {
                KatiDialog.showRetrofitFailDialog(
                        getContext(),
                        t.getMessage(),
                        null
                ).showDialog();
            }
        });


    }

    private void modifyUserData() {
        String name = !nameEditText.getText().toString().equals("") ? nameEditText.getText().toString() : nameEditText.getHint().toString();
        String birth = !birthEditText.getText().toString().equals("") ? LocalDate.parse(birthEditText.getText().toString()).format(DateTimeFormatter.ISO_DATE) :
                !birthEditText.getHint().toString().equals(BASIC_DATE_FORMAT) ? LocalDate.parse(birthEditText.getHint().toString()).format(DateTimeFormatter.ISO_DATE) : null;
        String address = !addressEditText.getText().toString().equals("") ? addressEditText.getText().toString() : addressEditText.getHint().toString();

        UserInfoModifyRequest request = new UserInfoModifyRequest(name, birth, address);

        new Thread(() -> {
            KatiDatabase database = KatiDatabase.getAppDatabase(this.getActivity());
            String token = database.katiDataDao().getValue(KatiDatabase.AUTHORIZATION);
            if (token != null) {
                Call<UserInfoResponse> listCall = RestAPIClient.getApiService2(token).modifyUserInfo(request);
                listCall.enqueue(new Callback<UserInfoResponse>() {
                    @SneakyThrows
                    @Override
                    public void onResponse(Call<UserInfoResponse> call, Response<UserInfoResponse> response) {
                        if (!response.isSuccessful()) {
                            KatiDialog.showRetrofitNotSuccessDialog(
                                    getContext(),
                                    Integer.toString(response.code()),
                                    (dialog, which) -> startActivity(new Intent(getActivity(), MainActivity.class))
                            ).showDialog();

                        } else {
                            KatiDialog.simpleAlertDialog(
                                    getContext(),
                                    USER_DATA_CHANGE_SUCCESSFUL_DIALOG_TITLE,
                                    USER_DATA_CHANGE_SUCCESSFUL_DIALOG_MESSAGE,
                                    (dialog, which) -> startActivity(new Intent(getActivity(), MainActivity.class)),
                                    getContext().getResources().getColor(R.color.kati_coral, getContext().getTheme())
                            ).showDialog();
                        }

                        new Thread(() -> {
                            String token = response.headers().get(KatiDatabase.AUTHORIZATION);
                            KatiDatabase database = KatiDatabase.getAppDatabase(getContext());
                            database.katiDataDao().insert(new KatiData(KatiDatabase.AUTHORIZATION, token));
                        }).start();
                    }

                    @Override
                    public void onFailure(Call<UserInfoResponse> call, Throwable t) {
                        KatiDialog.showRetrofitFailDialog(
                                getContext(),
                                t.getMessage(),
                                (dialog, which) -> startActivity(new Intent(getActivity(), MainActivity.class))
                        ).showDialog();
                    }
                });
            } else {
                KatiDialog.notLoginAlertDialog(
                        this.getContext(),
                        (dialog, which) -> startActivity(new Intent(this.getActivity(), LoginActivity.class))
                ).showDialog();
            }

        }).start();
    }

}

