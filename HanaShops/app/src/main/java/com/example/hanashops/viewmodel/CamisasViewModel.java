package com.example.hanashops.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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
        // Llamada al método del repository, pasando "5" como categoría fija
        repository.getProductTypes("5", productMap -> {
            if (productMap != null && !productMap.isEmpty()) {
                // Convertimos los valores del Map a una lista de String
                List<String> tipoCamisaNames = new ArrayList<>(productMap.values());

                // Verificamos si la lista tiene algún elemento antes de asignar al LiveData
                if (!tipoCamisaNames.isEmpty()) {
                    tipoCamisaList.setValue(tipoCamisaNames);
                } else {
                    // Si no hay productos, podrías asignar un valor predeterminado o mostrar un mensaje
                    tipoCamisaList.setValue(new ArrayList<>());  // Lista vacía como fallback
                }
            } else {
                // Si el Map está vacío o es nulo, puedes asignar un valor vacío o manejarlo como desees
                tipoCamisaList.setValue(new ArrayList<>());  // Lista vacía como fallback
            }
        });
    }
}
