package com.example.recipeapp.app;

import com.example.recipeapp.model.Recipe;
import com.example.recipeapp.repository.RecipeLoader;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Recipe App 起動 ===");

        try {
            // JSON ファイルのパス
            String path = "src/main/resources/sample_recipes.json";

            // レシピ読み込み
            List<Recipe> recipes = RecipeLoader.load(path);

            System.out.println("読み込んだレシピ数: " + recipes.size());

            // 動作確認として最初のレシピを表示
            if (!recipes.isEmpty()) {
                Recipe first = recipes.get(0);
                System.out.println("--- 最初のレシピ情報 ---");
                System.out.println("ID: " + first.getId());
                System.out.println("名前: " + first.getName());
                System.out.println("説明: " + first.getDescription());
                System.out.println("調理時間: " + first.getCookingTimeMin() + "分");
                System.out.println("材料数: " + first.getIngredients().size());
            }

        } catch (Exception e) {
            System.out.println("エラー発生: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
