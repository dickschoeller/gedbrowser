package org.schoellerfamily.gedbrowser.api.crud.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.api.crud.CrudHelper;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiFamily;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson;

/**
 * Tests for {@link CrudHelper}.
 */
final class CrudHelperTest {

    /** */
    @Test
    void testSpouseAttributeReturnsHusbandForMale() {
        final CrudHelper helper = new CrudHelper();
        final ApiPerson.ApiPersonBuilder<?, ?> person = ApiPerson.builder()
            .string("I1")
            .attribute(ApiAttribute.builder().type("attribute").string("Sex").tail("M").build());

        final ApiAttribute spouse = helper.spouseAttribute(person);

        assertEquals("husband", spouse.getType());
    }

    /** */
    @Test
    void testSpouseAttributeReturnsWifeForFemale() {
        final CrudHelper helper = new CrudHelper();
        final ApiPerson.ApiPersonBuilder<?, ?> person = ApiPerson.builder()
            .string("I2")
            .attribute(ApiAttribute.builder().type("attribute").string("Sex").tail("F").build());

        final ApiAttribute spouse = helper.spouseAttribute(person);

        assertEquals("wife", spouse.getType());
    }

    /** */
    @Test
    void testSpouseAttributeDefaultsToHusbandWhenSexUnknown() {
        final CrudHelper helper = new CrudHelper();
        final ApiPerson.ApiPersonBuilder<?, ?> person = ApiPerson.builder()
            .string("I3")
            .attribute(ApiAttribute.builder().type("attribute").string("Sex").tail("X").build());

        final ApiAttribute spouse = helper.spouseAttribute(person);

        assertEquals("husband", spouse.getType());
    }

    /** */
    @Test
    void testChildAttributeLinksPersonId() {
        final CrudHelper helper = new CrudHelper();
        final ApiPerson.ApiPersonBuilder<?, ?> person = ApiPerson.builder().string("I4");
        final ApiAttribute child = helper.childAttribute(person);
        assertEquals("I4", child.getString());
    }

    /** */
    @Test
    void testFamcAttributeLinksFamilyId() {
        final CrudHelper helper = new CrudHelper();
        final ApiFamily.ApiFamilyBuilder<?, ?> family = ApiFamily.builder().string("F1");
        final ApiAttribute famc = helper.famcAttribute(family);
        assertEquals("F1", famc.getString());
    }

    /** */
    @Test
    void testFamsAttributeLinksFamilyId() {
        final CrudHelper helper = new CrudHelper();
        final ApiFamily.ApiFamilyBuilder<?, ?> family = ApiFamily.builder().string("F2");
        final ApiAttribute fams = helper.famsAttribute(family);
        assertEquals("F2", fams.getString());
    }
}
