package com.example.recipeapp.llm;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * LLM 設定が実際に利用可能かを検証するクラス。
 *
 * Ollama サーバーへの疎通確認と、
 * 指定されたモデルの存在確認のみを責務とする。
 */
public class LlmValidator {

    /** 検証用に共有する HTTP クライアント */
    private static final HttpClient client =
            HttpClient.newHttpClient();

    /**
     * Ollama が起動しており、かつ指定モデルが存在するかを検証する。
     *
     * @return 検証結果を表す ValidationResult
     */
    public static ValidationResult validate() {
        try {
            // Chat API のエンドポイントから base URL を取得
            String baseUrl =
                    LlmConfig.getEndpoint().replace("/api/chat", "");

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + "/api/tags"))
                    .GET()
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                return ValidationResult.SERVER_UNAVAILABLE;
            }

            // tags API のレスポンスにモデル名が含まれているかを簡易チェック
            if (!response.body().contains(
                    "\"name\":\"" + LlmConfig.getModel() + "\"")) {
                return ValidationResult.MODEL_NOT_FOUND;
            }

            return ValidationResult.OK;

        } catch (Exception e) {
            // 通信・パース失敗時はサーバー未接続として扱う
            return ValidationResult.SERVER_UNAVAILABLE;
        }
    }
}
