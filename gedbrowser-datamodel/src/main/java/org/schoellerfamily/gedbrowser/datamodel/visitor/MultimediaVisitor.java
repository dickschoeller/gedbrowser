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
     * @return the file name of the multimedia item
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * @return the file format of the multimedia item
     */
    public String getFormat() {
        return format;
    }

    /**
     * @return whether the type is an image type
     */
    public boolean isImage() {
        final String form = getFormat();
        return "jpg".equalsIgnoreCase(form)
                || "gif".equalsIgnoreCase(form)
                || "png".equalsIgnoreCase(form)
                || "tif".equalsIgnoreCase(form);
    }

    /**
     * @return the title of the multimedia item
     */
    public String getTitle() {
        return title;
    }

    /**
     * Visit an Attribute. The values of specific attributes are gathered for
     * later use.
     *
     * @see GedObjectVisitor#visit(Attribute)
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
     * Visit a Multimedia. This is the primary focus of the visitation. From
     * here, interesting information is gathered from the attributes.
     *
     * @see GedObjectVisitor#visit(Multimedia)
     */
    @Override
    public void visit(final Multimedia multimedia) {
        for (final GedObject gedObject : multimedia.getAttributes()) {
            gedObject.accept(this);
        }
    }
}
