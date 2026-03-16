package org.schoellerfamily.gedbrowser.datamodel.appender;

import org.schoellerfamily.gedbrowser.datamodel.Tail;

/**
 * Implements the means by which string are appended in attributes, which is to
 * the tail string.
 *
 * @author Dick Schoeller
 */
public final class TailAppender implements AppenderStrategy {
    /**
     * The Attribute that owns this appender.
     */
    private final transient Tail owner;

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
