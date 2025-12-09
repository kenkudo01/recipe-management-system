package com.example.recipeapp.model;

public class IngredientAmount {

    private Double value;  // 数値の量（不明な場合 null）
    private String unit;   // 単位（g, ml, 個など）
    private String raw;    // 自由表記（例: "大さじ1", "少々"）

    public IngredientAmount(String raw) {
        this.raw = raw;
    }

    public IngredientAmount(Double value, String unit, String raw) {
        this.value = value;
        this.unit = unit;
        this.raw = raw;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }
}
