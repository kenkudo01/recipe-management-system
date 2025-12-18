package com.example.recipeapp.util;

import com.example.recipeapp.model.CategoryType;
import com.example.recipeapp.model.Nutrition;
import com.example.recipeapp.model.Recipe;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class KnapsackSolverTest {

    @AfterAll
    static void afterAllTests() {
        System.out.println("✅ RecipeSorterTest: ALL TESTS PASSED");
    }

    private List<Recipe> createSampleRecipes() {
        return List.of(
                new Recipe(
                        1, "レシピ1", "", 1, 20,
                        List.of(),
                        List.of(),
                        List.of(CategoryType.OTHER),
                        new Nutrition(300, 25, 10, 30),
                        null   // ← imageUrl を追加
                ),
                new Recipe(
                        2, "レシピ2", "", 1, 30,
                        List.of(),
                        List.of(),
                        List.of(CategoryType.OTHER),
                        new Nutrition(400, 30, 20, 40),
                        null
                ),
                new Recipe(
                        3, "レシピ3", "", 1, 15,
                        List.of(),
                        List.of(),
                        List.of(CategoryType.OTHER),
                        new Nutrition(200, 15, 5, 10),
                        null
                ),
                new Recipe(
                        4, "レシピ4", "", 1, 25,
                        List.of(),
                        List.of(),
                        List.of(CategoryType.OTHER),
                        new Nutrition(500, 35, 25, 50),
                        null
                )
        );
    }




    @Test
    void testKnapsackSampleCase() {
        List<Recipe> recipes = createSampleRecipes();

        KnapsackSolver.Result res = KnapsackSolver.solve(
                recipes,
                700,   // max calories
                45     // max time
        );

        System.out.println("Selected IDs: " + res.selectedIds);
        System.out.println("Total Protein: " + res.totalProtein);


        boolean valid1 = res.selectedIds.equals(List.of(1, 2)) && res.totalProtein == 55;
        boolean valid2 = res.selectedIds.equals(List.of(3, 4)) && res.totalProtein == 50;

        assertTrue(valid1 || valid2, "解が正しくありません: " + res.selectedIds);
    }


    // ---- テスト2: 制約が厳しくて何も選べないケース ----
    @Test
    void testKnapsackNoSelectable() {
        List<Recipe> recipes = createSampleRecipes();

        KnapsackSolver.Result res = KnapsackSolver.solve(
                recipes,
                100,  // カロリーが全レシピ未満
                5     // 時間も足りない
        );

        assertEquals(0, res.selectedIds.size());
        assertEquals(0, res.totalProtein);
    }


    // ---- テスト3: 1つだけ選べるケース ----
    @Test
    void testKnapsackChooseOne() {
        List<Recipe> recipes = createSampleRecipes();

        KnapsackSolver.Result res = KnapsackSolver.solve(
                recipes,
                350, // レシピ1(300kcal) しか入らない
                30
        );

        assertEquals(List.of(1), res.selectedIds);
        assertEquals(25, res.totalProtein);
    }


    // ---- テスト4: 最適がギリギリ制約にハマるケース ----
    @Test
    void testKnapsackEdgeCase() {
        List<Recipe> recipes = createSampleRecipes();

        KnapsackSolver.Result res = KnapsackSolver.solve(
                recipes,
                500, // カロリー上限ギリギリ
                25   // 時間上限ギリギリ
        );

        assertEquals(List.of(4), res.selectedIds);
        assertEquals(35, res.totalProtein);
    }
}
