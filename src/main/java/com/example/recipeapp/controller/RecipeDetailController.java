package com.example.recipeapp.controller;

import com.example.recipeapp.model.Ingredient;
import com.example.recipeapp.model.Recipe;
import javafx.application.Platform;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import com.example.recipeapp.llm.LlmClient;

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

    private Stage stage;

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
    private void askLLM(String prompt) {
        llmResultArea.setText("🤖 考え中...");

        new Thread(() -> {
            String answer = LlmClient.ask(prompt);

            Platform.runLater(() -> {
                llmResultArea.setText(answer);
            });
        }).start();
    }


    @FXML
    private void onAskMissing() {
        askLLM(buildPrompt("材料が足りない場合でも作れるか"));
    }

    @FXML
    private void onAskSubstitute() {
        askLLM(buildPrompt("代わりに使える材料"));
    }

    @FXML
    private void onAskSideDish() {
        askLLM(buildPrompt("この料理に合う一品"));
    }

    @FXML
    private void onAskPitfall() {
        askLLM(buildPrompt("失敗しやすいポイント"));
    }

    // ===== プロンプト生成（重要） =====
    private String buildPrompt(String questionType) {
        if (recipe == null) {
            return "料理について一般的に答えてください。";
        }

        StringBuilder sb = new StringBuilder();

        sb.append("料理名: ").append(recipe.getName()).append("\n");
        sb.append("説明: ").append(recipe.getDescription()).append("\n\n");

        sb.append("材料:\n");
        for (Ingredient i : recipe.getIngredients()) {
            sb.append("- ").append(i.getName()).append("\n");
        }

        sb.append("\n知りたいこと: ").append(questionType).append("\n");
        sb.append("家庭料理として、簡潔に日本語で答えてください。");

        return sb.toString();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void onBack() {
        stage.close();
    }
}
