package org.schoellerfamily.gedbrowser.datamodel.appender;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;

/**
 * @author Dick Schoeller
 */
public final class GedAppender implements AppenderStrategy {
    /**
     * The GedObject that owns this appender.
     */
    private final transient GedObject owner;

    /**
     * Constructor.
     *
     * @param owner the GedObject that owns this appender.
     */
    public GedAppender(final GedObject owner) {
        this.owner = owner;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void appendString(final String appendage) {
        owner.setString(owner.getString().concat(appendage));
    }
}
