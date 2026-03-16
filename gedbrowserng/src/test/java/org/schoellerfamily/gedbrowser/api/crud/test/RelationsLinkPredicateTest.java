package org.schoellerfamily.gedbrowser.api.crud.test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.api.crud.ParentCrud;
import org.schoellerfamily.gedbrowser.api.crud.SpouseCrud;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;

/**
 * Contains tests for relations link predicate.
 */
final class RelationsLinkPredicateTest {

    @Test
    void testParentCrudLinkMatchesWife() {
        final ParentCrud crud = new ParentCrud(null, null, null);
        final ApiAttribute wife = ApiAttribute.builder().type("wife").string("I1").build();
        assertTrue(crud.isTheLinkWeAreLookingFor(wife, "I1"));
    }

    @Test
    void testParentCrudLinkMatchesHusband() {
        final ParentCrud crud = new ParentCrud(null, null, null);
        final ApiAttribute husband = ApiAttribute.builder().type("husband").string("I2").build();
        assertTrue(crud.isTheLinkWeAreLookingFor(husband, "I2"));
    }

    @Test
    void testParentCrudLinkRejectsMismatchedType() {
        final ParentCrud crud = new ParentCrud(null, null, null);
        final ApiAttribute child = ApiAttribute.builder().type("child").string("I2").build();
        assertFalse(crud.isTheLinkWeAreLookingFor(child, "I2"));
    }

    @Test
    void testSpouseCrudLinkMatchesHusband() {
        final SpouseCrud crud = new SpouseCrud(null, null, null);
        final ApiAttribute husband = ApiAttribute.builder().type("husband").string("I3").build();
        assertTrue(crud.isTheLinkWeAreLookingFor(husband, "I3"));
    }

    @Test
    void testSpouseCrudLinkMatchesWife() {
        final SpouseCrud crud = new SpouseCrud(null, null, null);
        final ApiAttribute wife = ApiAttribute.builder().type("wife").string("I4").build();
        assertTrue(crud.isTheLinkWeAreLookingFor(wife, "I4"));
    }

    @Test
    void testSpouseCrudLinkRejectsMismatchedId() {
        final SpouseCrud crud = new SpouseCrud(null, null, null);
        final ApiAttribute wife = ApiAttribute.builder().type("wife").string("I4").build();
        assertFalse(crud.isTheLinkWeAreLookingFor(wife, "I5"));
    }
}
