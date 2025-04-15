package com.example.hanashops.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.hanashops.model.Product;
import com.example.hanashops.model.ProductResponse;
import com.example.hanashops.network.ApiService;
import com.example.hanashops.network.RetrofitClient;
import com.example.hanashops.repository.Repository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TazasViewModel extends AndroidViewModel {
    private Repository repository;
    private MutableLiveData<List<Product>> tipoTazaList = new MutableLiveData<>();

    public TazasViewModel(@NonNull Application application) {
        super(application);
    }


    public LiveData<List<Product>> getTipoTazaList() {
        return tipoTazaList;
    }

    public void loadTipoTaza() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        apiService.getProductTypes("1").enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    tipoTazaList.setValue(response.body().getTypes());
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                // Manejo de errores
            }
        });
    }
}
