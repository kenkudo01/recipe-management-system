package com.example.recipeapp.model;

/**
 * レシピを構成する材料を表すクラス。
 *
 * 材料名と分量情報を保持し、
 * レシピ表示や LLM プロンプト生成などで利用される。
 */
public class Ingredient {

    /** 材料名（例：玉ねぎ、鶏肉） */
    private String name;

    /** 分量情報 */
    private IngredientAmount amount;

    /**
     * 材料を生成する。
     *
     * @param name   材料名
     * @param amount 分量情報
     */
    public Ingredient(String name, IngredientAmount amount) {
        this.name = name;
        this.amount = amount;
    }

    // ===== Getter / Setter =====

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
