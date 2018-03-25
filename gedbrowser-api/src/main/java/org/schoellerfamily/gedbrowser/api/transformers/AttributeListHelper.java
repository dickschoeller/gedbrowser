package org.schoellerfamily.gedbrowser.api.transformers;

import java.util.List;

import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson;

/**
 * Helper class for building attribute lists. Manages order and aggregation.
 *
 * @author Dick Schoeller
 */
public class AttributeListHelper {
    /** */
    private final ApiModelToGedObjectVisitor parentVisitor;

    /**
     * @param parentVisitor the visitor that is using this helper.
     */
    public AttributeListHelper(final ApiModelToGedObjectVisitor parentVisitor) {
        this.parentVisitor = parentVisitor;
    }

    /**
     * @param apiParent the parent object
     */
    public void addAttributes(final ApiAttribute apiParent) {
        addByType(apiParent, "date");
        addByType(apiParent, "place");
        addIfNotTypes(apiParent, "date", "place");
    }

    /**
     * Add the attributes if they match the given type.
     *
     * @param apiParent the parent attribute
     * @param string the type we want to match
     */
    private void addByType(final ApiAttribute apiParent, final String string) {
        for (final ApiAttribute object : apiParent.getAttributes()) {
            if (object.isType(string)) {
                final ApiModelToGedObjectVisitor visitor = createVisitor();
                object.accept(visitor);
            }
        }
    }

    /**
     * Add the attributes that don't match any of the selected types.
     *
     * @param apiParent the parent attribute
     * @param types the types to check
     */
    private void addIfNotTypes(final ApiAttribute apiParent,
            final String... types) {
        for (final ApiAttribute object : apiParent.getAttributes()) {
            if (!matchOne(object, types)) {
                final ApiModelToGedObjectVisitor visitor = createVisitor();
                object.accept(visitor);
            }
        }
    }

    /**
     * @param object the object that we are checking
     * @param types a list of types to match
     * @return true if matches any of the types
     */
    private boolean matchOne(final ApiAttribute object,
            final String... types) {
        for (final String type : types) {
            if (object.isType(type)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param apiParent the parent object
     */
    public void addAttributes(final ApiPerson apiParent) {
        addToAttributes(apiParent.getAttributes());
        addToAttributes(apiParent.getImages());
        addToAttributes(apiParent.getFamc());
        addToAttributes(apiParent.getFams());
        addToAttributes(apiParent.getRefn());
        addToAttributes(apiParent.getChanged());
    }

    /**
     * Add to the attributes of the parent for saving.
     *
     * @param attributes some attributes to add to the list
     */
    private void addToAttributes(final List<ApiAttribute> attributes) {
        for (final ApiObject object : attributes) {
            final ApiModelToGedObjectVisitor visitor = createVisitor();
            object.accept(visitor);
        }
    }

    /**
     * @param apiParent the parent object
     */
    public void addAttributes(final ApiObject apiParent) {
        for (final ApiObject object : apiParent.getAttributes()) {
            final ApiModelToGedObjectVisitor visitor = createVisitor();
            object.accept(visitor);
        }
    }

    /**
     * @return the new visitor
     */
    private ApiModelToGedObjectVisitor createVisitor() {
        return parentVisitor.createVisitor();
    }

}
