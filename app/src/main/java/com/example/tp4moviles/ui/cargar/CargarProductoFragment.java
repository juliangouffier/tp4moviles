package com.example.tp4moviles.ui.cargar;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tp4moviles.R;
import com.example.tp4moviles.databinding.FragmentCargarProductoBinding;
import com.example.tp4moviles.model.Producto;
import com.example.tp4moviles.viewModel.ProductoViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.math.BigDecimal;

public class CargarProductoFragment extends Fragment {

    private FragmentCargarProductoBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProductoViewModel viewModel =
                new ViewModelProvider(requireActivity()).get(ProductoViewModel.class);

        binding = FragmentCargarProductoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.etPrecio.setFilters(new InputFilter[]{new FiltroPrecioDecimal()});

        binding.etCodigo.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                binding.inputLayoutCodigo.setError(null);
            }
        });
        binding.etDescripcion.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                binding.inputLayoutDescripcion.setError(null);
            }
        });
        binding.etPrecio.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                binding.inputLayoutPrecio.setError(null);
            }
        });

        binding.buttonAgregar.setOnClickListener(v -> intentarAgregar(viewModel));
        return root;
    }

    private void limpiarErroresDeCampos() {
        binding.inputLayoutCodigo.setError(null);
        binding.inputLayoutDescripcion.setError(null);
        binding.inputLayoutPrecio.setError(null);
    }

    private void intentarAgregar(ProductoViewModel viewModel) {
        limpiarErroresDeCampos();

        String codigo = textoDe(binding.etCodigo.getText()).trim();
        String descripcion = textoDe(binding.etDescripcion.getText()).trim();
        String precioStr = textoDe(binding.etPrecio.getText()).trim();

        boolean hasError = false;
        if (codigo.isEmpty()) {
            binding.inputLayoutCodigo.setError(getString(R.string.error_empty_field));
            hasError = true;
        }
        if (descripcion.isEmpty()) {
            binding.inputLayoutDescripcion.setError(getString(R.string.error_empty_field));
            hasError = true;
        }
        if (precioStr.isEmpty()) {
            binding.inputLayoutPrecio.setError(getString(R.string.error_empty_field));
            hasError = true;
        }
        if (hasError) {
            return;
        }

        BigDecimal precio;
        try {
            precio = new BigDecimal(precioStr.replace(',', '.'));
        } catch (NumberFormatException e) {
            binding.inputLayoutPrecio.setError(getString(R.string.error_invalid_price));
            return;
        }

        if (precio.compareTo(BigDecimal.ZERO) < 0) {
            binding.inputLayoutPrecio.setError(getString(R.string.error_price_negative));
            return;
        }

        Producto producto = new Producto(codigo, descripcion, precio);
        boolean added = viewModel.agregarProducto(producto);
        if (added) {
            Snackbar.make(binding.getRoot(), R.string.success_product_added, Snackbar.LENGTH_SHORT).show();
            binding.etCodigo.setText("");
            binding.etDescripcion.setText("");
            binding.etPrecio.setText("");
            limpiarErroresDeCampos();
        } else {
            binding.inputLayoutCodigo.setError(getString(R.string.error_duplicate_code));
        }
    }

    private static String textoDe(CharSequence cs) {
        return cs != null ? cs.toString() : "";
    }

    private static final class FiltroPrecioDecimal implements InputFilter {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            StringBuilder sb = new StringBuilder(dest);
            sb.replace(dstart, dend, source.subSequence(start, end).toString());
            String next = sb.toString();
            if (next.isEmpty()) {
                return null;
            }
            int separators = 0;
            for (int i = 0; i < next.length(); i++) {
                char c = next.charAt(i);
                if (c == '.' || c == ',') {
                    separators++;
                } else if (c < '0' || c > '9') {
                    return "";
                }
            }
            if (separators > 1) {
                return "";
            }
            return null;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
