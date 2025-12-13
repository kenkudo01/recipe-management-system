package com.example.recipeapp.app;

import com.example.recipeapp.llm.LlmClient;

public class TestLlm {
    public static void main(String[] args) {
        String ans = LlmClient.ask(
                "生クリームがなくてもホワイトソースは作れる？"
        );
        System.out.println(ans);
    }
}
