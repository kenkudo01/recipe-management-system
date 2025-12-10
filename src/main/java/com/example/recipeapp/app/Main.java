package com.example.recipeapp.app;

import com.example.recipeapp.model.Recipe;
import com.example.recipeapp.repository.RecipeLoader;
import com.example.recipeapp.util.KnapsackSolver;
import com.example.recipeapp.util.RecipeSorter;

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
                Recipe first = recipes.get(2);
                System.out.println("--- 最初のレシピ情報 ---");
                System.out.println("ID: " + first.getId());
                System.out.println("名前: " + first.getName());
                System.out.println("説明: " + first.getDescription());
                System.out.println("調理時間: " + first.getCookingTimeMin() + "分");
                System.out.println("材料数: " + first.getIngredients().size());
            }


            System.out.println("=== SORT ===");

            List<Recipe> sorted = RecipeSorter.sort(
                 recipes,
                 RecipeSorter.SortKey.CALORIES,
                 true
            );

            System.out.println("名前: " + sorted.get(0).getName());
            System.out.println("最小カロリー: " + sorted.get(0).getNutrition().getCalories());


            System.out.println("=== Knapsack ===");
            KnapsackSolver.Result res = KnapsackSolver.solve(recipes, 700, 45);

            System.out.println("最適なレシピID: " + res.selectedIds);

            for (int id : res.selectedIds) {
                Recipe r = findById(recipes, id);
                if (r != null) {
                    System.out.println("名前: " + r.getName());
                }
            }

            System.out.println("合計タンパク質: " + res.totalProtein);


        } catch (Exception e) {
            System.out.println("エラー発生: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static Recipe findById(List<Recipe> recipes, int id) {
        for (Recipe r : recipes) {
            if (r.getId() == id) {
                return r;
            }
        }
        return null;
    }

}
