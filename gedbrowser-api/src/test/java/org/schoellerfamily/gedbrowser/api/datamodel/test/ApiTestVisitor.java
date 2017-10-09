package org.schoellerfamily.gedbrowser.api.datamodel.test;

import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiFamily;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiHead;
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
     * @return the name of the method called
     */
    public String getMethodCalled() {
        return methodCalled;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final ApiAttribute attribute) {
        methodCalled = "attribute";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final ApiObject baseObject) {
        methodCalled = "base";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final ApiFamily family) {
        methodCalled = "family";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final ApiHead head) {
        methodCalled = "head";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final ApiPerson person) {
        methodCalled = "person";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final ApiSource source) {
        methodCalled = "source";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final ApiSubmission submission) {
        methodCalled = "submission";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final ApiSubmitter submitter) {
        methodCalled = "submitter";
    }

}
