package com.example.recipeapp.ui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.TextArea;
import javafx.util.Duration;

/**
 * チャット表示用の簡易アニメーションを提供するクラス。
 *
 * 「考え中…」の表示や、タイプライター風のテキスト表示を担当し、
 * チャット UI の演出を分離する目的で利用される。
 */
public class ChatAnimator {

    /** 表示対象となる TextArea */
    private final TextArea target;

    /** 「考え中…」アニメーション用の Timeline */
    private Timeline thinkingTimeline;

    public ChatAnimator(TextArea target) {
        this.target = target;
    }

    /**
     * 「考え中…」アニメーションを開始する。
     * LLM 応答待ちの間にユーザーへ処理中であることを示す。
     */
    public void startThinking() {
        String[] frames = {
                "🤖 考え中",
                "🤖 考え中.",
                "🤖 考え中..",
                "🤖 考え中..."
        };

        thinkingTimeline = new Timeline();
        for (int i = 0; i < frames.length; i++) {
            int index = i;
            thinkingTimeline.getKeyFrames().add(
                    new KeyFrame(
                            Duration.millis(500 * i),
                            e -> target.setText(frames[index])
                    )
            );
        }

        thinkingTimeline.setCycleCount(Timeline.INDEFINITE);
        thinkingTimeline.play();
    }

    /**
     * 「考え中…」アニメーションを停止する。
     */
    public void stopThinking() {
        if (thinkingTimeline != null) {
            thinkingTimeline.stop();
        }
    }

    /**
     * テキストをタイプライター風に表示する。
     *
     * @param text 表示するテキスト
     */
    public void showTyping(String text) {
        target.clear();

        Timeline timeline = new Timeline();
        final int[] index = {0};

        KeyFrame frame = new KeyFrame(
                Duration.millis(30),
                e -> {
                    if (index[0] < text.length()) {
                        target.appendText(
                                String.valueOf(text.charAt(index[0]))
                        );
                        index[0]++;
                    }
                }
        );

        timeline.getKeyFrames().add(frame);
        timeline.setCycleCount(text.length());
        timeline.play();
    }
}
