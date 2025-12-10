# Recipe Management System

## 概要（Project Overview）
本プロジェクトは、技術課題として実装した Java ベースのレシピ管理システムです。  
レシピデータの読み込み、管理、ソート、最適化を行う機能を備えており、  
標準ライブラリに依存しない独自のアルゴリズム実装を通して  
データ構造設計力とアルゴリズム理解を示すことを目的としています。

---

## 機能一覧（Features）
- JSON 形式でのレシピデータ読み込み（Gson を使用）
- レシピモデル（Recipe / Ingredient / IngredientAmount / Nutrition / CategoryType）の設計
- 挿入ソート（Insertion Sort）による独自のソート機能  
  - ソートキー：ID / 名前 / カロリー / 調理時間
- 2 次元 0-1 ナップサック問題の解法を用いた最適レシピ選択  
  - 制約：総カロリー、総調理時間  
  - 最大化：タンパク質量

---

## ディレクトリ構成（Directory Structure）
src/
main/java/com/example/recipeapp/
app/ - メインアプリケーション（Main）
model/ - レシピ関連のモデルクラス
repository/ - JSON ローダー（RecipeLoader）
util/ - アルゴリズム関連（Sorter, Knapsack）
resources/
sample_recipes.json
libs/
gson-2.10.1.jar


---

## 実行例（Sample Output）

### ▼レシピ読み込み
=== Recipe App 起動 ===
読み込んだレシピ数: 5
--- 最初のレシピ情報 ---
ID: 1
名前: サラダ
材料数: 4


### ▼ソート結果（例：カロリー昇順）
=== Sorted by calories (asc) ===
1: サラダ (200 kcal)
3: カレー (600 kcal)
2: パスタ (700 kcal)



### ▼ナップサック最適化結果
=== Knapsack ===
最適なレシピID: [1, 2]
名前: サラダ
名前: パスタ
合計タンパク質: 55



---

## データ構造設計（Design – Part 1）

### ● Recipe（可変オブジェクト）
- ID、名称、説明、提供人数、調理時間  
- 複数材料、手順、分類、栄養情報を保持

### ● Ingredient / IngredientAmount
- 材料名とその量を分離したモデル  
- 量をオブジェクト化することで、将来的な構造化数値や単位変換に対応可能

### ● Nutrition
- カロリー、タンパク質、脂質、炭水化物などを保持  
- 栄養最適化アルゴリズムで利用

### ● CategoryType（enum + OTHER）
- 主食、主菜、副菜、汁物、デザートなど  
- `OTHER` により将来拡張にも対応

---

## アルゴリズム設計（Algorithms – Part 2）

### ● 挿入ソート（Insertion Sort）
- 安定ソートで実装が簡潔  
- 要件で「標準ソート関数禁止」のため手実装  
- ID・名前・カロリー・調理時間のいずれでも比較可能な汎用設計

### ● 二次元 0-1 ナップサック（DP）
- 制約：カロリーと調理時間  
- 目的：総タンパク質の最大化  
- DP テーブルを用いて最適解を探索し、バックトラックで選択レシピを復元

---

## AI 利用について（AI Usage）
本プロジェクトでは以下の目的で AI（ChatGPT）を利用しました：

- データ構造設計の検討補助  
- アルゴリズムの実装方針の議論  
- コード生成およびバグ修正のサポート  
- README やコミットメッセージの文章生成  
- Git 運用やブランチ戦略の最適化

使用した AI：ChatGPT  

---












