package org.schoellerfamily.gedbrowser.datamodel;

import lombok.Getter;
import lombok.RequiredArgsConstructor;



/**
 * Represents object id in the domain model.
 *
 * @author Richard Schoeller
 */
@RequiredArgsConstructor
@Getter
public final class ObjectId {
    /** */
    private final transient String idString;
}
