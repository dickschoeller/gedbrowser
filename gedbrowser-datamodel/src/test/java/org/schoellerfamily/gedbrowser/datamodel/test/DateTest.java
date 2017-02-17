package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.datamodel.Root;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings({ "PMD.ExcessivePublicCount" })
public final class DateTest {
    /** */
    private final transient Root root = new Root("Root");

    /** */
    @Test
    public void testDateNullParentGedObject() {
        final Date date1 = new Date(null);
        assertNullParent(date1);
    }

    /** */
    @Test
    public void testDateRootParentGedObject() {
        final Date date2 = new Date(root);
        assertRootParent(date2);
    }

    /**
     * @param date date to check.
     * @return true
     */
    private boolean assertRootParent(final Date date) {
        assertEquals("Expected root as parent", root, date.getParent());
        assertEquals("Expected empty string", "", date.getString());
        assertEquals("Expected empty string", "", date.getDate());
        return true;
    }

    /**
     * @param date date to check.
     * @return true
     */
    private boolean assertNullParent(final Date date) {
        assertNull("Expected null parent", date.getParent());
        assertEquals("Expected empty string", "", date.getString());
        assertEquals("Expected empty string", "", date.getDate());
        return true;
    }

    /** */
    @Test
    public void test1NullDateNullParent() {
        final Date date1 = new Date(null, null);
        assertNullParent(date1);
    }

    /** */
    @Test
    public void testNullDateRootParent() {
        final Date date2 = new Date(root, null);
        assertRootParent(date2);
    }

    /** */
    @Test
    public void testEmptyStringNullParent() {
        final Date date3 = new Date(null, "");
        assertNullParent(date3);
    }

    /** */
    @Test
    public void testEmptyStringRootParent() {
        final Date date4 = new Date(root, "");
        assertRootParent(date4);
    }

    /** */
    @Test
    public void testDateGedObjectString() {
        final Date date6 = new Date(root, "2 JAN 2013");
        assertEquals("Date mismatch", "2 JAN 2013", date6.getString());
    }

    /** */
    @Test
    public void testDateGedObjectStringNullParent() {
        final Date date5 = new Date(null, "1 JAN 2013");
        assertNull("Expected null parent", date5.getParent());
    }

    /** */
    @Test
    public void testDateGedObjectStringRootParent() {
        final Date date6 = new Date(root, "2 JAN 2013");
        assertEquals("Expected root as parent", root, date6.getParent());
    }

    /** */
    @Test
    public void testDateGedObjectDate() {
        final Date date6 = new Date(root, "2 JAN 2013");
        assertEquals("Date mismatch", "2 JAN 2013", date6.getDate());
    }

    /** */
    @Test
    public void testDateGedObjectDateNullParent() {
        final Date date5 = new Date(null, "1 JAN 2013");
        assertNull("Expected null parent", date5.getParent());
    }

    /** */
    @Test
    public void testDateGedObjectDateRootParent() {
        final Date date6 = new Date(root, "2 JAN 2013");
        assertEquals("Expected root as parent", root, date6.getParent());
    }

    /** */
    @Test
    public void testDateGedObjectSortDate() {
        final Date date6 = new Date(root, "02 JAN 2013");
        assertEquals("Date mismatch", "20130102", date6.getSortDate());
    }

    /** */
    @Test
    public void testDateGedObjectSortDateNullParent() {
        final Date date5 = new Date(null, "01 JAN 2013");
        assertNull("Expceted null parent", date5.getParent());
    }

    /** */
    @Test
    public void testDateGedObjectSortDateRootParent() {
        final Date date6 = new Date(root, "02 JAN 2013");
        assertEquals("Expected root as parent", root, date6.getParent());
    }

    /** */
    @Test
    public void testDateGedObjectSortDateNoDay() {
        final Date date6 = new Date(root, "MAR 2013");
        assertEquals("Date mismatch", "20130301", date6.getSortDate());
    }

    /** */
    @Test
    public void testDateGedObjectSortDateNoDayNullParent() {
        final Date date5 = new Date(null, "FEB 2013");
        assertNull("Expected null parent", date5.getParent());
    }

    /** */
    @Test
    public void testDateGedObjectSortDateNoDayRootParent() {
        final Date date6 = new Date(root, "MAR 2013");
        assertEquals("Expected root as parent", root, date6.getParent());
    }

    /** */
    @Test
    public void testDateGedObjectSortDateNoMonth() {
        final Date date5 = new Date(null, "2013");
        assertEquals("Date mismatch", "20130101", date5.getSortDate());
    }

    /** */
    @Test
    public void testDateGedObjectSortDateNoMonthNullParent() {
        final Date date5 = new Date(null, "2013");
        assertNull("Expected null parent", date5.getParent());
    }

    /** */
    @Test
    public void testDateGedObjectSortDateNoMonthRootParent() {
        final Date date6 = new Date(root, "2013");
        assertEquals("Expected root as parent", root, date6.getParent());
    }

    /** */
    @Test
    public void testDateGedObjectYearNoDay() {
        final Date date5 = new Date(null, "FEB 2013");
        assertEquals("Date mismatch", "2013", date5.getYear());
    }

    /** */
    @Test
    public void testDateGedObjectYearNoDayNullParent() {
        final Date date5 = new Date(null, "FEB 2013");
        assertNull("Parent should be null", date5.getParent());
    }

    /** */
    @Test
    public void testDateGetYearNoDay() {
        final Date date6 = new Date(root, "FEB 2014");
        assertEquals("Date mismatch", "2014", date6.getYear());
    }

    /** */
    @Test
    public void testDateGetYearAboutMonth() {
        final Date date6 = new Date(root, "ABT MAR 2014");
        assertEquals("Date mismatch", "2014", date6.getYear());
    }

    /** */
    @Test
    public void testDateGetYearAfterMonth() {
        final Date date6 = new Date(root, "AFT MAR 2014");
        assertEquals("Date mismatch", "2014", date6.getYear());
    }

    /** */
    @Test
    public void testDateGetYearBeforeMonth() {
        final Date date6 = new Date(root, "BEF MAR 2014");
        assertEquals("Date mismatch", "2014", date6.getYear());
    }

    /** */
    @Test
    public void testDateGetYearBetweenMonth() {
        final Date date6 = new Date(root, "BETWEEN FEB 2014 AND MAR 2015");
        assertEquals("Date mismatch", "2014", date6.getYear());
    }

    /** */
    @Test
    public void testDateGetYearNoMonth() {
        final Date date5 = new Date(root, "2013");
        assertEquals("Date mismatch", "2013", date5.getYear());
    }

    /** */
    @Test
    public void testDateGetYearAbout() {
        final Date date6 = new Date(root, "ABT 2014");
        assertEquals("Date mismatch", "2014", date6.getYear());
    }

    /** */
    @Test
    public void testDateGetYearAfter() {
        final Date date6 = new Date(root, "AFT 2014");
        assertEquals("Date mismatch", "2014", date6.getYear());
    }

    /** */
    @Test
    public void testDateGetYearBefore() {
        final Date date6 = new Date(root, "BEF 2014");
        assertEquals("Date mismatch", "2014", date6.getYear());
    }

    /** */
    @Test
    public void testDateGetYearBetween() {
        final Date date6 = new Date(root, "BETWEEN 2013 AND 2014");
        assertEquals("Date mismatch", "2013", date6.getYear());
    }

    /** */
    @Test
    public void testDateGetYearBrokenBetween() {
        final Date date6 = new Date(root, "BETWEEN 2013 2014");
        assertEquals("Date mismatch", "2013", date6.getYear());
    }

    /** */
    @Test
    public void testDateGetYearUnknown() {
        final Date date6 = new Date(root, "Unknown");
        assertEquals("Date mismatch", "Unknown", date6.getYear());
    }

    /** */
    @Test
    public void testDateGetYearJunk() {
        final Date date6 = new Date(root, "XYZZY PLUGH");
        assertEquals("Date mismatch", "Unknown", date6.getYear());
    }

    /** */
    @Test
    public void testDateGetSortDateNoDay() {
        final Date date6 = new Date(root, "FEB 2014");
        assertEquals("Date mismatch", "20140201", date6.getSortDate());
    }

    /** */
    @Test
    public void testDateGetSortDateAboutMonth() {
        final Date date6 = new Date(root, "ABT MAR 2014");
        assertEquals("Date mismatch", "20140301", date6.getSortDate());
    }

    /** */
    @Test
    public void testDateGetSortDateAfterMonth() {
        final Date date6 = new Date(root, "AFT MAR 2014");
        assertEquals("Date mismatch", "20140301", date6.getSortDate());
    }

    /** */
    @Test
    public void testDateGetSortDateBeforeMonth() {
        final Date date6 = new Date(root, "BEF MAR 2014");
        assertEquals("Date mismatch", "20140301", date6.getSortDate());
    }

    /** */
    @Test
    public void testDateGetSortDateBetweenMonth() {
        final Date date6 = new Date(root, "BETWEEN FEB 2014 AND MAR 2015");
        assertEquals("Date mismatch", "20140201", date6.getSortDate());
    }

    /** */
    @Test
    public void testDateGetSortDateNoMonth() {
        final Date date5 = new Date(root, "2013");
        assertEquals("Date mismatch", "20130101", date5.getSortDate());
    }

    /** */
    @Test
    public void testDateGetSortDateAbout() {
        final Date date6 = new Date(root, "ABT 2014");
        assertEquals("Date mismatch", "20140101", date6.getSortDate());
    }

    /** */
    @Test
    public void testDateGetSortDateAfter() {
        final Date date6 = new Date(root, "AFT 2014");
        assertEquals("Date mismatch", "20140101", date6.getSortDate());
    }

    /** */
    @Test
    public void testDateGetSortDateBefore() {
        final Date date6 = new Date(root, "BEF 2014");
        assertEquals("Date mismatch", "20140101", date6.getSortDate());
    }

    /** */
    @Test
    public void testDateGetSortDateBetween() {
        final Date date6 = new Date(root, "BETWEEN 2013 AND 2014");
        assertEquals("Date mismatch", "20130101", date6.getSortDate());
    }

    /** */
    @Test
    public void testDateGetSortDateBrokenBetween() {
        final Date date6 = new Date(root, "BETWEEN 2013 2014");
        assertEquals("Date mismatch", "20130101", date6.getSortDate());
    }

    /** */
    @Test
    public void testDateGetSortDateUnknown() {
        final Date date6 = new Date(root, "Unknown");
        assertEquals("Date mismatch", "Unknown", date6.getSortDate());
    }

    /** */
    @Test
    public void testDateGetSortDateJunk() {
        final Date date6 = new Date(root, "XYZZY PLUGH");
        assertEquals("Date mismatch", "Unknown", date6.getSortDate());
    }

    /** */
    @Test
    public void testDateGetEstimateDateNoDay() {
        final Date date6 = new Date(root, "FEB 2014");
        assertEquals("Date mismatch", "20140201", date6.getEstimateDate());
    }

    /** */
    @Test
    public void testDateGetEstimateDateAboutMonth() {
        final Date date6 = new Date(root, "ABT MAR 2014");
        assertEquals("Date mismatch", "20140301", date6.getEstimateDate());
    }

    /** */
    @Test
    public void testDateGetEstimateDateAfterMonth() {
        final Date date6 = new Date(root, "AFT MAR 2014");
        assertEquals("Date mismatch", "20140401", date6.getEstimateDate());
    }

    /** */
    @Test
    public void testDateGetEstimateDateBeforeMonth() {
        final Date date6 = new Date(root, "BEF MAR 2014");
        assertEquals("Date mismatch", "20140228", date6.getEstimateDate());
    }

    /** */
    @Test
    public void testDateGetEstimateDateBeforeMonthLeap() {
        final Date date6 = new Date(root, "BEF MAR 2016");
        assertEquals("Date mismatch", "20160229", date6.getEstimateDate());
    }

    /** */
    @Test
    public void testDateGetEstimateDateBetweenMonth() {
        final Date date6 = new Date(root, "BETWEEN FEB 2014 AND MAR 2015");
        assertEquals("Date mismatch", "20140201", date6.getEstimateDate());
    }

    /** */
    @Test
    public void testDateGetEstimateDateNoMonth() {
        final Date date5 = new Date(root, "2013");
        assertEquals("Date mismatch", "20130101", date5.getEstimateDate());
    }

    /** */
    @Test
    public void testDateGetEstimateDateAbout() {
        final Date date6 = new Date(root, "ABT 2014");
        assertEquals("Date mismatch", "20140101", date6.getEstimateDate());
    }

    /** */
    @Test
    public void testDateGetEstimateDateAfter() {
        final Date date6 = new Date(root, "AFT 2014");
        assertEquals("Date mismatch", "20150101", date6.getEstimateDate());
    }

    /** */
    @Test
    public void testDateGetEstimateDateBefore() {
        final Date date6 = new Date(root, "BEF 2014");
        assertEquals("Date mismatch", "20131231", date6.getEstimateDate());
    }

    /** */
    @Test
    public void testDateGetEstimateDateBetween() {
        final Date date6 = new Date(root, "BETWEEN 2013 AND 2014");
        assertEquals("Date mismatch", "20130101", date6.getEstimateDate());
    }

    /** */
    @Test
    public void testDateGetEstimateDateBrokenBetween() {
        final Date date6 = new Date(root, "BETWEEN 2013 2014");
        assertEquals("Date mismatch", "20130101", date6.getEstimateDate());
    }

    /** */
    @Test
    public void testDateGetEstimateDateUnknown() {
        final Date date6 = new Date(root, "Unknown");
        assertEquals("Date mismatch", "Unknown", date6.getEstimateDate());
    }

    /** */
    @Test
    public void testDateGetEstimateDateJunk() {
        final Date date6 = new Date(root, "XYZZY PLUGH");
        assertEquals("Date mismatch", "Unknown", date6.getEstimateDate());
    }

    /** */
    @Test
    public void testDateGetEstimateDateAfterSpecific() {
        final Date date6 = new Date(root, "AFT 31 MAR 2014");
        assertEquals("Date mismatch", "20140401", date6.getEstimateDate());
    }

    /** */
    @Test
    public void testDateGetEstimateDateBeforeSpecific() {
        final Date date6 = new Date(root, "BEF 01 MAR 2014");
        assertEquals("Date mismatch", "20140228", date6.getEstimateDate());
    }

    /** */
    @Test
    public void testDateFTM1() {
        final Date date = new Date(root, "(1-2 MAR 2014)");
        assertEquals("Date mismatch", "20140302", date.getEstimateDate());
    }

    /** */
    @Test
    public void testDateFTM2() {
        final Date date = new Date(root, "(Abt 1-2 MAR 2014)");
        assertEquals("Date mismatch", "20140302", date.getEstimateDate());
    }

    /** */
    @Test
    public void testDateFTM3() {
        final Date date = new Date(root, "(Bef 1-2 MAR 2014)");
        assertEquals("Date mismatch", "20140301", date.getEstimateDate());
    }

    /** */
    @Test
    public void testDateFTM4() {
        final Date date = new Date(root, "(Aft 1-2 MAR 2014)");
        assertEquals("Date mismatch", "20140303", date.getEstimateDate());
    }

    /** */
    @Test
    public void testDateFTM5() {
        final Date date = new Date(root, "Est 2 MAR 2014");
        assertEquals("Date mismatch", "20140302", date.getEstimateDate());
    }

    /** */
    @Test
    public void testDateFTM6() {
        final Date date = new Date(root, "FROM 1 MAR 2014 TO 10 MAR 2014");
        assertEquals("Date mismatch", "20140301", date.getEstimateDate());
    }

    /** */
    @Test
    public void testDateFTM7() {
        final Date date = new Date(root, "(FROM 1 MAR 2014 TO 10 MAR 2014)");
        assertEquals("Date mismatch", "20140301", date.getEstimateDate());
    }

    /** */
    @Test
    public void testDateFTM8() {
        final Date date = new Date(root, "(1 MAR 2014-10 MAR 2014)");
        assertEquals("Date mismatch", "20140301", date.getEstimateDate());
    }
}
