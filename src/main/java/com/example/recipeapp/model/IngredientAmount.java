package com.example.recipeapp.model;

/**
 * 材料の分量を表すクラス。
 *
 * 数値＋単位で扱える場合と、
 * 「少々」「大さじ1」などの自由表記の両方に対応する。
 *
 * 数量と単位を分離することで、将来的に複数レシピをまとめた
 *  材料総量（g・個数など）の集計を可能にしている。
 */
public class IngredientAmount {

    /** 数値量（不明・曖昧な場合は null） */
    private Double value;

    /** 単位（g, ml, 個 など） */
    private String unit;

    /** 元の表記（表示・LLM 用） */
    private String raw;

    /**
     * 自由表記のみの分量を生成する。
     *
     * @param raw 分量の文字列表現
     */
    public IngredientAmount(String raw) {
        this.raw = raw;
    }

    /**
     * 数値＋単位を含む分量を生成する。
     *
     * @param value 数値量
     * @param unit  単位
     * @param raw   元の表記
     */
    public IngredientAmount(Double value, String unit, String raw) {
        this.value = value;
        this.unit = unit;
        this.raw = raw;
    }

    // ===== Getter / Setter =====

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
