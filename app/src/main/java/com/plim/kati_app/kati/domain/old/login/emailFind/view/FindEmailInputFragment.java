package com.plim.kati_app.kati.domain.old.login.emailFind.view;

import com.plim.kati_app.kati.crossDomain.domain.view.fragment.AbstractFragment_1EditText;

public class FindEmailInputFragment extends AbstractFragment_1EditText {
    @Override
    protected void buttonClicked() {

    }
//
//    @Override
//    protected void initializeView() {
//        super.initializeView();
//        this.mainTextView.setText(getString(R.string.find_id_maintext));
//        this.subTextView.setText(getString(R.string.find_id_subtext));
//        this.editText.setHint(getString(R.string.register_email_hint));
//        this.button.setText(getString(R.string.button_ok));
//    }
//
//    @Override
//    protected void katiEntityUpdated() {
//        if(this.dataset.containsKey(KatiEntity.EKatiData.AUTHORIZATION)){ this.showAlreadyLoginedDialog(); }
//    }
//
//    @Override
//    protected void buttonClicked() {
//
//        this.findEmail(); }
//
//    private class FindEmailRequestCallback implements JSHRetrofitCallback<FindEmailResponse>{
//        @Override
//        public void onSuccessResponse(Response<FindEmailResponse> response) {
//            showCompletedDialog();
//        }
//        @Override
//        public void onFailResponse(Response<FindEmailResponse> response) {
//            showNoUserDialog();
//            Log.d("Test", response.message());
//            System.out.println("으아악!" + response.message() + response.code());
//        }
//        @Override
//        public void onConnectionFail(Throwable t) {
//            Log.d("연결 실패", t.getMessage());
//        }
//    }
//
//    private void findEmail(){
//        FindEmailRequest request= new FindEmailRequest();
//        request.setSecondEmail(this.editText.getText().toString());
//        KatiRetrofitTool.getAPI().findEmail(request).enqueue(JSHRetrofitTool.getCallback(new FindEmailRequestCallback()));
//    }
//
//    private void showAlreadyLoginedDialog() {
//        KatiDialog.simplerAlertDialog(this.getActivity(),
//                getString(R.string.login_already_signed_dialog),
//                getString(R.string.login_already_signed_content_dialog),
//                (dialog, which) -> this.startActivity(TempMainActivity.class)
//                );
//    }
//
//    private void showNoUserDialog() {
//        KatiDialog.simplerAlertDialog(this.getActivity(),
//                Constant.NO_USER_DIALOG_TITLE,
//                Constant.NO_USER_DIALOG_MESSAGE,
//                (dialog, which) -> this.editText.setText("")
//        );
//    }
//
//    private void showCompletedDialog() {
//        KatiDialog.simplerAlertDialog(this.getActivity(),
//                getString(R.string.find_user_email_dialog_title), getString(R.string.find_user_email_dialog_message),
//                (dialog, which) -> navigateTo(R.id.action_findEmailInputFragment_to_findEmailResultFragment)
//        );
//    }
}