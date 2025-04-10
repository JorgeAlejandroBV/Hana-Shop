package com.example.hanashops.ui.productDetail;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.hanashops.R;
import com.example.hanashops.model.BudgetRequest;
import com.example.hanashops.model.Variation;
import com.example.hanashops.model.VariationRequest;
import com.example.hanashops.services.emailSender;
import com.example.hanashops.viewmodel.GorrasViewModel;

import java.security.Provider;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GorrasFragment extends Fragment {
    private GorrasViewModel viewModel;
    private Spinner tipoGorraSpinner;
    private Spinner tamanoImagenSpinner;
    private EditText ligaImagen;
    private EditText celular;
    private View enviarSolicitudButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_gorras, container, false);

        initViews(view);
        setupViewModel();
        setupObservers();
        setupListeners();

        return view;
    }

    private void initViews(View view) {
        tipoGorraSpinner = view.findViewById(R.id.tipoGorraSpinner);
        ligaImagen = view.findViewById(R.id.ligaImagen);
        celular = view.findViewById(R.id.celular);
        enviarSolicitudButton = view.findViewById(R.id.enviarSolicitud);
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(GorrasViewModel.class);
        viewModel.loadVariations("8"); // Puedes mover el ID a una constante si cambia por producto
    }

    private void setupObservers() {
        viewModel.getVariations().observe(getViewLifecycleOwner(), variations -> {
            List<String> variationNames = new ArrayList<>();
            for (Variation variation : variations) {
                variationNames.add(variation.getName());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    getContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    variationNames
            );
            tipoGorraSpinner.setAdapter(adapter);
        });
    }

    private void setupListeners() {
        enviarSolicitudButton.setOnClickListener(v -> handleSendRequest());
    }

    private void handleSendRequest() {
        String item = "Gorra";
        String colorSeleccionado = (String) tipoGorraSpinner.getSelectedItem();
        String imageUrl = ligaImagen.getText().toString().trim();
        String telefono = celular.getText().toString().trim();

        if (colorSeleccionado == null || imageUrl.isEmpty() || telefono.isEmpty()) {
            Toast.makeText(getContext(), "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        List<VariationRequest> variations = new ArrayList<>();
        variations.add(new VariationRequest("Color", colorSeleccionado));

        BudgetRequest request = new BudgetRequest(item, variations, imageUrl, telefono);

        emailSender.sendBudget(getContext(), request);
    }
}
