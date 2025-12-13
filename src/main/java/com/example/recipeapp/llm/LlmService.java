package com.example.recipeapp.llm;

import com.example.recipeapp.model.Recipe;

public class LlmService {

    public static String ask(PromptType type, Recipe recipe) {
        Prompt prompt = PromptBuilder.build(type, recipe);
        return LlmClient.ask(
                prompt.getSystem(),
                prompt.getUser()
        );
    }
}
