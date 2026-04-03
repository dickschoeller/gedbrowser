package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.users.UserRoleName;

import lombok.Getter;
import lombok.RequiredArgsConstructor;



/**
 * Renders rendering context output for display.
 *
 * @author Richard Schoeller
 */
@RequiredArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Getter
public abstract class RenderingContextRenderer implements Renderer {

    /** */
    private final RenderingContext renderingContext;

        /**
     * Gets the application name.
     *
     * @return the application name
     */
    @Override
    public final String getApplicationName() {
        return renderingContext.getApplicationName();
    }

    /**
     * Gets the application u r l.
     *
     * @return the application u r l
     */
    @Override
    public final String getApplicationURL() {
        return renderingContext.getApplicationURL();
    }

    /**
     * Gets the home url.
     *
     * @return the home url
     */
    @Override
    public final String getHomeUrl() {
        return renderingContext.getHomeURL();
    }

    /**
     * Gets the maintainer email.
     *
     * @return the maintainer email
     */
    @Override
    public final String getMaintainerEmail() {
        return renderingContext.getMaintainerEmail();
    }

    /**
     * Gets the maintainer name.
     *
     * @return the maintainer name
     */
    @Override
    public final String getMaintainerName() {
        return renderingContext.getMaintainerName();
    }

    /**
     * Gets the version.
     *
     * @return the version
     */
    @Override
    public final String getVersion() {
        return renderingContext.getVersion();
    }

    /**
     * Gets the user firstname.
     *
     * @return the user firstname
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
        return renderingContext.hasRole(UserRoleName.valueOf(role));
    }

    /**
     * Convert the string for use in HTML or URLs.
     *
     * @param input unescaped string.
     * @return the escaped string.
     */
    public static final String escapeString(final String input) {
        return input.replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("\n", "<br/>\n");
    }

    /**
     * Convert the string for use in HTML or URLs.
     *
     * @param delimiter a spacer string to add
     * @param input unescaped string.
     * @return the escaped string.
     */
    public static final String escapeString(final String delimiter,
            final String input) {
        final String escaped = escapeString(input);
        if (escaped.isEmpty()) {
            return escaped;
        }
        return delimiter + escaped;
    }

    /**
     * Convert the string for use in HTML or URLs.
     *
     * @param gedObject the object whose string we are going to escape.
     * @return the escaped string.
     */
    public static final String escapeString(final GedObject gedObject) {
        return escapeString(gedObject.getString());
    }
}
