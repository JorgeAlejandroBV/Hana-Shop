package com.example.hanashops.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.hanashops.model.Variation;
import com.example.hanashops.repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class GorrasViewModel extends AndroidViewModel {
    private Repository repository;

    private MutableLiveData<List<Variation>> variations = new MutableLiveData<>();

    public GorrasViewModel(Application application) {
        super(application);
        repository = new Repository();
        variations = new MutableLiveData<>();
    }



    public LiveData<List<Variation>> getVariations() {
        return variations;
    }

    public void loadVariations(String productId) {
        repository.getVariations(productId, variationsMap -> {
            variations.setValue(new ArrayList<>(variationsMap)); // Actualiza el LiveData con las variaciones
        });
    }
}
