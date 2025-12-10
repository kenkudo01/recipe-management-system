package com.example.recipeapp.app;

import com.example.recipeapp.model.CategoryType;
import com.example.recipeapp.model.Recipe;
import com.example.recipeapp.util.RecipeSorter;

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
        System.out.println("\n=== ソートメニュー ===");
        System.out.println("1. ID");
        System.out.println("2. 名前");
        System.out.println("3. カロリー");
        System.out.println("4. 調理時間");

        System.out.print("ソートキーを選択してください: ");
        String keyInput = scanner.nextLine().trim();

        RecipeSorter.SortKey key;

        switch (keyInput) {
            case "1": key = RecipeSorter.SortKey.ID; break;
            case "2": key = RecipeSorter.SortKey.NAME; break;
            case "3": key = RecipeSorter.SortKey.CALORIES; break;
            case "4": key = RecipeSorter.SortKey.COOKING_TIME; break;
            default:
                System.out.println("無効な入力です。メニューに戻ります。");
                return;
        }

        System.out.println("1. 昇順");
        System.out.println("2. 降順");
        System.out.print("並び順を選択してください: ");
        String orderInput = scanner.nextLine().trim();

        boolean asc;

        switch (orderInput) {
            case "1": asc = true; break;
            case "2": asc = false; break;
            default:
                System.out.println("無効な入力です。メニューに戻ります。");
                return;
        }

        // ソート処理
        List<Recipe> sorted = RecipeSorter.sort(recipes, key, asc);

        System.out.println("\n=== ソート結果 ===");
        RecipePrinter.printList(sorted);
    }


    private void suggestMenu() {
        System.out.println("[献立提案] 未実装です（Step5）");
    }
}
