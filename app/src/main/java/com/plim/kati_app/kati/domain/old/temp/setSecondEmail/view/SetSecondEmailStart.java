package com.plim.kati_app.kati.domain.old.temp.setSecondEmail.view;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.plim.kati_app.R;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitCallback;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.KatiDialog;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.AbstractFragment_1EditText;
import com.plim.kati_app.kati.domain.old.TempMainActivity;
import com.plim.kati_app.kati.domain.old.temp.setSecondEmail.model.SetSecondEmailRequest;
import com.plim.kati_app.kati.domain.old.temp.setSecondEmail.model.SetSecondEmailResponse;

import org.json.JSONObject;

import retrofit2.Response;

import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.LOG_OUT_ACTIVITY_FAILURE_DIALOG_TITLE;

public class SetSecondEmailStart extends AbstractFragment_1EditText {

    @Override
    protected void initializeView() {
        super.initializeView();

        this.mainTextView.setText(getString(R.string.register_second_email_maintext));
        this.subTextView.setVisibility(View.INVISIBLE);
        this.editText.setHint(getString(R.string.register_email_hint));
        this.button.setText(getString(R.string.button_ok));
    }

    @Override
    protected void katiEntityUpdated() {
        if(!this.dataset.containsKey(KatiEntity.EKatiData.AUTHORIZATION)){ this.showNotLoginedDialog(); }
    }

    @Override
    protected void buttonClicked() {
        this.setSecondEmail(); }

    private class SetSecondEmailRequestCallback implements JSHRetrofitCallback<SetSecondEmailResponse>{
        @Override
        public void onSuccessResponse(Response<SetSecondEmailResponse> response) {
            navigateTo(R.id.action_setSecondEmailStart_to_setSecondEmailResult);
        }
        @Override
        public void onFailResponse(Response<SetSecondEmailResponse> response) {
            try {
                JSONObject jObjError = new JSONObject(response.errorBody().string());
                Toast.makeText(getContext(), jObjError.getString("error-message"), Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        @Override
        public void onConnectionFail(Throwable t) {
            Log.d("Test", t.getMessage());
        }
    }

    private void setSecondEmail(){
        SetSecondEmailRequest request = new SetSecondEmailRequest();
        request.setSecondEmail(this.editText.getText().toString());
        String token = this.dataset.get(KatiEntity.EKatiData.AUTHORIZATION);
//        KatiRetrofitTool.getAPIWithAuthorizationToken(token).setSecondEmail(request).enqueue(JSHRetrofitTool.getCallback(new SetSecondEmailRequestCallback()));
    }

    private void showNotLoginedDialog() {
        KatiDialog.simplerAlertDialog(this.getActivity(),
                LOG_OUT_ACTIVITY_FAILURE_DIALOG_TITLE, LOG_OUT_ACTIVITY_FAILURE_DIALOG_TITLE,
                (dialog, which) -> this.startActivity(TempMainActivity.class)
        );
    }
}
