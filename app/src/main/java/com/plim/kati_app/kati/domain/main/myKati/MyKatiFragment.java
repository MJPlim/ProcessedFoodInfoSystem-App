package com.plim.kati_app.kati.domain.main.myKati;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.plim.kati_app.R;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitCallback;
import com.plim.kati_app.jshCrossDomain.tech.retrofit.JSHRetrofitTool;
import com.plim.kati_app.kati.crossDomain.domain.model.KatiEntity;
import com.plim.kati_app.kati.crossDomain.domain.view.dialog.KatiDialog;
import com.plim.kati_app.kati.crossDomain.domain.view.etc.JSHSelectItem;
import com.plim.kati_app.kati.crossDomain.domain.view.fragment.KatiHasTitleFragment;
import com.plim.kati_app.kati.crossDomain.tech.retrofit.KatiRetrofitTool;
import com.plim.kati_app.kati.domain.login.LoginActivity;
import com.plim.kati_app.kati.domain.main.myKati.model.UserSummaryResponse;
import com.plim.kati_app.kati.domain.signUp.SignUpActivity;

import org.json.JSONObject;

import retrofit2.Response;

import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.FOOD_SEARCH_RESULT_LIST_FRAGMENT_FAILURE_DIALOG_TITLE;
import static com.plim.kati_app.kati.crossDomain.domain.model.Constant.JSONOBJECT_ERROR_MESSAGE;

public class MyKatiFragment extends KatiHasTitleFragment {

    //associate
    //view
    private ConstraintLayout myInfoEditSelect, loginLayout;
    private JSHSelectItem reviewSelect;
    private JSHSelectItem allergySelect;
    private Button signUp, login;
    private TextView name;
    private ImageView myImage;

    //working variable
    private int numOfMyReview;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mykati;
    }

    @Override
    protected void associateView(View view) {
        this.loginLayout = view.findViewById(R.id.myKatiFragment_constraintLayout);
        this.name = view.findViewById(R.id.myKatiFragment_userNameTextView);
        this.myInfoEditSelect = view.findViewById(R.id.myKatiFragment_myInfoLayout);
        this.reviewSelect = view.findViewById(R.id.myKatiFragment_reviewItem);
        this.allergySelect = view.findViewById(R.id.myKatiFragment_allergyItem);
        this.signUp = view.findViewById(R.id.myKatiFragment_signUpButton);
        this.login = view.findViewById(R.id.myKatiFragment_loginButton);
        this.myImage = view.findViewById(R.id.myKatiFragment_profileImageView);

        BottomNavigationView bottomNavigationView = this.getActivity().findViewById(R.id.mainFragment_bottomNavigation);
        if (bottomNavigationView.getSelectedItemId() != R.id.action_mykati)
            bottomNavigationView.findViewById(R.id.action_mykati).performClick();
    }

    @Override
    protected void initializeView() {
        this.myInfoEditSelect.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_myKatiFragment_to_myInfoEditFragment));
        this.reviewSelect.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_myKatiFragment_to_reviewFlagment));
        this.allergySelect.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_myKatiFragment_to_allergyFragment));
        this.signUp.setOnClickListener(v -> this.getActivity().startActivity(new Intent(this.getContext(), SignUpActivity.class)));
        this.login.setOnClickListener(v -> this.getActivity().startActivity(new Intent(this.getContext(), LoginActivity.class)));
    }

    @Override
    protected boolean isLoginNeeded() {
        return false;
    }

    @Override
    protected void katiEntityUpdatedAndLogin() {
        this.loginLayout.setVisibility(View.GONE);
        this.myInfoEditSelect.setVisibility(View.VISIBLE);
        this.allergySelect.setVisibility(View.VISIBLE);
        this.reviewSelect.setVisibility(View.VISIBLE);
        this.getUserInfo();
    }

    @Override
    protected void katiEntityUpdatedAndNoLogin() {
        this.loginLayout.setVisibility(View.VISIBLE);
        this.myInfoEditSelect.setVisibility(View.GONE);
        this.allergySelect.setVisibility(View.GONE);
        this.reviewSelect.setVisibility(View.GONE);
    }

    /**
     * callback
     */
    private class UserSummaryResponseCallback implements JSHRetrofitCallback<UserSummaryResponse> {
        @Override
        public void onSuccessResponse(Response<UserSummaryResponse> response) {
            name.setText(response.body().getUser_name());
            dataset.put(KatiEntity.EKatiData.NAME, response.body().getUser_name());
            numOfMyReview = Integer.parseInt(response.body().getReview_count());
            if (numOfMyReview > 100) {
                myImage.setImageResource(R.drawable.gold_icon);
            } else if (numOfMyReview > 50) {
                myImage.setImageResource(R.drawable.silver_icon);
            } else {
                myImage.setImageResource(R.drawable.bronze_icon);
            }
        }

        @Override
        public void onFailResponse(Response<UserSummaryResponse> response) {
            try {
                JSONObject jObjError = new JSONObject(response.errorBody().string());
                Toast.makeText(getContext(), jObjError.getString(JSONOBJECT_ERROR_MESSAGE), Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onConnectionFail(Throwable t) {
            KatiDialog.simplerAlertDialog(getActivity(), FOOD_SEARCH_RESULT_LIST_FRAGMENT_FAILURE_DIALOG_TITLE, t.getMessage(), null);
        }
    }


    /**
     * method
     */
    private void getUserInfo() {
        KatiRetrofitTool.getAPIWithAuthorizationToken(dataset.get(KatiEntity.EKatiData.AUTHORIZATION)).getUserSummary().enqueue(JSHRetrofitTool.getCallback(new UserSummaryResponseCallback()));
    }
}