package org.schoellerfamily.gedbrowser.api.crud;

import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilderImpl;

/**
 * @author Richard Schoeller
 */
public interface BuilderCreator {
    /**
     * Creates a GedObjectBuilder for the given root. Override to provide
     * alternate implementations or mocks for testing.
     *
     * @param root the root object
     * @return a new GedObjectBuilder
     */
    default GedObjectBuilder createBuilder(final Root root) {
        return new GedObjectBuilderImpl(root);
    }
}
