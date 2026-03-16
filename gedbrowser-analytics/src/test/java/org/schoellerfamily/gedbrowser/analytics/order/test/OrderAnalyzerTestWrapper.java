package org.schoellerfamily.gedbrowser.analytics.order.test;

import org.schoellerfamily.gedbrowser.analytics.order.AbstractOrderAnalyzer;
import org.schoellerfamily.gedbrowser.analytics.order.OrderAnalyzer;
import org.schoellerfamily.gedbrowser.analytics.order.OrderAnalyzerResult;
import org.schoellerfamily.gedbrowser.datamodel.Person;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Dick Schoeller
 */
@Slf4j
public final class OrderAnalyzerTestWrapper {

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

    private void dump(final Person person, final OrderAnalyzerResult result) {
        log.info("{}: {}",
            person.getName().getString(), getOkString(result));

        for (final String message : result.getMismatches()) {
            log.info("    {}", message);
        }
    }

    private String getOkString(final OrderAnalyzerResult result) {
        if (result.isCorrect()) {
            return "OK";
        }
        return "Not OK";
    }
}
