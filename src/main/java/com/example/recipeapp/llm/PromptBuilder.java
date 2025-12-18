package com.example.recipeapp.llm;

import com.example.recipeapp.model.Recipe;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * レシピ情報とプロンプト種別から、
 * LLM に渡す system / user プロンプトを組み立てるクラス。
 *
 * プロンプト生成の責務をこのクラスに集約し、
 * Service や Client から分離している。
 */
public class PromptBuilder {

    // ===== Base system prompt =====
    // LLM の前提条件・回答スタイルを定義

    private static final String BASE_SYSTEM_PROMPT = """
        You are an AI assistant specialized in Japanese home cooking (家庭料理).
        Answer in natural Japanese.
        Focus on practical, realistic advice for everyday cooking.
        Avoid unnecessary explanations or uncommon ingredients.
        """;

    // ===== Task-specific prompts =====
    // PromptType ごとの追加ルール

    private static final String TASK_SIDE_DISH = """
        You are recommending a side dish (副菜).

        Rules:
        - Suggest EXACTLY ONE side dish.
        - The side dish must be common in Japanese home cooking.
        - Do NOT suggest ingredients or modifications to the main dish.
        - Keep the explanation short.
        """;

    private static final String TASK_MISSING = """
        You are judging whether the dish can still be cooked without some ingredients.
        State clearly whether it is possible or not.
        """;

    /**
     * プロンプト種別とレシピ情報から Prompt を生成する。
     *
     * @param type   プロンプトの用途
     * @param recipe 対象となるレシピ
     * @return LLM に渡す Prompt
     */
    public static Prompt build(PromptType type, Recipe recipe) {
        String taskPrompt = switch (type) {
            case SIDE_DISH -> TASK_SIDE_DISH;
            case MISSING_INGREDIENT -> TASK_MISSING;
            default -> "";
        };

        return new Prompt(
                BASE_SYSTEM_PROMPT + "\n\n" + taskPrompt,
                buildUserPrompt(recipe)
        );
    }

    // ===== Internal helpers =====

    /**
     * レシピ情報を LLM 向けのユーザープロンプト形式に整形する。
     */
    private static String buildUserPrompt(Recipe recipe) {

        String ingredients = recipe.getIngredients().stream()
                .map(i -> "- " + i.getName() + "（" + i.getAmount().getRaw() + "）")
                .collect(Collectors.joining("\n"));

        String steps = IntStream.range(0, recipe.getSteps().size())
                .mapToObj(i -> (i + 1) + ". " + recipe.getSteps().get(i))
                .collect(Collectors.joining("\n"));

        return """
            以下は家庭料理のレシピです。

            【料理名】
            %s

            【料理の概要】
            %s

            【材料】
            %s

            【調理手順】
            %s
            """.formatted(
                recipe.getName(),
                recipe.getDescription(),
                ingredients,
                steps
        );
    }
}
