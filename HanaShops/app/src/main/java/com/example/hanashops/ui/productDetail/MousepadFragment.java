package com.example.hanashops.ui.productDetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.hanashops.R;
import com.example.hanashops.model.BudgetRequest;
import com.example.hanashops.model.Product;
import com.example.hanashops.model.VariationRequest;
import com.example.hanashops.services.emailSender;
import com.example.hanashops.viewmodel.MousepadViewModel;

import java.util.ArrayList;
import java.util.List;

public class MousepadFragment extends Fragment {
    private MousepadViewModel viewModel;
    private Spinner mousepadSpinner;
    private EditText ligaImagen;
    private EditText celular;
    private TextView txtPrice;
    private Button enviarSolicitudButton;

    private List<Product> productos = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_mousepad, container, false);

        initViews(view);
        setupViewModel();
        setupListeners();

        return view;
    }

    private void initViews(View view) {
        mousepadSpinner = view.findViewById(R.id.mousepadSpinner);
        ligaImagen = view.findViewById(R.id.ligaImagen);
        celular = view.findViewById(R.id.celular);
        txtPrice = view.findViewById(R.id.txtPrice);
        enviarSolicitudButton = view.findViewById(R.id.enviarSolicitud);
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(MousepadViewModel.class);

        viewModel.getMousepadList().observe(getViewLifecycleOwner(), productList -> {
            this.productos = productList;
            populateMousepadSpinner(productList);
            if (!productList.isEmpty()) {
                actualizarPrecio(productList.get(0).getPrice());
            }
        });

        viewModel.loadMousepads();
    }

    private void setupListeners() {
        mousepadSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View view, int position, long id) {
                Product selectedProduct = productos.get(position);
                actualizarPrecio(selectedProduct.getPrice());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });

        enviarSolicitudButton.setOnClickListener(v -> handleSendRequest());
    }

    private void actualizarPrecio(String precio) {
        txtPrice.setText(precio);
    }

    private void populateMousepadSpinner(List<Product> products) {
        ArrayAdapter<Product> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, products);
        mousepadSpinner.setAdapter(adapter);
    }

    private void handleSendRequest() {
        Product selectedProduct = (Product) mousepadSpinner.getSelectedItem();

        if (selectedProduct == null || ligaImagen.getText().toString().trim().isEmpty() || celular.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext(), "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        List<VariationRequest> variations = new ArrayList<>();
        variations.add(new VariationRequest("Modelo", selectedProduct.getName()));
        variations.add(new VariationRequest("Precio", selectedProduct.getPrice()));

        BudgetRequest request = new BudgetRequest("Mousepad", variations, ligaImagen.getText().toString().trim(), celular.getText().toString().trim());

        emailSender.sendBudget(getContext(), request);
    }
}
