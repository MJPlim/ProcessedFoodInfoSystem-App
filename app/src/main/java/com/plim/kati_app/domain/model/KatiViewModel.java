package com.plim.kati_app.domain.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KatiViewModel extends AndroidViewModel {

    // Attribute
    private String token = "Not Initialized";

    public KatiViewModel(@NonNull Application application) {
        super(application);
    }
}

