package com.example.recipeapp.controller;

import com.example.recipeapp.llm.*;
import com.example.recipeapp.model.Recipe;
import com.example.recipeapp.ui.ChatAnimator;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * レシピ詳細画面のコントローラ。
 *
 * レシピ情報の表示に加え、LLM を用いた補助情報
 *（副菜提案・不足材料判断など）を提供する。
 */
public class RecipeDetailController {

    // ===== FXML components =====

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

    // ===== State =====

    private Recipe recipe;
    private ChatAnimator chatAnimator;
    private boolean llmAvailable = true;
    private Stage stage;

    /**
     * FXML 読み込み後に呼ばれる初期化処理。
     *
     * LLM の利用可否を確認し、
     * 利用不可の場合はその旨を画面に表示する。
     */
    @FXML
    public void initialize() {

        chatAnimator = new ChatAnimator(llmResultArea);

        ValidationResult result = LlmValidator.validate();

        if (result != ValidationResult.OK) {
            llmAvailable = false;

            String message = switch (result) {
                case MODEL_NOT_FOUND ->
                        "⚠ 指定されたAIモデルが見つかりません。\n設定を確認してください。";
                case SERVER_UNAVAILABLE ->
                        "⚠ Ollamaに接続できません。\n起動しているか確認してください。";
                default ->
                        "⚠ AIが利用できません。";
            };

            llmResultArea.setText(message);
        }
    }

    /**
     * 表示対象のレシピを設定し、画面に反映する。
     */
    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;

        titleLabel.setText(recipe.getName());
        descriptionLabel.setText(recipe.getDescription());

        // ---- Image ----
        if (recipe.getImageUrl() != null) {
            try {
                Image img = new Image(
                        getClass().getResourceAsStream(
                                "/" + recipe.getImageUrl()
                        )
                );
                imageView.setImage(img);
            } catch (Exception e) {
                imageView.setImage(null); // フォールバック
            }
        } else {
            imageView.setImage(null);
        }

        // ---- Ingredients ----
        ingredientList.getItems().setAll(
                recipe.getIngredients().stream()
                        .map(i ->
                                i.getName() + " : " +
                                        i.getAmount().getRaw()
                        )
                        .toList()
        );

        // ---- Steps ----
        stepList.getItems().setAll(recipe.getSteps());

        // ---- Nutrition ----
        calorieLabel.setText(
                "カロリー: " +
                        recipe.getNutrition().getCalories() + " kcal"
        );
        proteinLabel.setText(
                "たんぱく質: " +
                        recipe.getNutrition().getProtein() + " g"
        );
        fatLabel.setText(
                "脂質: " +
                        recipe.getNutrition().getFat() + " g"
        );
        carbLabel.setText(
                "炭水化物: " +
                        recipe.getNutrition().getCarbs() + " g"
        );
    }

    /**
     * LLM に問い合わせを行う共通処理。
     *
     * 通信処理は時間がかかるため、
     * JavaFX UI スレッドをブロックしないよう
     * Task を用いてバックグラウンドで実行する。
     */
    private void askLLM(PromptType type) {

        Task<String> task = new Task<>() {

            /**
             * バックグラウンドスレッドで実行される処理。
             * （UI 操作は禁止）
             */
            @Override
            protected String call() {
                return LlmService.ask(type, recipe);
            }
        };

        /**
         * Task 正常終了時の処理。
         * JavaFX Application Thread 上で実行される。
         */
        task.setOnSucceeded(e -> {
            chatAnimator.stopThinking();
            chatAnimator.showTyping(task.getValue());
        });

        // 処理開始前に「考え中…」アニメーションを表示
        chatAnimator.startThinking();

        // Task を別スレッドで実行
        new Thread(task).start();
    }

    // ===== Button handlers =====

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

    /**
     * 親ステージを設定する（戻るボタン用）。
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * 戻るボタン押下時に画面を閉じる。
     */
    @FXML
    private void onBack() {
        stage.close();
    }
}
