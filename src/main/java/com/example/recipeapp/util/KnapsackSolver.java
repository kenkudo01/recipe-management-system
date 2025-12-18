package com.example.recipeapp.util;

import com.example.recipeapp.model.Recipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * レシピ選択問題を 0-1 ナップサック問題として解くユーティリティ。
 *
 * 制限カロリー・制限調理時間の範囲内で、
 * 合計タンパク質量が最大となるレシピの組み合わせを求める。
 */
public class KnapsackSolver {

    /**
     * ナップサック問題の解を保持する結果クラス。
     */
    public static class Result {

        /** 選択されたレシピの ID 一覧 */
        public List<Integer> selectedIds;

        /** 達成された合計タンパク質量 */
        public int totalProtein;

        public Result(List<Integer> ids, int protein) {
            this.selectedIds = ids;
            this.totalProtein = protein;
        }
    }

    /**
     * 0-1 ナップサック DP により最適なレシピ組み合わせを求める。
     *
     * @param recipes     候補となるレシピ一覧
     * @param maxCalories 許容カロリー上限
     * @param maxTime     許容調理時間上限
     * @return 最適解（選択されたレシピ ID と合計タンパク質量）
     */
    public static Result solve(List<Recipe> recipes,
                               int maxCalories,
                               int maxTime) {

        int n = recipes.size();

        // dp[c][t] : カロリー c・時間 t 以内で得られる最大タンパク質量
        int[][] dp = new int[maxCalories + 1][maxTime + 1];

        // choice[c][t] : dp[c][t] を更新したレシピの index（復元用）
        int[][] choice = new int[maxCalories + 1][maxTime + 1];

        // 初期化（何も選ばれていない状態）
        for (int c = 0; c <= maxCalories; c++) {
            for (int t = 0; t <= maxTime; t++) {
                choice[c][t] = -1;
            }
        }

        // ===== DP 本体（0-1 ナップサック） =====
        for (int idx = 0; idx < n; idx++) {

            Recipe r = recipes.get(idx);
            int cal = r.getNutrition().getCalories();
            int time = r.getCookingTimeMin();
            int protein = (int) r.getNutrition().getProtein();

            // 逆順ループ：同じレシピを複数回使わないため
            for (int c = maxCalories; c >= cal; c--) {
                for (int t = maxTime; t >= time; t--) {

                    int without = dp[c][t];
                    int with = dp[c - cal][t - time] + protein;

                    if (with > without) {
                        dp[c][t] = with;
                        choice[c][t] = idx;
                    }
                }
            }
        }

        // ===== 解の復元 =====
        List<Integer> selected = new ArrayList<>();

        int c = maxCalories;
        int t = maxTime;

        while (c >= 0 && t >= 0) {
            int idx = choice[c][t];
            if (idx == -1) break;

            Recipe r = recipes.get(idx);
            selected.add(r.getId());

            // 直前の状態へ戻る
            c -= r.getNutrition().getCalories();
            t -= r.getCookingTimeMin();
        }

        Collections.reverse(selected);

        int totalProtein = dp[maxCalories][maxTime];

        return new Result(selected, totalProtein);
    }
}
