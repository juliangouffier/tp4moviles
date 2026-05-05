package com.example.tp4moviles.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tp4moviles.MainActivity;
import com.example.tp4moviles.model.Producto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ProductoViewModel extends ViewModel {

    private final MutableLiveData<List<Producto>> productosLive = new MutableLiveData<>();

    public ProductoViewModel() {
        actualizarLista();
    }

    public LiveData<List<Producto>> getProductos() {
        return productosLive;
    }

    public void actualizarLista() {
        List<Producto> sorted = new ArrayList<>(MainActivity.productos);
        sorted.sort(Comparator.comparing(p -> p.getDescripcion().toLowerCase()));
        productosLive.setValue(sorted);
    }

    public boolean agregarProducto(Producto product) {
        String code = product.getCodigo().trim();
        for (Producto existing : MainActivity.productos) {
            if (existing.getCodigo().equals(code)) {
                return false;
            }
        }
        product.setCodigo(code);
        product.setDescripcion(product.getDescripcion().trim());
        MainActivity.productos.add(product);
        actualizarLista();
        return true;
    }
}
