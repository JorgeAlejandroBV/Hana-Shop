package com.example.hanashops.ui.productDetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.hanashops.model.VariationRequest;
import com.example.hanashops.services.emailSender;
import com.example.hanashops.viewmodel.TermosViewModel;

import java.util.ArrayList;
import java.util.List;

public class TermosFragment extends Fragment {
    private TermosViewModel viewModel;
    private Spinner tipoTazaSpinner;
    private EditText ligaImagen;
    private EditText celular;
    private Button enviarSolicitudButton;
    private TextView txtPrice;

    private List<Product> termos = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_termos, container, false);


        initViews(view);
        setupViewModel();
        setupListeners();

        return view;
    }

    private void initViews(View view) {
        tipoTazaSpinner = view.findViewById(R.id.tipoTazaSpinner);
        ligaImagen = view.findViewById(R.id.ligaImagen);
        celular = view.findViewById(R.id.celular);
        enviarSolicitudButton = view.findViewById(R.id.enviarSolicitud);
        txtPrice = view.findViewById(R.id.txtPrice);
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(TermosViewModel.class);

        viewModel.getTermoList().observe(getViewLifecycleOwner(), productList -> {
            this.termos = productList;
            populateTipoTazaSpinner(productList);
            if (!productList.isEmpty()) {
                actualizarPrecio(productList.get(0).getPrice());
            }
        });

        viewModel.loadTermos(); // Cargar termos
    }

    private void setupListeners() {
        tipoTazaSpinner.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                Product selected = termos.get(position);
                actualizarPrecio(selected.getPrice());
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        });

        enviarSolicitudButton.setOnClickListener(v -> handleSendRequest());
    }

    private void actualizarPrecio(String precio) {
        txtPrice.setText(precio);
    }

    private void populateTipoTazaSpinner(List<Product> products) {
        ArrayAdapter<Product> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, products);
        tipoTazaSpinner.setAdapter(adapter);
    }

    private void handleSendRequest() {
        Product selectedProduct = (Product) tipoTazaSpinner.getSelectedItem();
        if (selectedProduct == null) {
            Toast.makeText(getContext(), "Por favor selecciona un tipo de termo", Toast.LENGTH_SHORT).show();
            return;
        }

        String tipoSeleccionado = selectedProduct.getName();
        String imageUrl = ligaImagen.getText().toString().trim();
        String telefono = celular.getText().toString().trim();
        String precio = txtPrice.getText().toString();

        if (tipoSeleccionado.isEmpty() || imageUrl.isEmpty() || telefono.isEmpty()) {
            Toast.makeText(getContext(), "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        List<VariationRequest> variations = new ArrayList<>();
        variations.add(new VariationRequest("Tipo", tipoSeleccionado));
        variations.add(new VariationRequest("Precio", precio));

        BudgetRequest request = new BudgetRequest("Termo", variations, imageUrl, telefono);
        emailSender.sendBudget(getContext(), request);
    }
}
