package com.example.recipeapp.util;

import com.example.recipeapp.model.Recipe;
import com.example.recipeapp.model.Nutrition;

import java.util.ArrayList;
import java.util.List;


// sort(List<Recipe>, SortKey, boolean asc)
//  ├─ List をコピー（オリジナルを破壊しない）
//  ├─ 挿入ソート本体
//  ├─ compare(Recipe a, Recipe b, SortKey key)
//  └─ return sorted list


public class RecipeSorter {

    public enum SortKey {
        ID,
        NAME,
        CALORIES,
        COOKING_TIME
    }

    public static List<Recipe> sort(List<Recipe> original, SortKey key, boolean asc) {

        // 元を壊さないようにコピー
        List<Recipe> list = new ArrayList<>(original);

        for (int i = 1; i < list.size(); i++) {

            Recipe current = list.get(i);
            int j = i - 1;

            while (j >= 0) {
                int cmp = compare(list.get(j), current, key);

                // 昇順: list[j] > current のとき交換
                // 降順: list[j] < current のとき交換
                boolean shouldSwap =
                        (asc && cmp > 0) || (!asc && cmp < 0);

                if (!shouldSwap) break;

                list.set(j + 1, list.get(j)); // 1つ右にずらす
                j--;
            }

            list.set(j + 1, current);
        }

        return list;
    }

    private static int compare(Recipe a, Recipe b, SortKey key) {
        switch (key) {
            case ID:
                return Integer.compare(a.getId(), b.getId());

            case NAME:
                return a.getName().compareTo(b.getName());

            case CALORIES:
                return Integer.compare(
                        a.getNutrition().getCalories(),
                        b.getNutrition().getCalories()
                );

            case COOKING_TIME:
                return Integer.compare(
                        a.getCookingTimeMin(),
                        b.getCookingTimeMin()
                );

            default:
                return 0;
        }
    }
}
