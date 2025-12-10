package com.example.recipeapp.util;

import com.example.recipeapp.model.CategoryType;
import com.example.recipeapp.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeFilter {

    public static List<Recipe> filterByCategory(List<Recipe> recipes, CategoryType category) {

        List<Recipe> result = new ArrayList<>();

        for (Recipe r : recipes) {
            if (r.getCategories().contains(category)) {
                result.add(r);
            }
        }

        return result;
    }
}
