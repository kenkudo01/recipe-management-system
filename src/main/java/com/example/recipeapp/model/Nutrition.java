package com.example.recipeapp.model;

/**
 * レシピの栄養情報をまとめて表すクラス。
 *
 * カロリーおよび主要な栄養素を保持し、
 * 表示や将来的な栄養計算の基礎データとして利用する。
 */
public class Nutrition {

    /** カロリー（kcal） */
    private int calories;

    /** たんぱく質量（g） */
    private double protein;

    /** 脂質量（g） */
    private double fat;

    /** 炭水化物量（g） */
    private double carbs;

    /**
     * 栄養情報を生成する。
     */
    public Nutrition(int calories, double protein, double fat, double carbs) {
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.carbs = carbs;
    }

    // ===== Getter / Setter =====

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getCarbs() {
        return carbs;
    }

    public void setCarbs(double carbs) {
        this.carbs = carbs;
    }
}
