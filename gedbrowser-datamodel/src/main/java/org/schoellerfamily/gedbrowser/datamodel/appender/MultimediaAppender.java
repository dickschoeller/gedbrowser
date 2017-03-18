package org.schoellerfamily.gedbrowser.datamodel.appender;

import org.schoellerfamily.gedbrowser.datamodel.Multimedia;

/**
 * @author Dick Schoeller
 *
 */
public final class MultimediaAppender implements AppenderStrategy {
    /**
     * The Multimedia that owns this appender.
     */
    private final transient Multimedia owner;

    /**
     * Constructor.
     *
     * @param owner the Attribute that owns this appender.
     */
    public MultimediaAppender(final Multimedia owner) {
        this.owner = owner;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void appendString(final String appendage) {
        owner.setTail(owner.getTail().concat(appendage));
    }
}
