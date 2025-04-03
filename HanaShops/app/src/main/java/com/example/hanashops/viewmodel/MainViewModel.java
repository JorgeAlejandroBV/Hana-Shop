package com.example.hanashops.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hanashops.model.ProductUI;
import com.example.hanashops.repository.ProductRepository;

import java.util.List;

public class MainViewModel extends ViewModel {
    private MutableLiveData<List<ProductUI>> products;
    private ProductRepository repository;

    public MainViewModel() {
        repository = new ProductRepository();
        products = new MutableLiveData<>();
        loadProducts();
    }

    private void loadProducts() {
        products.setValue(repository.getProducts());
    }

    public LiveData<List<ProductUI>> getProducts() {
        return products;
    }
}
