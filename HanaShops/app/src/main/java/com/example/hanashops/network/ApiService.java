package com.example.hanashops.network;

import com.example.hanashops.model.ProductResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("products/{category}")
    Call<ProductResponse> getProductTypes(@Path("category") String category);
}
