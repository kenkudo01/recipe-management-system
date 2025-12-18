package com.example.recipeapp.llm;

/**
 * LLM に渡すプロンプトの種類を表す列挙型。
 *
 * UI やビジネスロジックから「何を聞きたいか」を
 * 型安全に指定するために使用する。
 */
public enum PromptType {

    /** レシピに不足している材料について質問する */
    MISSING_INGREDIENT,

    /** 代替材料を提案 */
    SUBSTITUTE,

    /** 相性の良い副菜を提案 */
    SIDE_DISH,

    /** 調理時の注意点・失敗しやすいポイントを聞く */
    PITFALL
}
