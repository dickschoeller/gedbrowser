package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.datamodel.Root;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings({ "PMD.TooManyMethods" })
final class DateTest {
    private static final Root ROOT = new Root("Root");

    @Test
    void testDateNullParentGedObject() {
        final Date date1 = new Date(null);
        assertNullParent(date1);
    }

    @Test
    void testDateRootParentGedObject() {
        final Date date2 = new Date(ROOT);
        assertRootParent(date2);
    }

    private boolean assertRootParent(final Date date) {
        assertEquals(ROOT, date.getParent(), "Expected root as parent");
        assertEquals("", date.getString(), "Expected empty string");
        assertEquals("", date.getDate(), "Expected empty string");
        return true;
    }

    private boolean assertNullParent(final Date date) {
        assertNull(date.getParent(), "Expected null parent");
        assertEquals("", date.getString(), "Expected empty string");
        assertEquals("", date.getDate(), "Expected empty string");
        return true;
    }

    @Test
    void test1NullDateNullParent() {
        final Date date1 = new Date(null, null);
        assertNullParent(date1);
    }

    @Test
    void testNullDateRootParent() {
        final Date date2 = new Date(ROOT, null);
        assertRootParent(date2);
    }

    @Test
    void testEmptyStringNullParent() {
        final Date date3 = new Date(null, "");
        assertNullParent(date3);
    }

    @Test
    void testEmptyStringRootParent() {
        final Date date4 = new Date(ROOT, "");
        assertRootParent(date4);
    }

    @Test
    void testDateGedObjectString() {
        final Date date6 = new Date(ROOT, "2 JAN 2013");
        assertEquals("2 JAN 2013", date6.getString(), "Date mismatch");
    }

    @ParameterizedTest
    @MethodSource("dateGedObjectStringParentCases")
    void testDateGedObjectStringParent(final Root useRoot, final String dateString) {
        final Date date = new Date(useRoot, dateString);
        assertEquals(useRoot, date.getParent(), "Parent mismatch");
    }

    private static Stream<Arguments> dateGedObjectStringParentCases() {
        return Stream.of(
            Arguments.of(null, "1 JAN 2013"),
            Arguments.of(ROOT, "2 JAN 2013"),
            Arguments.of(null, "01 JAN 2013"),
            Arguments.of(ROOT, "02 JAN 2013"),
            Arguments.of(null, "FEB 2013"),
            Arguments.of(ROOT, "MAR 2013"),
            Arguments.of(null, "2013"),
            Arguments.of(ROOT, "2013")
        );
    }

    @Test
    void testDateGedObjectDate() {
        final Date date6 = new Date(ROOT, "2 JAN 2013");
        assertEquals("2 JAN 2013", date6.getDate(), "Date mismatch");
    }

    @Test
    void testDateGedObjectSortDate() {
        final Date date6 = new Date(ROOT, "02 JAN 2013");
        assertEquals("20130102", date6.getSortDate(), "Date mismatch");
    }

    @Test
    void testDateGedObjectSortDateNoDay() {
        final Date date6 = new Date(ROOT, "MAR 2013");
        assertEquals("20130301", date6.getSortDate(), "Date mismatch");
    }

    @Test
    void testDateGedObjectSortDateNoMonth() {
        final Date date5 = new Date(null, "2013");
        assertEquals("20130101", date5.getSortDate(), "Date mismatch");
    }

    @Test
    void testDateGedObjectYearNoDay() {
        final Date date5 = new Date(null, "FEB 2013");
        assertEquals("2013", date5.getYear(), "Date mismatch");
    }

    @Test
    void testDateGedObjectYearNoDayNullParent() {
        final Date date5 = new Date(null, "FEB 2013");
        assertNull(date5.getParent(), "Parent should be null");
    }

    @Test
    void testDateGetYearNoDay() {
        final Date date6 = new Date(ROOT, "FEB 2014");
        assertEquals("2014", date6.getYear(), "Date mismatch");
    }

    @ParameterizedTest(name = "getYear[{index}] {0} => {1}")
    @MethodSource("dateGetYearCases")
    void testDateGetYearParameterized(final String input, final String expectedYear) {
        final Date date = new Date(ROOT, input);
        assertEquals(expectedYear, date.getYear(), "Date mismatch");
    }

    private static Stream<Arguments> dateGetYearCases() {
        return Stream.of(
            Arguments.of("FEB 2013", "2013"),
            Arguments.of("FEB 2014", "2014"),
            Arguments.of("ABT MAR 2014", "2014"),
            Arguments.of("AFT MAR 2014", "2014"),
            Arguments.of("BEF MAR 2014", "2014"),
            Arguments.of("BETWEEN FEB 2014 AND MAR 2015", "2014"),
            Arguments.of("2013", "2013"),
            Arguments.of("ABT 2014", "2014"),
            Arguments.of("AFT 2014", "2014"),
            Arguments.of("BEF 2014", "2014"),
            Arguments.of("BETWEEN 2013 AND 2014", "2013"),
            Arguments.of("BETWEEN 2013 2014", "2013"),
            Arguments.of("Unknown", "Unknown"),
            Arguments.of("XYZZY PLUGH", "Unknown")
        );
    }

    @Test
    void testDateGetSortDateNoDay() {
        final Date date6 = new Date(ROOT, "FEB 2014");
        assertEquals("20140201", date6.getSortDate(), "Date mismatch");
    }

    @ParameterizedTest(name = "getSortDate[{index}] {0} => {1}")
    @MethodSource("dateGetSortDateCases")
    void testDateGetSortDateParameterized(final String input, final String expected) {
        final Date date = new Date(ROOT, input);
        assertEquals(expected, date.getSortDate(), "Date mismatch");
    }

    private static Stream<Arguments> dateGetSortDateCases() {
        return Stream.of(
            Arguments.of("02 JAN 2013", "20130102"),
            Arguments.of("01 JAN 2013", "20130101"),
            Arguments.of("FEB 2014", "20140201"),
            Arguments.of("ABT MAR 2014", "20140301"),
            Arguments.of("AFT MAR 2014", "20140301"),
            Arguments.of("BEF MAR 2014", "20140301"),
            Arguments.of("BETWEEN FEB 2014 AND MAR 2015", "20140201"),
            Arguments.of("2013", "20130101"),
            Arguments.of("ABT 2014", "20140101"),
            Arguments.of("AFT 2014", "20140101"),
            Arguments.of("BEF 2014", "20140101"),
            Arguments.of("BETWEEN 2013 AND 2014", "20130101"),
            Arguments.of("BETWEEN 2013 2014", "20130101"),
            Arguments.of("Unknown", "Unknown"),
            Arguments.of("XYZZY PLUGH", "Unknown")
        );
    }

    @Test
    void testDateGetEstimateDateNoDay() {
        final Date date6 = new Date(ROOT, "FEB 2014");
        assertEquals("20140201", date6.getEstimateDate(), "Date mismatch");
    }

    @ParameterizedTest(name = "getEstimateDate[{index}] {0} => {1}")
    @MethodSource("dateGetEstimateDateCases")
    void testDateGetEstimateDateParameterized(final String input, final String expected) {
        final Date date = new Date(ROOT, input);
        assertEquals(expected, date.getEstimateDate(), "Date mismatch");
    }

    private static Stream<Arguments> dateGetEstimateDateCases() {
        return Stream.of(
            Arguments.of("FEB 2014", "20140201"),
            Arguments.of("ABT MAR 2014", "20140301"),
            Arguments.of("AFT MAR 2014", "20140401"),
            Arguments.of("BEF MAR 2014", "20140228"),
            Arguments.of("BEF MAR 2016", "20160229"),
            Arguments.of("BETWEEN FEB 2014 AND MAR 2015", "20140201"),
            Arguments.of("2013", "20130101"),
            Arguments.of("ABT 2014", "20140101"),
            Arguments.of("AFT 2014", "20150101"),
            Arguments.of("BEF 2014", "20131231"),
            Arguments.of("BETWEEN 2013 AND 2014", "20130101"),
            Arguments.of("BETWEEN 2013 2014", "20130101"),
            Arguments.of("Unknown", "Unknown"),
            Arguments.of("XYZZY PLUGH", "Unknown"),
            Arguments.of("AFT 31 MAR 2014", "20140401"),
            Arguments.of("BEF 01 MAR 2014", "20140228"),
            Arguments.of("(1-2 MAR 2014)", "20140302"),
            Arguments.of("(Abt 1-2 MAR 2014)", "20140302"),
            Arguments.of("(Bef 1-2 MAR 2014)", "20140301"),
            Arguments.of("(Aft 1-2 MAR 2014)", "20140303"),
            Arguments.of("Est 2 MAR 2014", "20140302"),
            Arguments.of("FROM 1 MAR 2014 TO 10 MAR 2014", "20140301"),
            Arguments.of("(FROM 1 MAR 2014 TO 10 MAR 2014)", "20140301"),
            Arguments.of("(1 MAR 2014-10 MAR 2014)", "20140301")
        );
    }
}
