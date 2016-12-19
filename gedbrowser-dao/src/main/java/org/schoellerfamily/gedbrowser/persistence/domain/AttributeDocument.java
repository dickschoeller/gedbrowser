package org.schoellerfamily.gedbrowser.persistence.domain;

import org.schoellerfamily.gedbrowser.datamodel.Attribute;

/**
 * @author Dick Schoeller
 */
public interface AttributeDocument extends GedDocument<Attribute> {
    /**
     * @return the tail string
     */
    String getTail();

    /**
     * @param tail the new tail string
     */
    void setTail(String tail);
}
