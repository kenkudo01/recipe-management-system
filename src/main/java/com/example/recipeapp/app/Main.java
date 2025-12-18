package com.example.recipeapp.app;

import com.example.recipeapp.controller.MenuController;
import com.example.recipeapp.model.Recipe;
import com.example.recipeapp.repository.RecipeLoader;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Recipe App 起動 ===");

        try {
            String path = "src/main/resources/sample_recipes.json";

            List<Recipe> recipes = RecipeLoader.load(path);

            System.out.println("レシピ読み込み完了: " + recipes.size() + "件");

            // CLI メニュー起動
            MenuController controller = new MenuController(recipes);
            controller.start();

        } catch (Exception e) {
            System.out.println("エラー: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
