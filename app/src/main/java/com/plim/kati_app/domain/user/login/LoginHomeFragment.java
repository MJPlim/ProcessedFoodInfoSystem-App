package com.plim.kati_app.domain.user.login;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.plim.kati_app.R;
import com.plim.kati_app.domain.MainActivity;
import com.plim.kati_app.domain.user.register.RegisterActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class LoginHomeFragment extends Fragment {

    // Associate
    // View
    private EditText idText, pwText;
    private Button loginButton, idFindButton, pwFindButton, accountCreateButton;

    /**
     * System Callback
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_login_home_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.idText = view.findViewById(R.id.loginActivity_idText);
        this.pwText = view.findViewById(R.id.loginActivity_pwText);
        this.loginButton = view.findViewById(R.id.loginActivity_loginButton);
        this.idFindButton = view.findViewById(R.id.loginActivity_idFindButton);
        this.pwFindButton = view.findViewById(R.id.loginActivity_pwFindButton);
        this.accountCreateButton = view.findViewById(R.id.loginActivity_accountCreateButton);


        /*
         * 회원가입 버튼 클릭
         */
        this.accountCreateButton.setOnClickListener(v -> {
            this.startActivity(new Intent(getContext(), RegisterActivity.class));
        });

        /*
         * 로그인 버튼 클릭
         * */
        this.loginButton.setOnClickListener(v -> {
                String ID = idText.getText().toString();
                String PW = pwText.getText().toString();

                AlertDialog.Builder dlg = new AlertDialog.Builder(getContext());
                dlg.setTitle("성공적으로 로그인하였습니다.");
                dlg.setMessage("성공적으로 로그인되었습니다.");

                /*
                * 서버 URL 오면 아래거 쓸 것..Maybe?
                * */
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    startActivity(intent);
                }
            });
               dlg.show();


              /*  Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if (success) {//로그인 성공
                                String ID = jsonObject.getString("ID");
                                String PW = jsonObject.getString("Password");
                                String UserName = jsonObject.getString("UserName");

                                loginTrueDialog = View.inflate(getContext(), R.layout.dialog_login_true, null);
                                AlertDialog.Builder dlg = new AlertDialog.Builder(getContext());
                                dlg.setTitle("성공적으로 로그인하였습니다.");
                                dlg.setView(loginTrueDialog);
                                dlg.show();

                                Intent intent = new Intent( getContext(), MainActivity.class );

                                intent.putExtra("UserEmail", ID);
                                intent.putExtra("UserPwd", PW);
                                intent.putExtra("UserName", UserName);

                                startActivity(intent);

                            } else {//로그인 실패
                                loginFalseDialog = View.inflate(getContext(), R.layout.dialog_login_false, null);
                                AlertDialog.Builder dlg = new AlertDialog.Builder(getContext());
                                dlg.setTitle("해당하는 유저가 없습니다.");
                                dlg.setView(loginFalseDialog);
                                dlg.show();

                                idText.setText("");
                                pwText.setText("");
                                return;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };*/
               /* LoginRequest loginRequest = new LoginRequest(ID, PW, responseListener);
                RequestQueue queue = Volley.newRequestQueue(getContext());
                queue.add(loginRequest);*/
        });
    }
}