package org.schoellerfamily.gedbrowser.renderer;

/**
 * @author Dick Schoeller
 */
public class GedResourceNotFoundRenderer extends RenderingContextRenderer {
    /** */
    private final Throwable throwable;

    /**
     * @param throwable the cause
     * @param context the rendering context
     */
    public GedResourceNotFoundRenderer(final Throwable throwable,
            final RenderingContext context) {
        super(context);
        this.throwable = throwable;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String menuInsertions(final String omit) {
        // There are issues #243 and #244 that require filling this in.
        return "";
    }

    /**
     * @return the message to display
     */
    public String getMessage() {
        if (throwable == null) {
            return "Null exception";
        } else {
            return throwable.getMessage();
        }
    }

    /**
     * @return the exception that got us here.
     */
    public Throwable getException() {
        return throwable;
    }
}
