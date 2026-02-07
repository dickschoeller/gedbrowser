package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.datamodel.Root;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings({ "PMD.ExcessivePublicCount" })
final class DateTest {
    /** */
    private final transient Root root = new Root("Root");

    /** */
    @Test
    void testDateNullParentGedObject() {
        final Date date1 = new Date(null);
        assertNullParent(date1);
    }

    /** */
    @Test
    void testDateRootParentGedObject() {
        final Date date2 = new Date(root);
        assertRootParent(date2);
    }

    /**
     * @param date date to check.
     * @return true
     */
    private boolean assertRootParent(final Date date) {
        assertEquals(root, date.getParent(), "Expected root as parent");
        assertEquals("", date.getString(), "Expected empty string");
        assertEquals("", date.getDate(), "Expected empty string");
        return true;
    }

    /**
     * @param date date to check.
     * @return true
     */
    private boolean assertNullParent(final Date date) {
        assertNull(date.getParent(), "Expected null parent");
        assertEquals("", date.getString(), "Expected empty string");
        assertEquals("", date.getDate(), "Expected empty string");
        return true;
    }

    /** */
    @Test
    void test1NullDateNullParent() {
        final Date date1 = new Date(null, null);
        assertNullParent(date1);
    }

    /** */
    @Test
    void testNullDateRootParent() {
        final Date date2 = new Date(root, null);
        assertRootParent(date2);
    }

    /** */
    @Test
    void testEmptyStringNullParent() {
        final Date date3 = new Date(null, "");
        assertNullParent(date3);
    }

    /** */
    @Test
    void testEmptyStringRootParent() {
        final Date date4 = new Date(root, "");
        assertRootParent(date4);
    }

    /** */
    @Test
    void testDateGedObjectString() {
        final Date date6 = new Date(root, "2 JAN 2013");
        assertEquals("2 JAN 2013", date6.getString(), "Date mismatch");
    }

    /** */
    @Test
    void testDateGedObjectStringNullParent() {
        final Date date5 = new Date(null, "1 JAN 2013");
        assertNull(date5.getParent(), "Expected null parent");
    }

    /** */
    @Test
    void testDateGedObjectStringRootParent() {
        final Date date6 = new Date(root, "2 JAN 2013");
        assertEquals(root, date6.getParent(), "Expected root as parent");
    }

    /** */
    @Test
    void testDateGedObjectDate() {
        final Date date6 = new Date(root, "2 JAN 2013");
        assertEquals("2 JAN 2013", date6.getDate(), "Date mismatch");
    }

    /** */
    @Test
    void testDateGedObjectDateNullParent() {
        final Date date5 = new Date(null, "1 JAN 2013");
        assertNull(date5.getParent(), "Expected null parent");
    }

    /** */
    @Test
    void testDateGedObjectDateRootParent() {
        final Date date6 = new Date(root, "2 JAN 2013");
        assertEquals(root, date6.getParent(), "Expected root as parent");
    }

    /** */
    @Test
    void testDateGedObjectSortDate() {
        final Date date6 = new Date(root, "02 JAN 2013");
        assertEquals("20130102", date6.getSortDate(), "Date mismatch");
    }

    /** */
    @Test
    void testDateGedObjectSortDateNullParent() {
        final Date date5 = new Date(null, "01 JAN 2013");
        assertNull(date5.getParent(), "Expceted null parent");
    }

    /** */
    @Test
    void testDateGedObjectSortDateRootParent() {
        final Date date6 = new Date(root, "02 JAN 2013");
        assertEquals(root, date6.getParent(), "Expected root as parent");
    }

    /** */
    @Test
    void testDateGedObjectSortDateNoDay() {
        final Date date6 = new Date(root, "MAR 2013");
        assertEquals("20130301", date6.getSortDate(), "Date mismatch");
    }

    /** */
    @Test
    void testDateGedObjectSortDateNoDayNullParent() {
        final Date date5 = new Date(null, "FEB 2013");
        assertNull(date5.getParent(), "Expected null parent");
    }

    /** */
    @Test
    void testDateGedObjectSortDateNoDayRootParent() {
        final Date date6 = new Date(root, "MAR 2013");
        assertEquals(root, date6.getParent(), "Expected root as parent");
    }

    /** */
    @Test
    void testDateGedObjectSortDateNoMonth() {
        final Date date5 = new Date(null, "2013");
        assertEquals("20130101", date5.getSortDate(), "Date mismatch");
    }

    /** */
    @Test
    void testDateGedObjectSortDateNoMonthNullParent() {
        final Date date5 = new Date(null, "2013");
        assertNull(date5.getParent(), "Expected null parent");
    }

    /** */
    @Test
    void testDateGedObjectSortDateNoMonthRootParent() {
        final Date date6 = new Date(root, "2013");
        assertEquals(root, date6.getParent(), "Expected root as parent");
    }

    /** */
    @Test
    void testDateGedObjectYearNoDay() {
        final Date date5 = new Date(null, "FEB 2013");
        assertEquals("2013", date5.getYear(), "Date mismatch");
    }

    /** */
    @Test
    void testDateGedObjectYearNoDayNullParent() {
        final Date date5 = new Date(null, "FEB 2013");
        assertNull(date5.getParent(), "Parent should be null");
    }

    /** */
    @Test
    void testDateGetYearNoDay() {
        final Date date6 = new Date(root, "FEB 2014");
        assertEquals("2014", date6.getYear(), "Date mismatch");
    }

    /** */
    @Test
    void testDateGetYearAboutMonth() {
        final Date date6 = new Date(root, "ABT MAR 2014");
        assertEquals("2014", date6.getYear(), "Date mismatch");
    }

    /** */
    @Test
    void testDateGetYearAfterMonth() {
        final Date date6 = new Date(root, "AFT MAR 2014");
        assertEquals("2014", date6.getYear(), "Date mismatch");
    }

    /** */
    @Test
    void testDateGetYearBeforeMonth() {
        final Date date6 = new Date(root, "BEF MAR 2014");
        assertEquals("2014", date6.getYear(), "Date mismatch");
    }

    /** */
    @Test
    void testDateGetYearBetweenMonth() {
        final Date date6 = new Date(root, "BETWEEN FEB 2014 AND MAR 2015");
        assertEquals("2014", date6.getYear(), "Date mismatch");
    }

    /** */
    @Test
    void testDateGetYearNoMonth() {
        final Date date5 = new Date(root, "2013");
        assertEquals("2013", date5.getYear(), "Date mismatch");
    }

    /** */
    @Test
    void testDateGetYearAbout() {
        final Date date6 = new Date(root, "ABT 2014");
        assertEquals("2014", date6.getYear(), "Date mismatch");
    }

    /** */
    @Test
    void testDateGetYearAfter() {
        final Date date6 = new Date(root, "AFT 2014");
        assertEquals("2014", date6.getYear(), "Date mismatch");
    }

    /** */
    @Test
    void testDateGetYearBefore() {
        final Date date6 = new Date(root, "BEF 2014");
        assertEquals("2014", date6.getYear(), "Date mismatch");
    }

    /** */
    @Test
    void testDateGetYearBetween() {
        final Date date6 = new Date(root, "BETWEEN 2013 AND 2014");
        assertEquals("2013", date6.getYear(), "Date mismatch");
    }

    /** */
    @Test
    void testDateGetYearBrokenBetween() {
        final Date date6 = new Date(root, "BETWEEN 2013 2014");
        assertEquals("2013", date6.getYear(), "Date mismatch");
    }

    /** */
    @Test
    void testDateGetYearUnknown() {
        final Date date6 = new Date(root, "Unknown");
        assertEquals("Unknown", date6.getYear(), "Date mismatch");
    }

    /** */
    @Test
    void testDateGetYearJunk() {
        final Date date6 = new Date(root, "XYZZY PLUGH");
        assertEquals("Unknown", date6.getYear(), "Date mismatch");
    }

    /** */
    @Test
    void testDateGetSortDateNoDay() {
        final Date date6 = new Date(root, "FEB 2014");
        assertEquals("20140201", date6.getSortDate(), "Date mismatch");
    }

    /** */
    @Test
    void testDateGetSortDateAboutMonth() {
        final Date date6 = new Date(root, "ABT MAR 2014");
        assertEquals("20140301", date6.getSortDate(), "Date mismatch");
    }

    /** */
    @Test
    void testDateGetSortDateAfterMonth() {
        final Date date6 = new Date(root, "AFT MAR 2014");
        assertEquals("20140301", date6.getSortDate(), "Date mismatch");
    }

    /** */
    @Test
    void testDateGetSortDateBeforeMonth() {
        final Date date6 = new Date(root, "BEF MAR 2014");
        assertEquals("20140301", date6.getSortDate(), "Date mismatch");
    }

    /** */
    @Test
    void testDateGetSortDateBetweenMonth() {
        final Date date6 = new Date(root, "BETWEEN FEB 2014 AND MAR 2015");
        assertEquals("20140201", date6.getSortDate(), "Date mismatch");
    }

    /** */
    @Test
    void testDateGetSortDateNoMonth() {
        final Date date5 = new Date(root, "2013");
        assertEquals("20130101", date5.getSortDate(), "Date mismatch");
    }

    /** */
    @Test
    void testDateGetSortDateAbout() {
        final Date date6 = new Date(root, "ABT 2014");
        assertEquals("20140101", date6.getSortDate(), "Date mismatch");
    }

    /** */
    @Test
    void testDateGetSortDateAfter() {
        final Date date6 = new Date(root, "AFT 2014");
        assertEquals("20140101", date6.getSortDate(), "Date mismatch");
    }

    /** */
    @Test
    void testDateGetSortDateBefore() {
        final Date date6 = new Date(root, "BEF 2014");
        assertEquals("20140101", date6.getSortDate(), "Date mismatch");
    }

    /** */
    @Test
    void testDateGetSortDateBetween() {
        final Date date6 = new Date(root, "BETWEEN 2013 AND 2014");
        assertEquals("20130101", date6.getSortDate(), "Date mismatch");
    }

    /** */
    @Test
    void testDateGetSortDateBrokenBetween() {
        final Date date6 = new Date(root, "BETWEEN 2013 2014");
        assertEquals("20130101", date6.getSortDate(), "Date mismatch");
    }

    /** */
    @Test
    void testDateGetSortDateUnknown() {
        final Date date6 = new Date(root, "Unknown");
        assertEquals("Unknown", date6.getSortDate(), "Date mismatch");
    }

    /** */
    @Test
    void testDateGetSortDateJunk() {
        final Date date6 = new Date(root, "XYZZY PLUGH");
        assertEquals("Unknown", date6.getSortDate(), "Date mismatch");
    }

    /** */
    @Test
    void testDateGetEstimateDateNoDay() {
        final Date date6 = new Date(root, "FEB 2014");
        assertEquals("20140201", date6.getEstimateDate(), "Date mismatch");
    }

    /** */
    @Test
    void testDateGetEstimateDateAboutMonth() {
        final Date date6 = new Date(root, "ABT MAR 2014");
        assertEquals("20140301", date6.getEstimateDate(), "Date mismatch");
    }

    /** */
    @Test
    void testDateGetEstimateDateAfterMonth() {
        final Date date6 = new Date(root, "AFT MAR 2014");
        assertEquals("20140401", date6.getEstimateDate(), "Date mismatch");
    }

    /** */
    @Test
    void testDateGetEstimateDateBeforeMonth() {
        final Date date6 = new Date(root, "BEF MAR 2014");
        assertEquals("20140228", date6.getEstimateDate(), "Date mismatch");
    }

    /** */
    @Test
    void testDateGetEstimateDateBeforeMonthLeap() {
        final Date date6 = new Date(root, "BEF MAR 2016");
        assertEquals("20160229", date6.getEstimateDate(), "Date mismatch");
    }

    /** */
    @Test
    void testDateGetEstimateDateBetweenMonth() {
        final Date date6 = new Date(root, "BETWEEN FEB 2014 AND MAR 2015");
        assertEquals("20140201", date6.getEstimateDate(), "Date mismatch");
    }

    /** */
    @Test
    void testDateGetEstimateDateNoMonth() {
        final Date date5 = new Date(root, "2013");
        assertEquals("20130101", date5.getEstimateDate(), "Date mismatch");
    }

    /** */
    @Test
    void testDateGetEstimateDateAbout() {
        final Date date6 = new Date(root, "ABT 2014");
        assertEquals("20140101", date6.getEstimateDate(), "Date mismatch");
    }

    /** */
    @Test
    void testDateGetEstimateDateAfter() {
        final Date date6 = new Date(root, "AFT 2014");
        assertEquals("20150101", date6.getEstimateDate(), "Date mismatch");
    }

    /** */
    @Test
    void testDateGetEstimateDateBefore() {
        final Date date6 = new Date(root, "BEF 2014");
        assertEquals("20131231", date6.getEstimateDate(), "Date mismatch");
    }

    /** */
    @Test
    void testDateGetEstimateDateBetween() {
        final Date date6 = new Date(root, "BETWEEN 2013 AND 2014");
        assertEquals("20130101", date6.getEstimateDate(), "Date mismatch");
    }

    /** */
    @Test
    void testDateGetEstimateDateBrokenBetween() {
        final Date date6 = new Date(root, "BETWEEN 2013 2014");
        assertEquals("20130101", date6.getEstimateDate(), "Date mismatch");
    }

    /** */
    @Test
    void testDateGetEstimateDateUnknown() {
        final Date date6 = new Date(root, "Unknown");
        assertEquals("Unknown", date6.getEstimateDate(), "Date mismatch");
    }

    /** */
    @Test
    void testDateGetEstimateDateJunk() {
        final Date date6 = new Date(root, "XYZZY PLUGH");
        assertEquals("Unknown", date6.getEstimateDate(), "Date mismatch");
    }

    /** */
    @Test
    void testDateGetEstimateDateAfterSpecific() {
        final Date date6 = new Date(root, "AFT 31 MAR 2014");
        assertEquals("20140401", date6.getEstimateDate(), "Date mismatch");
    }

    /** */
    @Test
    void testDateGetEstimateDateBeforeSpecific() {
        final Date date6 = new Date(root, "BEF 01 MAR 2014");
        assertEquals("20140228", date6.getEstimateDate(), "Date mismatch");
    }

    /** */
    @Test
    void testDateFTM1() {
        final Date date = new Date(root, "(1-2 MAR 2014)");
        assertEquals("20140302", date.getEstimateDate(), "Date mismatch");
    }

    /** */
    @Test
    void testDateFTM2() {
        final Date date = new Date(root, "(Abt 1-2 MAR 2014)");
        assertEquals("20140302", date.getEstimateDate(), "Date mismatch");
    }

    /** */
    @Test
    void testDateFTM3() {
        final Date date = new Date(root, "(Bef 1-2 MAR 2014)");
        assertEquals("20140301", date.getEstimateDate(), "Date mismatch");
    }

    /** */
    @Test
    void testDateFTM4() {
        final Date date = new Date(root, "(Aft 1-2 MAR 2014)");
        assertEquals("20140303", date.getEstimateDate(), "Date mismatch");
    }

    /** */
    @Test
    void testDateFTM5() {
        final Date date = new Date(root, "Est 2 MAR 2014");
        assertEquals("20140302", date.getEstimateDate(), "Date mismatch");
    }

    /** */
    @Test
    void testDateFTM6() {
        final Date date = new Date(root, "FROM 1 MAR 2014 TO 10 MAR 2014");
        assertEquals("20140301", date.getEstimateDate(), "Date mismatch");
    }

    /** */
    @Test
    void testDateFTM7() {
        final Date date = new Date(root, "(FROM 1 MAR 2014 TO 10 MAR 2014)");
        assertEquals("20140301", date.getEstimateDate(), "Date mismatch");
    }

    /** */
    @Test
    void testDateFTM8() {
        final Date date = new Date(root, "(1 MAR 2014-10 MAR 2014)");
        assertEquals("20140301", date.getEstimateDate(), "Date mismatch");
    }
}
