package com.example.hanashops.services;

import android.content.Context;
import android.widget.Toast;

import com.example.hanashops.model.BudgetRequest;
import com.example.hanashops.network.ApiService;
import com.example.hanashops.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class emailSender {
    public static void sendBudget(Context context, BudgetRequest request) {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        apiService.enviarPresupuesto(request).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Solicitud enviada exitosamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Error al enviar solicitud: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Fallo de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
