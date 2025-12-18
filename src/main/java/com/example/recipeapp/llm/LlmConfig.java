package com.example.recipeapp.llm;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LlmConfig {

    // ===== Paths / Serializer =====

    private static final Path SETTINGS_PATH =
            Paths.get("settings", "settings.json");

    private static final Gson gson =
            new GsonBuilder().setPrettyPrinting().create();

    // ===== Default values =====
    // 設定ファイルが無い場合に使用される初期値

    private static String endpoint =
            "http://localhost:11434/api/chat";
    private static String model = "llama3.2:latest";

    private static double temperature = 0.3;
    private static double topP = 0.9;
    private static double repeatPenalty = 1.1;
    private static int maxTokens = 512;

    // ===== Load / Save =====

    /**
     * 設定ファイルを読み込み、現在の設定に反映する。
     * ファイルが存在しない場合はデフォルト値を使用する。
     */
    public static void load() {
        if (!Files.exists(SETTINGS_PATH)) {
            return;
        }

        try (Reader reader = Files.newBufferedReader(SETTINGS_PATH)) {
            LlmConfigData data = gson.fromJson(reader, LlmConfigData.class);

            if (data.getEndpoint() != null && !data.getEndpoint().isBlank()) {
                endpoint = data.getEndpoint();
            }
            if (data.getModel() != null && !data.getModel().isBlank()) {
                model = data.getModel();
            }

        } catch (Exception e) {
            // 設定が壊れていてもアプリは起動させる
            e.printStackTrace();
        }
    }

    /**
     * 現在の設定を JSON ファイルとして保存する。
     */
    public static void save() {
        try {
            LlmConfigData data = new LlmConfigData(endpoint, model);

            Path dir = SETTINGS_PATH.getParent();
            if (dir != null && !Files.exists(dir)) {
                Files.createDirectories(dir);
            }

            try (Writer writer = Files.newBufferedWriter(
                    SETTINGS_PATH,
                    StandardCharsets.UTF_8
            )) {
                gson.toJson(data, writer);
            }

        } catch (IOException e) {
            System.err.println("[ERROR] Failed to save LLM settings");
        }
    }

    // ===== Getter / Setter =====
    // 設定値の取得・更新のみを行う

    public static String getEndpoint() {
        return endpoint;
    }

    public static void setEndpoint(String value) {
        endpoint = value;
    }

    public static String getModel() {
        return model;
    }

    public static void setModel(String value) {
        model = value;
    }

    public static double getTemperature() {
        return temperature;
    }

    public static void setTemperature(double value) {
        temperature = value;
    }

    public static double getTopP() {
        return topP;
    }

    public static void setTopP(double value) {
        topP = value;
    }

    public static double getRepeatPenalty() {
        return repeatPenalty;
    }

    public static void setRepeatPenalty(double value) {
        repeatPenalty = value;
    }

    public static int getMaxTokens() {
        return maxTokens;
    }

    public static void setMaxTokens(int value) {
        maxTokens = value;
    }
}
