package com.example.recipeapp.controller;

import com.example.recipeapp.llm.LlmConfig;
import com.example.recipeapp.llm.LlmValidator;
import com.example.recipeapp.llm.ValidationResult;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class LlmSettingController {

    @FXML private TextField endpointField;
    @FXML private TextField modelField;
    @FXML private Label validationLabel;

    @FXML
    public void initialize() {
        endpointField.setText(LlmConfig.getEndpoint());
        modelField.setText(LlmConfig.getModel());
        validationLabel.setText("未検証");
    }

    // ===== 保存 =====
    @FXML
    private void onSave() {
        LlmConfig.setEndpoint(endpointField.getText());
        LlmConfig.setModel(modelField.getText());

        LlmConfig.save();

        Stage stage = (Stage) endpointField.getScene().getWindow();
        stage.close();
    }

    // ===== 閉じる =====
    @FXML
    private void onClose() {
        Stage stage = (Stage) endpointField.getScene().getWindow();
        stage.close();
    }

    // 表示用メッセージ変換（UX向上）
    private String toMessage(ValidationResult result) {
        return switch (result) {
            case OK -> "✅ LLMは利用可能です";
            case MODEL_NOT_FOUND -> "⚠ モデルが見つかりません";
            case SERVER_UNAVAILABLE -> "❌ Ollamaに接続できません";
        };
    }

    @FXML
    private void onValidate() {
        LlmConfig.setEndpoint(endpointField.getText().trim());
        LlmConfig.setModel(modelField.getText().trim());

        ValidationResult result = LlmValidator.validate();
        validationLabel.setText(toMessage(result));
    }




}
