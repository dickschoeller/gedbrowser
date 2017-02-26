package org.schoellerfamily.gedbrowser.analytics.visitor;

import java.util.ArrayList;
import java.util.List;

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
import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;

/**
 * Visitor aids in the process of analyzing the order of events in a family.
 *
 * @author Dick Schoeller
 */
public final class FamilyAnalysisVisitor extends IgnoreableProcessor
        implements GedObjectVisitor {
    /** */
    private final List<Attribute> attributes = new ArrayList<>();

    /** */
    private final List<Attribute> trimmedAttributes = new ArrayList<>();

    /** */
    private final List<Child> children = new ArrayList<>();

    /**
     * @return the list of all attributes
     */
    public List<Attribute> getAttributes() {
        return attributes;
    }

    /**
     * @return the list of attributes that are interesting
     */
    public List<Attribute> getTrimmedAttributes() {
        return trimmedAttributes;
    }

    /**
     * @return the list of attributes that are interesting
     */
    public List<Child> getChildren() {
        return children;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Attribute attribute) {
        attributes.add(attribute);
        if (ignoreable(attribute)) {
            return;
        }
        trimmedAttributes.add(attribute);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Child child) {
        children.add(child);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Date date) {
        // Type does not contribute to the algorithm
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final FamC famc) {
        // Type does not contribute to the algorithm
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Family family) {
        for (final GedObject gob : family.getAttributes()) {
            gob.accept(this);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final FamS fams) {
        // Type does not contribute to the algorithm
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Head head) {
        // Type does not contribute to the algorithm
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Husband husband) {
        // Type does not contribute to the algorithm
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Link link) {
        // Type does not contribute to the algorithm
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Multimedia multimedia) {
        // Type does not contribute to the algorithm
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Name name) {
        // Type does not contribute to the algorithm
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Person person) {
        // Type does not contribute to the algorithm
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Place place) {
        // Type does not contribute to the algorithm
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Root root) {
        // Type does not contribute to the algorithm
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Source source) {
        // Type does not contribute to the algorithm
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final SourceLink sourceLink) {
        // Type does not contribute to the algorithm
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Submittor submittor) {
        // Type does not contribute to the algorithm
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final SubmittorLink submittorLink) {
        // Type does not contribute to the algorithm
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Trailer trailer) {
        // Type does not contribute to the algorithm
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Wife wife) {
        // Type does not contribute to the algorithm
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final GedObject gedObject) {
        // Type does not contribute to the algorithm
    }
}
