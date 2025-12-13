package com.example.recipeapp.llm;


/**
 * Simple data holder for LLM prompts.
 * Holds system and user prompts separately.
 */
public class Prompt {

    private final String system;
    private final String user;

    public Prompt(String system, String user) {
        this.system = system;
        this.user = user;
    }

    public String getSystem() {
        return system;
    }

    public String getUser() {
        return user;
    }
}
