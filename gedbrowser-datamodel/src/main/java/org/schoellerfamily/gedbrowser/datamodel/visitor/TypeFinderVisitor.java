package org.schoellerfamily.gedbrowser.datamodel.visitor;

import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Child;
import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.datamodel.FamC;
import org.schoellerfamily.gedbrowser.datamodel.FamS;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.datamodel.Husband;
import org.schoellerfamily.gedbrowser.datamodel.Link;
import org.schoellerfamily.gedbrowser.datamodel.Multimedia;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Place;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.datamodel.SourceLink;
import org.schoellerfamily.gedbrowser.datamodel.Submittor;
import org.schoellerfamily.gedbrowser.datamodel.SubmittorLink;
import org.schoellerfamily.gedbrowser.datamodel.Trailer;
import org.schoellerfamily.gedbrowser.datamodel.Wife;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings({
    "PMD.CouplingBetweenObjects",
    "PMD.TooManyFields",
    "PMD.TooManyMethods" })
public class TypeFinderVisitor implements GedObjectVisitor {
    /** */
    private Attribute attributeField;
    /** */
    private Child childField;
    /** */
    private Date dateField;
    /** */
    private FamC famCField;
    /** */
    private Family familyField;
    /** */
    private FamS famSField;
    /** */
    private Head headField;
    /** */
    private Husband husbandField;
    /** */
    private Link linkField;
    /** */
    private Multimedia multimediaField;
    /** */
    private Name nameField;
    /** */
    private Person personField;
    /** */
    private Place placeField;
    /** */
    private Root rootField;
    /** */
    private Source sourceField;
    /** */
    private SourceLink sourceLinkField;
    /** */
    private Submittor submittorField;
    /** */
    private SubmittorLink submittorLinkField;
    /** */
    private Trailer trailerField;
    /** */
    private Wife wifeField;
    /** */
    private GedObject gedObjectField;

    /**
     * @return the attribute if one was provided
     */
    public Attribute getAttribute() {
        return attributeField;
    }

    /**
     * @return the child if one was provided
     */
    public Child getChild() {
        return childField;
    }

    /**
     * @return the date if one was provided
     */
    public Date getDate() {
        return dateField;
    }

    /**
     * @return the famC if one was provided
     */
    public FamC getFamC() {
        return famCField;
    }

    /**
     * @return the family if one was provided
     */
    public Family getFamily() {
        return familyField;
    }

    /**
     * @return the famS if one was provided
     */
    public FamS getFamS() {
        return famSField;
    }

    /**
     * @return the head if one was provided
     */
    public Head getHead() {
        return headField;
    }

    /**
     * @return the husband if one was provided
     */
    public Husband getHusband() {
        return husbandField;
    }

    /**
     * @return the link if one was provided
     */
    public Link getLink() {
        return linkField;
    }

    /**
     * @return the multimedia if one was provided
     */
    public Multimedia getMultimedia() {
        return multimediaField;
    }

    /**
     * @return the name if one was provided
     */
    public Name getName() {
        return nameField;
    }

    /**
     * @return the person if one was provided
     */
    public Person getPerson() {
        return personField;
    }

    /**
     * @return the place if one was provided
     */
    public Place getPlace() {
        return placeField;
    }

    /**
     * @return the root if one was provided
     */
    public Root getRoot() {
        return rootField;
    }

    /**
     * @return the source if one was provided
     */
    public Source getSource() {
        return sourceField;
    }

    /**
     * @return the sourceLink if one was provided
     */
    public SourceLink getSourceLink() {
        return sourceLinkField;
    }

    /**
     * @return the submittor if one was provided
     */
    public Submittor getSubmittor() {
        return submittorField;
    }

    /**
     * @return the submittorLink if one was provided
     */
    public SubmittorLink getSubmittorLink() {
        return submittorLinkField;
    }

    /**
     * @return the trailer if one was provided
     */
    public Trailer getTrailer() {
        return trailerField;
    }

    /**
     * @return the wife if one was provided
     */
    public Wife getWife() {
        return wifeField;
    }

    /**
     * @return the gedObject if we couldn't figure out what
     */
    public GedObject getGedObject() {
        return gedObjectField;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Attribute attribute) {
        attributeField = attribute;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Child child) {
        childField = child;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Date date) {
        dateField = date;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final FamC famc) {
        famCField = famc;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Family family) {
        familyField = family;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final FamS fams) {
        famSField = fams;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Head head) {
        headField = head;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Husband husband) {
        husbandField = husband;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Link link) {
        linkField = link;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Multimedia multimedia) {
        multimediaField = multimedia;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Name name) {
        nameField = name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Person person) {
        personField = person;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Place place) {
        placeField = place;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Root root) {
        rootField = root;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Source source) {
        sourceField = source;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final SourceLink sourceLink) {
        sourceLinkField = sourceLink;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Submittor submittor) {
        submittorField = submittor;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final SubmittorLink submittorLink) {
        submittorLinkField = submittorLink;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Trailer trailer) {
        trailerField = trailer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final Wife wife) {
        wifeField = wife;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final GedObject gedObject) {
        gedObjectField = gedObject;
    }
}
