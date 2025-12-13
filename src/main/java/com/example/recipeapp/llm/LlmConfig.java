package com.example.recipeapp.llm;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LlmConfig {

    private static final String SETTINGS_PATH =
            "/setting/settings.json";


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
        try (InputStream in =
                     LlmConfig.class.getResourceAsStream(SETTINGS_PATH)) {

            if (in == null) {
                System.err.println("settings.json not found in resources");
                return;
            }

            Gson gson = new Gson();
            try (Reader reader = new InputStreamReader(in, StandardCharsets.UTF_8)) {
                LlmConfigData data = gson.fromJson(reader, LlmConfigData.class);
                endpoint = data.endpoint;
                model = data.model;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // ===== save =====
    public static void save() {
        System.out.println(
                "[INFO] settings.json is loaded from resources. Save is disabled (temporary)."
        );
    }

}
