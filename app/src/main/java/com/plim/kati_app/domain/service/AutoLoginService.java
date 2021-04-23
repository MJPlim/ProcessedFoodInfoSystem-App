package com.plim.kati_app.domain.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.plim.kati_app.domain.model.room.KatiData;
import com.plim.kati_app.domain.model.room.KatiDatabase;
import com.plim.kati_app.domain.view.user.login.LoginRequest;
import com.plim.kati_app.domain.view.user.login.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);

        this.autoLogOut();
        this.stopSelf();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        this.autoLogOut();
    }

    private void autoLogOut() {
        new Thread(() -> {
//            Log.d("디버그", "종료 로그아웃");
            KatiDatabase database = KatiDatabase.getAppDatabase(this);
            database.katiDataDao().delete(KatiDatabase.AUTHORIZATION);
        }).start();
    }

    public void autoLogin() {
        new Thread(() -> {
            KatiDatabase database = KatiDatabase.getAppDatabase(this);
            if (database.katiDataDao().getValue(KatiDatabase.AUTO_LOGIN)==null||database.katiDataDao().getValue(KatiDatabase.AUTO_LOGIN).equals("0")) {
                return;
            } else if (database.katiDataDao().getValue(KatiDatabase.AUTO_LOGIN).equals("1")) {
                Log.d("자동로그인", "시작");
                String email = database.katiDataDao().getValue(KatiDatabase.EMAIL);
                String password = database.katiDataDao().getValue(KatiDatabase.PASSWORD);
                Log.d("자동로그인 이메일/비번", email + "/" + password);
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
            @Override
            public void onResponse(Call<LoginRequest> call, Response<LoginRequest> response) {
                if (response.isSuccessful()) {
                    Log.d("연결 성공적 : ", "code : " + response.code());
                    new Thread(()->{

                        KatiDatabase database = KatiDatabase.getAppDatabase(getApplicationContext());
                        database.katiDataDao().insert(new KatiData(KatiDatabase.AUTHORIZATION, response.headers().get(KatiDatabase.AUTHORIZATION)));
                    }).start();
                } else {
                    Log.e("연결 비정상적 : ", "error code : " + response.code());
                    return;
                }
            }

            @Override
            public void onFailure(Call<LoginRequest> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });
    }
}
