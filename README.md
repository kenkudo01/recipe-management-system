# Recipe Management System

## 目次
- [概要（Project Overview）](#概要project-overview)
- [使用技術（Tech Stack）](#使用技術tech-stack)
- [セットアップ要件について（Setup Requirements）](#セットアップ要件についてsetup-requirements)
  - [Part 1・Part 2 のみを実行する場合](#part-1part-2-のみを実行する場合)
  - [Part 3（GUI アプリケーション）を実行する場合](#part-3gui-アプリケーションを実行する場合)
  - [Ollama について（Optional）](#ollama-についてoptional)
- [ディレクトリ構成（Directory Structure）](#ディレクトリ構成directory-structure)
- [実行方法（Execution）](#実行方法execution)
  - [Part 1・2（Java 実行）](#part-12java-実行)
  - [Part 3 アプリケーション](#part-3-アプリケーション)
- [データ構造の設計理由](#データ構造の設計理由)
- [AI利用について](#ai利用について)
- [テスト結果](#テスト結果)



## 概要（Project Overview）
本プロジェクトは、技術課題として実装した Java ベースのレシピ管理システムである。  
レシピデータの読み込み、管理、ソート、最適化を行う機能を備えており、  
標準ライブラリに依存しない独自のアルゴリズム実装を通して、  
データ構造設計力およびアルゴリズム理解を示すことを目的としている。

※ Windows の VSCode / PowerShell 環境では、
デフォルト文字コード（Shift-JIS）の影響により
日本語検索が正しく動作しない場合があります。

UTF-8 環境（IntelliJ、または UTF-8 設定済みターミナル）での
実行を推奨します。


---
## 使用技術（Tech Stack）

### 言語・実行環境
- Java 17  
- JavaFX 17（GUI 部分のみ / Part 3）

### ライブラリ
- Gson 2.10.1  
  - JSON 形式のレシピデータ読み込みに使用
- Ollama
  -Localhost 経由の LLM API

※ LLM 機能はオプションであり、  
　利用できない環境でもアプリケーションは起動可能。

## セットアップ要件について（Setup Requirements）

本プロジェクトは課題の実行範囲に応じて、必要なセットアップが異なる。

### Part 1・Part 2 のみを実行する場合
- Java（JDK）のみで実行可能
- JavaFX のセットアップは不要
- Ollama（LLM）環境は不要

アルゴリズム（ソート・ナップサック問題）および  
データ構造の検証は、Part 1・Part 2 のみで完結する。

### Part 3（GUI アプリケーション）を実行する場合
- JavaFX のセットアップが必要
- Ollama は **必須ではない**

Ollama（LLM）はレシピ補助提案機能にのみ使用されており、  
LLM が利用できない場合でもアプリケーション自体は起動可能である。

LLM が利用不可の場合は、以下のようなフォールバック挙動となる：
- アプリは通常通り起動・操作可能
- LLM 関連ボタンを押した際に警告メッセージを表示
- アプリケーション全体の動作には影響しない

---

### Ollama について（Optional）
Ollama はローカル環境で動作する LLM API として利用しているが、  
本プロジェクトでは **拡張機能（オプション）**として位置付けている。

将来的な LLM 切り替えや無効化を想定し、  
設定・検証・通信処理は独立したクラスとして設計されている。

[Ollama github](https://ollama.com/)


---


## ディレクトリ構成（Directory Structure）

```text
src/
 └─ main/java/com/example/recipeapp/
     ├─ app/         メインアプリケーション
     ├─ model/       レシピ関連モデル
     ├─ repository/  JSON ローダー
     ├─ util/        ソート・最適化アルゴリズム
resources/
 └─ sample_recipes.json
libs/
 └─ gson-2.10.1.jar
```
---

## 実行方法（Execution）
### Part 1・2（Java 実行）

```
java -cp "out/production/coding;libs/gson-2.10.1.jar" com.example.recipeapp.app.Main

```

### part 3 アプリケーション
```

java `
  --module-path "C:\javafx-sdk-17.0.17\lib" `
  --add-modules javafx.controls,javafx.fxml `
  -cp "out\production\coding;libs\gson-2.10.1.jar" `
  com.example.recipeapp.app.MainApp

```

※上手くいかない時はjaavafxのmodule pathを確認してください

---

## データ構造の設計理由
本システムは、家庭料理レシピを管理・閲覧するためのデスクトップアプリケーションである。
レシピ情報の表示、検索、並び替えに加え、LLM を用いた補助提案機能を備えている。
データ構造は MVC をベースとし、将来的な機能拡張を見据えて責務分離を重視して設計した。

IngredientAmount クラスでは「数値」「単位」「元の表記」を分離して保持している。
これは表示の柔軟性を確保しつつ、将来的に複数レシピを選択した際の
材料総量計算（g・ml 単位への集約）を可能にするためである。
現時点では元の表記を主に使用しているが、拡張時に計算ロジックを追加しやすい構造となっている。

LLM 機能については、設定・検証・通信・プロンプト生成を
それぞれ独立したクラスとして分離した。
これにより、モデルやエンドポイントの変更、
LLM 利用不可時のフォールバックにも柔軟に対応できる構造としている。

本システムのデータ構造は、現在の要件だけでなく、
レシピ総量計算や LLM の切り替えなど、将来的な拡張を見据えて設計されている。
各クラスの責務を明確に分離することで、
機能追加およびテストが容易な構成を実現している。

---

## AI利用について

使用ＬＬＭ；ChatGPT
本プロジェクトでは以下の目的で AI（ChatGPT）を利用しました：

- データ構造設計の検討補助  
- アルゴリズムの実装方針の議論  
- コード生成およびバグ修正のサポート  
- READMEの文章生成  
- Git 運用やブランチ戦略の最適化


---
## テスト結果

ソート・ナップサック問題　テスト結果
<img width="1242" height="422" alt="image" src="https://github.com/user-attachments/assets/ec225bd2-08f5-42ea-ba04-d433890c5abe" />

<img width="1190" height="400" alt="image" src="https://github.com/user-attachments/assets/3d82dc55-24a6-47a4-9acc-45c7c6dc31a2" />










