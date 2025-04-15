package com.example.hanashops.ui.productDetail;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.hanashops.R;

public class ProductDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String productType = getIntent().getStringExtra("productType");
        if (productType == null) {
            Toast.makeText(this, "Error: No se recibió el tipo de producto", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Log.d("info", productType);
        Fragment fragment = getFragmentForProductType(productType);
        if (fragment == null) {
            Toast.makeText(this, "Producto no válido", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        setContentView(R.layout.activity_product_detail);

        // Iniciar el fragmento adecuado para el tipo de producto
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    private Fragment getFragmentForProductType(String productType) {
        switch (productType) {
            case "Camisas":
                return new CamisasFragment();  // Usamos un fragmento específico para Camisas
            case "Gorras":
                return new GorrasFragment();   // Un fragmento específico para Gorras
            case "Tazas":
                return new TazasFragment();
            case "Mousepad":
                return new MousepadFragment();
            default:
                return null; // Si no hay tipo válido, devolvemos null
        }
    }
}
