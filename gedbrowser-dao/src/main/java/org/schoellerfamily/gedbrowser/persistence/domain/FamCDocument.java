package org.schoellerfamily.gedbrowser.persistence.domain;

import org.schoellerfamily.gedbrowser.datamodel.FamC;

/**
 * Represents the persisted form of fam c.
 *
 * @author Richard Schoeller
 */
public interface FamCDocument extends GedDocument<FamC> {
    @Override
    default void accept(final GedDocumentVisitor visitor) {
        visitor.visit(this);
    }
}
