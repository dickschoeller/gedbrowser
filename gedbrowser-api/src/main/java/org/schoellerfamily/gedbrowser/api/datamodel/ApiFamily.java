package org.schoellerfamily.gedbrowser.api.datamodel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dick Schoeller
 */
public final class ApiFamily extends ApiObject {
    /** */
    private static final long serialVersionUID = 2L;

    /**
     * The list of child attributes of this object.
     */
    private final List<ApiAttribute> children = new ArrayList<>();

    /**
     * The list of husband, wife, spouse attributes of this object.
     */
    private final List<ApiAttribute> spouses = new ArrayList<>();

    /**
     * The list of image attributes of this object.
     */
    private final List<ApiAttribute> images = new ArrayList<>();

    /**
     * Constructor.
     */
    public ApiFamily() {
        super();
    }

    /**
     * Constructor.
     *
     * @param type a string describing the data type of this object
     * @param string a string containing the primary value of this object
     */
    public ApiFamily(final String type, final String string) {
        super(type, string);
    }

    /**
     * Constructor.
     *
     * @param type a string describing the data type of this object
     * @param string a string containing the primary value of this object
     * @param attributes the list of subordinate attributes of this object
     */
    public ApiFamily(final String type, final String string,
            final List<ApiAttribute> attributes) {
        super(type, string, attributes);
    }

    /**
     * Constructor.
     *
     * @param in a family to copy (except for the ID)
     * @param string the ID of this object
     */
    public ApiFamily(final ApiFamily in, final String string) {
        super(in.getType(), string, in.getAttributes());
        this.children.addAll(in.children);
        this.spouses.addAll(in.spouses);
        this.images.addAll(in.images);
    }

    /**
     * @return the list of child attributes
     */
    public List<ApiAttribute> getChildren() {
        return children;
    }

    /**
     * @return the list of husband, wife, spouse attributes
     */
    public List<ApiAttribute> getSpouses() {
        return spouses;
    }

    /**
     * @return the list of image attributes
     */
    public List<ApiAttribute> getImages() {
        return images;
    }

    /**
     * @param visitor the visitor
     */
    public void accept(final ApiObjectVisitor visitor) {
        visitor.visit(this);
    }


    /**
     * Special handling of adding attributes to an ApiPerson because the list
     * gets broken up into different sections.
     *
     * @param attribute the attribute to add
     */
    public void addAttribute(final ApiAttribute attribute) {
        if (attribute.isType("husband") || attribute.isType("wife")
                || attribute.isType("spouse")) {
            spouses.add(attribute);
            return;
        }
        if (attribute.isType("child")) {
            children.add(attribute);
            return;
        }
        if (new ImageUtils().isImageWrapper(attribute)) {
            images.add(attribute);
            return;
        }
        getAttributes().add(attribute);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result
                + childrenHash();
        result = prime * result + imagesHash();
        result = prime * result + spousesHash();
        return result;
    }

    /**
     * @return hash code for the children list
     */
    private int childrenHash() {
        if (children == null) {
            return 0;
        }
        return children.hashCode();
    }

    /**
     * @return hash code for the images list
     */
    private int imagesHash() {
        if (images == null) {
            return 0;
        }
        return images.hashCode();
    }

    /**
     * @return hash code for the spouses list
     */
    private int spousesHash() {
        if (spouses == null) {
            return 0;
        }
        return spouses.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        final ApiFamily other = (ApiFamily) obj;
        if (!listCompare(children, other.children)) {
            return false;
        }
        if (!listCompare(images, other.images)) {
            return false;
        }
        return listCompare(spouses, other.spouses);
    }
}
