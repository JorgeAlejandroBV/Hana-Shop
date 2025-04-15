package com.example.hanashops.ui.productDetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.hanashops.R;
import com.example.hanashops.model.BudgetRequest;
import com.example.hanashops.model.Product;
import com.example.hanashops.model.Variation;
import com.example.hanashops.model.VariationRequest;
import com.example.hanashops.services.emailSender;
import com.example.hanashops.viewmodel.CamisasViewModel;

import java.util.ArrayList;
import java.util.List;

public class CamisasFragment extends Fragment {
    private CamisasViewModel viewModel;
    private Spinner tipoCamisaSpinner;
    private Spinner colorCamisaSpinner;
    private EditText ligaImagen;
    private EditText celular;
    private Button enviarSolicitudButton;
    private TextView txtPrice; // Mostrar el precio

    private List<Product> productos = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_camisas, container, false);

        initViews(view);
        setupViewModel();
        setupListeners();

        return view;
    }

    private void initViews(View view) {
        tipoCamisaSpinner = view.findViewById(R.id.tipoCamisaSpinner);
        colorCamisaSpinner = view.findViewById(R.id.colorCamisaSpinner);
        ligaImagen = view.findViewById(R.id.ligaImagen);
        celular = view.findViewById(R.id.celular);
        enviarSolicitudButton = view.findViewById(R.id.enviarSolicitud);
        txtPrice = view.findViewById(R.id.txtPrice);
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(CamisasViewModel.class);

        viewModel.getTipoCamisaList().observe(getViewLifecycleOwner(), productList -> {
            this.productos = productList;
            populateTipoCamisaSpinner(productList);
            if (!productList.isEmpty()) {
                actualizarPrecio(productList.get(0).getPrice());
            }
        });

        viewModel.getColorCamisaList().observe(getViewLifecycleOwner(), this::populateColorCamisaSpinner);

        viewModel.loadTipoCamisa(); // Cargar tipos de camisas
    }

    private void setupListeners() {
        tipoCamisaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View view, int position, long id) {
                Product selectedProduct = productos.get(position);
                viewModel.loadVariationsForTipo(String.valueOf(selectedProduct.getId()));
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

    private void populateTipoCamisaSpinner(List<Product> products) {
        ArrayAdapter<Product> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, products);
        tipoCamisaSpinner.setAdapter(adapter);
    }

    private void populateColorCamisaSpinner(List<Variation> variations) {
        if (variations != null && !variations.isEmpty()) {
            ArrayAdapter<Variation> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, variations);
            colorCamisaSpinner.setAdapter(adapter);
        } else {
            colorCamisaSpinner.setAdapter(null);
        }
    }

    private void handleSendRequest() {
        Product selectedProduct = (Product) tipoCamisaSpinner.getSelectedItem();
        if (selectedProduct == null) {
            Toast.makeText(getContext(), "Por favor selecciona un tipo de camisa", Toast.LENGTH_SHORT).show();
            return;
        }

        String tipoCamisaSeleccionado = selectedProduct.getName();

        Variation selectedVariation = (Variation) colorCamisaSpinner.getSelectedItem();
        String colorSeleccionado = selectedVariation != null ? selectedVariation.getName() : null;

        String imageUrl = ligaImagen.getText().toString().trim();
        String telefono = celular.getText().toString().trim();

        if (tipoCamisaSeleccionado.isEmpty() || colorSeleccionado == null || imageUrl.isEmpty() || telefono.isEmpty()) {
            Toast.makeText(getContext(), "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        String precio = txtPrice.getText().toString();

        List<VariationRequest> variations = new ArrayList<>();
        variations.add(new VariationRequest("Tipo", tipoCamisaSeleccionado));
        variations.add(new VariationRequest("Color", colorSeleccionado));

        variations.add(new VariationRequest("Precio", precio));

        BudgetRequest request = new BudgetRequest("Camisa", variations, imageUrl, telefono);

        emailSender.sendBudget(getContext(), request);
    }
}
