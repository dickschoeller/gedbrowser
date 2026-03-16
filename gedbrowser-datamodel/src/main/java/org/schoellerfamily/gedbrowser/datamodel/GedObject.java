package org.schoellerfamily.gedbrowser.datamodel;

import java.util.ArrayList;
import java.util.List;

import org.schoellerfamily.gedbrowser.datamodel.appender.AppenderStrategy;
import org.schoellerfamily.gedbrowser.datamodel.appender.GedAppender;
import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;

/**
 * Represents ged object in the domain model.
 *
 * @author Richard Schoeller
 */
public abstract class GedObject extends AbstractFinderObject
        implements GetString {

    /**
     * The default i d x  n a m e value.
     */
    protected static final String DEFAULT_IDX_NAME = "";
    /**
     * The project version string.
     */
    public static final String VERSION = "1.3.0-RC3-SNAPSHOT";
    /**
     * The default s u r n a m e value.
     */
    protected static final String DEFAULT_SURNAME = "";
    /**
     * The default b i r t h d a t e value.
     */
    protected static final String DEFAULT_BIRTHDATE = "";
    /**
     * The default d e a t h d a t e value.
     */
    protected static final String DEFAULT_DEATHDATE = "";
    /**
     * The default d a t e value.
     */
    protected static final String DEFAULT_DATE = "";

    /**
     * The p r i m e value.
     */
    private static final int PRIME = 31;

    /**
     * The string value.
     */
    private String string;
    /**
     * The parent value.
     */
    private GedObject parent;
    /**
     * The attributes value.
     */
    private final transient List<GedObject> attributes =
            new ArrayList<GedObject>();
    /**
     * The set value.
     */
    private final transient boolean set;

    /**
     * The appender value.
     */
    private transient AppenderStrategy appender;

    /**
     * Creates a new GedObject.
     */
    protected GedObject() {
        this.set = false;
        this.string = "";
        this.parent = null;
        appender = new GedAppender(this);
    }

    /**
     * Creates a new GedObject.
     *
     * @param parent the parent
     */
    protected GedObject(final GedObject parent) {
        this.string = "";
        this.parent = parent;
        this.set = true;
        appender = new GedAppender(this);
    }

    /**
     * Creates a new GedObject.
     *
     * @param parent the parent
     * @param string the string
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
     * Checks whether set.
     *
     * @return true if the condition is met; otherwise false
     */
    public final boolean isSet() {
        return this.set;
    }

    /**
     * Gets the string.
     *
     * @return the string
     */
    @Override
    public final String getString() {
        return string;
    }

    /**
     * Sets the string.
     *
     * @param string the string
     */
    public final void setString(final String string) {
        if (string == null) {
            this.string = "";
        } else {
            this.string = string;
        }
    }

    /**
     * Executes append string.
     *
     * @param appendage the appendage
     */
    public final void appendString(final String appendage) {
        appender.appendString(appendage);
    }

    /**
     * Gets the parent.
     *
     * @return the parent
     */
    @Override
    public final GedObject getParent() {
        return parent;
    }

    /**
     * Sets the parent.
     *
     * @param parent the parent
     */
    public final void setParent(final GedObject parent) {
        this.parent = parent;
    }

    /**
     * Executes add attribute.
     *
     * @param attribute the attribute
     */
    public final void addAttribute(final GedObject attribute) {
        attributes.add(attribute);
    }

    /**
     * Executes remove attribute.
     *
     * @param attribute the attribute
     */
    public final void removeAttribute(final GedObject attribute) {
        attributes.remove(attribute);
    }

    /**
     * Indicates whether attribute is present.
     *
     * @param attribute the attribute
     * @return true if the condition is met; otherwise false
     */
    public final boolean hasAttribute(final GedObject attribute) {
        return attributes.contains(attribute);
    }

    /**
     * Checks whether h code.
     *
     * @return true if the condition is met; otherwise false
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

    @Override
    @SuppressWarnings({ "PMD.CyclomaticComplexity",
            "PMD.ModifiedCyclomaticComplexity",
            "PMD.StdCyclomaticComplexity" })
    /**
     * Executes equals.
     *
     * @param obj the obj
     * @return the resulting boolean
     */
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
     * Gets the attributes.
     *
     * @return the attributes
     */
    public final List<GedObject> getAttributes() {
        return attributes;
    }

    /**
     * Checks whether attributes.
     *
     * @return true if the condition is met; otherwise false
     */
    public final boolean hasAttributes() {
        return !attributes.isEmpty();
    }

    /**
     * Returns the string.
     *
     * @return the resulting string
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
     *
     * @param visitor the visitor
     */
    public abstract void accept(GedObjectVisitor visitor);
}
