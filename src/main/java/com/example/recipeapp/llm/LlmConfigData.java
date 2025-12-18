package com.example.recipeapp.llm;

/**
 * JSONに保存するための純粋なデータクラス
 */
public class LlmConfigData {
    private String endpoint;
    private String model;

    public LlmConfigData(String endpoint, String model) {
        this.endpoint = endpoint;
        this.model = model;
    }
    public LlmConfigData() {

    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
