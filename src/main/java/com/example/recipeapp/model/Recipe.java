package com.example.recipeapp.model;

import java.util.List;

/**
 * レシピ全体を表すドメインモデル。
 *
 * 表示・検索・LLM 連携などアプリケーション全体で利用される中核クラスであり、
 * 材料・手順・カテゴリ・栄養情報などを集約して保持する。
 */
public class Recipe {

    /** レシピID（内部識別用） */
    private int id;

    /** レシピ名 */
    private String name;

    /** レシピの概要説明 */
    private String description;

    /** 何人分か */
    private int servings;

    /** 調理時間（分） */
    private int cookingTimeMin;

    /** 材料一覧 */
    private List<Ingredient> ingredients;

    /** 調理手順 */
    private List<String> steps;

    /** レシピカテゴリ */
    private List<CategoryType> categories;

    /** 栄養情報（任意） */
    private Nutrition nutrition;

    /** 画像URL（UI表示用） */
    private String imageUrl;

    /**
     * デフォルトコンストラクタ。
     * JSON デシリアライズや UI 編集を考慮し、空生成を許可している。
     */
    public Recipe() {
        // mutable なモデルとして利用するため空コンストラクタを用意
    }

    /**
     * レシピを生成する。
     */
    public Recipe(int id, String name, String description,
                  int servings, int cookingTimeMin,
                  List<Ingredient> ingredients, List<String> steps,
                  List<CategoryType> categories, Nutrition nutrition, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.servings = servings;
        this.cookingTimeMin = cookingTimeMin;
        this.ingredients = ingredients;
        this.steps = steps;
        this.categories = categories;
        this.nutrition = nutrition;
        this.imageUrl = imageUrl;
    }

    // ===== Getter / Setter =====

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getServings() { return servings; }
    public void setServings(int servings) { this.servings = servings; }

    public int getCookingTimeMin() { return cookingTimeMin; }
    public void setCookingTimeMin(int cookingTimeMin) { this.cookingTimeMin = cookingTimeMin; }

    public List<Ingredient> getIngredients() { return ingredients; }
    public void setIngredients(List<Ingredient> ingredients) { this.ingredients = ingredients; }

    public List<String> getSteps() { return steps; }
    public void setSteps(List<String> steps) { this.steps = steps; }

    public List<CategoryType> getCategories() { return categories; }
    public void setCategories(List<CategoryType> categories) { this.categories = categories; }

    public Nutrition getNutrition() { return nutrition; }
    public void setNutrition(Nutrition nutrition) { this.nutrition = nutrition; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
