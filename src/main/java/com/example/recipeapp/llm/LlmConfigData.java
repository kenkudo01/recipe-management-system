package com.example.recipeapp.llm;

/**
 * LLM設定をJSONに保存するための純粋なデータクラス
 */

public class LlmConfigData {

    /** Ollama の Chat API エンドポイント */
    private String endpoint;

    /** 使用する LLM モデル名 */
    private String model;

    //======== Construct ============
    public LlmConfigData() {
    }

    public LlmConfigData(String endpoint, String model) {
        this.endpoint = endpoint;
        this.model = model;
    }

    // ===== Getter / Setter =====
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
