package org.schoellerfamily.gedbrowser.datamodel.appender;

import org.schoellerfamily.gedbrowser.datamodel.Attribute;

/**
 * Implements the means by which string are appended in attributes, which is to
 * the tail string.
 *
 * @author Dick Schoeller
 */
public final class AttributeAppender implements AppenderStrategy {
    /**
     * The Attribute that owns this appender.
     */
    private final transient Attribute owner;

    /**
     * Constructor.
     *
     * @param owner the Attribute that owns this appender.
     */
    public AttributeAppender(final Attribute owner) {
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