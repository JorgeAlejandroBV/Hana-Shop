package com.example.hanashops.ui.productDetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.hanashops.R;
import com.example.hanashops.viewmodel.CamisasViewModel;

import java.util.List;

public class CamisasFragment extends Fragment {
    private CamisasViewModel viewModel;
    private Spinner tipoCamisaSpinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_camisas, container, false);
        tipoCamisaSpinner = view.findViewById(R.id.tipoCamisaSpinner);

        // Obtiene el ViewModel para cargar los tipos de camisas
        viewModel = new ViewModelProvider(this).get(CamisasViewModel.class);
        viewModel.getTipoCamisaList().observe(getViewLifecycleOwner(), this::populateSpinner);

        // Cargar los tipos de camisas (Esto se puede hacer también desde el ViewModel)
        viewModel.loadTipoCamisa();
        return view;
    }

    private void populateSpinner(List<String> items) {
        // Llenar el spinner con los nombres de las camisas
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        tipoCamisaSpinner.setAdapter(adapter);
    }
}
