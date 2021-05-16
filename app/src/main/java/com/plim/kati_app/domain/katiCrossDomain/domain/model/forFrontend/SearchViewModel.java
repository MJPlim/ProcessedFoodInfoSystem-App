package com.plim.kati_app.domain.katiCrossDomain.domain.model.forFrontend;

import androidx.lifecycle.ViewModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchViewModel extends ViewModel {
    String searchPageNum, searchMode, searchText;
}
