package com.example.recipeapp.repository;

import com.example.recipeapp.model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class RecipeLoader {

    public static List<Recipe> load(String path) throws Exception {

        String jsonText = Files.readString(Paths.get(path));
        JSONArray arr = new JSONArray(jsonText);

        List<Recipe> recipes = new ArrayList<>();

        for (int i = 0; i < arr.length(); i++) {
            JSONObject obj = arr.getJSONObject(i);

            int id = obj.getInt("id");
            String name = obj.getString("name");
            String description = obj.getString("description");
            int servings = obj.getInt("servings");
            int cookingTime = obj.getInt("cookingTimeMin");

            // ------ Ingredients ------
            List<Ingredient> ingredients = new ArrayList<>();
            JSONArray ingArr = obj.getJSONArray("ingredients");
            for (int j = 0; j < ingArr.length(); j++) {
                JSONObject ingObj = ingArr.getJSONObject(j);

                String ingName = ingObj.getString("name");
                JSONObject amountObj = ingObj.getJSONObject("amount");

                Double value = amountObj.isNull("value") ? null : amountObj.getDouble("value");
                String unit = amountObj.isNull("unit") ? null : amountObj.getString("unit");
                String raw = amountObj.getString("raw");

                IngredientAmount amount = new IngredientAmount(value, unit, raw);
                ingredients.add(new Ingredient(ingName, amount));
            }

            // ------ Steps ------
            List<String> steps = new ArrayList<>();
            JSONArray stepArr = obj.getJSONArray("steps");
            for (int j = 0; j < stepArr.length(); j++) {
                steps.add(stepArr.getString(j));
            }

            // ------ Categories ------
            List<CategoryType> categories = new ArrayList<>();
            JSONArray catArr = obj.getJSONArray("categories");
            for (int j = 0; j < catArr.length(); j++) {
                categories.add(CategoryType.valueOf(catArr.getString(j)));
            }

            // ------ Nutrition ------
            JSONObject nutObj = obj.getJSONObject("nutrition");
            Nutrition nutrition = new Nutrition(
                    nutObj.getInt("calories"),
                    nutObj.getDouble("protein"),
                    nutObj.getDouble("fat"),
                    nutObj.getDouble("carbs")
            );

            Recipe recipe = new Recipe(
                    id, name, description, servings, cookingTime,
                    ingredients, steps, categories, nutrition
            );

            recipes.add(recipe);
        }

        return recipes;
    }
}
