package org.schoellerfamily.gedbrowser.api.datamodel.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;
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
        assertNotEquals("object should not equal string", "", o);
    }

    /** */
    @Test
    public void testSameEquals() {
        final ApiExtraLists o = new ApiExtraLists();
        assertEquals("object should equal self", o, o);
    }

    /** */
    @Test
    public void testSimpleEquals() {
        final ApiExtraLists o1 = new ApiExtraLists();
        final ApiExtraLists o2 = new ApiExtraLists();
        assertEquals("simple object should equal identical", o1, o2);
    }

    /** */
    @Test
    public void testChangedNotEquals() {
        final ApiExtraLists o1 = new ApiExtraLists();
        o1.addAttribute(new ApiAttribute("attribute", "Changed"));
        final ApiExtraLists o2 = new ApiExtraLists();
        assertNotEquals("changed lists should be different", o1, o2);
    }

    /** */
    @Test
    public void testChangedEquals() {
        final ApiExtraLists o1 = new ApiExtraLists();
        o1.addAttribute(new ApiAttribute("attribute", "Changed"));
        final ApiExtraLists o2 = new ApiExtraLists();
        o2.addAttribute(new ApiAttribute("attribute", "Changed"));
        assertEquals("changed lists should be the same", o1, o2);
    }

    /** */
    @Test
    public void testFamcInOneNotEquals() {
        final ApiExtraLists o1 = new ApiExtraLists();
        o1.addAttribute(new ApiAttribute("famc", "F1"));
        final ApiExtraLists o2 = new ApiExtraLists();
        assertNotEquals("famc lists should be different", o1, o2);
    }

    /** */
    @Test
    public void testDiffFamcNotEquals() {
        final ApiExtraLists o1 = new ApiExtraLists();
        o1.addAttribute(new ApiAttribute("famc", "F1"));
        final ApiExtraLists o2 = new ApiExtraLists();
        o1.addAttribute(new ApiAttribute("famc", "F2"));
        assertNotEquals("famc lists should be different", o1, o2);
    }

    /** */
    @Test
    public void testFamcEquals() {
        final ApiExtraLists o1 = new ApiExtraLists();
        o1.addAttribute(new ApiAttribute("famc", "F1"));
        final ApiExtraLists o2 = new ApiExtraLists();
        o2.addAttribute(new ApiAttribute("famc", "F1"));
        assertEquals("famc lists should be the same", o1, o2);
    }

    /** */
    @Test
    public void testFamsInOneNotEquals() {
        final ApiExtraLists o1 = new ApiExtraLists();
        o1.addAttribute(new ApiAttribute("fams", "F1"));
        final ApiExtraLists o2 = new ApiExtraLists();
        assertNotEquals("famc lists should be different", o1, o2);
    }

    /** */
    @Test
    public void testDiffFamsNotEquals() {
        final ApiExtraLists o1 = new ApiExtraLists();
        o1.addAttribute(new ApiAttribute("fams", "F1"));
        final ApiExtraLists o2 = new ApiExtraLists();
        o1.addAttribute(new ApiAttribute("fams", "F2"));
        assertNotEquals("famc lists should be different", o1, o2);
    }

    /** */
    @Test
    public void testFamsEquals() {
        final ApiExtraLists o1 = new ApiExtraLists();
        o1.addAttribute(new ApiAttribute("fams", "F1"));
        final ApiExtraLists o2 = new ApiExtraLists();
        o2.addAttribute(new ApiAttribute("fams", "F1"));
        assertEquals("famc lists should be the same", o1, o2);
    }

    /** */
    @Test
    public void testRefnNotEquals() {
        final ApiExtraLists o1 = new ApiExtraLists();
        o1.addAttribute(new ApiAttribute("attribute", "Reference Number", "1"));
        final ApiExtraLists o2 = new ApiExtraLists();
        o2.addAttribute(new ApiAttribute("attribute", "Reference Number", "2"));
        assertNotEquals("changed lists should be different", o1, o2);
    }

    /** */
    @Test
    public void testRefnEquals() {
        final ApiExtraLists o1 = new ApiExtraLists();
        o1.addAttribute(new ApiAttribute("attribute", "Reference Number", "1"));
        final ApiExtraLists o2 = new ApiExtraLists();
        o2.addAttribute(new ApiAttribute("attribute", "Reference Number", "1"));
        assertEquals("changed lists should be the same", o1, o2);
    }
}
