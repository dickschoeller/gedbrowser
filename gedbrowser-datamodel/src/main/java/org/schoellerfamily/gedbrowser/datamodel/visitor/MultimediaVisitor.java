package org.schoellerfamily.gedbrowser.datamodel.visitor;

import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Child;
import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.datamodel.FamC;
import org.schoellerfamily.gedbrowser.datamodel.FamS;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.datamodel.Husband;
import org.schoellerfamily.gedbrowser.datamodel.Link;
import org.schoellerfamily.gedbrowser.datamodel.Multimedia;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Place;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.datamodel.SourceLink;
import org.schoellerfamily.gedbrowser.datamodel.Submittor;
import org.schoellerfamily.gedbrowser.datamodel.SubmittorLink;
import org.schoellerfamily.gedbrowser.datamodel.Trailer;
import org.schoellerfamily.gedbrowser.datamodel.Wife;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings("PMD.TooManyMethods")
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
     * {@inheritDoc}
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
     * {@inheritDoc}
     */
    @Override
    public void visit(final Child child) {
        // Type does not contribute to algorithm
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Date date) {
        // Type does not contribute to algorithm
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final FamC famc) {
        // Type does not contribute to algorithm
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Family family) {
        // Type does not contribute to algorithm
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final FamS fams) {
        // Type does not contribute to algorithm
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Head head) {
        // Type does not contribute to algorithm
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Husband husband) {
        // Type does not contribute to algorithm
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Link link) {
        // Type does not contribute to algorithm
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Multimedia multimedia) {
        for (final GedObject gedObject : multimedia.getAttributes()) {
            gedObject.accept(this);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Name name) {
        // Type does not contribute to algorithm
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Person person) {
        // Type does not contribute to algorithm
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Place place) {
        // Type does not contribute to algorithm
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Root root) {
        // Type does not contribute to algorithm
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Source source) {
        // Type does not contribute to algorithm
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final SourceLink sourceLink) {
        // Type does not contribute to algorithm
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Submittor submittor) {
        // Type does not contribute to algorithm
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final SubmittorLink submittorLink) {
        // Type does not contribute to algorithm
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Trailer trailer) {
        // Type does not contribute to algorithm
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Wife wife) {
        // Type does not contribute to algorithm
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final GedObject gedObject) {
        // Type does not contribute to algorithm
    }
}
