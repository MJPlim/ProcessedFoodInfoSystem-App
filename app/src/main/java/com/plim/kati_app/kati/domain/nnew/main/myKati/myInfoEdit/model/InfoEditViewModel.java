package com.plim.kati_app.kati.domain.nnew.main.myKati.myInfoEdit.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.plim.kati_app.kati.domain.old.dataChange.model.UserInfoResponse;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InfoEditViewModel extends ViewModel {

    MutableLiveData<UserInfoResponse> userInfoResponse;

    public InfoEditViewModel(){
        this.userInfoResponse= new MutableLiveData<>();
        this.userInfoResponse.setValue(new UserInfoResponse());
    }


}