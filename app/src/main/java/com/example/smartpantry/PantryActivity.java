package com.example.smartpantry;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PantryActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private PantryAdapter adapter;
    private List<PantryItem> items;
    private TextView empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry);

        db = new DatabaseHelper(this);
        empty = findViewById(R.id.txtEmpty);

        RecyclerView recycler = findViewById(R.id.recyclerPantry);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        findViewById(R.id.btnAdd).setOnClickListener(v ->
                startActivity(new Intent(this, AddEditIngredientActivity.class)));

        findViewById(R.id.navPantry).setOnClickListener(v -> {});
        findViewById(R.id.navRecipes).setOnClickListener(v ->
                startActivity(new Intent(this, SuggestedRecipesActivity.class)));
        findViewById(R.id.navSettings).setOnClickListener(v ->
                startActivity(new Intent(this, SettingsActivity.class)));

        loadPantry(recycler);
    }

    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView recycler = findViewById(R.id.recyclerPantry);
        loadPantry(recycler);
    }

    private void loadPantry(RecyclerView recycler) {
        items = db.getAllPantryItems();

        adapter = new PantryAdapter(items, new PantryAdapter.Listener() {
            @Override
            public void onEdit(PantryItem item) {
                Intent i = new Intent(PantryActivity.this, AddEditIngredientActivity.class);
                i.putExtra("item_id", item.getId());
                startActivity(i);
            }

            @Override
            public void onDelete(PantryItem item) {
                new AlertDialog.Builder(PantryActivity.this)
                        .setTitle("Delete ingredient")
                        .setMessage("Delete " + item.getName() + "?")
                        .setPositiveButton("Delete", (dialog, which) -> {
                            db.deletePantry(item.getId());
                            loadPantry(recycler);
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });

        recycler.setAdapter(adapter);
        empty.setVisibility(items.isEmpty() ? TextView.VISIBLE : TextView.GONE);
    }
}
