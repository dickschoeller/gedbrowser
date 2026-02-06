package org.schoellerfamily.gedbrowser.analytics.visitor;

import java.util.ArrayList;
import java.util.List;

import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Child;
import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;

/**
 * Implements behaviors shared by Person and Family analysis visitors.
 *
 * @author Dick Schoeller
 */
public abstract class AbstractAnalysisVisitor extends IgnoreableProcessor
        implements GedObjectVisitor {
    /** */
    private final List<Attribute> attributes = new ArrayList<>();
    /** */
    private final List<Attribute> trimmedAttributes = new ArrayList<>();
    /** */
    private final List<Child> children = new ArrayList<>();

    /**
     * Constructor.
     */
    protected AbstractAnalysisVisitor() {
        super();
    }

    /**
     * Get the list of attributes that are not children.
     *
     * @return the list of all attributes
     */
    public final List<Attribute> getAttributes() {
        return attributes;
    }

    /**
     * Get a list of attributes that are not children and are not ignored.
     *
     * @return the list of attributes that are interesting
     */
    public final List<Attribute> getTrimmedAttributes() {
        return trimmedAttributes;
    }

    /**
     * Get the list of attributes that refer to children.
     *
     * @return the list of attributes that are interesting
     */
    public final List<Child> getChildren() {
        return children;
    }

    /**
     * Visit an Attribute. Track the complete list of Attributes and a list
     * trimmed by removing "ignoreable" attributes.
     *
     * @see GedObjectVisitor#visit(Attribute)
     */
    @Override
    public final void visit(final Attribute attribute) {
        attributes.add(attribute);
        if (ignoreable(attribute)) {
            return;
        }
        trimmedAttributes.add(attribute);
    }

    /**
     * Visit a Child. The list of children may be used by the calling
     * algorithm.
     *
     * @see GedObjectVisitor#visit(Child)
     */
    @Override
    public final void visit(final Child child) {
        children.add(child);
    }
}
