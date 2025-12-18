package com.example.recipeapp.controller;

import com.example.recipeapp.model.*;
import com.example.recipeapp.repository.RecipeLoader;
import com.example.recipeapp.util.RecipeSorter;
import com.example.recipeapp.view.RecipeCard;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * メイン画面（レシピ一覧）のコントローラ。
 *
 * レシピの検索・カテゴリ絞り込み・ソートを制御し、
 * 詳細画面や設定画面への遷移を管理する。
 */
public class MainViewController {

    private static final double DETAIL_WIDTH = 1200;
    private static final double DETAIL_HEIGHT = 600;

    // ===== FXML components =====

    @FXML private TextField searchField;
    @FXML private ComboBox<CategoryType> categoryCombo;
    @FXML private ComboBox<RecipeSorter.SortKey> sortKeyCombo;
    @FXML private ComboBox<String> sortOrderCombo;

    @FXML private FlowPane flowPane;
    @FXML private ScrollPane scrollPane;
    @FXML private ListView<String> ingredientList;

    // ===== State =====

    /** 読み込まれた全レシピ */
    private List<Recipe> allRecipes = new ArrayList<>();

    /** 右上メニュー（設定） */
    private ContextMenu menu;

    /**
     * FXML 読み込み後に呼ばれる初期化処理。
     * レシピ読み込み、UI 初期設定、イベント登録を行う。
     */
    @FXML
    public void initialize() {

        // ---- Load recipes ----
        try {
            allRecipes = RecipeLoader.load(
                    "src/main/resources/sample_recipes.json"
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ---- Category filter ----
        categoryCombo.getItems().add(null); // = ALL
        categoryCombo.getItems().addAll(CategoryType.values());
        categoryCombo.setPromptText("ALL");

        // ---- Sort options ----
        sortKeyCombo.getItems().addAll(RecipeSorter.SortKey.values());
        sortKeyCombo.setValue(RecipeSorter.SortKey.ID);

        sortOrderCombo.getItems().addAll("ASC", "DESC");
        sortOrderCombo.setValue("ASC");

        // ---- Event listeners ----
        searchField.textProperty().addListener((obs, o, n) -> updateView());
        categoryCombo.valueProperty().addListener((obs, o, n) -> updateView());
        sortKeyCombo.valueProperty().addListener((obs, o, n) -> updateView());
        sortOrderCombo.valueProperty().addListener((obs, o, n) -> updateView());

        // ---- Context menu ----
        MenuItem settingsItem = new MenuItem("⚙ 設定");
        settingsItem.setOnAction(e -> openSettings());
        menu = new ContextMenu(settingsItem);

        // 初回表示
        updateView();
    }

    /**
     * 現在の検索・フィルタ・ソート条件をもとに
     * レシピ一覧表示を更新する。
     */
    private void updateView() {

        List<Recipe> filtered = new ArrayList<>(allRecipes);

        // ---- Search filter ----
        String keyword = searchField.getText();
        if (keyword != null && !keyword.isBlank()) {
            filtered = filtered.stream()
                    .filter(r -> r.getName().contains(keyword))
                    .collect(Collectors.toList());
        }

        // ---- Category filter ----
        CategoryType selectedCategory = categoryCombo.getValue();
        if (selectedCategory != null) {
            filtered = filtered.stream()
                    .filter(r -> r.getCategories().contains(selectedCategory))
                    .collect(Collectors.toList());
        }

        // ---- Sort ----
        boolean asc = "ASC".equals(sortOrderCombo.getValue());
        filtered = RecipeSorter.sort(
                filtered,
                sortKeyCombo.getValue(),
                asc
        );

        // ---- Render cards ----
        flowPane.getChildren().clear();

        for (Recipe r : filtered) {
            RecipeCard card = new RecipeCard(r);
            card.setOnMouseClicked(e -> openDetail(r));
            flowPane.getChildren().add(card);
        }
    }

    /**
     * レシピ詳細画面を開く。
     */
    private void openDetail(Recipe recipe) {
        try {
            var url = getClass().getResource(
                    "/com/example/recipeapp/view/RecipeDetailView.fxml"
            );
            if (url == null) {
                throw new IllegalStateException(
                        "RecipeDetailView.fxml not found."
                );
            }

            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();

            RecipeDetailController controller =
                    loader.getController();
            controller.setRecipe(recipe);

            Stage stage = new Stage();
            stage.setTitle(recipe.getName());
            stage.setScene(
                    new Scene(root, DETAIL_WIDTH, DETAIL_HEIGHT)
            );

            controller.setStage(stage);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * LLM 設定画面をモーダルで開く。
     */
    private void openSettings() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/com/example/recipeapp/view/LlmSettingsView.fxml"
                    )
            );

            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("LLM 設定");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * メニューボタン押下時にコンテキストメニューを表示する。
     */
    @FXML
    private void onOpenMenu(ActionEvent event) {
        Button btn = (Button) event.getSource();
        menu.show(btn, Side.BOTTOM, 0, 0);
    }
}
