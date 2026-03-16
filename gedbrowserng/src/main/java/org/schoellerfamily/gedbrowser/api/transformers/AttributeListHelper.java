package org.schoellerfamily.gedbrowser.api.transformers;

import java.util.List;

import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiFamily;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSource;

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

    private void addByType(final ApiAttribute apiParent, final String string) {
        for (final ApiAttribute object : apiParent.getAttributes()) {
            if (object.isType(string)) {
                final ApiModelToGedObjectVisitor visitor = createVisitor();
                object.accept(visitor);
            }
        }
    }

    private void addIfNotTypes(final ApiAttribute apiParent,
            final String... types) {
        for (final ApiAttribute object : apiParent.getAttributes()) {
            if (!matchOne(object, types)) {
                final ApiModelToGedObjectVisitor visitor = createVisitor();
                object.accept(visitor);
            }
        }
    }

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
        addToAttributes(apiParent.getFamcs());
        addToAttributes(apiParent.getFamss());
        addToAttributes(apiParent.getRefns());
        addToAttributes(apiParent.getChanges());
    }

    /**
     * @param apiParent the parent object
     */
    public void addAttributes(final ApiFamily apiParent) {
        addToAttributes(apiParent.getSpouses());
        addToAttributes(apiParent.getChildren());
        addToAttributes(apiParent.getAttributes());
        addToAttributes(apiParent.getImages());
    }

    /**
     * @param apiParent the parent object
     */
    public void addAttributes(final ApiSource apiParent) {
        addToAttributes(apiParent.getAttributes());
        addToAttributes(apiParent.getImages());
    }

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

    private ApiModelToGedObjectVisitor createVisitor() {
        return parentVisitor.createVisitor();
    }

}
