package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.datamodel.Root;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings({ "PMD.ExcessivePublicCount", "PMD.TooManyMethods" })
public final class DateTest {
    /** */
    private final transient Root root = new Root(null, "Root");

    /** */
    @Test
    public void testDateNullParentGedObject() {
        final Date date1 = new Date(null);
        assertTrue(nullParentCheck(date1));
    }

    /** */
    @Test
    public void testDateRootParentGedObject() {
        final Date date2 = new Date(root);
        assertTrue(rootParentCheck(date2));
    }

    /**
     * @param date2 date to check.
     * @return true
     */
    private boolean rootParentCheck(final Date date2) {
        assertEquals(root, date2.getParent());
        assertEquals("", date2.getString());
        assertEquals("", date2.getDate());
        return true;
    }

    /**
     * @param date1 date to check.
     * @return true
     */
    private boolean nullParentCheck(final Date date1) {
        assertNull(date1.getParent());
        assertEquals("", date1.getString());
        assertEquals("", date1.getDate());
        return true;
    }

    /** */
    @Test
    public void testDateGedObjectString() {
        final Date date1 = new Date(null, null);
        nullParentCheck(date1);
        final Date date2 = new Date(root, null);
        rootParentCheck(date2);

        final Date date3 = new Date(null, "");
        nullParentCheck(date3);
        final Date date4 = new Date(root, "");
        rootParentCheck(date4);

        final Date date5 = new Date(null, "1 JAN 2013");
        assertNull(date5.getParent());
        assertEquals("1 JAN 2013", date5.getString());
        final Date date6 = new Date(root, "2 JAN 2013");
        assertEquals(root, date6.getParent());
        assertEquals("2 JAN 2013", date6.getString());
    }

    /** */
    @Test
    public void testDateGedObjectDate() {
        final Date date1 = new Date(null, null);
        nullParentCheck(date1);
        final Date date2 = new Date(root, null);
        rootParentCheck(date2);

        final Date date3 = new Date(null, "");
        nullParentCheck(date3);
        final Date date4 = new Date(root, "");
        rootParentCheck(date4);

        final Date date5 = new Date(null, "1 JAN 2013");
        assertNull(date5.getParent());
        assertEquals("1 JAN 2013", date5.getDate());
        final Date date6 = new Date(root, "2 JAN 2013");
        assertEquals(root, date6.getParent());
        assertEquals("2 JAN 2013", date6.getDate());
    }

    /** */
    @Test
    public void testDateGedObjectSortDate() {
        final Date date1 = new Date(null, null);
        nullParentCheck(date1);
        final Date date2 = new Date(root, null);
        rootParentCheck(date2);

        final Date date3 = new Date(null, "");
        nullParentCheck(date3);
        final Date date4 = new Date(root, "");
        rootParentCheck(date4);

        final Date date5 = new Date(null, "01 JAN 2013");
        assertNull(date5.getParent());
        assertEquals("20130101", date5.getSortDate());
        final Date date6 = new Date(root, "02 JAN 2013");
        assertEquals(root, date6.getParent());
        assertEquals("20130102", date6.getSortDate());
    }

    /** */
    @Test
    public void testDateGedObjectSortDateNoDay() {
        final Date date1 = new Date(null, null);
        nullParentCheck(date1);
        final Date date2 = new Date(root, null);
        rootParentCheck(date2);

        final Date date3 = new Date(null, "");
        nullParentCheck(date3);
        final Date date4 = new Date(root, "");
        rootParentCheck(date4);

        final Date date5 = new Date(null, "FEB 2013");
        assertNull(date5.getParent());
        assertEquals("20130201", date5.getSortDate());
        final Date date6 = new Date(root, "MAR 2013");
        assertEquals(root, date6.getParent());
        assertEquals("20130301", date6.getSortDate());
    }

    /** */
    @Test
    public void testDateGedObjectSortDateNoMonth() {
        final Date date1 = new Date(null, null);
        nullParentCheck(date1);
        final Date date2 = new Date(root, null);
        rootParentCheck(date2);

        final Date date3 = new Date(null, "");
        nullParentCheck(date3);
        final Date date4 = new Date(root, "");
        rootParentCheck(date4);

        final Date date5 = new Date(null, "2013");
        assertNull(date5.getParent());
        assertEquals("20130101", date5.getSortDate());
        final Date date6 = new Date(root, "2013");
        assertEquals(root, date6.getParent());
        assertEquals("20130101", date6.getSortDate());
    }

    /** */
    @Test
    public void testDateGedObjectYear() {
        final Date date1 = new Date(null, null);
        nullParentCheck(date1);
        final Date date2 = new Date(root, null);
        rootParentCheck(date2);

        final Date date3 = new Date(null, "");
        nullParentCheck(date3);
        final Date date4 = new Date(root, "");
        rootParentCheck(date4);

        final Date date5 = new Date(null, "01 JAN 2013");
        assertNull(date5.getParent());
        assertEquals("2013", date5.getYear());
        final Date date6 = new Date(root, "02 JAN 2014");
        assertEquals(root, date6.getParent());
        assertEquals("2014", date6.getYear());
    }

    /** */
    @Test
    public void testDateGedObjectYearNoDay() {
        final Date date1 = new Date(null, null);
        nullParentCheck(date1);
        final Date date2 = new Date(root, null);
        rootParentCheck(date2);

        final Date date3 = new Date(null, "");
        nullParentCheck(date3);
        final Date date4 = new Date(root, "");
        rootParentCheck(date4);

        final Date date5 = new Date(null, "FEB 2013");
        assertNull(date5.getParent());
        assertEquals("2013", date5.getYear());
    }

    /** */
    @Test
    public void testDateGetYearNoDay() {
        final Date date6 = new Date(root, "FEB 2014");
        assertEquals("2014", date6.getYear());
    }

    /** */
    @Test
    public void testDateGetYearAboutMonth() {
        final Date date6 = new Date(root, "ABT MAR 2014");
        assertEquals("2014", date6.getYear());
    }

    /** */
    @Test
    public void testDateGetYearAfterMonth() {
        final Date date6 = new Date(root, "AFT MAR 2014");
        assertEquals("2014", date6.getYear());
    }

    /** */
    @Test
    public void testDateGetYearBeforeMonth() {
        final Date date6 = new Date(root, "BEF MAR 2014");
        assertEquals("2014", date6.getYear());
    }

    /** */
    @Test
    public void testDateGetYearBetweenMonth() {
        final Date date6 = new Date(root, "BETWEEN FEB 2014 AND MAR 2015");
        assertEquals("2014", date6.getYear());
    }

    /** */
    @Test
    public void testDateGetYearNoMonth() {
        final Date date5 = new Date(root, "2013");
        assertEquals("2013", date5.getYear());
    }

    /** */
    @Test
    public void testDateGetYearAbout() {
        final Date date6 = new Date(root, "ABT 2014");
        assertEquals("2014", date6.getYear());
    }

    /** */
    @Test
    public void testDateGetYearAfter() {
        final Date date6 = new Date(root, "AFT 2014");
        assertEquals("2014", date6.getYear());
    }

    /** */
    @Test
    public void testDateGetYearBefore() {
        final Date date6 = new Date(root, "BEF 2014");
        assertEquals("2014", date6.getYear());
    }

    /** */
    @Test
    public void testDateGetYearBetween() {
        final Date date6 = new Date(root, "BETWEEN 2013 AND 2014");
        assertEquals("2013", date6.getYear());
    }

    /** */
    @Test
    public void testDateGetYearBrokenBetween() {
        final Date date6 = new Date(root, "BETWEEN 2013 2014");
        assertEquals("2013", date6.getYear());
    }

    /** */
    @Test
    public void testDateGetYearUnknown() {
        final Date date6 = new Date(root, "Unknown");
        assertEquals("Unknown", date6.getYear());
    }

    /** */
    @Test
    public void testDateGetYearJunk() {
        final Date date6 = new Date(root, "XYZZY PLUGH");
        assertEquals("Unknown", date6.getYear());
    }

    /** */
    @Test
    public void testDateGetSortDateNoDay() {
        final Date date6 = new Date(root, "FEB 2014");
        assertEquals("20140201", date6.getSortDate());
    }

    /** */
    @Test
    public void testDateGetSortDateAboutMonth() {
        final Date date6 = new Date(root, "ABT MAR 2014");
        assertEquals("20140301", date6.getSortDate());
    }

    /** */
    @Test
    public void testDateGetSortDateAfterMonth() {
        final Date date6 = new Date(root, "AFT MAR 2014");
        assertEquals("20140301", date6.getSortDate());
    }

    /** */
    @Test
    public void testDateGetSortDateBeforeMonth() {
        final Date date6 = new Date(root, "BEF MAR 2014");
        assertEquals("20140301", date6.getSortDate());
    }

    /** */
    @Test
    public void testDateGetSortDateBetweenMonth() {
        final Date date6 = new Date(root, "BETWEEN FEB 2014 AND MAR 2015");
        assertEquals("20140201", date6.getSortDate());
    }

    /** */
    @Test
    public void testDateGetSortDateNoMonth() {
        final Date date5 = new Date(root, "2013");
        assertEquals("20130101", date5.getSortDate());
    }

    /** */
    @Test
    public void testDateGetSortDateAbout() {
        final Date date6 = new Date(root, "ABT 2014");
        assertEquals("20140101", date6.getSortDate());
    }

    /** */
    @Test
    public void testDateGetSortDateAfter() {
        final Date date6 = new Date(root, "AFT 2014");
        assertEquals("20140101", date6.getSortDate());
    }

    /** */
    @Test
    public void testDateGetSortDateBefore() {
        final Date date6 = new Date(root, "BEF 2014");
        assertEquals("20140101", date6.getSortDate());
    }

    /** */
    @Test
    public void testDateGetSortDateBetween() {
        final Date date6 = new Date(root, "BETWEEN 2013 AND 2014");
        assertEquals("20130101", date6.getSortDate());
    }

    /** */
    @Test
    public void testDateGetSortDateBrokenBetween() {
        final Date date6 = new Date(root, "BETWEEN 2013 2014");
        assertEquals("20130101", date6.getSortDate());
    }

    /** */
    @Test
    public void testDateGetSortDateUnknown() {
        final Date date6 = new Date(root, "Unknown");
        assertEquals("Unknown", date6.getSortDate());
    }

    /** */
    @Test
    public void testDateGetSortDateJunk() {
        final Date date6 = new Date(root, "XYZZY PLUGH");
        assertEquals("Unknown", date6.getSortDate());
    }

    /** */
    @Test
    public void testDateGetEstimateDateNoDay() {
        final Date date6 = new Date(root, "FEB 2014");
        assertEquals("20140201", date6.getEstimateDate());
    }

    /** */
    @Test
    public void testDateGetEstimateDateAboutMonth() {
        final Date date6 = new Date(root, "ABT MAR 2014");
        assertEquals("20140301", date6.getEstimateDate());
    }

    /** */
    @Test
    public void testDateGetEstimateDateAfterMonth() {
        final Date date6 = new Date(root, "AFT MAR 2014");
        assertEquals("20140401", date6.getEstimateDate());
    }

    /** */
    @Test
    public void testDateGetEstimateDateBeforeMonth() {
        final Date date6 = new Date(root, "BEF MAR 2014");
        assertEquals("20140228", date6.getEstimateDate());
    }

    /** */
    @Test
    public void testDateGetEstimateDateBeforeMonthLeap() {
        final Date date6 = new Date(root, "BEF MAR 2016");
        assertEquals("20160229", date6.getEstimateDate());
    }

    /** */
    @Test
    public void testDateGetEstimateDateBetweenMonth() {
        final Date date6 = new Date(root, "BETWEEN FEB 2014 AND MAR 2015");
        assertEquals("20140201", date6.getEstimateDate());
    }

    /** */
    @Test
    public void testDateGetEstimateDateNoMonth() {
        final Date date5 = new Date(root, "2013");
        assertEquals("20130101", date5.getEstimateDate());
    }

    /** */
    @Test
    public void testDateGetEstimateDateAbout() {
        final Date date6 = new Date(root, "ABT 2014");
        assertEquals("20140101", date6.getEstimateDate());
    }

    /** */
    @Test
    public void testDateGetEstimateDateAfter() {
        final Date date6 = new Date(root, "AFT 2014");
        assertEquals("20150101", date6.getEstimateDate());
    }

    /** */
    @Test
    public void testDateGetEstimateDateBefore() {
        final Date date6 = new Date(root, "BEF 2014");
        assertEquals("20131231", date6.getEstimateDate());
    }

    /** */
    @Test
    public void testDateGetEstimateDateBetween() {
        final Date date6 = new Date(root, "BETWEEN 2013 AND 2014");
        assertEquals("20130101", date6.getEstimateDate());
    }

    /** */
    @Test
    public void testDateGetEstimateDateBrokenBetween() {
        final Date date6 = new Date(root, "BETWEEN 2013 2014");
        assertEquals("20130101", date6.getEstimateDate());
    }

    /** */
    @Test
    public void testDateGetEstimateDateUnknown() {
        final Date date6 = new Date(root, "Unknown");
        assertEquals("Unknown", date6.getEstimateDate());
    }

    /** */
    @Test
    public void testDateGetEstimateDateJunk() {
        final Date date6 = new Date(root, "XYZZY PLUGH");
        assertEquals("Unknown", date6.getEstimateDate());
    }

    /** */
    @Test
    public void testDateGetEstimateDateAfterSpecific() {
        final Date date6 = new Date(root, "AFT 31 MAR 2014");
        assertEquals("20140401", date6.getEstimateDate());
    }

    /** */
    @Test
    public void testDateGetEstimateDateBeforeSpecific() {
        final Date date6 = new Date(root, "BEF 01 MAR 2014");
        assertEquals("20140228", date6.getEstimateDate());
    }
}
