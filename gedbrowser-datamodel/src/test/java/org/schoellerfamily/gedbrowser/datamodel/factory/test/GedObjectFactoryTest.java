package org.schoellerfamily.gedbrowser.datamodel.factory.test;

import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;
import org.schoellerfamily.gedobject.datamodel.factory.AbstractGedObjectFactory.GedObjectFactory;


/**
 * Contains tests for ged object factory.
 */

final class GedObjectFactoryTest {

    @SuppressWarnings("checkstyle:nowhitespaceafter")
    static Stream<Arguments> params() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Root root = builder.getRoot();

        return Arrays.stream(new Object[][] { { null, null, null, null },
            { null, null, "ROOT", null }, { null, "", "ROOT", null }, { null, null, "ROOT", "" },
            { root, null, null, null }, { root, null, "ROOT", null }, { root, "", "ROOT", null },
            { root, null, "ROOT", "" }, }).map(Arguments::of);
    }

    @ParameterizedTest
    @MethodSource("params")
    void testFactory(final GedObject parent, final String xref, final String tag,
        final String tail) {
        final GedObjectFactory factory = new GedObjectFactory();
        final GedObject gob = factory.create(parent, new ObjectId(xref), tag, tail);
        assertNull(gob, "should always return null");
    }
}
