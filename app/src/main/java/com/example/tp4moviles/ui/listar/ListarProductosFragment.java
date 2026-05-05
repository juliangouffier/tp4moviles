package com.example.tp4moviles.ui.listar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.tp4moviles.adapter.ProductoAdapter;
import com.example.tp4moviles.databinding.FragmentListarProductosBinding;
import com.example.tp4moviles.viewModel.ProductoViewModel;

public class ListarProductosFragment extends Fragment {

    private FragmentListarProductosBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProductoViewModel viewModel =
                new ViewModelProvider(requireActivity()).get(ProductoViewModel.class);

        binding = FragmentListarProductosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ProductoAdapter adapter = new ProductoAdapter();
        binding.recyclerviewProductos.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerviewProductos.setAdapter(adapter);

        viewModel.getProductos().observe(getViewLifecycleOwner(), productos -> {
            adapter.setData(productos);
            boolean empty = productos == null || productos.isEmpty();
            binding.textEmptyList.setVisibility(empty ? View.VISIBLE : View.GONE);
            binding.recyclerviewProductos.setVisibility(empty ? View.GONE : View.VISIBLE);
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
