package com.plim.kati_app.kati.domain.temp.signOut.view;

import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.plim.kati_app.R;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.AbstractFragment_1EditText;
import com.plim.kati_app.kati.domain.temp.signOut.model.WithdrawRequest;
import com.plim.kati_app.kati.domain.temp.signOut.model.WithdrawResponse;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitCallback;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitTool;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.KatiRetrofitTool;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;

import org.json.JSONObject;

import retrofit2.Response;

import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.JSONOBJECT_ERROR_MESSAGE;

public class SignOutPasswordInputFragmentEditText extends AbstractFragment_1EditText {

    /**
     * System Life Cycle Callback
     */
    @Override
    protected void initializeView() {
        super.initializeView();
        this.editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        this.mainTextView.setText(this.getStringOfId(R.string.withdrawalPasswordInputFragment_mainTextView_inputPassword));
        this.subTextView.setVisibility(View.INVISIBLE);
        this.editText.setHint(this.getStringOfId(R.string.withdrawalPasswordInputFragment_editText_password));
        this.button.setText(this.getStringOfId(R.string.common_ok));
    }

    /**
     * Callback
     */
    @Override
    protected void buttonClicked() { this.signOut(); }
    private class SignOutRequestCallback implements JSHRetrofitCallback<WithdrawResponse> {
        @Override
        public void onSuccessResponse(Response<WithdrawResponse> response) {
            dataset.remove(KatiEntity.EKatiData.AUTHORIZATION);
            navigateTo(R.id.action_withdrawalActivity_withdrawalfragment1_to_withdrawalActivity_withdrawalfragment2);
        }
        @Override
        public void onFailResponse(Response<WithdrawResponse> response) {
            Log.d("TEST123", "onFailResponse "
                    +response.message()+"\r\n"+
                    response.toString()+"\r\n"
            );
            try {
                JSONObject jObjError = new JSONObject(response.errorBody().string());
                Toast.makeText(getContext(), jObjError.getString(JSONOBJECT_ERROR_MESSAGE), Toast.LENGTH_LONG).show();
            } catch (Exception e) { Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show(); }
        }
        @Override
        public void onConnectionFail(Throwable t) {
            Log.d(getStringOfId(R.string.withdrawalPasswordInputFragment_log_pleaseCheckInternet), t.getMessage());
        }
    }

    /**
     * Method
     */
    private void signOut() {
        WithdrawRequest withdrawRequest = new WithdrawRequest();
        withdrawRequest.setPassword(this.editText.getText().toString());
        String token = this.dataset.get(KatiEntity.EKatiData.AUTHORIZATION);
        Log.d("TEST123", "PW: "+this.editText.getText().toString());
        Log.d("TEST123", "AUTHORIZATION: "+token);
        KatiRetrofitTool.getAPIWithAuthorizationToken(token).withdraw(withdrawRequest).enqueue(JSHRetrofitTool.getCallback(new SignOutRequestCallback()));
    }
}