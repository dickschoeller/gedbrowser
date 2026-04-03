package org.schoellerfamily.gedbrowser.datamodel.appender;

/**
 * Defines the contract for appender strategy.
 *
 * @author Richard Schoeller
 */
public interface AppenderStrategy {
    /**
     * Append this text to the string being managed by a containing GedObject.
     *
     * @param appendage text to append to the string
     */
    void appendString(String appendage);
}
