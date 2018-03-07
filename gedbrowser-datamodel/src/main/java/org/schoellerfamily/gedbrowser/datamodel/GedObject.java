package org.schoellerfamily.gedbrowser.datamodel;

import java.util.ArrayList;
import java.util.List;

import org.schoellerfamily.gedbrowser.datamodel.appender.AppenderStrategy;
import org.schoellerfamily.gedbrowser.datamodel.appender.GedAppender;
import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;

/**
 * @author Dick Schoeller
 */
public abstract class GedObject extends AbstractFinderObject
        implements GetString {

    /** */
    protected static final String DEFAULT_IDX_NAME = "";
    /** */
    public static final String VERSION = "1.3.0-M1-SNAPSHOT";
    /** */
    protected static final String DEFAULT_SURNAME = "";
    /** */
    protected static final String DEFAULT_BIRTHDATE = "";
    /** */
    protected static final String DEFAULT_DEATHDATE = "";
    /** */
    protected static final String DEFAULT_DATE = "";

    /** */
    private static final int PRIME = 31;

    /** */
    private String string;
    /** */
    private GedObject parent;
    /** */
    private final transient List<GedObject> attributes =
            new ArrayList<GedObject>();
    /** */
    private final transient boolean set;

    /** */
    private transient AppenderStrategy appender;

    /** */
    protected GedObject() {
        this.set = false;
        this.string = "";
        this.parent = null;
        appender = new GedAppender(this);
    }

    /**
     * @param parent
     *            parent object of this object
     */
    protected GedObject(final GedObject parent) {
        this.string = "";
        this.parent = parent;
        this.set = true;
        appender = new GedAppender(this);
    }

    /**
     * @param parent
     *            parent object of this object
     * @param string
     *            long version of type string
     */
    protected GedObject(final GedObject parent, final String string) {
        this.parent = parent;
        if (string == null) {
            this.string = "";
        } else {
            this.string = string;
        }
        set = true;
        appender = new GedAppender(this);
    }

    /**
     * @return whether this object was set. If false this is a stub.
     */
    public final boolean isSet() {
        return this.set;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getString() {
        return string;
    }

    /**
     * @param string long version of type string
     */
    public final void setString(final String string) {
        if (string == null) {
            this.string = "";
        } else {
            this.string = string;
        }
    }

    /**
     * @param appendage
     *            text to append to the string
     */
    public final void appendString(final String appendage) {
        appender.appendString(appendage);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final GedObject getParent() {
        return parent;
    }

    /**
     * @param parent
     *            parent object of this object
     */
    public final void setParent(final GedObject parent) {
        this.parent = parent;
    }

    /**
     * @param attribute
     *            object to add to the attribute list
     */
    public final void addAttribute(final GedObject attribute) {
        attributes.add(attribute);
    }

    /**
     * @param attribute
     *            object to remove from the attribute list
     */
    public final void removeAttribute(final GedObject attribute) {
        attributes.remove(attribute);
    }

    /**
     * @param attribute
     *            object to check in the attribute list
     * @return whether the provided object is in the list
     */
    public final boolean hasAttribute(final GedObject attribute) {
        return attributes.contains(attribute);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int hashCode() {
        int result = 1;
        if (parent == null) {
            result = PRIME * result;
        } else {
            result = PRIME * result + parent.hashCode();
        }
        result = PRIME * result + string.hashCode();
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings({ "PMD.CyclomaticComplexity",
            "PMD.ModifiedCyclomaticComplexity",
            "PMD.StdCyclomaticComplexity" })
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GedObject other = (GedObject) obj;
        if (parent == null) {
            if (other.parent != null) {
                return false;
            }
        } else if (!parent.equals(other.parent)) {
            return false;
        }
        return string.equals(other.string);
    }

    /**
     * @return the list of attributes and sub-objects for this object
     */
    public final List<GedObject> getAttributes() {
        return attributes;
    }

    /**
     * @return whether the list of attributes has some content
     */
    public final boolean hasAttributes() {
        return !attributes.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString() {
        return string;
    }

    /**
     * Set the strategy that implements appending.
     *
     * @param appender the appender.
     */
    protected final void setAppender(final AppenderStrategy appender) {
        this.appender = appender;
    }

    /**
     * Hook for using the visitor design pattern to accumulate information
     * about a GedObject and its children.
     *
     * @param visitor the visitor
     */
    public abstract void accept(GedObjectVisitor visitor);
}
