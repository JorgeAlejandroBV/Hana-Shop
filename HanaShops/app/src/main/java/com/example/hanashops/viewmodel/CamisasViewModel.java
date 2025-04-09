package com.example.hanashops.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.gson.Gson;

import com.example.hanashops.repository.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CamisasViewModel extends ViewModel {
    private final MutableLiveData<List<String>> tipoCamisaList = new MutableLiveData<>();
    private final Repository repository;

    public CamisasViewModel() {
        repository = new Repository();
    }

    public LiveData<List<String>> getTipoCamisaList() {
        return tipoCamisaList;
    }
    public void loadTipoCamisa() {


        // Enviando "5" como categoría al repositorio
        repository.getProductTypes("5", productMap -> {
            if (productMap != null && !productMap.isEmpty()) {
                // Convertimos los valores del Map a una lista de String
                List<String> tipoCamisaNames = new ArrayList<>(productMap.values());
                Log.d("CamisasViewModel", "Productos recibidos: " + tipoCamisaNames.toString());

                tipoCamisaList.setValue(tipoCamisaNames);
            } else {
                tipoCamisaList.setValue(new ArrayList<>());
                Log.d("CamisasViewModel", "No se encontraron productos.");
            }
        });
    }

}
