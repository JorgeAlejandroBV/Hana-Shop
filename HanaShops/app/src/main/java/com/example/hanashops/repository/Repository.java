package com.example.hanashops.repository;

import android.util.Log;

import com.example.hanashops.model.Product;
import com.example.hanashops.model.ProductResponse;
import com.example.hanashops.network.ApiService;
import com.example.hanashops.network.RetrofitClient;
import retrofit2.Retrofit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    private final ApiService apiService;

    public Repository() {
        Retrofit retrofit = RetrofitClient.getClient();
        apiService = retrofit.create(ApiService.class);
    }

    public void getProductTypes(String category, Consumer<Map<Integer, String>> callback) {
        apiService.getProductTypes(category).enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Map<Integer, String> productMap = new HashMap<>();
                    for (Product product : response.body().getData()) {
                        productMap.put(product.getId(), product.getName());
                    }
                    callback.accept(productMap);
                } else {
                    Log.e("Repository", "Error en la respuesta del servidor");
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Log.e("Repository", "Error en la solicitud: " + t.getMessage());
            }
        });
    }
}
