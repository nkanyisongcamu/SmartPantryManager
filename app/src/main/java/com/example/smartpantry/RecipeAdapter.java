package com.example.smartpantry;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeHolder> {

    public interface Listener {
        void onRecipeClick(Recipe recipe);
    }

    private final List<Recipe> recipes;
    private final Listener listener;

    public RecipeAdapter(List<Recipe> recipes, Listener listener) {
        this.recipes = recipes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recipe, parent, false);
        return new RecipeHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeHolder h, int position) {
        Recipe recipe = recipes.get(position);
        h.name.setText(recipe.getName());
        h.info.setText(recipe.ingredientList().size() + " ingredients - tap to view method");
        h.itemView.setOnClickListener(v -> listener.onRecipeClick(recipe));
    }

    @Override
    public int getItemCount() { return recipes.size(); }

    static class RecipeHolder extends RecyclerView.ViewHolder {
        TextView name, info;

        RecipeHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txtRecipeName);
            info = itemView.findViewById(R.id.txtRecipeInfo);
        }
    }
}
