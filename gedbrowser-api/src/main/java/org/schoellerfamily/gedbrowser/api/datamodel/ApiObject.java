package org.schoellerfamily.gedbrowser.api.datamodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dick Schoeller
 */
public class ApiObject implements Serializable {
    /** */
    private static final long serialVersionUID = 1L;

    /**
     * A string describing the data type of this object.
     */
    private String type;

    /**
     * A string containing the primary value of this object. In Attributes
     * this will be the sub-type and tail will contain the data value.
     */
    private String string;

    /**
     * The list of subordinate attributes of this object.
     */
    private List<ApiObject> attributes;

    /**
     * Constructor.
     */
    public ApiObject() {
        this.type = "";
        this.string = "";
    }

    /**
     * Constructor.
     *
     * @param type a string describing the data type of this object
     * @param string a string containing the primary value of this object
     */
    public ApiObject(final String type, final String string) {
        this(type, string, new ArrayList<>());
    }

    /**
     * Constructor.
     *
     * @param type the type of data in this object
     * @param string the primary data value
     * @param attributes the list of subordinate attributes of this object
     */
    public ApiObject(final String type, final String string,
            final List<ApiObject> attributes) {
        super();
        this.type = type;
        this.string = string;
        this.attributes = attributes;
    }

    /**
     * @return the type string
     */
    public final String getType() {
        return type;
    }

    /**
     * @return the value string
     */
    public final String getString() {
        return string;
    }

    /**
     * @return the list of additional attributes
     */
    public final List<ApiObject> getAttributes() {
        return attributes;
    }

    /**
     * @param visitor the visitor
     */
    public void accept(final ApiObjectVisitor visitor) {
        visitor.visit(this);
    }
}
