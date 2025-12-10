package com.example.recipeapp.util;

import com.example.recipeapp.model.Recipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KnapsackSolver {

    public static class Result {
        public List<Integer> selectedIds;
        public int totalProtein;

        public Result(List<Integer> ids, int protein) {
            this.selectedIds = ids;
            this.totalProtein = protein;
        }
    }

    public static Result solve(List<Recipe> recipes, int maxCalories, int maxTime) {

        int n = recipes.size();

        // DPテーブル
        int[][] dp = new int[maxCalories + 1][maxTime + 1];

        // どのレシピを使って更新したかを記録（復元用）
        int[][] choice = new int[maxCalories + 1][maxTime + 1];

        // 0 で初期化（使わなかった状態）
        for (int c = 0; c <= maxCalories; c++) {
            for (int t = 0; t <= maxTime; t++) {
                choice[c][t] = -1;
            }
        }

        // DP本体
        for (int idx = 0; idx < n; idx++) {

            Recipe r = recipes.get(idx);
            int cal = r.getNutrition().getCalories();
            int time = r.getCookingTimeMin();
            int protein = (int)r.getNutrition().getProtein();

            // 逆順ループ（0-1ナップサックの必須）
            for (int c = maxCalories; c >= cal; c--) {
                for (int t = maxTime; t >= time; t--) {

                    int without = dp[c][t];
                    int with = dp[c - cal][t - time] + protein;

                    if (with > without) {
                        dp[c][t] = with;
                        choice[c][t] = idx; // idx のレシピで更新された！
                    }
                }
            }
        }

        // ======== 復元処理 ========

        List<Integer> selected = new ArrayList<>();

        int c = maxCalories;
        int t = maxTime;

        while (c >= 0 && t >= 0) {

            int idx = choice[c][t];

            if (idx == -1) {
                break;
            }

            Recipe r = recipes.get(idx);
            selected.add(r.getId());

            // 上の dp に戻る
            c -= r.getNutrition().getCalories();
            t -= r.getCookingTimeMin();
        }

        Collections.reverse(selected);

        int totalProtein = dp[maxCalories][maxTime];

        return new Result(selected, totalProtein);
    }
}
