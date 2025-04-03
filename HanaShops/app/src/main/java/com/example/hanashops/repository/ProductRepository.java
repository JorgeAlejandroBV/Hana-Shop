package com.example.hanashops.repository;

import com.example.hanashops.R;
import com.example.hanashops.model.ProductUI;

import java.util.Arrays;
import java.util.List;

public class ProductRepository {

    public List<ProductUI> getProducts() {
        return Arrays.asList(
                new ProductUI("Tazas", R.layout.activity_tazas),
                new ProductUI("Mousepad", R.layout.activity_mousepad),
                new ProductUI("Termos", R.layout.activity_termos),
                new ProductUI("Gorras", R.layout.activity_gorras),
                new ProductUI("Camisas", R.layout.activity_camisas),
                new ProductUI("Sudaderas", R.layout.activity_sudaderas)
        );
    }
}
