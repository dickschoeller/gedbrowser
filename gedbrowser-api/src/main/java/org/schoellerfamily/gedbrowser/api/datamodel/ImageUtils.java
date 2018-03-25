package org.schoellerfamily.gedbrowser.api.datamodel;

import java.util.Locale;

/**
 * @author Dick Schoeller
 */
public class ImageUtils {
    /**
     * @param attr the attribute to check
     * @return true if it wraps an image
     */
    public boolean isImageWrapper(final ApiAttribute attr) {
        for (final ApiAttribute attribute : attr.getAttributes()) {
            if (this.isImage(attribute) || this.isImageWrapper(attribute)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param attribute the attribute to check
     * @return true if it is a reference to an image file type
     */
    private boolean isImage(final ApiAttribute attribute) {
        final String[] types = {
                "bmp", "gif", "ico", "jpg", "jpeg", "png", "tiff",
                "tif", "svg" };
        for (final String t : types) {
            if (attribute.getTail().toLowerCase(Locale.ENGLISH).endsWith(t)) {
                return true;
            }
        }
        return false;
    }
}
