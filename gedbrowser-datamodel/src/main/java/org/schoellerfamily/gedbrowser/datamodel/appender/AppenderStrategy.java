package org.schoellerfamily.gedbrowser.datamodel.appender;

/**
 * @author Dick Schoeller
 */
public interface AppenderStrategy {
    /**
     * Append this text to the string being managed by a containing GedObject.
     *
     * @param appendage text to append to the string
     */
    void appendString(String appendage);
}
