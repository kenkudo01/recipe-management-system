package com.example.recipeapp.ui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.TextArea;
import javafx.util.Duration;

public class ChatAnimator {

    private final TextArea target;
    private Timeline thinkingTimeline;

    public ChatAnimator(TextArea target) {
        this.target = target;
    }

    // 🤖 考え中…
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

    public void stopThinking() {
        if (thinkingTimeline != null) {
            thinkingTimeline.stop();
        }
    }

    // タイプライター表示
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
