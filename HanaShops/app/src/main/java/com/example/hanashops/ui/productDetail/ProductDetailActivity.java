package com.example.hanashops.ui.productDetail;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hanashops.R;
import com.example.hanashops.repository.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductDetailActivity extends AppCompatActivity {
    private Spinner tipoCamisaSpinner;
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String productType = getIntent().getStringExtra("productType");
        if (productType == null) {
            Toast.makeText(this, "Error: No se recibió el tipo de producto", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        int layoutResId = getLayoutForProductType(productType);
        if (layoutResId == 0) {
            Toast.makeText(this, "Producto no válido", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        setContentView(layoutResId);
        tipoCamisaSpinner = findViewById(R.id.tipoCamisaSpinner);

        repository = new Repository();
        loadProductTypes(productType.toLowerCase());
    }

    private int getLayoutForProductType(String productType) {
        switch (productType) {
            case "Camisas":
                return R.layout.activity_camisas;
            case "Gorras":
                return R.layout.activity_gorras;
            default:
                return 0;
        }
    }

    private void loadProductTypes(String category) {
        repository.getProductTypes(category, productMap -> {
            List<String> productNames = new ArrayList<>(productMap.values());

            runOnUiThread(() -> {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, productNames);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                tipoCamisaSpinner.setAdapter(adapter);
            });
        });
    }
}
