package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;

/**
 * Some base rendering behaviors outside of the context of GedObject rendering.
 * This provides methods that can be used in the context of error pages, etc.
 *
 * @author Dick Schoeller
 */
public abstract class RenderingContextRenderer implements Renderer {

    /** */
    private final RenderingContext renderingContext;

    /**
     * Constructor.
     *
     * @param renderingContext the context that we are rendering in
     */
    public RenderingContextRenderer(final RenderingContext renderingContext) {
        this.renderingContext = renderingContext;
    }

    /**
     * @return the user context we are rendering in
     */
    public final RenderingContext getRenderingContext() {
        return renderingContext;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getApplicationName() {
        return renderingContext.getApplicationName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getApplicationURL() {
        return renderingContext.getApplicationURL();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getHomeUrl() {
        return renderingContext.getHomeURL();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getMaintainerEmail() {
        return renderingContext.getMaintainerEmail();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getMaintainerName() {
        return renderingContext.getMaintainerName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getVersion() {
        return renderingContext.getVersion();
    }

    /**
     * @return user's first name
     */
    public final String getUserFirstname() {
        return renderingContext.getFirstname();
    }

    /**
     * Check if the user in the rendering context has a particular role.
     *
     * @param role role that we are looking for
     * @return true if the user has the role
     */
    public final boolean hasRole(final String role) {
        return renderingContext.hasRole(role);
    }

    /**
     * Convert the string for use in HTML or URLs.
     *
     * @param input unescaped string.
     * @return the escaped string.
     */
    protected static final String escapeString(final String input) {
        return input.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;").replaceAll("\n", "<br/>\n");
    }

    /**
     * Convert the string for use in HTML or URLs.
     *
     * @param gedObject the object whose string we are going to escape.
     * @return the escaped string.
     */
    protected static final String escapeString(final GedObject gedObject) {
        return escapeString(gedObject.getString());
    }
}
