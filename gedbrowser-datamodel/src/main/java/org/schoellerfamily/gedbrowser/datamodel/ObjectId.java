package org.schoellerfamily.gedbrowser.datamodel;

/**
 * @author Dick Schoeller
 */
public final class ObjectId {
    /** */
    private final transient String idString;

    /**
     * @param idString the ID string
     */
    public ObjectId(final String idString) {
        this.idString = idString;
    }

    /**
     * @return the cross reference string
     */
    public String getIdString() {
        return idString;
    }
}
