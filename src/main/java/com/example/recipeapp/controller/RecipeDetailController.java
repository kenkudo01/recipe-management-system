package com.example.recipeapp.controller;

import com.example.recipeapp.llm.*;
import com.example.recipeapp.model.Ingredient;
import com.example.recipeapp.model.Recipe;
import com.example.recipeapp.ui.ChatAnimator;
import javafx.application.Platform;
import javafx.collections.*;
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
        chatAnimator.startThinking();

        new Thread(() -> {
            String answer = LlmService.ask(type, recipe);

            Platform.runLater(() -> {
                chatAnimator.stopThinking();
                chatAnimator.showTyping(answer);
            });
        }).start();
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
