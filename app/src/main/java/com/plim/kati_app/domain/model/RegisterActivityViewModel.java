package com.plim.kati_app.domain.model;

import androidx.lifecycle.ViewModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterActivityViewModel extends ViewModel {

    // Attribute
    private User user;

    public RegisterActivityViewModel() {
        this.user = new User();
    }
}

