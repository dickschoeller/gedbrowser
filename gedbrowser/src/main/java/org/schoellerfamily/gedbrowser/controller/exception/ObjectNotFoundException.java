package org.schoellerfamily.gedbrowser.controller.exception;

import org.schoellerfamily.gedbrowser.renderer.RenderingContext;

/**
 * @author Dick Schoeller
 */
public class ObjectNotFoundException extends RuntimeException {
    /** */
    private static final long serialVersionUID = 3L;

    /** */
    private final String id;

    /** */
    private final String datasetName;

    /** */
    private final RenderingContext context;

    /**
     * @param message the message to display
     * @param id the ID of the object not found
     * @param datasetName the name of the dataset being searched
     * @param context the rendering context
     */
    public ObjectNotFoundException(final String message, final String id,
            final String datasetName, final RenderingContext context) {
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
     * @return get the name of the dataset being searched
     */
    public String getDatasetName() {
        return datasetName;
    }

    /**
     * @return href string to the index page for surnames beginning with A.
     */
    public String getIndexHref() {
        return "surnames?db=" + datasetName + "&letter=" + "A";
    }

    /**
     * @return href string to the index page for surnames beginning with A.
     */
    public String getHeaderHref() {
        return "head?db=" + datasetName;
    }

    /**
     * @return href string to the index page for surnames beginning with A.
     */
    public String getSaveHref() {
        return "save?db=" + datasetName;
    }

    /**
     * @return href string to the index page for sources.
     */
    public String getSourcesHref() {
        return "sources?db=" + datasetName;
    }

    /**
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
        return context.hasRole(role);
    }

    /**
     * @return the href string to the living estimator.
     */
    public String getLivingHref() {
        return "living?db=" + datasetName;
    }

    /**
     * @return the href string to the living estimator.
     */
    public String getPlacesHref() {
        return "places?db=" + datasetName;
    }
}
