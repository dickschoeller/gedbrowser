package org.schoellerfamily.gedbrowser.datamodel.visitor;

import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Multimedia;

/**
 * @author Dick Schoeller
 */
public final class MultimediaVisitor implements GedObjectVisitor {
    /** */
    private String filePath;

    /** */
    private String format;

    /** */
    private String title;

    /**
     * Gets the file path.
     *
     * @return the file path
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Gets the format.
     *
     * @return the format
     */
    public String getFormat() {
        return format;
    }

    /**
     * Checks whether image.
     *
     * @return true if the condition is met; otherwise false
     */
    public boolean isImage() {
        final String form = getFormat();
        return "jpg".equalsIgnoreCase(form)
                || "gif".equalsIgnoreCase(form)
                || "png".equalsIgnoreCase(form)
                || "tif".equalsIgnoreCase(form);
    }

    /**
     * Checks whether video.
     *
     * @return true if the condition is met; otherwise false
     */
    public boolean isVideo() {
        final String form = getFormat();
        return "avi".equalsIgnoreCase(form)
                || "mp4".equalsIgnoreCase(form)
                || "mov".equalsIgnoreCase(form)
                || "m4v".equalsIgnoreCase(form)
                || "mpg".equalsIgnoreCase(form)
                || "mpeg".equalsIgnoreCase(form)
                || "webm".equalsIgnoreCase(form);
    }

    /**
     * Checks whether you tube.
     *
     * @return true if the condition is met; otherwise false
     */
    public boolean isYouTube() {
        return "youtube".equalsIgnoreCase(getFormat());
    }

    /**
     * Checks whether media.
     *
     * @return true if the condition is met; otherwise false
     */
    public boolean isMedia() {
        return isImage() || isVideo() || isYouTube();
    }

    /**
     * Gets the title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Executes visit.
     *
     * @param attribute the attribute
     */
    @Override
    public void visit(final Attribute attribute) {
        final String string = attribute.getString();
        if ("File".equals(string)) {
            filePath = attribute.getTail();
            for (final GedObject subObject : attribute.getAttributes()) {
                subObject.accept(this);
            }
        }
        if ("Format".equals(string)) {
            format = attribute.getTail();
        }
        if ("Title".equals(string)) {
            title = attribute.getTail();
        }
    }

    /**
     * Executes visit.
     *
     * @param multimedia the multimedia
     */
    @Override
    public void visit(final Multimedia multimedia) {
        for (final GedObject gedObject : multimedia.getAttributes()) {
            gedObject.accept(this);
        }
    }
}
