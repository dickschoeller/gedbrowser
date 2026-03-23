package org.schoellerfamily.gedbrowser.api.transformers;

import java.util.List;

import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiFamily;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiObjectVisitor;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSource;

import lombok.RequiredArgsConstructor;

/**
 * Represents attribute list helper.
 *
 * @author Richard Schoeller
 */
@RequiredArgsConstructor
public class AttributeListHelper {
    /** */
    private final ApiObjectVisitor parentVisitor;

    /**
     * Executes add attributes.
     *
     * @param apiParent the api parent
     */
    public void addAttributes(final ApiAttribute apiParent) {
        addByType(apiParent, "date");
        addByType(apiParent, "place");
        addIfNotTypes(apiParent, "date", "place");
    }

    private void addByType(final ApiAttribute apiParent, final String string) {
        for (final ApiAttribute object : apiParent.getAttributes()) {
            if (object.isType(string)) {
                final ApiObjectVisitor visitor = createVisitor();
                object.accept(visitor);
            }
        }
    }

    private void addIfNotTypes(final ApiAttribute apiParent,
            final String... types) {
        for (final ApiAttribute object : apiParent.getAttributes()) {
            if (!matchOne(object, types)) {
                final ApiObjectVisitor visitor = createVisitor();
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
     * Executes add attributes.
     *
     * @param apiParent the api parent
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
     * Executes add attributes.
     *
     * @param apiParent the api parent
     */
    public void addAttributes(final ApiFamily apiParent) {
        addToAttributes(apiParent.getSpouses());
        addToAttributes(apiParent.getChildren());
        addToAttributes(apiParent.getAttributes());
        addToAttributes(apiParent.getImages());
    }

    /**
     * Executes add attributes.
     *
     * @param apiParent the api parent
     */
    public void addAttributes(final ApiSource apiParent) {
        addToAttributes(apiParent.getAttributes());
        addToAttributes(apiParent.getImages());
    }

    private void addToAttributes(final List<ApiAttribute> attributes) {
        for (final ApiObject object : attributes) {
            final ApiObjectVisitor visitor = createVisitor();
            object.accept(visitor);
        }
    }

    /**
     * Executes add attributes.
     *
     * @param apiParent the api parent
     */
    public void addAttributes(final ApiObject apiParent) {
        for (final ApiObject object : apiParent.getAttributes()) {
            final ApiObjectVisitor visitor = createVisitor();
            object.accept(visitor);
        }
    }

    private ApiObjectVisitor createVisitor() {
        return parentVisitor.createVisitor();
    }
}
