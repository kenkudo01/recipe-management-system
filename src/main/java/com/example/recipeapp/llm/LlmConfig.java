package com.example.recipeapp.llm;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class LlmConfig {

    private static final Path SETTINGS_PATH =
            Paths.get("settings", "settings.json");

    private static final Gson gson =
            new GsonBuilder().setPrettyPrinting().create();

    private static final Path CONFIG_DIR =
            Paths.get(System.getProperty("user.home"), ".recipeapp");

    private static final Path CONFIG_FILE =
            CONFIG_DIR.resolve("llm-config.json");

    // ===== デフォルト値 =====
    private static String endpoint =
            "http://localhost:11434/api/chat";
    private static String model = "llama3.2:latest";

    private static double temperature = 0.3;
    private static double topP = 0.9;
    private static double repeatPenalty = 1.1;
    private static int maxTokens = 512;


    // ===== getter =====
    public static String getEndpoint() {
        return endpoint;
    }

    public static String getModel() {
        return model;
    }

    public static double getTemperature() {
        return temperature;
    }
    public static double getTopP() {
        return topP;
    }

    public static double getRepeatPenalty() {
        return repeatPenalty;
    }

    public static int getMaxTokens() {
        return maxTokens;
    }

    // ===== setter =====
    public static void setEndpoint(String value) {
        endpoint = value;
    }

    public static void setModel(String value) {
        model = value;
    }

    public static void setTemperature(double value) {
        temperature = value;
    }

    public static void setTopP(double value) {
        topP = value;
    }

    public static void setRepeatPenalty(double value) {
        repeatPenalty = value;
    }

    public static void setMaxTokens(int value) {
        maxTokens = value;
    }

    // ===== load =====
    public static void load() {


        if (!Files.exists(SETTINGS_PATH)) {
            // 設定ファイルが無い場合はデフォルト値を使う
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
            // 壊れていてもアプリは落とさない
            e.printStackTrace();
        }
    }



    // ===== save =====
    public static void save() {
        try {

            LlmConfigData data = new LlmConfigData(endpoint, model);

            // settings/ ディレクトリが無ければ作成
            Path dir = SETTINGS_PATH.getParent();
            if (dir != null && !Files.exists(dir)) {
                Files.createDirectories(dir);
            }

            // JSON 書き込み（UTF-8 / 上書き）
            try (Writer writer = Files.newBufferedWriter(
                    SETTINGS_PATH,
                    StandardCharsets.UTF_8
            )) {
                gson.toJson(data, writer);
            }

            System.out.println(
                    "[INFO] LLM settings saved to " +
                            SETTINGS_PATH.toAbsolutePath()
            );

        } catch (IOException e) {
            // 設定保存失敗でもアプリは落とさない
            System.err.println("[ERROR] Failed to save LLM settings");
        }
    }
    }