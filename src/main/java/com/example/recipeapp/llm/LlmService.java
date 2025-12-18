package com.example.recipeapp.llm;

import com.example.recipeapp.model.Recipe;

/**
 * LLM への問い合わせを行うアプリケーションサービス。
 *
 * PromptType と Recipe を受け取り、
 * プロンプト生成から LLM 呼び出しまでを仲介する。
 * UI 層は本クラスのみを通じて LLM を利用する。
 */
public class LlmService {

    /**
     * 指定されたプロンプト種別とレシピ情報をもとに、
     * LLM へ問い合わせを行う。
     *
     * @param type   問い合わせ内容の種別
     * @param recipe 対象となるレシピ
     * @return LLM の応答テキスト
     */
    public static String ask(PromptType type, Recipe recipe) {
        Prompt prompt = PromptBuilder.build(type, recipe);
        return LlmClient.ask(
                prompt.getSystem(),
                prompt.getUser()
        );
    }
}
