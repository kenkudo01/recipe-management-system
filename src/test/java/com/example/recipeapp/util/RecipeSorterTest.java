package com.example.recipeapp.util;

import com.example.recipeapp.model.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RecipeSorterTest {

    // ---------- compare() 単体テスト ----------

    @Test
    void testCompareId() {
        Recipe r1 = new Recipe(
                1, "A", "", 1, 10,
                List.of(), List.of(), List.of(CategoryType.OTHER),
                new Nutrition(100, 10, 10, 10)
        );

        Recipe r2 = new Recipe(
                2, "B", "", 1, 10,
                List.of(), List.of(), List.of(CategoryType.OTHER),
                new Nutrition(200, 20, 20, 20)
        );

        assertTrue(RecipeSorter.compare(r1, r2, RecipeSorter.SortKey.ID) < 0);
        assertTrue(RecipeSorter.compare(r2, r1, RecipeSorter.SortKey.ID) > 0);
    }

    @Test
    void testCompareName() {
        Recipe curry = new Recipe(
                1, "カレー", "", 1, 10,
                List.of(), List.of(), List.of(CategoryType.OTHER),
                new Nutrition(100, 5, 5, 5)
        );

        Recipe salad = new Recipe(
                2, "サラダ", "", 1, 10,
                List.of(), List.of(), List.of(CategoryType.OTHER),
                new Nutrition(200, 10, 10, 10)
        );

        int cmp = RecipeSorter.compare(curry, salad, RecipeSorter.SortKey.NAME);

        // "カレー" と "サラダ" の Unicode 順比較結果（実行時に確認）
        assertNotEquals(0, cmp);
    }

    @Test
    void testCompareCalories() {
        Recipe low = new Recipe(
                1, "Low", "", 1, 10,
                List.of(), List.of(), List.of(CategoryType.OTHER),
                new Nutrition(100, 10, 10, 10)
        );

        Recipe high = new Recipe(
                2, "High", "", 1, 10,
                List.of(), List.of(), List.of(CategoryType.OTHER),
                new Nutrition(300, 20, 20, 20)
        );

        assertTrue(RecipeSorter.compare(low, high, RecipeSorter.SortKey.CALORIES) < 0);
        assertTrue(RecipeSorter.compare(high, low, RecipeSorter.SortKey.CALORIES) > 0);
    }

    @Test
    void testCompareCookingTime() {
        Recipe r1 = new Recipe(
                1, "A", "", 1, 10,
                List.of(), List.of(), List.of(CategoryType.OTHER),
                new Nutrition(100, 10, 10, 10)
        );

        Recipe r2 = new Recipe(
                2, "B", "", 1, 20,
                List.of(), List.of(), List.of(CategoryType.OTHER),
                new Nutrition(150, 15, 15, 15)
        );

        assertTrue(RecipeSorter.compare(r1, r2, RecipeSorter.SortKey.COOKING_TIME) < 0);
        assertTrue(RecipeSorter.compare(r2, r1, RecipeSorter.SortKey.COOKING_TIME) > 0);
    }



    // ---- テストデータ作成 ----
    private List<Recipe> createSampleRecipes() {

        List<Recipe> list = new ArrayList<>();

        // ID:3
        list.add(new Recipe(
                3, "カレー", "", 2, 45,
                new ArrayList<>(), new ArrayList<>(),
                List.of(CategoryType.OTHER),
                new Nutrition(600, 25, 20, 80)
        ));

        // ID:1
        list.add(new Recipe(
                1, "サラダ", "", 1, 10,
                new ArrayList<>(), new ArrayList<>(),
                List.of(CategoryType.OTHER),
                new Nutrition(200, 5, 1, 20)
        ));

        // ID:2
        list.add(new Recipe(
                2, "パスタ", "", 1, 20,
                new ArrayList<>(), new ArrayList<>(),
                List.of(CategoryType.OTHER),
                new Nutrition(700, 30, 15, 90)
        ));

        return list;
    }

    // ---- テスト1: カロリー昇順 ----
    @Test
    void testSortByCaloriesAsc() {
        List<Recipe> recipes = createSampleRecipes();

        List<Recipe> sorted = RecipeSorter.sort(
                recipes,
                RecipeSorter.SortKey.CALORIES,
                true // asc
        );

        assertEquals(1, sorted.get(0).getId()); // 200kcal
        assertEquals(3, sorted.get(1).getId()); // 600kcal
        assertEquals(2, sorted.get(2).getId()); // 700kcal
    }

    // ---- テスト2: ID降順 ----
    @Test
    void testSortByIdDesc() {
        List<Recipe> recipes = createSampleRecipes();

        List<Recipe> sorted = RecipeSorter.sort(
                recipes,
                RecipeSorter.SortKey.ID,
                false // desc
        );

        assertEquals(3, sorted.get(0).getId());
        assertEquals(2, sorted.get(1).getId());
        assertEquals(1, sorted.get(2).getId());
    }

    // ---- テスト3: 名前昇順 ----
    @Test
    void testSortByNameAsc() {
        List<Recipe> recipes = createSampleRecipes();

        List<Recipe> sorted = RecipeSorter.sort(
                recipes,
                RecipeSorter.SortKey.NAME,
                true
        );

        assertEquals("カレー", sorted.get(0).getName());
        assertEquals("サラダ", sorted.get(1).getName());
        assertEquals("パスタ", sorted.get(2).getName());
    }

    // ---- テスト4: 空リスト ----
    @Test
    void testSortEmptyList() {
        List<Recipe> empty = new ArrayList<>();
        List<Recipe> sorted = RecipeSorter.sort(
                empty,
                RecipeSorter.SortKey.ID,
                true
        );
        assertTrue(sorted.isEmpty());
    }
}
