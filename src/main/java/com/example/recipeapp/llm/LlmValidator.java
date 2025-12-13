package com.example.recipeapp.llm;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class LlmValidator {

    private static final HttpClient client = HttpClient.newHttpClient();

    /**
     * Ollamaが起動しており、指定モデルが存在するか確認
     */
    public static ValidationResult validate() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:11434/api/tags"))
                    .GET()
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                return ValidationResult.SERVER_UNAVAILABLE;
            }

            if (!response.body().contains("\"name\":\"" + LlmConfig.getModel() + "\"")) {
                return ValidationResult.MODEL_NOT_FOUND;
            }

            return ValidationResult.OK;

        } catch (Exception e) {
            return ValidationResult.SERVER_UNAVAILABLE;
        }
    }
}
