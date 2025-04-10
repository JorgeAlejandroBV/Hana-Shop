package com.example.hanashops.repository;

import android.util.Log;

import com.example.hanashops.model.Product;
import com.example.hanashops.model.ProductResponse;
import com.example.hanashops.model.Variation;
import com.example.hanashops.model.VariationResponse;
import com.example.hanashops.network.ApiService;
import com.example.hanashops.network.RetrofitClient;

import retrofit2.Retrofit;

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

    // Obtener los tipos de productos
    public void getProductTypes(String category, Consumer<Map<Integer, String>> callback) {
        Log.d("Repository", "Categoria solicitada: " + category);  // Log para verificar la categoría
        apiService.getProductTypes(category).enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Map<Integer, String> productMap = new HashMap<>();
                    for (Product product : response.body().getTypes()) {
                        productMap.put(product.getId(), product.getName());
                    }
                    callback.accept(productMap);
                } else {
                    Log.e("Repository", "Error en la respuesta del servidor: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Log.e("Repository", "Error en la solicitud: " + t.getMessage());
            }
        });
    }

    // Obtener las variaciones de un producto
    public void getVariations(String productId, Consumer<List<Variation>> callback) {
        Log.d("Repository", "Producto solicitado para variaciones: " + productId);
        apiService.getProductVariations(productId).enqueue(new Callback<VariationResponse>() {
            @Override
            public void onResponse(Call<VariationResponse> call, Response<VariationResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.accept(response.body().getVariations());
                } else {
                    Log.e("Repository", "Error en la respuesta del servidor: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<VariationResponse> call, Throwable t) {
                Log.e("Repository", "Error en la solicitud de variaciones: " + t.getMessage());
            }
        });
    }
}
