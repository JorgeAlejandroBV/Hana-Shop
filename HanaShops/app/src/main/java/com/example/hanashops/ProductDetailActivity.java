package com.example.hanashops;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class ProductDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String productType = getIntent().getStringExtra("productType");


        if (productType != null) {
            switch (productType) {
                case "Tazas":
                    setContentView(R.layout.activity_tazas);
                    break;
                case "Mousepad":
                    setContentView(R.layout.activity_mousepad);
                    break;
                case "Termos":
                    setContentView(R.layout.activity_termos);
                    break;
                case "Gorras":
                    setContentView(R.layout.activity_gorras);
                    break;
                case "Camisas":
                    setContentView(R.layout.activity_camisas);
                    break;
                case "Sudaderas":
                    setContentView(R.layout.activity_sudaderas);
                    break;
                default:
                    finish();
                    break;
            }
        } else {
            finish();
        }
    }
}

