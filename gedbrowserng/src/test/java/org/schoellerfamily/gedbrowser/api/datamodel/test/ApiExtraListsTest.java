package org.schoellerfamily.gedbrowser.api.datamodel.test;

import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * @author Dick Schoeller
 */
class ApiExtraListsTest {
    /** */
    @Test
    void testHashAndEquals() {
        EqualsVerifier.forClass(ApiExtraListsTest.class)
            .suppress(Warning.STRICT_INHERITANCE, Warning.INHERITED_DIRECTLY_FROM_OBJECT)
            .verify();
    }
}
