package org.schoellerfamily.gedbrowser.analytics.visitor;

import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.visitor.GetDateVisitor;

/**
 * @author Dick Schoeller
 */
public class IgnoreableProcessor {
    /**
     * Certain events have no time basis on the person.
     *
     * @param event the event
     * @return true if it is non-time event
     */
    protected boolean ignoreable(final Attribute event) {
        // Layed out like this because it is easier to understand
        // coverage. No performance differences expected compared
        // to tighter layout.
        if ("Sex".equals(event.getString())) {
            return true;
        }
        if ("Changed".equals(event.getString())) {
            return true;
        }
        if ("Ancestral File Number".equals(event.getString())) {
            return true;
        }
        if ("Title".equals(event.getString())) {
            return true;
        }
        if ("Attribute".equals(event.getString())) {
            // Only care about random attributes if they are dated
            final GetDateVisitor visitor = new GetDateVisitor();
            event.accept(visitor);
            return "".equals(visitor.getDate());
        }
        if ("Note".equals(event.getString())) {
            // Only care about notes if they are dated
            final GetDateVisitor visitor = new GetDateVisitor();
            event.accept(visitor);
            return "".equals(visitor.getDate());
        }
        return "Reference Number".equals(event.getString());
    }
}
