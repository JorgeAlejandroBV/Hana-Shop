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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SudaderasViewModel extends AndroidViewModel {

    private MutableLiveData<List<Product>> sudaderasList = new MutableLiveData<>();
    private MutableLiveData<List<Variation>> tipoSudaderaVariations = new MutableLiveData<>();
    private MutableLiveData<String> selectedProductPrice = new MutableLiveData<>();

    public SudaderasViewModel(Application application) {
        super(application);
    }

    public LiveData<List<Product>> getSudaderasList() {
        return sudaderasList;
    }

    // Obtener variaciones de tipo para una sudadera
    public LiveData<List<Variation>> getTipoSudaderaVariations() {
        return tipoSudaderaVariations;
    }

    // Cargar tipos de sudaderas desde la API
    public void loadSudaderas() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        apiService.getProductTypes("6").enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Product> products = response.body().getTypes();
                    sudaderasList.setValue(products);
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                // Manejo de errores
            }
        });
    }

    // Cargar las variaciones de un tipo de sudadera
    public void loadVariationsForSudadera(String sudaderaId) {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        apiService.getProductVariations(sudaderaId).enqueue(new Callback<VariationResponse>() {
            @Override
            public void onResponse(Call<VariationResponse> call, Response<VariationResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Variation> variations = response.body().getVariations();
                    tipoSudaderaVariations.setValue(variations);
                }
            }

            @Override
            public void onFailure(Call<VariationResponse> call, Throwable t) {
                // Manejo de errores
            }
        });
    }

    public LiveData<String> getSelectedProductPrice() {
        return selectedProductPrice;
    }

    public void setSelectedProductPrice(String price) {
        selectedProductPrice.setValue(price);
    }
}
