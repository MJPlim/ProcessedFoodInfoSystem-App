package com.plim.kati_app.domain.view.login.pwFind;

import android.view.View;

import com.plim.kati_app.R;
import com.plim.kati_app.domain.katiCrossDomain.domain.view.AbstractFragment1;
import com.plim.kati_app.domain.katiCrossDomain.domain.view.KatiDialog;
import com.plim.kati_app.domain.katiCrossDomain.domain.view.LoadingDialog;
import com.plim.kati_app.domain.katiCrossDomain.domain.model.forBackend.userAccount.FindPasswordRequest;
import com.plim.kati_app.domain.katiCrossDomain.domain.model.forBackend.userAccount.FindPasswordResponse;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitCallback;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitTool;
import com.plim.kati_app.domain.katiCrossDomain.tech.retrofit.KatiRetrofitTool;
import com.plim.kati_app.domain.katiCrossDomain.domain.model.forFrontend.KatiEntity;
import com.plim.kati_app.domain.view.TempMainActivity;

import retrofit2.Response;

import static com.plim.kati_app.domain.katiCrossDomain.domain.Constant.DIALOG_CONFIRM;
import static com.plim.kati_app.domain.katiCrossDomain.domain.Constant.EMAIL_INPUT_HINT;
import static com.plim.kati_app.domain.katiCrossDomain.domain.Constant.EMAIL_INPUT_MESSAGE;
import static com.plim.kati_app.domain.katiCrossDomain.domain.Constant.LOGINED_DIALOG_TITLE;
import static com.plim.kati_app.domain.katiCrossDomain.domain.Constant.NO_USER_DIALOG_MESSAGE;
import static com.plim.kati_app.domain.katiCrossDomain.domain.Constant.NO_USER_DIALOG_TITLE;

public class FindPasswordEmailInputFragment extends AbstractFragment1 {

    // Component
        // View
        private LoadingDialog loadingDialog;

    /**
     * System Life Cycle Callback
     */
    @Override
    protected void initializeView() {
        super.initializeView();
        this.mainTextView.setText(EMAIL_INPUT_MESSAGE);
        this.subTextView.setVisibility(View.INVISIBLE);
        this.editText.setHint(EMAIL_INPUT_HINT);
        this.button.setText(DIALOG_CONFIRM);
    }
    @Override
    protected void katiEntityUpdated() {
        if(this.dataset.containsKey(KatiEntity.EKatiData.AUTHORIZATION)){ this.showLoginedDialog(); }
    }

    /**
     * Callback
     */
    @Override
    protected void buttonClicked() {
        this.loadingDialog=new LoadingDialog(this.getContext());
        this.loadingDialog.show();

        FindPasswordRequest request= new FindPasswordRequest();
        request.setEmail(this.editText.getText().toString());
        KatiRetrofitTool.getAPI().findPassword(request).enqueue(JSHRetrofitTool.getCallback(new FindPasswordRequestCallback()));
    }
    private class FindPasswordRequestCallback implements JSHRetrofitCallback<FindPasswordResponse> {
        @Override
        public void onSuccessResponse(Response<FindPasswordResponse> response) {
            showCompletedDialog();
        }
        @Override
        public void onFailResponse(Response<FindPasswordResponse> response) {
            showNoUserDialog();
        }
        @Override
        public void onConnectionFail(Throwable t) {
            loadingDialog.hide();
        }
    }

    /**
     * Method
     */
    private void showLoginedDialog() {
        KatiDialog.simplerAlertDialog(this.getActivity(),
                LOGINED_DIALOG_TITLE, LOGINED_DIALOG_TITLE,
                (dialog, which) -> this.startActivity(TempMainActivity.class)
        );
    }
    private void showNoUserDialog() {
        KatiDialog.simplerAlertDialog(this.getActivity(),
                NO_USER_DIALOG_TITLE, NO_USER_DIALOG_MESSAGE,
                null
        );
    }
    private void showCompletedDialog() {
        this.navigateTo(R.id.action_findPasswordEmailInputFragment_to_findPasswordResultFragment);
    }
}