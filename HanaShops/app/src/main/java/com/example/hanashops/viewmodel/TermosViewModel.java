package com.example.hanashops.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.hanashops.model.Product;
import com.example.hanashops.model.ProductResponse;
import com.example.hanashops.network.ApiService;
import com.example.hanashops.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TermosViewModel extends AndroidViewModel {
    private MutableLiveData<List<Product>> termoList = new MutableLiveData<>();

    public TermosViewModel(Application application) {
        super(application);
    }

    public LiveData<List<Product>> getTermoList() {
        return termoList;
    }

    public void loadTermos() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        apiService.getProductTypes("3").enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    termoList.setValue(response.body().getTypes());
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                // Manejo de errores
            }
        });
    }
}
