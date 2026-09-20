package com.example.smartpantry;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SuggestedRecipesActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private TextView noMatches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestions);

        db = new DatabaseHelper(this);
        noMatches = findViewById(R.id.txtNoMatches);

        findViewById(R.id.navPantry).setOnClickListener(v ->
                startActivity(new Intent(this, PantryActivity.class)));
        findViewById(R.id.navRecipes).setOnClickListener(v -> {});
        findViewById(R.id.navSettings).setOnClickListener(v ->
                startActivity(new Intent(this, SettingsActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();

        RecyclerView recycler = findViewById(R.id.recyclerRecipes);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        List<Recipe> recipes = db.getStrictSuggestions();

        noMatches.setVisibility(recipes.isEmpty() ? TextView.VISIBLE : TextView.GONE);

        recycler.setAdapter(new RecipeAdapter(recipes, recipe -> {
            Intent i = new Intent(this, RecipeDetailActivity.class);
            i.putExtra("recipe_id", recipe.getId());
            startActivity(i);
        }));
    }
}
