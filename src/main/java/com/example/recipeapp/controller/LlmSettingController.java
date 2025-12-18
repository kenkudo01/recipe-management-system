package com.example.recipeapp.controller;

import com.example.recipeapp.llm.LlmConfig;
import com.example.recipeapp.llm.LlmValidator;
import com.example.recipeapp.llm.ValidationResult;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * LLM 設定画面のコントローラ。
 *
 * ユーザー入力をもとに LLM 設定を更新し、
 * 検証結果に応じて保存可否を制御する。
 */
public class LlmSettingController {

    @FXML private TextField endpointField;
    @FXML private TextField modelField;
    @FXML private Label validationLabel;

    /**
     * FXML 読み込み後に呼ばれる初期化処理。
     * 現在の LLM 設定値を画面に反映する。
     */
    @FXML
    public void initialize() {
        endpointField.setText(LlmConfig.getEndpoint());
        modelField.setText(LlmConfig.getModel());
        validationLabel.setText("未検証");
    }

    /**
     * 保存ボタン押下時の処理。
     *
     * 入力値を一度 Config に反映した上で検証を行い、
     * 検証成功時のみ設定を保存する。
     */
    @FXML
    private void onSave() {

        // 入力値を一旦 Config に反映（検証用）
        LlmConfig.setEndpoint(endpointField.getText().trim());
        LlmConfig.setModel(modelField.getText().trim());

        // 検証
        ValidationResult result = LlmValidator.validate();
        validationLabel.setText(toMessage(result));

        // NG の場合は保存せず終了
        if (result != ValidationResult.OK) {
            return;
        }

        // 検証 OK の場合のみ永続化
        LlmConfig.save();

        // 設定画面を閉じる
        Stage stage = (Stage) endpointField.getScene().getWindow();
        stage.close();
    }

    /**
     * 閉じるボタン押下時の処理。
     * 設定を保存せずに画面を閉じる。
     */
    @FXML
    private void onClose() {
        Stage stage = (Stage) endpointField.getScene().getWindow();
        stage.close();
    }

    /**
     * 検証結果を表示用メッセージに変換する。
     * UI 側の表現をここに集約することで可読性を保つ。
     */
    private String toMessage(ValidationResult result) {
        return switch (result) {
            case OK -> "✅ LLMは利用可能です";
            case MODEL_NOT_FOUND -> "⚠ モデルが見つかりません";
            case SERVER_UNAVAILABLE -> "❌ Ollamaに接続できません";
        };
    }

    /**
     * 検証ボタン押下時の処理。
     * 設定は保存せず、検証結果のみを表示する。
     */
    @FXML
    private void onValidate() {

        LlmConfig.setEndpoint(endpointField.getText().trim());
        LlmConfig.setModel(modelField.getText().trim());

        ValidationResult result = LlmValidator.validate();
        validationLabel.setText(toMessage(result));
    }
}
