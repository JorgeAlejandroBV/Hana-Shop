package com.example.hanashops.ui.productDetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.example.hanashops.viewmodel.TazasViewModel;

import java.util.ArrayList;
import java.util.List;

public class TazasFragment extends Fragment {
    private TazasViewModel viewModel;
    private Spinner tipoTazaSpinner;
    private EditText ligaImagen;
    private EditText celular;
    private View enviarSolicitudButton;
    private TextView txtPrice;

    private List<Product> productos = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_tazas, container, false);

        initViews(view);
        setupViewModel();
        setupObservers();
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
        viewModel = new ViewModelProvider(this).get(TazasViewModel.class);
        viewModel.loadTipoTaza();
    }

    private void setupObservers() {
        viewModel.getTipoTazaList().observe(getViewLifecycleOwner(), productList -> {
            this.productos = productList;

            List<String> nombres = new ArrayList<>();
            for (Product p : productList) {
                nombres.add(p.getName());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    getContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    nombres
            );
            tipoTazaSpinner.setAdapter(adapter);

            if (!productList.isEmpty()) {
                actualizarPrecio(productList.get(0).getPrice());
            }
        });
    }

    private void setupListeners() {
        enviarSolicitudButton.setOnClickListener(v -> handleSendRequest());

        tipoTazaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Product productoSeleccionado = productos.get(position);
                actualizarPrecio(productoSeleccionado.getPrice());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void actualizarPrecio(String precio) {
        txtPrice.setText(precio);
    }

    private void handleSendRequest() {
        String item = "Taza";
        String tipoSeleccionado = (String) tipoTazaSpinner.getSelectedItem();
        String imageUrl = ligaImagen.getText().toString().trim();
        String telefono = celular.getText().toString().trim();

        if (tipoSeleccionado == null || imageUrl.isEmpty() || telefono.isEmpty()) {
            Toast.makeText(getContext(), "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Obtener el precio desde el TextView
        String precio = txtPrice.getText().toString();

        // Crear la lista de variaciones, ahora incluyendo el precio
        List<VariationRequest> variations = new ArrayList<>();
        variations.add(new VariationRequest("Tipo", tipoSeleccionado));
        variations.add(new VariationRequest("Precio", precio));

        // Crear el objeto de presupuesto
        BudgetRequest request = new BudgetRequest(item, variations, imageUrl, telefono);

        // Enviar el presupuesto por correo
        emailSender.sendBudget(getContext(), request);
    }
}