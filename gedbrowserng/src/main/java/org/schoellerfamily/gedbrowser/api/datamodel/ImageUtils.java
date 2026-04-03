package org.schoellerfamily.gedbrowser.api.datamodel;

import java.util.Locale;

/**
 * Represents image utils in the domain model.
 *
 * @author Richard Schoeller
 */
public class ImageUtils {
    /**
     * Indicates whether image wrapper.
     *
     * @param attr the attr
     * @return true if the condition is met; otherwise false
     */
    public boolean isImageWrapper(final ApiAttribute attr) {
        for (final ApiAttribute attribute : attr.getAttributes()) {
            if (this.isImage(attribute) || this.isVideo(attribute)
                    || this.isYouTube(attribute) || this.isImageWrapper(attribute)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Indicates whether image.
     *
     * @param attribute the attribute
     * @return true if the condition is met; otherwise false
     */
    public boolean isImage(final ApiAttribute attribute) {
        final String[] types = {
            "bmp", "gif", "ico", "jpg", "jpeg", "png", "tiff", "tif", "svg" };
        for (final String t : types) {
            if (attribute.getTail().toLowerCase(Locale.ENGLISH).endsWith(t)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Indicates whether video.
     *
     * @param attribute the attribute
     * @return true if the condition is met; otherwise false
     */
    public boolean isVideo(final ApiAttribute attribute) {
        final String[] types = {
            "avi", "m4v", "mov", "mp4", "mpg", "mpeg", "webm" };
        for (final String t : types) {
            if (attribute.getTail().toLowerCase(Locale.ENGLISH).endsWith(t)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Indicates whether you tube.
     *
     * @param attribute the attribute
     * @return true if the condition is met; otherwise false
     */
    public boolean isYouTube(final ApiAttribute attribute) {
        final String tail = attribute.getTail().toLowerCase(Locale.ENGLISH);
        return tail.contains("youtube.com/watch") || tail.contains("youtu.be/");
    }
}
