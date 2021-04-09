package com.plim.kati_app.domain.model;

import androidx.lifecycle.ViewModel;


public class KatiViewModel extends ViewModel {

    // Attribute
    private String header = "Not Initialized";

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }
}

