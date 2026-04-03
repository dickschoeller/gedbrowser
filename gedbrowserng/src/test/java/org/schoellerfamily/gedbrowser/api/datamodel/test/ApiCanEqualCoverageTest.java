package org.schoellerfamily.gedbrowser.api.datamodel.test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiHasImages;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSource;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSubmission;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSubmitter;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiTail;



/**
 * Contains tests for api can equal coverage.
 */
@SuppressWarnings("PMD.TooManyMethods")
final class ApiCanEqualCoverageTest {

    @Test
    void testApiHasImagesCanEqualSameType() {
        final ApiHasImages value = ApiHasImages.builder().type("x").string("1").build();
        assertTrue(value.canEqual(ApiHasImages.builder().type("y").string("2").build()));
    }

    @Test
    void testApiHasImagesCanEqualDifferentType() {
        final ApiHasImages value = ApiHasImages.builder().type("x").string("1").build();
        assertFalse(value.canEqual(ApiObject.builder().type("y").string("2").build()));
    }

    @Test
    void testApiTailCanEqualSameType() {
        final ApiTail value = ApiTail.builder().type("x").string("1").tail("t").build();
        assertTrue(value.canEqual(ApiTail.builder().type("x").string("2").tail("u").build()));
    }

    @Test
    void testApiTailCanEqualDifferentType() {
        final ApiTail value = ApiTail.builder().type("x").string("1").tail("t").build();
        assertFalse(value.canEqual(ApiObject.builder().type("y").string("2").build()));
    }

    @Test
    void testApiSubmissionCanEqualDifferentType() {
        final ApiSubmission value = ApiSubmission.builder().type("submission").string("S1").build();
        assertFalse(value.canEqual(ApiObject.builder().type("object").string("O1").build()));
    }

    @Test
    void testApiSubmitterCanEqualDifferentType() {
        final ApiSubmitter value = ApiSubmitter.builder().type("submitter").string("SUB1").build();
        assertFalse(value.canEqual(ApiObject.builder().type("object").string("O1").build()));
    }

    @Test
    void testApiSourceCanEqualDifferentType() {
        final ApiSource value = ApiSource.builder().type("source").string("S1").title("t").build();
        assertFalse(value.canEqual(ApiObject.builder().type("object").string("O1").build()));
    }

    @Test
    void testApiPersonCanEqualDifferentType() {
        final ApiPerson value = ApiPerson.builder().type("person").string("I1").build();
        assertFalse(value.canEqual(ApiObject.builder().type("object").string("O1").build()));
    }
}
