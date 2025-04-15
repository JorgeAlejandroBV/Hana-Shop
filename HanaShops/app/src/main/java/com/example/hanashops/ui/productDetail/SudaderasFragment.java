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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.hanashops.R;
import com.example.hanashops.model.BudgetRequest;
import com.example.hanashops.model.Product;
import com.example.hanashops.model.Variation;
import com.example.hanashops.model.VariationRequest;
import com.example.hanashops.services.emailSender;
import com.example.hanashops.viewmodel.SudaderasViewModel;

import java.util.ArrayList;
import java.util.List;

public class SudaderasFragment extends Fragment {

    private SudaderasViewModel viewModel;
    private Spinner tipoSudaderaSpinner;
    private Spinner tipoSpinner; // Nuevo Spinner para los tipos de variación (por ejemplo, colores)
    private EditText ligaImagen;
    private EditText celular;
    private Button enviarSolicitudButton;
    private TextView txtPrice;

    private List<Product> productos = new ArrayList<>();
    private List<Variation> variaciones = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_sudaderas, container, false);

        initViews(view);
        setupViewModel();
        setupListeners();

        return view;
    }

    private void initViews(View view) {
        tipoSudaderaSpinner = view.findViewById(R.id.tipoSudaderaSpinner);
        tipoSpinner = view.findViewById(R.id.variantesSudaderaSpinner); // El nuevo spinner
        ligaImagen = view.findViewById(R.id.ligaImagen);
        celular = view.findViewById(R.id.celular);
        enviarSolicitudButton = view.findViewById(R.id.enviarSolicitud);
        txtPrice = view.findViewById(R.id.txtPrice);
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(SudaderasViewModel.class);

        // Observando los cambios en la lista de productos
        viewModel.getSudaderasList().observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> productList) {
                if (productList != null && !productList.isEmpty()) {
                    productos = productList;
                    populateTipoSudaderaSpinner(productList);
                    if (!productList.isEmpty()) {
                        actualizarPrecio(productList.get(0).getPrice());
                    }
                } else {
                    Toast.makeText(getContext(), "No se encontraron productos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Observando las variaciones de tipo para una sudadera seleccionada
        viewModel.getTipoSudaderaVariations().observe(getViewLifecycleOwner(), new Observer<List<Variation>>() {
            @Override
            public void onChanged(List<Variation> variationList) {
                if (variationList != null && !variationList.isEmpty()) {
                    variaciones = variationList;
                    populateTipoSpinner(variaciones);
                } else {
                    Toast.makeText(getContext(), "No se encontraron variaciones", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Cargar tipos de sudaderas desde el ViewModel
        viewModel.loadSudaderas();
    }

    private void setupListeners() {
        tipoSudaderaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View view, int position, long id) {
                Product selectedProduct = productos.get(position);
                actualizarPrecio(selectedProduct.getPrice());

                // Cargar las variaciones de la sudadera seleccionada
                viewModel.loadVariationsForSudadera(String.valueOf(selectedProduct.getId()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });

        enviarSolicitudButton.setOnClickListener(v -> handleSendRequest());
    }

    private void actualizarPrecio(String precio) {
        txtPrice.setText(precio);
    }

    private void populateTipoSudaderaSpinner(List<Product> products) {
        // Asegúrate de que cada producto tenga un nombre representativo para mostrar en el Spinner
        if (products != null && !products.isEmpty()) {
            ArrayAdapter<Product> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, products);
            tipoSudaderaSpinner.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(getContext(), "No se encontraron productos", Toast.LENGTH_SHORT).show();
        }
    }

    private void populateTipoSpinner(List<Variation> variations) {
        // Configurar el Spinner de variaciones
        if (variations != null && !variations.isEmpty()) {
            ArrayAdapter<Variation> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, variations);
            tipoSpinner.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(getContext(), "No se encontraron variaciones", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleSendRequest() {
        Product selectedProduct = (Product) tipoSudaderaSpinner.getSelectedItem();
        Variation selectedVariation = (Variation) tipoSpinner.getSelectedItem(); // Obtener la variación seleccionada

        if (selectedProduct == null || selectedVariation == null) {
            Toast.makeText(getContext(), "Por favor selecciona un tipo de sudadera y variación", Toast.LENGTH_SHORT).show();
            return;
        }

        String tipoSudaderaSeleccionada = selectedProduct.getName();
        String tipoVariacionSeleccionada = selectedVariation.getName(); // Nombre de la variación seleccionada

        String imageUrl = ligaImagen.getText().toString().trim();
        String telefono = celular.getText().toString().trim();

        if (tipoSudaderaSeleccionada.isEmpty() || tipoVariacionSeleccionada.isEmpty() || imageUrl.isEmpty() || telefono.isEmpty()) {
            Toast.makeText(getContext(), "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        String precio = txtPrice.getText().toString();

        List<VariationRequest> variations = new ArrayList<>();
        variations.add(new VariationRequest("Tipo", tipoSudaderaSeleccionada));
        variations.add(new VariationRequest("Variación", tipoVariacionSeleccionada));
        variations.add(new VariationRequest("Precio", precio));

        BudgetRequest request = new BudgetRequest("Sudadera", variations, imageUrl, telefono);

        emailSender.sendBudget(getContext(), request);
    }
}
