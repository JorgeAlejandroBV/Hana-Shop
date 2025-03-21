package com.example.hanashops;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private LinearLayout tazasLayout, mousepadLayout, termosLayout, gorrasLayout, camisasLayout, sudaderasLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tazasLayout = findViewById(R.id.tazasLayout);
        mousepadLayout = findViewById(R.id.mousepadLayout);
        termosLayout = findViewById(R.id.termosLayout);
        gorrasLayout = findViewById(R.id.gorrasLayout);
        camisasLayout = findViewById(R.id.camisasLayout);
        sudaderasLayout = findViewById(R.id.sudaderasLayout);

        setupProductClickListeners();
    }

    private void setupProductClickListeners() {
        tazasLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProductDetail("Tazas");
            }
        });

        mousepadLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProductDetail("Mousepad");
            }
        });

        termosLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProductDetail("Termos");
            }
        });

        gorrasLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProductDetail("Gorras");
            }
        });

        camisasLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProductDetail("Camisas");
            }
        });

        sudaderasLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProductDetail("Sudaderas");
            }
        });
    }

    private void openProductDetail(String productType) {
        Intent intent = new Intent(this, ProductDetailActivity.class);
        intent.putExtra("productType", productType);
        startActivity(intent);
    }

}