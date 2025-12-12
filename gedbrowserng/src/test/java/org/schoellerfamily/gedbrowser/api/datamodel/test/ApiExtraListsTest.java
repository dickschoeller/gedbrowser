package org.schoellerfamily.gedbrowser.api.datamodel.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiExtraLists;

/**
 * @author Dick Schoeller
 */
public class ApiExtraListsTest {
    /** */
    @Test
    public void testTypeMismatchNotEquals() {
        final ApiExtraLists o = new ApiExtraLists();
        assertNotEquals("", o, "object should not equal string");
    }

    /** */
    @Test
    public void testSameEquals() {
        final ApiExtraLists o = new ApiExtraLists();
        assertEquals(o, o, "object should equal self");
    }

    /** */
    @Test
    public void testSimpleEquals() {
        final ApiExtraLists o1 = new ApiExtraLists();
        final ApiExtraLists o2 = new ApiExtraLists();
        assertEquals(o1, o2, "simple object should equal identical");
    }

    /** */
    @Test
    public void testChangedNotEquals() {
        final ApiExtraLists o1 = new ApiExtraLists();
        o1.addAttribute(new ApiAttribute("attribute", "Changed"));
        final ApiExtraLists o2 = new ApiExtraLists();
        assertNotEquals(o1, o2, "changed lists should be different");
    }

    /** */
    @Test
    public void testChangedEquals() {
        final ApiExtraLists o1 = new ApiExtraLists();
        o1.addAttribute(new ApiAttribute("attribute", "Changed"));
        final ApiExtraLists o2 = new ApiExtraLists();
        o2.addAttribute(new ApiAttribute("attribute", "Changed"));
        assertEquals(o1, o2, "changed lists should be the same");
    }

    /** */
    @Test
    public void testFamcInOneNotEquals() {
        final ApiExtraLists o1 = new ApiExtraLists();
        o1.addAttribute(new ApiAttribute("famc", "F1"));
        final ApiExtraLists o2 = new ApiExtraLists();
        assertNotEquals(o1, o2, "famc lists should be different");
    }

    /** */
    @Test
    public void testDiffFamcNotEquals() {
        final ApiExtraLists o1 = new ApiExtraLists();
        o1.addAttribute(new ApiAttribute("famc", "F1"));
        final ApiExtraLists o2 = new ApiExtraLists();
        o1.addAttribute(new ApiAttribute("famc", "F2"));
        assertNotEquals(o1, o2, "famc lists should be different");
    }

    /** */
    @Test
    public void testFamcEquals() {
        final ApiExtraLists o1 = new ApiExtraLists();
        o1.addAttribute(new ApiAttribute("famc", "F1"));
        final ApiExtraLists o2 = new ApiExtraLists();
        o2.addAttribute(new ApiAttribute("famc", "F1"));
        assertEquals(o1, o2, "famc lists should be the same");
    }

    /** */
    @Test
    public void testFamsInOneNotEquals() {
        final ApiExtraLists o1 = new ApiExtraLists();
        o1.addAttribute(new ApiAttribute("fams", "F1"));
        final ApiExtraLists o2 = new ApiExtraLists();
        assertNotEquals(o1, o2, "famc lists should be different");
    }

    /** */
    @Test
    public void testDiffFamsNotEquals() {
        final ApiExtraLists o1 = new ApiExtraLists();
        o1.addAttribute(new ApiAttribute("fams", "F1"));
        final ApiExtraLists o2 = new ApiExtraLists();
        o1.addAttribute(new ApiAttribute("fams", "F2"));
        assertNotEquals(o1, o2, "famc lists should be different");
    }

    /** */
    @Test
    public void testFamsEquals() {
        final ApiExtraLists o1 = new ApiExtraLists();
        o1.addAttribute(new ApiAttribute("fams", "F1"));
        final ApiExtraLists o2 = new ApiExtraLists();
        o2.addAttribute(new ApiAttribute("fams", "F1"));
        assertEquals(o1, o2, "famc lists should be the same");
    }

    /** */
    @Test
    public void testRefnNotEquals() {
        final ApiExtraLists o1 = new ApiExtraLists();
        o1.addAttribute(new ApiAttribute("attribute", "Reference Number", "1"));
        final ApiExtraLists o2 = new ApiExtraLists();
        o2.addAttribute(new ApiAttribute("attribute", "Reference Number", "2"));
        assertNotEquals(o1, o2, "changed lists should be different");
    }

    /** */
    @Test
    public void testRefnEquals() {
        final ApiExtraLists o1 = new ApiExtraLists();
        o1.addAttribute(new ApiAttribute("attribute", "Reference Number", "1"));
        final ApiExtraLists o2 = new ApiExtraLists();
        o2.addAttribute(new ApiAttribute("attribute", "Reference Number", "1"));
        assertEquals(o1, o2, "changed lists should be the same");
    }
}