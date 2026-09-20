package com.example.smartpantry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "smart_pantry.db";
    private static final int DB_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE pantry_items (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "quantity REAL NOT NULL," +
                "unit TEXT NOT NULL," +
                "expiry_date TEXT)");

        db.execSQL("CREATE TABLE recipes (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "ingredients TEXT NOT NULL," +
                "method TEXT NOT NULL)");

        seedRecipes(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS pantry_items");
        db.execSQL("DROP TABLE IF EXISTS recipes");

        onCreate(db);
    }

    private void seedRecipes(SQLiteDatabase db) {

        addRecipe(
                db,
                "Tomato Egg Scramble",
                "egg,tomato,onion,salt",
                "Chop the tomato and onion. Fry briefly, add beaten eggs, season with salt and cook until set."
        );

        addRecipe(
                db,
                "Cheese Omelette",
                "egg,cheese,salt,pepper",
                "Beat the eggs with salt and pepper. Cook in a pan and add cheese before folding."
        );

        addRecipe(
                db,
                "Chicken Rice",
                "chicken,rice,onion,garlic,salt",
                "Cook rice. Fry onion, garlic and chicken until cooked, then combine with the rice."
        );

        addRecipe(
                db,
                "Vegetable Rice",
                "rice,carrot,peas,onion,salt",
                "Cook rice. Fry vegetables and onion, then mix everything together and season."
        );

        addRecipe(
                db,
                "Tuna Sandwich",
                "bread,tuna,mayonnaise,onion",
                "Mix tuna, mayonnaise and chopped onion. Spread onto bread and serve."
        );

        addRecipe(
                db,
                "Egg Sandwich",
                "bread,egg,mayonnaise,salt",
                "Boil eggs, chop them and mix with mayonnaise and salt. Spread on bread."
        );

        addRecipe(
                db,
                "Chicken Pasta",
                "pasta,chicken,tomato,onion,garlic",
                "Cook pasta. Fry chicken with onion and garlic, add tomato and combine with pasta."
        );

        addRecipe(
                db,
                "Garlic Tomato Pasta",
                "pasta,tomato,garlic,olive oil,salt",
                "Cook pasta. Fry garlic in olive oil, add chopped tomato and salt, then mix with pasta."
        );

        addRecipe(
                db,
                "Potato Omelette",
                "potato,egg,onion,salt",
                "Cook sliced potato and onion. Add beaten egg and cook until firm on both sides."
        );

        addRecipe(
                db,
                "Chicken Salad",
                "chicken,lettuce,tomato,onion,salt",
                "Cook and slice chicken. Combine with lettuce, tomato and onion. Season lightly."
        );

        addRecipe(
                db,
                "Tomato Toast",
                "bread,tomato,cheese,salt",
                "Place tomato and cheese on bread, season, and toast until the cheese melts."
        );

        addRecipe(
                db,
                "Pancakes",
                "flour,egg,milk,sugar,salt",
                "Mix ingredients into a smooth batter. Cook small portions in a lightly greased pan."
        );

        addRecipe(
                db,
                "French Toast",
                "bread,egg,milk,sugar",
                "Whisk egg, milk and sugar. Dip bread and fry on both sides until golden."
        );

        addRecipe(
                db,
                "Creamy Chicken Pasta",
                "pasta,chicken,milk,garlic,salt",
                "Cook pasta. Cook chicken and garlic, add milk, then combine with pasta and season."
        );

        addRecipe(
                db,
                "Bean Rice Bowl",
                "rice,beans,tomato,onion,salt",
                "Cook rice. Warm beans with tomato and onion, season, and serve over rice."
        );

        addRecipe(
                db,
                "Cheese Pasta",
                "pasta,cheese,milk,salt",
                "Cook pasta. Warm milk and cheese together, season, and mix with the cooked pasta."
        );

        addRecipe(
                db,
                "Vegetable Omelette",
                "egg,tomato,onion,carrot,salt",
                "Beat eggs. Cook chopped vegetables, pour in eggs, season, and cook until firm."
        );

        addRecipe(
                db,
                "Chicken Tomato Stew",
                "chicken,tomato,onion,garlic,salt",
                "Brown chicken. Add onion and garlic, then tomato and seasoning. Simmer until cooked."
        );

        addRecipe(
                db,
                "Peanut Banana Toast",
                "bread,banana,peanut butter",
                "Toast bread and spread with peanut butter. Add sliced banana and serve."
        );

        addRecipe(
                db,
                "Banana Pancakes",
                "banana,egg,flour,milk",
                "Mash banana, mix with egg, flour and milk, then cook small pancakes in a pan."
        );
    }

    private void addRecipe(
            SQLiteDatabase db,
            String name,
            String ingredients,
            String method) {

        ContentValues values = new ContentValues();

        values.put("name", name);
        values.put("ingredients", ingredients);
        values.put("method", method);

        db.insert("recipes", null, values);
    }

    // CREATE - Add a pantry item
    public long insertPantry(
            String name,
            double quantity,
            String unit,
            String expiry) {

        ContentValues values = new ContentValues();

        values.put("name", normalize(name));
        values.put("quantity", quantity);
        values.put("unit", normalize(unit));
        values.put("expiry_date", expiry);

        return getWritableDatabase().insert(
                "pantry_items",
                null,
                values
        );
    }

    // UPDATE - Edit a pantry item
    public int updatePantry(
            long id,
            String name,
            double quantity,
            String unit,
            String expiry) {

        ContentValues values = new ContentValues();

        values.put("name", normalize(name));
        values.put("quantity", quantity);
        values.put("unit", normalize(unit));
        values.put("expiry_date", expiry);

        return getWritableDatabase().update(
                "pantry_items",
                values,
                "id=?",
                new String[]{String.valueOf(id)}
        );
    }

    // DELETE - Remove a pantry item
    public int deletePantry(long id) {

        return getWritableDatabase().delete(
                "pantry_items",
                "id=?",
                new String[]{String.valueOf(id)}
        );
    }

    // READ - Get one pantry item
    public PantryItem getPantryItem(long id) {

        Cursor cursor = getReadableDatabase().query(
                "pantry_items",
                null,
                "id=?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null
        );

        try {

            if (cursor.moveToFirst()) {

                return new PantryItem(
                        cursor.getLong(
                                cursor.getColumnIndexOrThrow("id")
                        ),

                        cursor.getString(
                                cursor.getColumnIndexOrThrow("name")
                        ),

                        cursor.getDouble(
                                cursor.getColumnIndexOrThrow("quantity")
                        ),

                        cursor.getString(
                                cursor.getColumnIndexOrThrow("unit")
                        ),

                        cursor.getString(
                                cursor.getColumnIndexOrThrow("expiry_date")
                        )
                );
            }

            return null;

        } finally {

            cursor.close();
        }
    }

    // READ - Get all pantry items
    public List<PantryItem> getAllPantryItems() {

        List<PantryItem> items = new ArrayList<>();

        Cursor cursor = getReadableDatabase().query(
                "pantry_items",
                null,
                null,
                null,
                null,
                null,
                "name ASC"
        );

        try {

            while (cursor.moveToNext()) {

                items.add(
                        new PantryItem(
                                cursor.getLong(
                                        cursor.getColumnIndexOrThrow("id")
                                ),

                                cursor.getString(
                                        cursor.getColumnIndexOrThrow("name")
                                ),

                                cursor.getDouble(
                                        cursor.getColumnIndexOrThrow("quantity")
                                ),

                                cursor.getString(
                                        cursor.getColumnIndexOrThrow("unit")
                                ),

                                cursor.getString(
                                        cursor.getColumnIndexOrThrow("expiry_date")
                                )
                        )
                );
            }

        } finally {

            cursor.close();
        }

        return items;
    }

    // Get recipes that strictly match the pantry
    public List<Recipe> getStrictSuggestions() {

        List<Recipe> matches = new ArrayList<>();

        List<PantryItem> pantry = getAllPantryItems();

        Cursor cursor = getReadableDatabase().query(
                "recipes",
                null,
                null,
                null,
                null,
                null,
                "name ASC"
        );

        try {

            while (cursor.moveToNext()) {

                Recipe recipe = new Recipe(

                        cursor.getLong(
                                cursor.getColumnIndexOrThrow("id")
                        ),

                        cursor.getString(
                                cursor.getColumnIndexOrThrow("name")
                        ),

                        cursor.getString(
                                cursor.getColumnIndexOrThrow("ingredients")
                        ),

                        cursor.getString(
                                cursor.getColumnIndexOrThrow("method")
                        )
                );

                if (canMakeRecipe(recipe, pantry)) {

                    matches.add(recipe);
                }
            }

        } finally {

            cursor.close();
        }

        return matches;
    }

    /*
     * STRICT MATCHING RULE
     *
     * A recipe is suggested only when:
     *
     * 1. Every required ingredient exists in the pantry.
     * 2. The pantry quantity is at least 1.
     *
     * If even one ingredient is missing,
     * the recipe is NOT suggested.
     */

    private boolean canMakeRecipe(
            Recipe recipe,
            List<PantryItem> pantry) {

        for (String required : recipe.ingredientList()) {

            String requiredName = normalize(required);

            PantryItem found = findPantryItem(
                    pantry,
                    requiredName
            );

            if (found == null) {

                return false;
            }

            if (found.getQuantity() < 1.0) {

                return false;
            }
        }

        return true;
    }

    // Find a matching pantry ingredient
    private PantryItem findPantryItem(
            List<PantryItem> pantry,
            String required) {

        for (PantryItem item : pantry) {

            String pantryName = normalize(
                    item.getName()
            );

            if (pantryName.equals(required)) {

                return item;
            }
        }

        return null;
    }

    // READ - Get a recipe by ID
    public Recipe getRecipe(long id) {

        Cursor cursor = getReadableDatabase().query(
                "recipes",
                null,
                "id=?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null
        );

        try {

            if (cursor.moveToFirst()) {

                return new Recipe(

                        cursor.getLong(
                                cursor.getColumnIndexOrThrow("id")
                        ),

                        cursor.getString(
                                cursor.getColumnIndexOrThrow("name")
                        ),

                        cursor.getString(
                                cursor.getColumnIndexOrThrow("ingredients")
                        ),

                        cursor.getString(
                                cursor.getColumnIndexOrThrow("method")
                        )
                );
            }

            return null;

        } finally {

            cursor.close();
        }
    }

    /*
     * NORMALIZATION
     *
     * Makes simple ingredient differences easier to match.
     *
     * Example:
     * tomato  -> tomato
     * tomatoes -> tomato
     * eggs -> egg
     */

    public static String normalize(String value) {

        if (value == null) {

            return "";
        }

        String text = value
                .trim()
                .toLowerCase(Locale.ROOT);

        if (text.endsWith("ies")) {

            text = text.substring(
                    0,
                    text.length() - 3
            ) + "y";

        } else if (text.endsWith("oes")) {

            text = text.substring(
                    0,
                    text.length() - 2
            );

        } else if (
                text.endsWith("s")
                        && !text.endsWith("ss")) {

            text = text.substring(
                    0,
                    text.length() - 1
            );
        }

        return text;
    }
}