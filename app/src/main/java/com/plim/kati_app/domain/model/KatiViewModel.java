package com.plim.kati_app.domain.model;

import androidx.lifecycle.ViewModel;

public class KatiViewModel extends ViewModel {

    // Attribute
    private String header = "Not Initialized";

    // Getter & Setter
    public String getHeader(){ return this.header; }
    public void setHeader(String header) { this.header = header; }
}

