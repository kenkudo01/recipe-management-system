package com.example.recipeapp.model;

/**
 * レシピのカテゴリ種別を表す列挙型。
 *
 * 主菜・副菜・汁物など、UI 表示やフィルタリングに使用する。
 */
public enum CategoryType {
    MAIN,
    SIDE,
    SOUP,
    DESSERT,
    SNACK,
    DRINK,
    OTHER
}
