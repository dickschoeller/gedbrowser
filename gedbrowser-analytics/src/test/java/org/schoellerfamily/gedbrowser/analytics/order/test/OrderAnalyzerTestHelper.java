package org.schoellerfamily.gedbrowser.analytics.order.test;

import org.schoellerfamily.gedbrowser.analytics.order.AbstractOrderAnalyzer;
import org.schoellerfamily.gedbrowser.analytics.order.OrderAnalyzer;
import org.schoellerfamily.gedbrowser.analytics.order.OrderAnalyzerResult;
import org.schoellerfamily.gedbrowser.datamodel.Person;

/**
 * @author Dick Schoeller
 */
public final class OrderAnalyzerTestHelper {
    /**
     * Analyze and dump the result.
     *
     * @param person the person to analyze
     * @return the analysis result
     */
    public OrderAnalyzerResult analyze(final Person person) {
        final AbstractOrderAnalyzer orderAnalyzer = new OrderAnalyzer(person);
        final OrderAnalyzerResult result = orderAnalyzer.analyze();
        dump(person, result);
        return result;
    }

    /**
     * Dump the analysis result for this person.
     *
     * @param person the person whose analysis was done
     * @param result the result
     */
    public void dump(final Person person, final OrderAnalyzerResult result) {
        System.out.println();
        System.out.println("================================");
        System.out.println(
                person.getName().getString() + ": " + getOkString(result));
        for (final String message : result.getMismatches()) {
            System.out.println("    " + message);
        }
    }

    /**
     * @param result the result
     * @return appropriate string
     */
    public String getOkString(final OrderAnalyzerResult result) {
        if (result.isCorrect()) {
            return "OK";
        }
        return "Not OK";
    }
}
