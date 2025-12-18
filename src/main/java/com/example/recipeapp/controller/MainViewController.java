package com.example.recipeapp.controller;

import com.example.recipeapp.model.*;
import com.example.recipeapp.repository.RecipeLoader;
import com.example.recipeapp.util.RecipeSorter;
import com.example.recipeapp.view.RecipeCard;
import javafx.collections.*;
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

import java.util.*;
import java.util.stream.Collectors;

public class MainViewController {

    private static final double DETAIL_WIDTH = 1200;
    private static final double DETAIL_HEIGHT = 600;



    @FXML private TextField searchField;
    @FXML private ComboBox<CategoryType> categoryCombo;
    @FXML private ComboBox<RecipeSorter.SortKey> sortKeyCombo;
    @FXML private ComboBox<String> sortOrderCombo;

    @FXML private FlowPane flowPane;
    @FXML private ScrollPane scrollPane;
    @FXML private ListView<String> ingredientList;

    private List<Recipe> allRecipes = new ArrayList<>();
    private ContextMenu menu;

    @FXML
    public void initialize() {

        // ---- Load Recipes ----
        try {
            allRecipes = RecipeLoader.load("src/main/resources/sample_recipes.json");
            for (int i = 0; i < allRecipes.size(); i++) {
                Recipe r = allRecipes.get(i);
                System.out.println("Loaded Recipe " + i + " : " + r.getName() + " / imageUrl=" + r.getImageUrl());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // ---- Category Combo ----
        categoryCombo.getItems().add(null); // = ALL
        categoryCombo.getItems().addAll(CategoryType.values());
        categoryCombo.setPromptText("ALL");

        // ---- Sort Combo ----
        sortKeyCombo.getItems().addAll(RecipeSorter.SortKey.values());
        sortKeyCombo.setValue(RecipeSorter.SortKey.ID);

        sortOrderCombo.getItems().addAll("ASC", "DESC");
        sortOrderCombo.setValue("ASC");

        // ---- Event Listeners ----
        searchField.textProperty().addListener((obs, oldV, newV) -> updateView());
        categoryCombo.valueProperty().addListener((obs, oldV, newV) -> updateView());
        sortKeyCombo.valueProperty().addListener((obs, oldV, newV) -> updateView());
        sortOrderCombo.valueProperty().addListener((obs, oldV, newV) -> updateView());

        MenuItem settingsItem = new MenuItem("⚙ 設定");
        settingsItem.setOnAction(e -> openSettings());

        menu = new ContextMenu(settingsItem);

        // 初回表示
        updateView();
    }

    private void updateView() {
        List<Recipe> filtered = new ArrayList<>(allRecipes);



        // ---- Search Filter ----
        String keyword = searchField.getText();
        if (keyword != null && !keyword.isBlank()) {
            filtered = filtered.stream()
                    .filter(r -> r.getName().contains(keyword))
                    .collect(Collectors.toList());
        }

        // ---- Category Filter ----
        CategoryType selectedCategory = categoryCombo.getValue();
        if (selectedCategory != null) {
            filtered = filtered.stream()
                    .filter(r -> r.getCategories().contains(selectedCategory))
                    .collect(Collectors.toList());
        }

        // ---- Sort ----
        boolean asc = sortOrderCombo.getValue().equals("ASC");
        filtered = RecipeSorter.sort(filtered, sortKeyCombo.getValue(), asc);

        flowPane.getChildren().clear();

        for (Recipe r : filtered) {
            RecipeCard card = new RecipeCard(r);

            card.setOnMouseClicked(e -> openDetail(r)); // カードクリックで詳細表示

            flowPane.getChildren().add(card);
        }

    }


    private void openDetail(Recipe recipe) {
        try {
            var url = getClass().getResource("/com/example/recipeapp/view/RecipeDetailView.fxml");
            if (url == null) {
                throw new IllegalStateException("RecipeDetailView.fxml not found (resource path is wrong).");
            }

            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();

            RecipeDetailController controller = loader.getController();
            controller.setRecipe(recipe);

            Stage stage = new Stage();
            stage.setTitle(recipe.getName());
            stage.setScene(new Scene(root, DETAIL_WIDTH, DETAIL_HEIGHT));

            controller.setStage(stage);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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



    @FXML
    private void onOpenMenu(ActionEvent event) {
        Button btn = (Button) event.getSource();
        menu.show(btn, Side.BOTTOM, 0, 0);
    }


}
