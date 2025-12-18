package com.example.recipeapp.repository;

import com.example.recipeapp.model.Recipe;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

/**
 * レシピ情報を JSON ファイルから読み込むリポジトリクラス。
 *
 * JSON → Recipe オブジェクトへの変換のみを責務とし、
 * 読み込んだデータの加工や検証は行わない。
 */
public class RecipeLoader {

    /**
     * 指定されたパスの JSON ファイルからレシピ一覧を読み込む。
     *
     * @param path JSON ファイルのパス
     * @return 読み込まれた Recipe のリスト
     * @throws Exception ファイル読み込みやパースに失敗した場合
     */
    public static List<Recipe> load(String path) throws Exception {

        Gson gson = new Gson();

        // try-with-resources により Reader を安全にクローズ
        try (Reader reader = new FileReader(path)) {

            Type recipeListType =
                    new TypeToken<List<Recipe>>() {}.getType();

            List<Recipe> list = gson.fromJson(reader, recipeListType);

            // 読み込み結果の簡易ログ（デバッグ用）
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) == null) {
                    System.out.println(
                            "WARNING: RecipeLoader found null at index " + i
                    );
                    continue;
                }

            }

            return list;
        }
    }
}
