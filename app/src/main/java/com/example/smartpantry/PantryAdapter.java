package com.example.smartpantry;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.PantryHolder> {

    public interface Listener {
        void onEdit(PantryItem item);
        void onDelete(PantryItem item);
    }

    private final List<PantryItem> items;
    private final Listener listener;

    public PantryAdapter(List<PantryItem> items, Listener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PantryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pantry, parent, false);
        return new PantryHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PantryHolder h, int position) {
        PantryItem item = items.get(position);
        h.name.setText(item.getName());
        h.details.setText(String.format(Locale.getDefault(), "%.2f %s",
                item.getQuantity(), item.getUnit()));
        h.expiry.setText(item.getExpiryDate() == null || item.getExpiryDate().isEmpty()
                ? "Expiry: not specified"
                : "Expiry: " + item.getExpiryDate());

        h.edit.setOnClickListener(v -> listener.onEdit(item));
        h.delete.setOnClickListener(v -> listener.onDelete(item));
    }

    @Override
    public int getItemCount() { return items.size(); }

    static class PantryHolder extends RecyclerView.ViewHolder {
        TextView name, details, expiry;
        Button edit, delete;

        PantryHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txtName);
            details = itemView.findViewById(R.id.txtDetails);
            expiry = itemView.findViewById(R.id.txtExpiry);
            edit = itemView.findViewById(R.id.btnEdit);
            delete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
