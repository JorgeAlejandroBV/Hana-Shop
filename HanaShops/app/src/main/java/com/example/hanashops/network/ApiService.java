package com.example.hanashops.network;

import com.example.hanashops.model.BudgetRequest;
import com.example.hanashops.model.ProductResponse;
import com.example.hanashops.model.VariationResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @GET("products/{category}")
    Call<ProductResponse> getProductTypes(@Path("category") String category);

    @GET("variations/{product}")
    Call<VariationResponse> getProductVariations(@Path("product") String productId);

    @POST("budget")
    Call<Void> enviarPresupuesto(@Body BudgetRequest request);
}
