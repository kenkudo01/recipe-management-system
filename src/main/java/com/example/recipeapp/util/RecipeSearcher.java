package com.example.recipeapp.util;

import com.example.recipeapp.model.Recipe;
import java.util.ArrayList;
import java.util.List;

/**
 * レシピ一覧から条件に一致するものを検索するユーティリティ。
 *
 * 現在はレシピ名による部分一致検索のみを提供する。
 * UI 側での簡易検索用途を想定。
 */
public class RecipeSearcher {

    /**
     * レシピ名にキーワードが部分一致するものを返す。
     *
     * @param recipes 検索対象のレシピ一覧
     * @param keyword 検索キーワード
     * @return 条件に一致したレシピ一覧（新しいリスト）
     */
    public static List<Recipe> searchByName(List<Recipe> recipes, String keyword) {

        List<Recipe> result = new ArrayList<>();

        // 空キーワードの場合は空結果を返す
        if (keyword == null || keyword.isBlank()) {
            return result;
        }

        for (Recipe r : recipes) {
            if (r.getName().contains(keyword)) {
                result.add(r);
            }
        }

        return result;
    }
}
