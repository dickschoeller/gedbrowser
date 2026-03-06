package org.schoellerfamily.gedbrowser.datamodel;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Dick Schoeller
 */
@RequiredArgsConstructor
@Getter
public final class ObjectId {
    /** */
    private final transient String idString;
}
