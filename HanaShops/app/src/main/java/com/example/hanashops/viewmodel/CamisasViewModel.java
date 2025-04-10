package com.example.hanashops.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.hanashops.model.Product;
import com.example.hanashops.model.ProductResponse;
import com.example.hanashops.model.Variation;
import com.example.hanashops.model.VariationResponse;
import com.example.hanashops.network.ApiService;
import com.example.hanashops.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CamisasViewModel extends AndroidViewModel {
    // MutableLiveData de tipo Product para almacenar los productos
    private MutableLiveData<List<Product>> tipoCamisaList = new MutableLiveData<>();
    // MutableLiveData de tipo Variation para almacenar las variaciones de color
    private MutableLiveData<List<Variation>> colorCamisaList = new MutableLiveData<>();

    public CamisasViewModel(Application application) {
        super(application);
    }

    public LiveData<List<Product>> getTipoCamisaList() {
        return tipoCamisaList;
    }

    public void loadTipoCamisa() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        apiService.getProductTypes("5").enqueue(new Callback<ProductResponse>()  {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Extrae la lista de productos desde ProductResponse
                    List<Product> products = response.body().getTypes();

                    // Actualiza tipoCamisaList con la lista de productos
                    tipoCamisaList.setValue(products);
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                // Manejo de errores
            }
        });
    }

    public LiveData<List<Variation>> getColorCamisaList() {
        return colorCamisaList;
    }

    public void loadVariationsForTipo(String tipoCamisaId) {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        apiService.getProductVariations(tipoCamisaId).enqueue(new Callback<VariationResponse>()  {
            @Override
            public void onResponse(Call<VariationResponse> call, Response<VariationResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Extrae la lista de variaciones desde VariationResponse
                    List<Variation> variations = response.body().getVariations();

                    // Actualiza colorCamisaList con la lista de variaciones
                    colorCamisaList.setValue(variations);
                }
            }

            @Override
            public void onFailure(Call<VariationResponse> call, Throwable t) {
                // Manejo de errores
            }
        });
    }
}
