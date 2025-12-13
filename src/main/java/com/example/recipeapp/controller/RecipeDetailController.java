package com.example.recipeapp.controller;

import com.example.recipeapp.llm.*;
import com.example.recipeapp.model.Ingredient;
import com.example.recipeapp.model.Recipe;
import com.example.recipeapp.ui.ChatAnimator;
import javafx.application.Platform;
import javafx.collections.*;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import static com.example.recipeapp.llm.ValidationResult.SERVER_UNAVAILABLE;

public class RecipeDetailController {

    @FXML private Label titleLabel;
    @FXML private Label descriptionLabel;
    @FXML private Label infoLabel;
    @FXML private ImageView imageView;
    @FXML private ListView<String> ingredientList;
    @FXML private ListView<String> stepList;
    @FXML private Label calorieLabel;
    @FXML private Label proteinLabel;
    @FXML private Label fatLabel;
    @FXML private Label carbLabel;

    @FXML private TextArea llmResultArea;

    private Recipe recipe;
    private ChatAnimator chatAnimator;
    private boolean llmAvailable = true;
    private Stage stage;

    @FXML
    public void initialize(){
        chatAnimator = new ChatAnimator(llmResultArea);

        ValidationResult result = LlmValidator.validate();

        if (result != ValidationResult.OK) {
            llmAvailable = false;

            String message = switch (result) {
                case MODEL_NOT_FOUND ->
                        "⚠ 指定されたAIモデルが見つかりません。\n設定を確認してください。";
                case SERVER_UNAVAILABLE ->
                        "⚠ Ollamaに接続できません。\n起動しているか確認してください。";
                default -> "⚠ AIが利用できません。";
            };

            llmResultArea.setText(message);
        }
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
        titleLabel.setText(recipe.getName());
        descriptionLabel.setText(recipe.getDescription());

        if (recipe.getImageUrl() != null) {
            try {
                Image img = new Image(
                        getClass().getResourceAsStream("/" + recipe.getImageUrl())
                );
                imageView.setImage(img);
            } catch (Exception e) {
                imageView.setImage(null); // フォールバック
            }
        } else {
            imageView.setImage(null);
        }

        ingredientList.getItems().setAll(
                recipe.getIngredients().stream()
                        .map(i -> i.getName() + " : " + i.getAmount().getRaw())
                        .toList()
        );

        stepList.getItems().setAll(recipe.getSteps());

        // 栄養情報
        calorieLabel.setText("カロリー: " + recipe.getNutrition().getCalories() + " kcal");
        proteinLabel.setText("たんぱく質: " + recipe.getNutrition().getProtein() + " g");
        fatLabel.setText("脂質: " + recipe.getNutrition().getFat() + " g");
        carbLabel.setText("炭水化物: " + recipe.getNutrition().getCarbs() + " g");
    }


    private void askLLM(PromptType type) {

        // ===============================
        // LLM呼び出しは時間がかかるため、
        // JavaFXのUIスレッドをブロックしないよう
        // Task（バックグラウンド処理）で実行する
        // ===============================
        Task<String> task = new Task<>() {

            // バックグラウンドスレッドで実行される処理
            // （ここではUI操作をしてはいけない）
            @Override
            protected String call() {
                return LlmService.ask(type, recipe);
            }
        };

        // ===============================
        // Task が正常終了したときに呼ばれる
        // この処理は JavaFX Application Thread 上で実行される
        // ===============================
        task.setOnSucceeded(e -> {

            // 「考え中…」アニメーションを停止
            chatAnimator.stopThinking();

            // LLMの回答を1文字ずつ表示するアニメーションを開始
            chatAnimator.showTyping(task.getValue());
        });

        // ===============================
        // LLM処理開始前に「考え中…」表示を開始
        // （UIスレッド上で安全に実行される）
        // ===============================
        chatAnimator.startThinking();

        // ===============================
        // Taskを別スレッドで実行
        // （new Thread(...) しないと処理が始まらない）
        // ===============================
        new Thread(task).start();
    }





    @FXML
    private void onAskSideDish() {
        askLLM(PromptType.SIDE_DISH);
    }

    @FXML
    private void onAskMissing() {
        askLLM(PromptType.MISSING_INGREDIENT);
    }

    @FXML
    private void onAskSubstitute() {
        askLLM(PromptType.SUBSTITUTE);
    }

    @FXML
    private void onAskPitfall() {
        askLLM(PromptType.PITFALL);
    }



    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void onBack() {
        stage.close();
    }
}
