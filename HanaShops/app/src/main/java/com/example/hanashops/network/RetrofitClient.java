package com.example.hanashops.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .hostnameVerifier((hostname, session) -> hostname.equals("127.0.0.1") || hostname.equals("localhost"))
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://127.0.0.1:8000/")  // Cambia esta URL a la de tu servidor
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
