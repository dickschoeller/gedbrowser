package org.schoellerfamily.gedbrowser.datamodel.util.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.schoellerfamily.gedbrowser.datamodel.util.GetStringComparator;

/**
 * @author Dick Schoeller
 */
@RunWith(Parameterized.class)
public class GetStringComparatorTest {
    /** */
    private final String arg0;
    /** */
    private final String arg1;
    /** */
    private final Direction direction;

    /** */
    private static final Object[][] PARAMETERS = {
            {null, null, Direction.equalto},
            {null, "", Direction.lessthan},
            {"", null, Direction.greaterthan},
            {null, "A", Direction.lessthan},
            {"A", null, Direction.greaterthan},
            {"", "", Direction.equalto},
            {"I", "I", Direction.equalto},
            {"I", "J", Direction.lessthan},
            {"I9", "I10", Direction.lessthan},
            {"J", "I", Direction.greaterthan},
            {"I9", "I8", Direction.greaterthan},
            {"I7", "I8", Direction.lessthan},
            {"I100", "I99", Direction.greaterthan},
            {"I1J100", "I1J99", Direction.greaterthan},
            {"I1J99", "I1J100", Direction.lessthan},
            {"I1J99", "I1J991", Direction.lessthan},
            {"I1J991", "I1J99", Direction.greaterthan},
            {"&99", "&98", Direction.greaterthan},
            {"I99", "&98", Direction.greaterthan},
            {"I", "9", Direction.greaterthan},
            {"9", "I", Direction.lessthan},
            {"I01", "I10", Direction.lessthan},
            {"I01J", "I1K", Direction.greaterthan},
            {"999", "99", Direction.greaterthan},
            {"99", "999", Direction.lessthan},
    };

    /** */
    private enum Direction { greaterthan, lessthan, equalto };

    /**
     * @param arg0 the first value to compare
     * @param arg1 the second value to compare
     * @param direction the expected direction
     */
    public GetStringComparatorTest(final String arg0, final String arg1,
            final Direction direction) {
        this.arg0 = arg0;
        this.arg1 = arg1;
        this.direction = direction;
    }

    /**
     * @return the array of tests values
     */
    @Parameters
    @SuppressWarnings("PMD.MethodReturnsInternalArray")
    public static Object[][] params() {
        return PARAMETERS;
    }

    /** */
    @Test
    public void testComparator() {
        final GetStringComparator gsc = new GetStringComparator();
        assertMatches(gsc.compare(() -> arg0, () -> arg1), direction);
    }

    /**
     * @param result the result of a comparison
     * @param expectation the expected direction
     */
    private void assertMatches(final int result, final Direction expectation) {
        switch (expectation) {
        case greaterthan:
            assertTrue("result should be greater than 0", result > 0);
            break;
        case lessthan:
            assertTrue("result should be less than 0", result < 0);
            break;
        case equalto:
            assertEquals("result should be equal to 0", 0, result);
            break;
        default:
            fail("Unexpected direction");
            break;
        }
    }
}
