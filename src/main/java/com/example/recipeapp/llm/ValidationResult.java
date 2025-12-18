package com.example.recipeapp.llm;

/**
 * LLM 設定の検証結果を表す列挙型。
 *
 * 設定画面でのバリデーション結果や、
 * 保存可否の判定に使用する。
 */
public enum ValidationResult {

    /** 検証成功（LLM が利用可能） */
    OK,

    /** Ollama サーバーに接続できない */
    SERVER_UNAVAILABLE,

    /** 指定されたモデルが存在しない */
    MODEL_NOT_FOUND
}
