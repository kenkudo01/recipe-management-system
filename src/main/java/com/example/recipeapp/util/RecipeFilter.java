package com.example.recipeapp.util;

import com.example.recipeapp.model.CategoryType;
import com.example.recipeapp.model.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * レシピ一覧に対して条件フィルタを適用するユーティリティ。
 *
 * 現在はカテゴリによる絞り込みのみを提供する。
 */
public class RecipeFilter {

    /**
     * 指定されたカテゴリを含むレシピのみを返す。
     *
     * @param recipes  フィルタ対象のレシピ一覧
     * @param category 絞り込みに使用するカテゴリ
     * @return 条件に一致したレシピ一覧（新しいリスト）
     */
    public static List<Recipe> filterByCategory(List<Recipe> recipes,
                                                CategoryType category) {

        List<Recipe> result = new ArrayList<>();

        // category 未指定の場合は空結果を返す
        if (category == null) {
            return result;
        }

        for (Recipe r : recipes) {
            if (r.getCategories().contains(category)) {
                result.add(r);
            }
        }

        return result;
    }
}
