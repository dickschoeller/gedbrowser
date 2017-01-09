package org.schoellerfamily.gedbrowser.analytics.order;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dick Schoeller
 */
public class OrderAnalyzerResult {
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
     * @return true if no mismatches are found
     */
    public boolean isCorrect() {
        return correct;
    }

    /**
     * @param string the description of the current mismatch
     */
    public void addMismatch(final String string) {
        correct = false;
        mismatches.add(string);
    }

    /**
     * @return the list of mismatches
     */
    public List<String> getMismatches() {
        return mismatches;
    }
}
