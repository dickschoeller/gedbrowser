package org.schoellerfamily.gedbrowser.persistence.domain;

import org.schoellerfamily.gedbrowser.datamodel.Multimedia;

/**
 * @author Dick Schoeller
 */
public interface MultimediaDocument extends GedDocument<Multimedia> {
    /**
     * @return the tail string
     */
    String getTail();

    /**
     * @param tail the new tail string
     */
    void setTail(String tail);
}
