package com.example.recipeapp.controller;

import com.example.recipeapp.model.Recipe;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

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


    private Stage stage;

    public void setRecipe(Recipe recipe) {
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

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void onBack() {
        stage.close();
    }
}
