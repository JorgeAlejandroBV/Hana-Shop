package com.example.hanashops.ui.productDetail;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(CamisasViewModel.class);
        viewModel.getTipoCamisaList().observe(getViewLifecycleOwner(), this::populateTipoCamisaSpinner);
        viewModel.loadTipoCamisa(); // Cargar tipos de camisas

        viewModel.getColorCamisaList().observe(getViewLifecycleOwner(), this::populateColorCamisaSpinner);

        viewModel.loadTipoCamisa(); // Cargar tipos de camisas
    }


    private void setupListeners() {
        tipoCamisaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View view, int position, long id) {
                // Obtener el objeto Product seleccionado
                Product selectedProduct = (Product) tipoCamisaSpinner.getItemAtPosition(position);
                String tipoCamisa = String.valueOf(selectedProduct.getId()) ;  // Extraer el nombre del producto (o lo que necesites)

                Log.d("info", tipoCamisa);
                viewModel.loadVariationsForTipo(tipoCamisa); // Cargar variaciones para el tipo seleccionado
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Opcional: manejar el caso cuando no se seleccione nada
            }
        });

        enviarSolicitudButton.setOnClickListener(v -> handleSendRequest());
    }



    private void populateTipoCamisaSpinner(List<Product> products) {
        ArrayAdapter<Product> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, products);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipoCamisaSpinner.setAdapter(adapter);
    }


    private void populateColorCamisaSpinner(List<Variation> variations) {
        if (variations != null && !variations.isEmpty()) {
            // Crear un ArrayAdapter para el Spinner de variaciones
            ArrayAdapter<Variation> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, variations);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            colorCamisaSpinner.setAdapter(adapter);
        } else {
            // Si no hay variaciones, mostrar un mensaje o dejar el spinner vacío
            colorCamisaSpinner.setAdapter(null);
        }
    }

    private void handleSendRequest() {
        // Obtener el producto seleccionado del spinner tipoCamisaSpinner
        Product selectedProduct = (Product) tipoCamisaSpinner.getSelectedItem();
        if (selectedProduct == null) {
            Toast.makeText(getContext(), "Por favor selecciona un tipo de camisa", Toast.LENGTH_SHORT).show();
            return;
        }

        // Obtener el id del producto seleccionado
        String tipoCamisaSeleccionado = selectedProduct.getName();

        // Obtener el color seleccionado del spinner colorCamisaSpinner
        Variation selectedVariation = (Variation) colorCamisaSpinner.getSelectedItem();  // Obtener el objeto Variation
        String colorSeleccionado = selectedVariation != null ? selectedVariation.getName() : null;  // Acceder al nombre

        String imageUrl = ligaImagen.getText().toString().trim();
        String telefono = celular.getText().toString().trim();

        // Validación de campos
        if (tipoCamisaSeleccionado.isEmpty() || colorSeleccionado == null || imageUrl.isEmpty() || telefono.isEmpty()) {
            Toast.makeText(getContext(), "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear la lista de variaciones
        List<VariationRequest> variations = new ArrayList<>();
        variations.add(new VariationRequest("Tipo", tipoCamisaSeleccionado));
        variations.add(new VariationRequest("Color", colorSeleccionado));

        // Crear el objeto de la solicitud
        BudgetRequest request = new BudgetRequest("Camisa", variations, imageUrl, telefono);

        // Usar la clase de servicio para enviar el presupuesto
        emailSender.sendBudget(getContext(), request);
    }
}
