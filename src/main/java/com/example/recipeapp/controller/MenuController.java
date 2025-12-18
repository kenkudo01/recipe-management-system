package com.example.recipeapp.controller;

import com.example.recipeapp.app.RecipePrinter;
import com.example.recipeapp.model.CategoryType;
import com.example.recipeapp.model.Recipe;
import com.example.recipeapp.util.KnapsackSolver;
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
        pause();
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
        System.out.println("\n=== カテゴリ絞り込み ===");
        System.out.println("カテゴリ一覧:");

        for (CategoryType type : CategoryType.values()) {
            System.out.println(" - " + type);
        }
        System.out.println("0. 戻る");

        System.out.print("カテゴリ名（または 0）を入力してください: ");
        String input = scanner.nextLine().trim().toUpperCase();

        if (input.equals("0")) {
            System.out.println("メニューに戻ります。");
            pause();
            return;
        }

        CategoryType category;
        try {
            category = CategoryType.valueOf(input);
        } catch (Exception e) {
            System.out.println("無効なカテゴリ名です。");
            pause();
            return;
        }

        List<Recipe> filtered =
                com.example.recipeapp.util.RecipeFilter.filterByCategory(recipes, category);

        System.out.println("\n=== 絞り込み結果 ===");
        RecipePrinter.printList(filtered);
        pause();
    }



    private void sortMenu() {
        System.out.println("\n=== ソートメニュー ===");
        System.out.println("1. ID");
        System.out.println("2. 名前");
        System.out.println("3. カロリー");
        System.out.println("4. 調理時間");
        System.out.println("0. 戻る");

        System.out.print("ソートキーを選択してください: ");
        String keyInput = scanner.nextLine().trim();

        if (keyInput.equals("0")) {
            pause();
            return;
        }

        RecipeSorter.SortKey key;
        switch (keyInput) {
            case "1": key = RecipeSorter.SortKey.ID; break;
            case "2": key = RecipeSorter.SortKey.NAME; break;
            case "3": key = RecipeSorter.SortKey.CALORIES; break;
            case "4": key = RecipeSorter.SortKey.COOKING_TIME; break;
            default:
                System.out.println("無効な入力です。");
                pause();
                return;
        }

        System.out.println("1. 昇順");
        System.out.println("2. 降順");
        System.out.println("0. 戻る");
        System.out.print("並び順を選択してください: ");

        String orderInput = scanner.nextLine().trim();

        if (orderInput.equals("0")) {
            pause();
            return;
        }

        boolean asc;
        switch (orderInput) {
            case "1": asc = true; break;
            case "2": asc = false; break;
            default:
                System.out.println("無効な入力です。");
                pause();
                return;
        }

        List<Recipe> sorted = RecipeSorter.sort(recipes, key, asc);

        System.out.println("\n=== ソート結果 ===");
        RecipePrinter.printList(sorted);
        pause();
    }

    private void suggestMenu() {
        System.out.println("\n=== 献立提案（ナップサック） ===");
        System.out.println("0 を入力するとキャンセルします。");

        try {
            System.out.print("カロリー上限を入力してください: ");
            String calInput = scanner.nextLine().trim();
            if (calInput.equals("0")) { pause(); return; }
            int maxCal = Integer.parseInt(calInput);

            System.out.print("調理時間上限を入力してください: ");
            String timeInput = scanner.nextLine().trim();
            if (timeInput.equals("0")) { pause(); return; }
            int maxTime = Integer.parseInt(timeInput);

            KnapsackSolver.Result result =
                    com.example.recipeapp.util.KnapsackSolver.solve(recipes, maxCal, maxTime);

            System.out.println("\n=== 献立提案 ===");

            if (result.selectedIds.isEmpty()) {
                System.out.println("条件に合う献立は見つかりませんでした。");
                pause();
                return;
            }

            for (Integer id : result.selectedIds) {
                recipes.stream()
                        .filter(r -> r.getId() == id)
                        .findFirst()
                        .ifPresent(r -> RecipePrinter.printList(List.of(r)));
            }

            System.out.println("\n合計タンパク質: " + result.totalProtein + "g\n");

        } catch (NumberFormatException e) {
            System.out.println("数字を入力してください。");
        }

        pause();
    }


    private void pause() {
        System.out.println("\nEnter を押してメニューに戻ります...");
        scanner.nextLine();
    }


}
