package com.example.recipeapp.model;

import java.util.List;

public class Recipe {

    private int id;
    private String name;
    private String description;
    private int servings;
    private int cookingTimeMin;

    private List<Ingredient> ingredients;
    private List<String> steps;
    private List<CategoryType> categories;

    private Nutrition nutrition;

    private String imageUrl;

    public Recipe() {
        // mutable なので空のコンストラクタを許可
    }

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

    // ===== GETTER / SETTER =====
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
    public void setImageUrl(String imagePath) { this.imageUrl = imagePath; }
}
