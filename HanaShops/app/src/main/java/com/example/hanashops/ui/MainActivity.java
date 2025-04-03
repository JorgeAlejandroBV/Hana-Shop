package com.example.hanashops.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.hanashops.R;
import com.example.hanashops.model.ProductUI;
import com.example.hanashops.ui.productDetail.ProductDetailActivity;
import com.example.hanashops.viewmodel.MainViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;
    private LinearLayout tazasLayout, mousepadLayout, termosLayout, gorrasLayout, camisasLayout, sudaderasLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        tazasLayout = findViewById(R.id.tazasLayout);
        mousepadLayout = findViewById(R.id.mousepadLayout);
        termosLayout = findViewById(R.id.termosLayout);
        gorrasLayout = findViewById(R.id.gorrasLayout);
        camisasLayout = findViewById(R.id.camisasLayout);
        sudaderasLayout = findViewById(R.id.sudaderasLayout);

        // Cambiar Observer para observar una lista de ProductUI
        viewModel.getProducts().observe(this, this::setupProductClickListeners);
    }

    private void setupProductClickListeners(List<ProductUI> products) {
        for (ProductUI product : products) {
            View productView = getProductViewByName(product.getName());
            if (productView != null) {
                productView.setOnClickListener(v -> openProductDetail(product.getName()));
            }
        }
    }

    private View getProductViewByName(String productName) {
        switch (productName) {
            case "Tazas":
                return tazasLayout;
            case "Mousepad":
                return mousepadLayout;
            case "Termos":
                return termosLayout;
            case "Gorras":
                return gorrasLayout;
            case "Camisas":
                return camisasLayout;
            case "Sudaderas":
                return sudaderasLayout;
            default:
                return null;
        }
    }

    private void openProductDetail(String productType) {
        Intent intent = new Intent(this, ProductDetailActivity.class);
        intent.putExtra("productType", productType);
        startActivity(intent);
    }
}
