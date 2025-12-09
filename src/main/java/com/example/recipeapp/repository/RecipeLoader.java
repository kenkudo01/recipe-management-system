package com.example.recipeapp.repository;

import com.example.recipeapp.model.CategoryType;
import com.example.recipeapp.model.Ingredient;
import com.example.recipeapp.model.IngredientAmount;
import com.example.recipeapp.model.Nutrition;
import com.example.recipeapp.model.Recipe;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

public class RecipeLoader {

    public static List<Recipe> load(String path) throws Exception {

        Gson gson = new Gson();

        Reader reader = new FileReader(path);

        Type recipeListType = new TypeToken<List<Recipe>>(){}.getType();
        List<Recipe> recipes = gson.fromJson(reader, recipeListType);

        reader.close();
        return recipes;
    }
}