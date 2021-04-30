package com.plim.kati_app.domain.asset;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import java.util.HashMap;
import java.util.Objects;

/**
 * 어떤 Fragment의 일이 끝난 것을 listener을 통해 알고, Bundle을 통해 값을 받아오기 위한 abstract fragment 클래스.
 */
public abstract class GetResultFragment extends Fragment {
    protected String fragmentRequestKey;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.setFragmentRequestKey();
        this.requireActivity().getSupportFragmentManager().setFragmentResultListener(this.fragmentRequestKey,this.requireActivity(),(this::ResultParse));
    }

    /**
     * fragment가 값을 받아오기 위해 알아야 하는 request key를 여기서 설정해야 한다 (this.fragmentRequestKey="").
     */
    public abstract void setFragmentRequestKey();

    /**
     * bundle을 받아와서 어떤 일을 할 할 지를 여기에 구현해야 한다.
     * @param requestKey 리퀘스트 키.
     * @param result 결과로 받아온 번들.
     */
    public abstract void ResultParse(String requestKey, Bundle result);






}
