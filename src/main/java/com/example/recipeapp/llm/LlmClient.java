package com.example.recipeapp.llm;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Ollama の Chat API にリクエストを送り、
 * LLM からの応答テキストを取得するクライアントクラス。
 *
 * 通信処理のみに責務を限定し、設定値は LlmConfig から取得する。
 */
public class LlmClient {

    /** アプリ全体で共有する HTTP クライアント */
    private static final HttpClient client =
            HttpClient.newHttpClient();

    /**
     * LLM に問い合わせを行い、生成されたテキストを返す。
     *
     * @param systemPrompt システムプロンプト
     * @param userPrompt   ユーザープロンプト
     * @return LLM の応答テキスト（失敗時はユーザー向けメッセージ）
     */
    public static String ask(String systemPrompt, String userPrompt) {
        try {
            String json = """
                {
                  "model": "%s",
                  "stream": false,
                  "temperature": %s,
                  "top_p": %s,
                  "repeat_penalty": %s,
                  "max_tokens": %d,
                  "messages": [
                    { "role": "system", "content": "%s" },
                    { "role": "user",   "content": "%s" }
                  ]
                }
                """.formatted(
                    LlmConfig.getModel(),
                    LlmConfig.getTemperature(),
                    LlmConfig.getTopP(),
                    LlmConfig.getRepeatPenalty(),
                    LlmConfig.getMaxTokens(),
                    escape(systemPrompt),
                    escape(userPrompt)
            );

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(LlmConfig.getEndpoint()))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            return extractContent(response.body());

        } catch (Exception e) {
            // 通信失敗時は例外を外に投げず、UI 向けメッセージを返す
            return "⚠ AIに接続できませんでした。\nローカルLLMが起動しているか確認してください。";
        }
    }

    // ===== Utility methods =====

    /**
     * JSON 文字列に埋め込むための簡易エスケープ処理。
     * （完全な JSON エスケープではないが、本用途では十分）
     */
    private static String escape(String s) {
        return s.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n");
    }

    /**
     * Ollama のレスポンス JSON から content 部分のみを抽出する。
     * 構造が単純であることを前提とした軽量実装。
     */
    private static String extractContent(String json) {
        int idx = json.indexOf("\"content\":\"");
        if (idx == -1) {
            return json;
        }

        int start = idx + 11;
        int end = json.indexOf("\"", start);

        return json.substring(start, end)
                .replace("\\n", "\n");
    }
}
