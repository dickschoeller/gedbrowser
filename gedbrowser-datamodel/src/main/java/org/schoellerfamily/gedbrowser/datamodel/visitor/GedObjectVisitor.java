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
 * This interface provides for using the visitor pattern to accumulate
 * information about a GedObject and its children. This has visitors for all
 * of the "standard" concrete objects and for the base GedObject. That last
 * would be the catch-all, which can be important for anonymous subclasses.
 *
 * @author Dick Schoeller
 */
@SuppressWarnings("PMD.TooManyMethods")
public interface GedObjectVisitor {
    /**
     * Visit an attribute.
     *
     * @param attribute the attribute to visit
     */
    void visit(Attribute attribute);

    /**
     * Visit a child.
     *
     * @param child the child to visit
     */
    void visit(Child child);

    /**
     * Visit a date.
     *
     * @param date the date to visit
     */
    void visit(Date date);

    /**
     * Visit a link to a family that a person is a child of.
     *
     * @param famc the link to visit
     */
    void visit(FamC famc);

    /**
     * Visit a family.
     *
     * @param family the family to visit
     */
    void visit(Family family);

    /**
     * Visit a link to a family that a person is a spouse of.
     *
     * @param fams the link to visit
     */
    void visit(FamS fams);

    /**
     * Visit a header.
     *
     * @param head the header to visit
     */
    void visit(Head head);

    /**
     * Visit a link to the husband of a family.
     *
     * @param husband the link to visit
     */
    void visit(Husband husband);

    /**
     * Visit a link to another object.
     *
     * @param link the link to visit
     */
    void visit(Link link);

    /**
     * Visit a multimedia object.
     *
     * @param multimedia the multimedia object to visit
     */
    void visit(Multimedia multimedia);

    /**
     * Visit a name.
     *
     * @param name the name to visit
     */
    void visit(Name name);

    /**
     * Visit a person.
     *
     * @param person the person to visit
     */
    void visit(Person person);

    /**
     * Visit a place.
     *
     * @param place the place to visit
     */
    void visit(Place place);

    /**
     * Visit a root.
     *
     * @param root the root to visit
     */
    void visit(Root root);

    /**
     * Visit a source.
     *
     * @param source the source to visit
     */
    void visit(Source source);

    /**
     * Visit a link to a source.
     *
     * @param sourceLink the link to visit
     */
    void visit(SourceLink sourceLink);

    /**
     * Visit a submittor.
     *
     * @param submittor the submittor to visit
     */
    void visit(Submittor submittor);

    /**
     * Visit a link to a submittor.
     *
     * @param submittorLink the link to visit
     */
    void visit(SubmittorLink submittorLink);

    /**
     * Visit a trailer.
     *
     * @param trailer the trailer to visit
     */
    void visit(Trailer trailer);

    /**
     * Visit a link to the wife of a family.
     *
     * @param wife the link to visit
     */
    void visit(Wife wife);

    /**
     * Visit a gedObject that isn't one of the above.
     *
     * @param gedObject the object to visit
     */
    void visit(GedObject gedObject);
}
