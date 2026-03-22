package org.schoellerfamily.gedbrowser.datamodel.appender;

import org.schoellerfamily.gedbrowser.datamodel.Multimedia;

/**
 * Represents multimedia appender in the domain model.
 *
 * @author Richard Schoeller
 */
public final class MultimediaAppender implements AppenderStrategy {
    /**
     * The Multimedia that owns this appender.
     */
    private final Multimedia owner;

    /**
     * Creates a new MultimediaAppender.
     *
     * @param owner the owner
     */
    public MultimediaAppender(final Multimedia owner) {
        this.owner = owner;
    }

    /**
     * Executes append string.
     *
     * @param appendage the appendage
     */
    @Override
    public void appendString(final String appendage) {
        owner.setTail(owner.getTail().concat(appendage));
    }
}
