package org.schoellerfamily.gedbrowser.datamodel.appender;

import org.schoellerfamily.gedbrowser.datamodel.Tail;

/**
 * Represents tail appender in the domain model.
 *
 * @author Richard Schoeller
 */
public final class TailAppender implements AppenderStrategy {
    /**
     * The Attribute that owns this appender.
     */
    private final Tail owner;

    /**
     * Creates a new TailAppender.
     *
     * @param owner the owner
     */
    public TailAppender(final Tail owner) {
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
