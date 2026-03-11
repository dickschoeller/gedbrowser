package org.schoellerfamily.gedbrowser.api.datamodel;

import java.util.Locale;

/**
 * @author Dick Schoeller
 */
public class ImageUtils {
    /**
     * @param attr the attribute to check
     * @return true if it wraps any supported media type (image, video, or YouTube)
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
     * @param attribute the attribute to check
     * @return true if it is a reference to an image file type
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
     * @param attribute the attribute to check
     * @return true if it is a reference to a video file type
     */
    public boolean isVideo(final ApiAttribute attribute) {
        final String[] types = { "avi", "m4v", "mov", "mp4", "mpg", "mpeg", "webm" };
        for (final String t : types) {
            if (attribute.getTail().toLowerCase(Locale.ENGLISH).endsWith(t)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param attribute the attribute to check
     * @return true if it is a YouTube URL
     */
    public boolean isYouTube(final ApiAttribute attribute) {
        final String tail = attribute.getTail().toLowerCase(Locale.ENGLISH);
        return tail.contains("youtube.com/watch") || tail.contains("youtu.be/");
    }
}
