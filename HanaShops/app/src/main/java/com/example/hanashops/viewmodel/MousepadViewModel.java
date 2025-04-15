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

public class MousepadViewModel extends AndroidViewModel {
    private final MutableLiveData<List<Product>> mousepadList = new MutableLiveData<>();

    public MousepadViewModel(Application application) {
        super(application);
    }

    public LiveData<List<Product>> getMousepadList() {
        return mousepadList;
    }

    public void loadMousepads() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        apiService.getProductTypes("2").enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Product> products = response.body().getTypes();
                    mousepadList.setValue(products);
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                // Manejo de errores si lo necesitas
            }
        });
    }
}
