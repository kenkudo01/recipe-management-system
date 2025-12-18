package com.example.recipeapp.util;

import com.example.recipeapp.model.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * レシピ一覧を指定キーでソートするユーティリティ。
 *
 * 元のリストを破壊しない（コピーして返す）。
 * データ量が比較的小さい想定のため、実装が簡潔な挿入ソートを採用している。
 */
public class RecipeSorter {

    /** ソートに使用するキー */
    public enum SortKey {
        ID,
        NAME,
        CALORIES,
        COOKING_TIME
    }

    /**
     * レシピ一覧を指定キーでソートして返す。
     *
     * @param original 元のレシピ一覧（破壊しない）
     * @param key      ソートキー
     * @param asc      true: 昇順 / false: 降順
     * @return ソート済みの新しいリスト
     */
    public static List<Recipe> sort(List<Recipe> original, SortKey key, boolean asc) {

        // 元リストを破壊しないためコピーして操作する
        List<Recipe> list = new ArrayList<>(original);

        // insertion sort
        for (int i = 1; i < list.size(); i++) {
            Recipe current = list.get(i);
            int j = i - 1;

            while (j >= 0) {
                int cmp = compare(list.get(j), current, key);

                // 昇順: list[j] > current のとき右へシフト
                // 降順: list[j] < current のとき右へシフト
                boolean shouldShift =
                        (asc && cmp > 0) || (!asc && cmp < 0);

                if (!shouldShift) break;

                list.set(j + 1, list.get(j));
                j--;
            }

            list.set(j + 1, current);
        }

        return list;
    }

    /**
     * 2つの Recipe を指定キーで比較する。
     * sort() の内部利用を想定。
     */
    public static int compare(Recipe a, Recipe b, SortKey key) {
        return switch (key) {
            case ID -> Integer.compare(a.getId(), b.getId());
            case NAME -> a.getName().compareTo(b.getName());
            case CALORIES -> Integer.compare(
                    a.getNutrition().getCalories(),
                    b.getNutrition().getCalories()
            );
            case COOKING_TIME -> Integer.compare(
                    a.getCookingTimeMin(),
                    b.getCookingTimeMin()
            );
        };
    }
}
