package com.plim.kati_app.domain.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.plim.kati_app.R;
import com.plim.kati_app.constants.Constant_yun;
import com.plim.kati_app.domain.asset.KatiDialog;
import com.plim.kati_app.domain.model.room.KatiData;
import com.plim.kati_app.domain.model.room.KatiDatabase;
import com.plim.kati_app.domain.view.user.login.LoginRequest;
import com.plim.kati_app.domain.view.user.login.RetrofitClient;
import com.plim.kati_app.exception.LoginDataWrongException;
import com.plim.kati_app.exception.RequestFailureException;
import com.plim.kati_app.exception.RequestNotSuccessfulException;
import com.plim.kati_app.tech.RestAPIClient;

import lombok.SneakyThrows;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 접속 시 자동로그인, 나갈 때 자동 로그아웃 해주는 서비스.
 */
public class AutoLoginService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.autoLogin();
    }

    /**
     * 정상적인 종료가 아닌, 강제종료 당했을 때.
     * @param rootIntent
     */
    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        this.autoLogOut();
        this.stopSelf();
    }

    /**
     * 정상적으로 종료 될 때.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        this.autoLogOut();
    }

    /**
     * 자동 로그아웃 하는 메소드.
     */
    private void autoLogOut() {
        new Thread(() -> {
            KatiDatabase database = KatiDatabase.getAppDatabase(this);
            database.katiDataDao().delete(KatiDatabase.AUTHORIZATION);
        }).start();
    }

    /**
     * 자동 로그인 하는 메소드.
     */
    public void autoLogin() {
        new Thread(() -> {
            KatiDatabase database = KatiDatabase.getAppDatabase(this);
            //만약 설정값이 아예 없다면, 설정값을 거짓으로 저장한다.
            if (database.katiDataDao().getValue(KatiDatabase.AUTO_LOGIN)==null){
                database.katiDataDao().insert(new KatiData(KatiDatabase.AUTO_LOGIN,KatiDatabase.FALSE));
            //설정값이 거짓이라면 아무것도 하지 않고 돌아간다.
            }else if (database.katiDataDao().getValue(KatiDatabase.AUTO_LOGIN).equals(KatiDatabase.FALSE)) {
                return;


            //설정값이 참이라면, 자동로그인을 시작한다.
            } else if (database.katiDataDao().getValue(KatiDatabase.AUTO_LOGIN).equals(KatiDatabase.TRUE)) {
                String email = database.katiDataDao().getValue(KatiDatabase.EMAIL);
                String password = database.katiDataDao().getValue(KatiDatabase.PASSWORD);
                this.retrofitLogin(email,password);
            }
        }).start();

    }


    /**
     * 로그인 요청.
     */
    private void retrofitLogin(String email, String password) {

        LoginRequest login = new LoginRequest(email, password);
        RetrofitClient retrofitClient = new RetrofitClient();
        Call<LoginRequest> call = retrofitClient.apiService.postRetrofitData(login);
        call.enqueue(new Callback<LoginRequest>() {

            @SneakyThrows
            @Override
            public void onResponse(Call<LoginRequest> call, Response<LoginRequest> response) {
                if (response.isSuccessful()) {
                    new Thread(()->{
                        KatiDatabase database = KatiDatabase.getAppDatabase(getApplicationContext());
                        database.katiDataDao().insert(new KatiData(KatiDatabase.AUTHORIZATION, response.headers().get(KatiDatabase.AUTHORIZATION)));
                    }).start();
                } else {
                    if(response.code()== Constant_yun.LOGIN_DATA_WRONG_EXCEPTION_CODE){
                        showLoginFailDialog();
                        throw new LoginDataWrongException();
                    }else{
                        throw new RequestNotSuccessfulException(response.code());
                    }
                }
            }

            @SneakyThrows
            @Override
            public void onFailure(Call<LoginRequest> call, Throwable t) {
                throw new RequestFailureException();
            }
        });
    }

    /**
     * 로그인 실패 다이얼로그를 표시한다.
     */
    private void showLoginFailDialog() {
        KatiDialog.simpleAlertDialog(this.getBaseContext(),
                Constant_yun.AUTO_LOGIN_SERVICE_FAIL_DIALOG_TITLE,
                Constant_yun.AUTO_LOGIN_SERVICE_FAIL_DIALOG_MESSAGE,
                null,
                this.getBaseContext().getResources().getColor(R.color.kati_coral,this.getTheme())).showDialog();
    }
}
