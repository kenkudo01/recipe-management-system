package com.example.recipeapp.app;

import com.example.recipeapp.model.Recipe;

import java.util.List;

public class RecipePrinter {

    public static void printList(List<Recipe> recipes) {
        if (recipes.isEmpty()) {
            System.out.println("レシピがありません。");
            return;
        }

        for (Recipe r : recipes) {
            System.out.printf(
                    "ID: %d | %s | %d分 | %dkcal\n",
                    r.getId(),
                    r.getName(),
                    r.getCookingTimeMin(),
                    r.getNutrition().getCalories()
            );
        }
    }
}
