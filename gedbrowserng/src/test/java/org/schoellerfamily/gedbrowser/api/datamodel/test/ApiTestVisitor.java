package org.schoellerfamily.gedbrowser.api.datamodel.test;

import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiFamily;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiHead;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiNote;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiObjectVisitor;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSource;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSubmission;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSubmitter;

/**
 * @author Dick Schoeller
 */
public class ApiTestVisitor implements ApiObjectVisitor {
    /** */
    private String methodCalled = "";

    /**
     * Gets the method called.
     *
     * @return the method called
     */
    public String getMethodCalled() {
        return methodCalled;
    }

    /**
     * Executes visit.
     *
     * @param attribute the attribute
     */
    @Override
    public void visit(final ApiAttribute attribute) {
        methodCalled = "attribute";
    }

    /**
     * Executes visit.
     *
     * @param baseObject the base object
     */
    @Override
    public void visit(final ApiObject baseObject) {
        methodCalled = "base";
    }

    /**
     * Executes visit.
     *
     * @param family the family
     */
    @Override
    public void visit(final ApiFamily family) {
        methodCalled = "family";
    }

    /**
     * Executes visit.
     *
     * @param head the head
     */
    @Override
    public void visit(final ApiHead head) {
        methodCalled = "head";
    }

    /**
     * Executes visit.
     *
     * @param note the note
     */
    @Override
    public void visit(final ApiNote note) {
        methodCalled = "note";
    }

    /**
     * Executes visit.
     *
     * @param person the person
     */
    @Override
    public void visit(final ApiPerson person) {
        methodCalled = "person";
    }

    /**
     * Executes visit.
     *
     * @param source the source
     */
    @Override
    public void visit(final ApiSource source) {
        methodCalled = "source";
    }

    /**
     * Executes visit.
     *
     * @param submission the submission
     */
    @Override
    public void visit(final ApiSubmission submission) {
        methodCalled = "submission";
    }

    /**
     * Executes visit.
     *
     * @param submitter the submitter
     */
    @Override
    public void visit(final ApiSubmitter submitter) {
        methodCalled = "submitter";
    }

}
