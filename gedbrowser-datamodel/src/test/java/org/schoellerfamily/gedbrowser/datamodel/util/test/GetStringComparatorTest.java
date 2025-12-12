package org.schoellerfamily.gedbrowser.datamodel.util.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.schoellerfamily.gedbrowser.datamodel.util.GetStringComparator;

/**
 * @author Dick Schoeller
 */
public class GetStringComparatorTest {
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
      * @return the stream of test values
      */
     @SuppressWarnings("PMD.MethodReturnsInternalArray")
     public static Stream<Arguments> params() {
         return Stream.of(PARAMETERS).map(a -> Arguments.of(a[0], a[1], a[2]));
     }

     /** */
     @ParameterizedTest
     @MethodSource("params")
     public void testComparator(final String arg0, final String arg1,
             final Direction direction) {
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
             assertTrue(result > 0, "result should be greater than 0");
             break;
         case lessthan:
             assertTrue(result < 0, "result should be less than 0");
             break;
         case equalto:
             assertEquals(0, result, "result should be equal to 0");
             break;
         default:
             fail("Unexpected direction");
             break;
         }
     }
 }