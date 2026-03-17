package org.schoellerfamily.gedbrowser.api.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.api.DummyEncoder;

/**
 * Contains tests for dummy encoder.
 */
final class DummyEncoderTest {

    @Test
    void testEncodeReturnsRawPassword() {
        final DummyEncoder encoder = new DummyEncoder();
        assertEquals("secret", encoder.encode("secret"));
    }

    @Test
    void testMatchesReturnsTrueForBothNull() {
        final DummyEncoder encoder = new DummyEncoder();
        assertTrue(encoder.matches(null, null));
    }

    @Test
    void testMatchesReturnsFalseForNullEncodedPassword() {
        final DummyEncoder encoder = new DummyEncoder();
        assertFalse(encoder.matches("secret", null));
    }

    @Test
    void testMatchesReturnsFalseForNullRawPassword() {
        final DummyEncoder encoder = new DummyEncoder();
        assertFalse(encoder.matches(null, "secret"));
    }

    @Test
    void testMatchesReturnsTrueForSamePassword() {
        final DummyEncoder encoder = new DummyEncoder();
        assertTrue(encoder.matches("secret", "secret"));
    }

    @Test
    void testMatchesReturnsFalseForDifferentPassword() {
        final DummyEncoder encoder = new DummyEncoder();
        assertFalse(encoder.matches("secret", "other"));
    }
}
