package com.example.recipeapp.util;

import com.example.recipeapp.model.Recipe;
import java.util.ArrayList;
import java.util.List;

public class RecipeSearcher {

    /**
     * レシピ名に部分一致するものを返す
     */
    public static List<Recipe> searchByName(List<Recipe> recipes, String keyword) {

        List<Recipe> result = new ArrayList<>();

        if (keyword == null || keyword.isBlank()) {
            return result;
        }

        for (Recipe r : recipes) {
            if (r.getName().contains(keyword)) {
                result.add(r);
            }
        }

        return result;
    }
}
