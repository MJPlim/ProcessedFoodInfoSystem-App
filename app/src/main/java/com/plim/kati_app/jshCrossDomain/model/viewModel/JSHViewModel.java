package com.plim.kati_app.jshCrossDomain.model.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.plim.kati_app.jshCrossDomain.model.repository.JSHRepository;
import com.plim.kati_app.jshCrossDomain.model.room.entity.JSHEntity;

import java.util.List;

public class JSHViewModel extends AndroidViewModel {

    // Associate
    private LiveData<List<JSHEntity>> dataset;

    // Component
    private JSHRepository mRepository;

    // Constructor
    public JSHViewModel(Application application) {
        super(application);

        // Create Component
        this.mRepository = new JSHRepository(application);

        // Associate Model
        this.dataset = this.mRepository.getDataset();
    }

    // Method
    public void insert(JSHEntity data) { this.mRepository.insert(data); }
    public void update(JSHEntity... dataset) { this.mRepository.update(dataset); }
    public void delete(JSHEntity data) { this.mRepository.delete(data); }

    // Getter & Setter
    public LiveData<List<JSHEntity>> getDataset() { return dataset; }
}
