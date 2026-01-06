package org.schoellerfamily.gedbrowser.analytics.order;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds the result of an order analysis.
 *
 * @author Dick Schoeller
 */
public final class OrderAnalyzerResult {
    /** Current status of the analysis. */
    private boolean correct;
    /** List of mismatch descriptions. */
    private final List<String> mismatches = new ArrayList<>();

    /**
     * Constructor.
     */
    public OrderAnalyzerResult() {
        correct = true;
    }

    /**
     * Check for mismatches.
     *
     * @return true if no mismatches are found
     */
    public boolean isCorrect() {
        return correct;
    }

    /**
     * Add the description of some mismatch to the list.
     *
     * @param string the description of the current mismatch
     */
    public void addMismatch(final String string) {
        correct = false;
        mismatches.add(string);
    }

    /**
     * Get the list of mismatches.
     *
     * @return the list of mismatches
     */
    public List<String> getMismatches() {
        return mismatches;
    }
}
