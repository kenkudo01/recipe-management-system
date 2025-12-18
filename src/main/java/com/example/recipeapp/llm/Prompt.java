package com.example.recipeapp.llm;

/**
 * LLM に渡すプロンプトを表すデータクラス。
 *
 * system プロンプトと user プロンプトを分離して保持し、
 * LLM へのリクエスト生成時に利用する。
 */
public class Prompt {

    /** システムプロンプト（LLM の振る舞いを定義） */
    private final String system;

    /** ユーザープロンプト（実際の質問内容） */
    private final String user;

    public Prompt(String system, String user) {
        this.system = system;
        this.user = user;
    }

    // ===== Getter =====

    public String getSystem() {
        return system;
    }

    public String getUser() {
        return user;
    }
}
