package com.example.recipeapp.llm;

import com.example.recipeapp.model.Recipe;

public class PromptBuilder {

    // --- Base system prompt ---
    private static final String BASE_SYSTEM_PROMPT = """
You are an AI assistant specialized in Japanese home cooking (家庭料理).
Answer in natural Japanese.
Focus on practical, realistic advice for everyday cooking.
Avoid unnecessary explanations or uncommon ingredients.
""";

    // --- Task prompts ---
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

    // --- public API ---
    public static Prompt build(PromptType type, Recipe recipe) {
        String taskPrompt = switch (type) {
            case SIDE_DISH -> TASK_SIDE_DISH;
            case MISSING_INGREDIENT -> TASK_MISSING;
            default -> "";
        };

        String userPrompt = buildUserPrompt(recipe, type);

        return new Prompt(
                BASE_SYSTEM_PROMPT + "\n\n" + taskPrompt,
                userPrompt
        );
    }

    private static String buildUserPrompt(Recipe recipe, PromptType type) {
        return """
料理名: %s
説明: %s
""".formatted(recipe.getName(), recipe.getDescription());
    }
}
