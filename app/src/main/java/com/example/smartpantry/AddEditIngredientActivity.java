package com.example.smartpantry;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddEditIngredientActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private EditText name, quantity, unit, expiry;
    private long itemId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        db = new DatabaseHelper(this);
        name = findViewById(R.id.edtName);
        quantity = findViewById(R.id.edtQuantity);
        unit = findViewById(R.id.edtUnit);
        expiry = findViewById(R.id.edtExpiry);

        TextView title = findViewById(R.id.txtTitle);

        itemId = getIntent().getLongExtra("item_id", -1);

        if (itemId != -1) {
            title.setText("Edit Ingredient");
            PantryItem item = db.getPantryItem(itemId);
            if (item != null) {
                name.setText(item.getName());
                quantity.setText(String.valueOf(item.getQuantity()));
                unit.setText(item.getUnit());
                expiry.setText(item.getExpiryDate());
            }
        }

        findViewById(R.id.btnSave).setOnClickListener(v -> save());
        findViewById(R.id.btnCancel).setOnClickListener(v -> finish());
    }

    private void save() {
        String n = name.getText().toString().trim();
        String q = quantity.getText().toString().trim();
        String u = unit.getText().toString().trim();
        String e = expiry.getText().toString().trim();

        if (n.isEmpty()) {
            name.setError("Ingredient name is required");
            name.requestFocus();
            return;
        }

        if (q.isEmpty()) {
            quantity.setError("Quantity is required");
            quantity.requestFocus();
            return;
        }

        if (u.isEmpty()) {
            unit.setError("Unit is required");
            unit.requestFocus();
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(q);
        } catch (NumberFormatException ex) {
            quantity.setError("Enter a valid number");
            quantity.requestFocus();
            return;
        }

        if (amount <= 0) {
            quantity.setError("Quantity must be greater than zero");
            quantity.requestFocus();
            return;
        }

        if (itemId == -1) {
            db.insertPantry(n, amount, u, e);
            Toast.makeText(this, "Ingredient added", Toast.LENGTH_SHORT).show();
        } else {
            db.updatePantry(itemId, n, amount, u, e);
            Toast.makeText(this, "Ingredient updated", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}
