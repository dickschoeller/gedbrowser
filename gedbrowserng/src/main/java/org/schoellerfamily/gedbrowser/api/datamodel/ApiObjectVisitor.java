package org.schoellerfamily.gedbrowser.api.datamodel;

/**
 * @author Dick Schoeller
 */
public interface ApiObjectVisitor {
    /**
     * @param attribute the Attribute to visit
     */
    void visit(ApiAttribute attribute);

    /**
     * @param baseObject the BaseObject to visit
     */
    void visit(ApiObject baseObject);

    /**
     * @param family the Family to visit
     */
    void visit(ApiFamily family);

    /**
     * @param head the Head object to visit
     */
    void visit(ApiHead head);

    /**
     * @param note the Note object to visit
     */
    void visit(ApiNote note);

    /**
     * @param person the Person object to visit
     */
    void visit(ApiPerson person);

    /**
     * @param source the Source object to visit
     */
    void visit(ApiSource source);

    /**
     * @param submission the submission object to visit
     */
    void visit(ApiSubmission submission);

    /**
     * @param submitter the submitter object to visit
     */
    void visit(ApiSubmitter submitter);
}
