package com.example.smartpantry;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RecipeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        DatabaseHelper db = new DatabaseHelper(this);
        long id = getIntent().getLongExtra("recipe_id", -1);
        Recipe recipe = db.getRecipe(id);

        TextView title = findViewById(R.id.txtRecipeTitle);
        TextView ingredients = findViewById(R.id.txtIngredients);
        TextView method = findViewById(R.id.txtMethod);

        if (recipe != null) {
            title.setText(recipe.getName());

            StringBuilder ingredientText = new StringBuilder("Ingredients\n\n");
            for (String item : recipe.ingredientList()) {
                ingredientText.append("• ").append(item).append("\n");
            }

            ingredients.setText(ingredientText.toString());
            method.setText("Method\n\n" + recipe.getMethod());
        }

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
    }
}
