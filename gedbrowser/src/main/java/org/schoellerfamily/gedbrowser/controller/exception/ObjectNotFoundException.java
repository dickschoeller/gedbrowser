package org.schoellerfamily.gedbrowser.controller.exception;

import org.schoellerfamily.gedbrowser.datamodel.users.UserRoleName;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;

/**
 * Exception thrown when an object is not found.
 *
 * @author Dick Schoeller
 */
public class ObjectNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 3L;

    /** Object ID. */
    private final String id;

    /** Dataset name. */
    private final String datasetName;

    /** Rendering context. */
    private final transient RenderingContext context;

    /**
     * Creates a new ObjectNotFoundException.
     *
     * @param message the message
     * @param id the unique identifier for the target
     * @param datasetName the dataset name to use
     * @param context the context
     */
    public ObjectNotFoundException(final String message, final String id, final String datasetName,
        final RenderingContext context) {
        super(message);
        this.id = id;
        this.datasetName = datasetName;
        this.context = context;
    }

    /**
     * Get the ID of the person that was not found.
     *
     * @return the ID
     */
    public String getId() {
        return id;
    }

    /**
     * Get the name of the dataset.
     *
     * @return get the name of the dataset being searched
     */
    public String getDatasetName() {
        return datasetName;
    }

    /**
     * Get the href string to the index page for surnames beginning with A.
     *
     * @return href string to the index page for surnames beginning with A.
     */
    public String getIndexHref() {
        return "surnames?db=" + datasetName + "&letter=" + "A";
    }

    /**
     * Get the href string to the header page.
     *
     * @return href string to the header page.
     */
    public String getHeaderHref() {
        return "head?db=" + datasetName;
    }

    /**
     * Get the href string to the save page.
     *
     * @return href string to the save page.
     */
    public String getSaveHref() {
        return "save?db=" + datasetName;
    }

    /**
     * Get the href string to the index page for sources.
     *
     * @return href string to the index page for sources.
     */
    public String getSourcesHref() {
        return "sources?db=" + datasetName;
    }

    /**
     * Get the href string to the index page for submitters.
     *
     * @return href string to the index page for submitters.
     */
    public String getSubmittersHref() {
        return "submitters?db=" + datasetName;
    }

    /**
     * Check if the user has a particular role.
     *
     * @param role role that we are looking for
     * @return true if the user has the role
     */
    public boolean hasRole(final String role) {
        return context.hasRole(UserRoleName.valueOf(role));
    }

    /**
     * Get the href string to the living estimator.
     *
     * @return the href string to the living estimator.
     */
    public String getLivingHref() {
        return "living?db=" + datasetName;
    }

    /**
     * Get the href string to the places report.
     *
     * @return the href string to the places report.
     */
    public String getPlacesHref() {
        return "places?db=" + datasetName;
    }
}
