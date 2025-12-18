package com.example.recipeapp.view;

import com.example.recipeapp.model.Recipe;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.URL;

/**
 * レシピ一覧画面で使用するカード型 UI コンポーネント。
 *
 * レシピの画像・名前・基本情報を表示し、
 * クリック可能な View として利用される。
 */
public class RecipeCard extends VBox {

    // ===== Layout constants =====

    private static final double CARD_PADDING = 12;
    private static final double CARD_SPACING = 8;

    private static final double IMAGE_WIDTH = 160;
    private static final double IMAGE_HEIGHT = 110;

    // ===== Style constants =====

    private static final String CARD_STYLE =
            "-fx-background-color: #ffffff;" +
                    "-fx-background-radius: 10;" +
                    "-fx-effect: dropshadow(gaussian, #cccccc, 8, 0, 0, 2);";

    private static final String TITLE_STYLE =
            "-fx-font-weight: bold; -fx-font-size: 14px;";

    /** 表示対象のレシピ */
    private final Recipe recipe;

    // ===== UI Components =====
    private ImageView imageView;
    private Rectangle placeholder;
    private Label titleLabel;
    private Label infoLabel;

    /**
     * 指定されたレシピ情報をもとにカード UI を生成する。
     *
     * @param recipe 表示対象のレシピ
     */
    public RecipeCard(Recipe recipe) {
        this.recipe = recipe;

        initialize();
        layoutComponents();
        applyStyles();
    }

    // ===== Step 1: Create UI Components =====
    private void initialize() {

        // ---- Image or Placeholder ----
        Image img = loadImage(recipe.getImageUrl());

        if (img != null) {
            imageView = new ImageView(img);
            imageView.setFitWidth(IMAGE_WIDTH);
            imageView.setFitHeight(IMAGE_HEIGHT);
        } else {
            placeholder = new Rectangle(IMAGE_WIDTH, IMAGE_HEIGHT);
            placeholder.setArcWidth(12);
            placeholder.setArcHeight(12);
            placeholder.setFill(Color.LIGHTGRAY);
        }

        // ---- Title ----
        titleLabel = new Label(recipe.getName());

        // ---- Info (calories / cooking time) ----
        infoLabel = new Label(
                recipe.getNutrition().getCalories() + " kcal / " +
                        recipe.getCookingTimeMin() + " min"
        );

        // フォーカス枠を表示しない（カード UI 用）
        setFocusTraversable(false);
    }

    // ===== Step 2: Layout =====
    private void layoutComponents() {
        setSpacing(CARD_SPACING);
        setPadding(new Insets(CARD_PADDING));

        if (imageView != null) {
            getChildren().add(imageView);
        } else {
            getChildren().add(placeholder);
        }

        getChildren().addAll(titleLabel, infoLabel);
    }

    // ===== Step 3: Style =====
    private void applyStyles() {
        setStyle(CARD_STYLE);
        titleLabel.setStyle(TITLE_STYLE);
    }

    /**
     * 画像パスから Image を読み込む。
     * 読み込めない場合は null を返し、プレースホルダー表示とする。
     */
    private Image loadImage(String path) {
        try {
            if (path == null || path.isBlank()) {
                return null;
            }

            URL url = getClass().getResource("/" + path);
            if (url == null) {
                return null;
            }

            return new Image(
                    url.toString(),
                    IMAGE_WIDTH,
                    IMAGE_HEIGHT,
                    false,
                    true
            );

        } catch (Exception e) {
            return null;
        }
    }

    /**
     * このカードに紐づくレシピを返す。
     * クリック時の詳細画面遷移などで使用する。
     */
    public Recipe getRecipe() {
        return recipe;
    }
}
