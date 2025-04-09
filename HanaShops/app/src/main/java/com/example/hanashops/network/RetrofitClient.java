package com.example.hanashops.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .hostnameVerifier((hostname, session) -> true)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.1.64:8000/api/")  // Asegúrate de que esté bien configurado
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
