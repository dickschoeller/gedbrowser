package org.schoellerfamily.gedbrowser.renderer;

/**
 * Renders ged resource not found output for display.
 *
 * @author Richard Schoeller
 */
public class GedResourceNotFoundRenderer extends RenderingContextRenderer {
    /** */
    private final Throwable throwable;

    /**
     * Creates a new GedResourceNotFoundRenderer.
     *
     * @param throwable the throwable
     * @param context the context
     */
    public GedResourceNotFoundRenderer(final Throwable throwable,
            final RenderingContext context) {
        super(context);
        this.throwable = throwable;
    }

    /**
     * Returns the string.
     *
     * @param omit the omit
     * @return the resulting string
     */
    @Override
    public String menuInsertions(final String omit) {
        // There are issues #243 and #244 that require filling this in.
        return "";
    }

    /**
     * Gets the message.
     *
     * @return the message
     */
    public String getMessage() {
        if (throwable == null) {
            return "Null exception";
        } else {
            return throwable.getMessage();
        }
    }

    /**
     * Gets the exception.
     *
     * @return the exception
     */
    public Throwable getException() {
        return throwable;
    }
}
