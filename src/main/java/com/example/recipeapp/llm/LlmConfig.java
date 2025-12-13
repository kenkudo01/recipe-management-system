package com.example.recipeapp.llm;

/**
 * LLM (Ollama) configuration parameters.
 * These values control model behavior at inference time.
 */
public class LlmConfig {

    /** Ollama chat API endpoint (localhost) */
    public static final String ENDPOINT =
            "http://localhost:11434/api/chat";

    /** Model name installed in Ollama */
    public static final String MODEL = "lla";

    /**
     * Sampling temperature.
     * Controls randomness / creativity of the output.
     *
     * Low (0.1–0.3): deterministic, stable, practical
     * Medium (0.4–0.6): balanced
     * High (0.7+): creative, less predictable
     */
    public static final double TEMPERATURE = 0.3;

    /**
     * Top-p (nucleus sampling).
     * Limits token selection to a cumulative probability mass.
     *
     * Typical range: 0.7 – 1.0
     * Lower values make output more conservative.
     */
    public static final double TOP_P = 0.9;

    /**
     * Repetition penalty.
     * Penalizes repeating the same tokens or phrases.
     *
     * 1.0  : no penalty (default)
     * 1.05–1.2 : slight repetition suppression (recommended)
     * 1.5+ : strong suppression (may hurt fluency)
     */
    public static final double REPEAT_PENALTY = 1.1;

    /**
     * Maximum number of tokens in the generated response.
     * Acts as a safety limit to prevent overly long outputs.
     */
    public static final int MAX_TOKENS = 200;

    // ----------------------------------------------------
    // Optional / advanced parameters (not always needed)
    // ----------------------------------------------------

    /**
     * Whether to use streaming responses.
     *
     * true  : tokens are sent incrementally (harder to handle)
     * false : response is returned all at once (recommended for GUI apps)
     */
    // public static final boolean STREAM = false;

    /**
     * Stop sequences.
     * Generation will stop when any of these strings appear.
     *
     * Useful for strict output control, but often unnecessary.
     */
    // public static final String[] STOP = { "\n\n\n" };

}
