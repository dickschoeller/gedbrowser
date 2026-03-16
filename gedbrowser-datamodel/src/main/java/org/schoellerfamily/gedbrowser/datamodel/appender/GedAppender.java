package org.schoellerfamily.gedbrowser.datamodel.appender;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;

import lombok.RequiredArgsConstructor;

/**
 * @author Dick Schoeller
 */
@RequiredArgsConstructor
public final class GedAppender implements AppenderStrategy {
    /**
     * The GedObject that owns this appender.
     */
    private final transient GedObject owner;

    /**
     * Executes append string.
     *
     * @param appendage the appendage
     */
    @Override
    public void appendString(final String appendage) {
        owner.setString(owner.getString().concat(appendage));
    }
}
