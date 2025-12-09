package com.example.recipeapp.model;

public class Ingredient {

    private String name;
    private IngredientAmount amount;

    public Ingredient(String name, IngredientAmount amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IngredientAmount getAmount() {
        return amount;
    }

    public void setAmount(IngredientAmount amount) {
        this.amount = amount;
    }
}
