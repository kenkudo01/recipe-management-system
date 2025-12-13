package com.example.recipeapp.llm;

/**
 * JSONに保存するための純粋なデータクラス
 */
public class LlmConfigData {
    public String endpoint;
    public String model;

    public LlmConfigData(String endpoint, String model) {
        this.endpoint = endpoint;
        this.model = model;
    }
}
