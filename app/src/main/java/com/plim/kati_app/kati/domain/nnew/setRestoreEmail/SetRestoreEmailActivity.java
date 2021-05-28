package com.plim.kati_app.kati.domain.nnew.setRestoreEmail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.plim.kati_app.R;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitCallback;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitTool;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.kati.crossDomain.domain.view.activity.KatiViewModelActivity;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.KatiDialog;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.KatiRetrofitTool;
import com.plim.kati_app.kati.domain.nnew.main.MainActivity;
import com.plim.kati_app.kati.domain.nnew.setRestoreEmail.model.SetSecondEmailRequest;
import com.plim.kati_app.kati.domain.nnew.setRestoreEmail.model.SetSecondEmailResponse;

import org.json.JSONObject;

import java.util.Map;

import retrofit2.Response;

import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.LOG_OUT_ACTIVITY_FAILURE_DIALOG_TITLE;

public class SetRestoreEmailActivity extends KatiViewModelActivity {

    Button setRestoreEmailAddressButton;
    EditText editTextRestoreEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restore_email);
    }

    @Override
    protected void associateView() {
        this.setRestoreEmailAddressButton = findViewById(R.id.setRestoreEmailAddressButton);
        this.editTextRestoreEmail = findViewById(R.id.editTextRestoreEmail);
    }

    @Override
    protected void initializeView() {
        this.setRestoreEmailAddressButton.setOnClickListener(v->this.setSecondEmail());
    }

    @Override
    public void katiEntityUpdated() {
        if(!this.dataset.containsKey(KatiEntity.EKatiData.AUTHORIZATION)){ this.showNotLoginedDialog(); }
    }

    private void showNotLoginedDialog() {
        KatiDialog.simplerAlertDialog(this,
                LOG_OUT_ACTIVITY_FAILURE_DIALOG_TITLE, LOG_OUT_ACTIVITY_FAILURE_DIALOG_TITLE,
                (dialog, which) -> this.startMainActivity()
        );
    }

    private void setSecondEmail(){
        SetSecondEmailRequest request = new SetSecondEmailRequest();
        request.setSecondEmail(this.editTextRestoreEmail.getText().toString());
        String token = this.dataset.get(KatiEntity.EKatiData.AUTHORIZATION);
        KatiRetrofitTool.getAPIWithAuthorizationToken(token).setSecondEmail(request).enqueue(JSHRetrofitTool.getCallback(new SetSecondEmailRequestCallback()));
    }

    private class SetSecondEmailRequestCallback implements JSHRetrofitCallback<SetSecondEmailResponse> {
        @Override
        public void onSuccessResponse(Response<SetSecondEmailResponse> response) {
            startMainActivity();
        }
        @Override
        public void onFailResponse(Response<SetSecondEmailResponse> response) {
            try {
                JSONObject jObjError = new JSONObject(response.errorBody().string());
                Toast.makeText(SetRestoreEmailActivity.this, jObjError.getString("error-message"), Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(SetRestoreEmailActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        @Override
        public void onConnectionFail(Throwable t) {
            Log.d("Test", t.getMessage());
        }
    }

    public void startMainActivity() {
        this.startActivity(new Intent(this, MainActivity.class));
    }
}