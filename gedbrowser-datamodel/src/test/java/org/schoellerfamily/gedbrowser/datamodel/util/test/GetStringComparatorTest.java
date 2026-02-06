package org.schoellerfamily.gedbrowser.datamodel.util.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.schoellerfamily.gedbrowser.datamodel.util.GetStringComparator;

/**
 * @author Dick Schoeller
 */
public final class GetStringComparatorTest {
    /** */
    @SuppressWarnings("checkstyle:nowhitespaceafter")
    private static final Object[][] PARAMETERS = { { null, null, Direction.EQUALTO },
        { null, "", Direction.LESSTHAN }, { "", null, Direction.GREATERTHAN },
        { null, "A", Direction.LESSTHAN }, { "A", null, Direction.GREATERTHAN },
        { "", "", Direction.EQUALTO }, { "I", "I", Direction.EQUALTO },
        { "I", "J", Direction.LESSTHAN }, { "I9", "I10", Direction.LESSTHAN },
        { "J", "I", Direction.GREATERTHAN }, { "I9", "I8", Direction.GREATERTHAN },
        { "I7", "I8", Direction.LESSTHAN }, { "I100", "I99", Direction.GREATERTHAN },
        { "I1J100", "I1J99", Direction.GREATERTHAN }, { "I1J99", "I1J100", Direction.LESSTHAN },
        { "I1J99", "I1J991", Direction.LESSTHAN }, { "I1J991", "I1J99", Direction.GREATERTHAN },
        { "&99", "&98", Direction.GREATERTHAN }, { "I99", "&98", Direction.GREATERTHAN },
        { "I", "9", Direction.GREATERTHAN }, { "9", "I", Direction.LESSTHAN },
        { "I01", "I10", Direction.LESSTHAN }, { "I01J", "I1K", Direction.GREATERTHAN },
        { "999", "99", Direction.GREATERTHAN }, { "99", "999", Direction.LESSTHAN }, };

    /** */
    private enum Direction {
        /** */
        GREATERTHAN,
        /** */
        LESSTHAN,
        /** */
        EQUALTO
    };

    /**
     * @return the stream of test values
     */
    @SuppressWarnings("PMD.MethodReturnsInternalArray")
    public static Stream<Arguments> params() {
        return Stream.of(PARAMETERS).map(a -> Arguments.of(a[0], a[1], a[2]));
    }

    @ParameterizedTest
    @MethodSource("params")
    void testComparator(final String arg0, final String arg1, final Direction direction) {
        final GetStringComparator gsc = new GetStringComparator();
        assertMatches(gsc.compare(() -> arg0, () -> arg1), direction);
    }

    private void assertMatches(final int result, final Direction expectation) {
        switch (expectation) {
        case GREATERTHAN:
            assertTrue(result > 0, "result should be greater than 0");
            break;
        case LESSTHAN:
            assertTrue(result < 0, "result should be less than 0");
            break;
        case EQUALTO:
            assertEquals(0, result, "result should be equal to 0");
            break;
        default:
            fail("Unexpected direction");
            break;
        }
    }
}
