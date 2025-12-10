package com.example.recipeapp.app;

import com.example.recipeapp.model.CategoryType;
import com.example.recipeapp.model.Recipe;
import java.util.List;
import java.util.Scanner;

public class MenuController {

    private final List<Recipe> recipes;
    private final Scanner scanner = new Scanner(System.in);

    public MenuController(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public void start() {
        while (true) {
            printMenu();
            System.out.print("> ");
            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    showAllRecipes();
                    break;
                case "2":
                    searchByName();
                    break;
                case "3":
                    filterByCategory();
                    break;
                case "4":
                    sortMenu();
                    break;
                case "5":
                    suggestMenu();
                    break;
                case "0":
                    System.out.println("終了します。");
                    return;
                default:
                    System.out.println("無効な入力です。もう一度入力してください。");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n=== Recipe Explorer ===");
        System.out.println("1. レシピ一覧を見る");
        System.out.println("2. 名前で検索する");
        System.out.println("3. カテゴリで絞り込む");
        System.out.println("4. ソートする");
        System.out.println("5. 献立提案（ナップサック）");
        System.out.println("0. 終了");
    }

    private void showAllRecipes() {
        System.out.println("\n=== レシピ一覧 ===");
        RecipePrinter.printList(recipes);
    }

    // --- ここから先は Step2 以降で実装 ---
    private void searchByName() {
        System.out.print("検索ワードを入力してください: ");
        String keyword = scanner.nextLine().trim();

        List<Recipe> result = com.example.recipeapp.util.RecipeSearcher.searchByName(recipes, keyword);

        System.out.println("\n=== 検索結果 ===");
        RecipePrinter.printList(result);
    }


    private void filterByCategory() {
        System.out.println("\n=== カテゴリ一覧 ===");
        for (CategoryType type : CategoryType.values()) {
            System.out.println("- " + type);
        }

        System.out.print("カテゴリ名を入力してください: ");
        String input = scanner.nextLine().trim().toUpperCase();

        CategoryType category = null;

        try {
            category = CategoryType.valueOf(input);
        } catch (Exception e) {
            System.out.println("無効なカテゴリ名です。再度選択してください。");
            return;
        }

        // フィルタ処理
        List<Recipe> filtered = com.example.recipeapp.util.RecipeFilter.filterByCategory(recipes, category);

        System.out.println("\n=== フィルタ結果 ===");
        RecipePrinter.printList(filtered);
    }


    private void sortMenu() {
        System.out.println("[ソート] 未実装です（Step4）");
    }

    private void suggestMenu() {
        System.out.println("[献立提案] 未実装です（Step5）");
    }
}
