package org.schoellerfamily.gedobject.datamodel.factory;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Associates a GEDCOM token with a full string and a factory for GedObject creation.
 *
 * @author Richard Schoeller
 */
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
/* default */ class GedToken {
    /**
     * The full string value.
     */
    private final String fullString;
    /**
     * The factory value.
     */
    private final GedObjectFactory factory;
}
