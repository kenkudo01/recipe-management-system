package com.example.recipeapp.llm;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class LlmClient {

    private static final HttpClient client =
            HttpClient.newHttpClient();

    public static String ask(String prompt) {
        try {
            String json = """
            {
              "model": "%s",
              "stream": false,
              "messages": [
                {
                  "role": "system",
                  "content": "You are a cooking assistant for home cooking. Answer in natural Japanese. Use only common ingredients. Avoid store-bought products unless necessary. Explain briefly and practically."
                },
                {
                  "role": "user",
                  "content": "%s"
                }
              ]
            }
            """.formatted(
                                LlmConfig.MODEL,
                                escape(prompt)
                        );


            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(LlmConfig.ENDPOINT))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            return extractContent(response.body());

        } catch (Exception e) {
            return "⚠ LLMに接続できませんでした。\nOllamaが起動しているか確認してください。";
        }
    }

    // JSON文字列用エスケープ（最低限）
    private static String escape(String s) {
        return s.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n");
    }

    // OllamaのJSONから content だけ抜く（簡易）
    private static String extractContent(String json) {
        int idx = json.indexOf("\"content\":\"");
        if (idx == -1) return json;

        int start = idx + 11;
        int end = json.indexOf("\"", start);

        return json.substring(start, end)
                .replace("\\n", "\n");
    }



}
