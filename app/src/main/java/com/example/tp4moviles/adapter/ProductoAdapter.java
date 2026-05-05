package com.example.tp4moviles.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp4moviles.R;
import com.example.tp4moviles.model.Producto;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.VH> {

    private final NumberFormat priceFormat = NumberFormat.getNumberInstance(new Locale("es", "AR"));

    private List<Producto> data = new ArrayList<>();

    public ProductoAdapter() {
        priceFormat.setMinimumFractionDigits(2);
        priceFormat.setMaximumFractionDigits(2);
    }

    public void setData(List<Producto> nuevaLista) {
        this.data = nuevaLista != null ? nuevaLista : new ArrayList<>();
        notifyDataSetChanged();
    }

    static class VH extends RecyclerView.ViewHolder {
        final TextView codigo;
        final TextView descripcion;
        final TextView precio;

        VH(View v) {
            super(v);
            codigo = v.findViewById(R.id.tvCodigo);
            descripcion = v.findViewById(R.id.tvDescripcion);
            precio = v.findViewById(R.id.tvPrecio);
        }
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_producto, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Producto p = data.get(position);
        holder.descripcion.setText(p.getDescripcion());
        holder.codigo.setText(holder.itemView.getContext().getString(R.string.card_item_code, p.getCodigo()));
        BigDecimal amount = p.getPrecio();
        holder.precio.setText(holder.itemView.getContext().getString(
                R.string.card_item_price,
                priceFormat.format(amount)));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
