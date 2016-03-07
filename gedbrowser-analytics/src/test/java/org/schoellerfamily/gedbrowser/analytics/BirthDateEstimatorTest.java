package org.schoellerfamily.gedbrowser.analytics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Child;
import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.datamodel.FamC;
import org.schoellerfamily.gedbrowser.datamodel.FamS;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Husband;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Wife;
import org.schoellerfamily.gedbrowser.reader.AbstractGedLine;
import org.schoellerfamily.gedbrowser.reader.ReaderHelper;

/**
 * @author Dick Schoeller
 */
public class BirthDateEstimatorTest {
    /** */
    @Test
    public final void testSimple() {
        final Root root = new Root(null);
        final Person person = new Person(root);
        final Attribute birth = new Attribute(person, "Birth");
        final Date birthDate = new Date(birth, "11 JUL 1960");

        root.insert("I0", person);
        person.insert(new Name(person, "J. Random/Schoeller/"));
        person.insert(birth);
        birth.insert(birthDate);

        final BirthDateEstimator estimator = createBirthEstimator(person);
        final int birthYear = 1960;
        final int birthMonth = 7;
        final int birthDay = 11;
        final LocalDate expected =
                new LocalDate(birthYear, birthMonth, birthDay);
        final LocalDate actual = estimator.estimateBirthDate();
        assertMatch(expected, actual);
    }

    /** */
    @Test
    public final void testEmpty() {
        final Root root = new Root(null);
        final Person person = new Person(root);

        root.insert("I0", person);
        person.insert(new Name(person, "J. Random/Schoeller/"));

        final BirthDateEstimator estimator = createBirthEstimator(person);
        final LocalDate actual = estimator.estimateBirthDate();
        assertNull(actual);
    }

    /** */
    @Test
    public final void testFromYoungerSibling() {
        final Root root = new Root(null);
        final Person person1 = new Person(root, new ObjectId("I1"));
        final Attribute birth1 = new Attribute(person1, "Birth");
        person1.insert(new Name(person1, "J. Random/Schoeller/"));
        person1.insert(birth1);
        birth1.insert(new Date(birth1, "11 JUL 1960"));

        final Person person2 = new Person(root, new ObjectId("I2"));
        person2.insert(new Name(person2, "Anon/Schoeller/"));

        final ObjectId xrefFam1 = new ObjectId("F1");
        final Family family = new Family(root, xrefFam1);
        final FamC famC1 = new FamC(person1, "FAMC", xrefFam1);
        final FamC famC2 = new FamC(person2, "FAMC", xrefFam1);
        final Child child1 = new Child(family, "Child", new ObjectId("I1"));
        final Child child2 = new Child(family, "Child", new ObjectId("I2"));

        root.insert(family);
        root.insert(person1);
        root.insert(person2);
        family.insert(child1);
        family.insert(child2);
        person1.insert(famC1);
        person2.insert(famC2);

        final BirthDateEstimator estimator = createBirthEstimator(person2);
        final int birthYear = 1962;
        final int birthMonth = 7;
        final int birthDay = 1;
        final LocalDate expected =
                new LocalDate(birthYear, birthMonth, birthDay);
        final LocalDate actual = estimator.estimateBirthDate();
        assertMatch(expected, actual);
    }

    /** */
    @Test
    public final void testFromYoungerSiblings() {
        final Root root = new Root(null);

        final Person person1 = new Person(root, new ObjectId("I1"));
        final Attribute birth1 = new Attribute(person1, "Birth");
        person1.insert(new Name(person1, "J. Random/Schoeller/"));
        person1.insert(birth1);
        birth1.insert(new Date(birth1, "11 JUL 1960"));

        final Person person2 = new Person(root, new ObjectId("I2"));
        person2.insert(new Name(person2, "Anonymous/Schoeller/"));

        final Person person3 = new Person(root, new ObjectId("I3"));
        person3.insert(new Name(person3, "Anonyma/Schoeller/"));

        final ObjectId xrefFam1 = new ObjectId("F1");
        final Family family = new Family(root, xrefFam1);
        final FamC famC1 = new FamC(person1, "FAMC", xrefFam1);
        final FamC famC2 = new FamC(person2, "FAMC", xrefFam1);
        final FamC famC3 = new FamC(person3, "FAMC", xrefFam1);
        final Child child1 = new Child(family, "Child", new ObjectId("I1"));
        final Child child2 = new Child(family, "Child", new ObjectId("I2"));
        final Child child3 = new Child(family, "Child", new ObjectId("I3"));

        root.insert(family);
        root.insert(person1);
        root.insert(person2);
        root.insert(person3);
        family.insert(child1);
        family.insert(child2);
        family.insert(child3);
        person1.insert(famC1);
        person2.insert(famC2);
        person3.insert(famC3);

        final BirthDateEstimator estimator = createBirthEstimator(person3);
        final int birthYear = 1964;
        final int birthMonth = 7;
        final int birthDay = 1;
        final LocalDate expected =
                new LocalDate(birthYear, birthMonth, birthDay);
        final LocalDate actual = estimator.estimateBirthDate();
        assertMatch(expected, actual);
    }

    /** */
    @Test
    public final void testFromEmptyYoungerSiblings() {
        final Root root = new Root(null);

        final Person person1 = new Person(root, new ObjectId("I1"));
        person1.insert(new Name(person1, "J. Random/Schoeller/"));

        final Person person2 = new Person(root, new ObjectId("I2"));
        person2.insert(new Name(person2, "Anonymous/Schoeller/"));

        final Person person3 = new Person(root, new ObjectId("I3"));
        person3.insert(new Name(person3, "Anonyma/Schoeller/"));

        final ObjectId xrefFam1 = new ObjectId("F1");
        final Family family = new Family(root, xrefFam1);
        final FamC famC1 = new FamC(person1, "FAMC", xrefFam1);
        final FamC famC2 = new FamC(person2, "FAMC", xrefFam1);
        final FamC famC3 = new FamC(person3, "FAMC", xrefFam1);
        final Child child1 = new Child(family, "Child", new ObjectId("I1"));
        final Child child2 = new Child(family, "Child", new ObjectId("I2"));
        final Child child3 = new Child(family, "Child", new ObjectId("I3"));

        root.insert(family);
        root.insert(person1);
        root.insert(person2);
        root.insert(person3);
        family.insert(child1);
        family.insert(child2);
        family.insert(child3);
        person1.insert(famC1);
        person2.insert(famC2);
        person3.insert(famC3);

        final BirthDateEstimator estimator = createBirthEstimator(person3);
        final LocalDate actual = estimator.estimateBirthDate();
        assertNull(actual);
    }

    /** */
    @Test
    public final void testFromOlderSibling() {
        final Root root = new Root(null);

        final Person person1 = new Person(root, new ObjectId("I1"));
        final Attribute birth1 = new Attribute(person1, "Birth");
        person1.insert(new Name(person1, "J. Random/Schoeller/"));
        person1.insert(birth1);
        birth1.insert(new Date(birth1, "11 JUL 1960"));

        final Person person2 = new Person(root, new ObjectId("I2"));
        person2.insert(new Name(person2, "Anon/Schoeller/"));

        final ObjectId xrefFam1 = new ObjectId("F1");
        final Family family = new Family(root, xrefFam1);
        final FamC famC1 = new FamC(person1, "FAMC", xrefFam1);
        final FamC famC2 = new FamC(person2, "FAMC", xrefFam1);
        final Child child1 = new Child(family, "Child", new ObjectId("I1"));
        final Child child2 = new Child(family, "Child", new ObjectId("I2"));

        root.insert(family);
        root.insert(person1);
        root.insert(person2);
        family.insert(child2);
        family.insert(child1);
        person1.insert(famC1);
        person2.insert(famC2);

        final BirthDateEstimator estimator = createBirthEstimator(person2);
        final int birthYear = 1958;
        final int birthMonth = 7;
        final int birthDay = 1;
        final LocalDate expected =
                new LocalDate(birthYear, birthMonth, birthDay);
        final LocalDate actual = estimator.estimateBirthDate();
        assertMatch(expected, actual);
    }

    /** */
    @Test
    public final void testFromOlderSiblings() {
        final Root root = new Root(null);

        final Person person1 = new Person(root, new ObjectId("I1"));
        final Attribute birth1 = new Attribute(person1, "Birth");
        person1.insert(new Name(person1, "J. Random/Schoeller/"));
        person1.insert(birth1);
        birth1.insert(new Date(birth1, "11 JUL 1960"));

        final Person person2 = new Person(root, new ObjectId("I2"));
        person2.insert(new Name(person2, "Anonymous/Schoeller/"));

        final Person person3 = new Person(root, new ObjectId("I3"));
        person3.insert(new Name(person3, "Anonyma/Schoeller/"));

        final ObjectId xrefFam1 = new ObjectId("F1");
        final Family family = new Family(root, xrefFam1);
        final FamC famC1 = new FamC(person1, "FAMC", xrefFam1);
        final FamC famC2 = new FamC(person2, "FAMC", xrefFam1);
        final FamC famC3 = new FamC(person3, "FAMC", xrefFam1);
        final Child child1 = new Child(family, "Child", new ObjectId("I1"));
        final Child child2 = new Child(family, "Child", new ObjectId("I2"));
        final Child child3 = new Child(family, "Child", new ObjectId("I3"));

        root.insert(family);
        root.insert(person1);
        root.insert(person2);
        root.insert(person3);
        family.insert(child3);
        family.insert(child2);
        family.insert(child1);
        person1.insert(famC1);
        person2.insert(famC2);
        person3.insert(famC3);

        final BirthDateEstimator estimator = createBirthEstimator(person3);
        final int birthYear = 1956;
        final int birthMonth = 7;
        final int birthDay = 1;
        final LocalDate expected =
                new LocalDate(birthYear, birthMonth, birthDay);
        final LocalDate actual = estimator.estimateBirthDate();
        assertMatch(expected, actual);
    }

    /** */
    @Test
    public final void testFromEmptyOlderSiblings() {
        final Root root = new Root(null);

        final Person person1 = new Person(root, new ObjectId("I1"));
        person1.insert(new Name(person1, "J. Random/Schoeller/"));

        final Person person2 = new Person(root, new ObjectId("I2"));
        person2.insert(new Name(person2, "Anonymous/Schoeller/"));

        final Person person3 = new Person(root, new ObjectId("I3"));
        person3.insert(new Name(person3, "Anonyma/Schoeller/"));

        final ObjectId xrefFam1 = new ObjectId("F1");
        final Family family = new Family(root, xrefFam1);
        final FamC famC1 = new FamC(person1, "FAMC", xrefFam1);
        final FamC famC2 = new FamC(person2, "FAMC", xrefFam1);
        final FamC famC3 = new FamC(person3, "FAMC", xrefFam1);
        final Child child1 = new Child(family, "Child", new ObjectId("I1"));
        final Child child2 = new Child(family, "Child", new ObjectId("I2"));
        final Child child3 = new Child(family, "Child", new ObjectId("I3"));

        root.insert(family);
        root.insert(person1);
        root.insert(person2);
        root.insert(person3);
        family.insert(child3);
        family.insert(child2);
        family.insert(child1);
        person1.insert(famC1);
        person2.insert(famC2);
        person3.insert(famC3);

        final BirthDateEstimator estimator = createBirthEstimator(person3);
        final LocalDate actual = estimator.estimateBirthDate();
        assertNull(actual);
    }

    /** */
    @Test
    public final void testFromOwnMarriage() {
        final Root root = new Root(null, "Root");

        final Person person1 = new Person(root, new ObjectId("I1"));
        person1.insert(new Name(person1, "J. Random/Schoeller/"));

        final Person person2 = new Person(root, new ObjectId("I2"));
        person2.insert(new Name(person2, "Anonymous/Smith/"));

        final ObjectId xrefFam1 = new ObjectId("F1");
        final Family family = new Family(root, xrefFam1);
        final FamS famS1 = new FamS(person1, "FAMS", xrefFam1);
        final FamS famS2 = new FamS(person2, "FAMS", xrefFam1);
        final Husband husband = new Husband(family, "Husband",
                new ObjectId("I1"));
        final Wife wife = new Wife(family, "Wife", new ObjectId("I2"));

        root.insert(null, person1);
        root.insert(null, person2);
        root.insert(null, family);

        family.insert(husband);
        family.insert(wife);
        person1.insert(famS1);
        person2.insert(famS2);

        final Attribute marriage = new Attribute(family, "Marriage");
        family.insert(marriage);
        final Date marriageDate = new Date(marriage, "27 MAY 1984");
        marriage.insert(marriageDate);

        final BirthDateEstimator estimator = createBirthEstimator(person2);
        final int birthYear = 1959;
        final int birthMonth = 5;
        final int birthDay = 1;
        final LocalDate expected =
                new LocalDate(birthYear, birthMonth, birthDay);
        final LocalDate actual = estimator.estimateBirthDate();
        assertMatch(expected, actual);
    }

    /** */
    @Test
    public final void testFromOwnEmptyMarriage() {
        final Root root = new Root(null);

        final Person person1 = new Person(root, new ObjectId("I1"));
        person1.insert(new Name(person1, "J. Random/Schoeller/"));

        final Person person2 = new Person(root, new ObjectId("I2"));
        person2.insert(new Name(person2, "Anonymous/Smith/"));

        final ObjectId xrefFam1 = new ObjectId("F1");
        final Family family = new Family(root, xrefFam1);
        final FamS famS1 = new FamS(person1, "FAMS", xrefFam1);
        final FamS famS2 = new FamS(person2, "FAMS", xrefFam1);
        final Husband husband = new Husband(family, "Husband",
                new ObjectId("I1"));
        final Wife wife = new Wife(family, "Wife", new ObjectId("I2"));

        root.insert(family);
        root.insert(person1);
        root.insert(person2);

        family.insert(husband);
        family.insert(wife);
        person1.insert(famS1);
        person2.insert(famS2);

        final BirthDateEstimator estimator = createBirthEstimator(person2);
        final LocalDate actual = estimator.estimateBirthDate();
        assertNull(actual);
    }

    /** */
    @Test
    public final void testFromOwnMarriages() {
        final Root root = new Root(null);

        final Person person1 = new Person(root, new ObjectId("I1"));
        person1.insert(new Name(person1, "J. Random/Schoeller/"));

        final Person person2 = new Person(root, new ObjectId("I2"));
        person2.insert(new Name(person2, "Anonymous/Smith/"));

        final Person person3 = new Person(root, new ObjectId("I3"));
        person3.insert(new Name(person3, "Anonymous/Jones/"));

        final ObjectId xrefFam1 = new ObjectId("F1");
        final Family family1 = new Family(root, xrefFam1);
        final FamS famS11 = new FamS(person1, "FAMS", xrefFam1);
        final FamS famS12 = new FamS(person2, "FAMS", xrefFam1);
        final Husband husband1 = new Husband(family1, "Husband",
                new ObjectId("I1"));
        final Wife wife1 = new Wife(family1, "Wife", new ObjectId("I2"));

        final ObjectId xrefFam2 = new ObjectId("F2");
        final Family family2 = new Family(root, xrefFam2);
        final FamS famS21 = new FamS(person1, "FAMS", xrefFam2);
        final FamS famS23 = new FamS(person3, "FAMS", xrefFam2);
        final Husband husband2 = new Husband(family2, "Husband",
                new ObjectId("I1"));
        final Wife wife2 = new Wife(family2, "Wife", new ObjectId("I3"));

        final Attribute marriage1 = new Attribute(family1, "Marriage");
        family1.insert(marriage1);
        final Date marriageDate1 = new Date(marriage1, "27 MAY 1984");
        marriage1.insert(marriageDate1);

        final Attribute marriage2 = new Attribute(family2, "Marriage");
        family2.insert(marriage2);
        final Date marriageDate = new Date(marriage2, "14 JUN 1980");
        marriage2.insert(marriageDate);

        root.insert(family1);
        root.insert(family2);
        root.insert(person1);
        root.insert(person2);
        root.insert(person3);

        family1.insert(husband1);
        family1.insert(wife1);
        person1.insert(famS11);
        person2.insert(famS12);

        family2.insert(husband2);
        family2.insert(wife2);
        person1.insert(famS21);
        person3.insert(famS23);

        final BirthDateEstimator estimator = createBirthEstimator(person1);
        final int birthYear = 1955;
        final int birthMonth = 6;
        final int birthDay = 1;
        final LocalDate expected =
                new LocalDate(birthYear, birthMonth, birthDay);
        final LocalDate actual = estimator.estimateBirthDate();
        assertMatch(expected, actual);
    }

    /** */
    @Test
    public final void testFromParentsMarriage() {
        final Root root = new Root(null);

        final Person person1 = new Person(root, new ObjectId("I1"));
        person1.insert(new Name(person1, "J. Random/Schoeller/"));

        final Person person2 = new Person(root, new ObjectId("I2"));
        person2.insert(new Name(person2, "Anonymous/Smith/"));

        final Person person3 = new Person(root, new ObjectId("I3"));
        person3.insert(new Name(person3, "Anonymous/Jones/"));

        final ObjectId xrefFam1 = new ObjectId("F1");
        final Family family1 = new Family(root, xrefFam1);
        final FamS famS1 = new FamS(person1, "FAMS", xrefFam1);
        final FamS famS2 = new FamS(person2, "FAMS", xrefFam1);
        final FamC famC3 = new FamC(person3, "FAMC", xrefFam1);

        final Husband husband1 = new Husband(family1, "Husband",
                new ObjectId("I1"));
        final Wife wife1 = new Wife(family1, "Wife", new ObjectId("I2"));
        final Child child1 = new Child(family1, "Child", new ObjectId("I3"));

        final Attribute marriage1 = new Attribute(family1, "Marriage");
        family1.insert(marriage1);
        final Date marriageDate1 = new Date(marriage1, "27 MAY 1984");
        marriage1.insert(marriageDate1);

        root.insert(family1);
        root.insert(person1);
        root.insert(person2);
        root.insert(person3);

        family1.insert(husband1);
        family1.insert(wife1);
        family1.insert(child1);
        person1.insert(famS1);
        person2.insert(famS2);
        person3.insert(famC3);

        final BirthDateEstimator estimator = createBirthEstimator(person3);
        final int birthYear = 1986;
        final int birthMonth = 5;
        final int birthDay = 1;
        final LocalDate expected =
                new LocalDate(birthYear, birthMonth, birthDay);
        final LocalDate actual = estimator.estimateBirthDate();
        assertMatch(expected, actual);
    }

    /** */
    @Test
    public final void testFromParentsMarriageWithOlderSiblings() {
        final Root root = new Root(null);

        final Person person1 = new Person(root, new ObjectId("I1"));
        person1.insert(new Name(person1, "J. Random/Schoeller/"));

        final Person person2 = new Person(root, new ObjectId("I2"));
        person2.insert(new Name(person2, "Anonymous/Smith/"));

        final Person person3 = new Person(root, new ObjectId("I3"));
        person3.insert(new Name(person3, "Anonymous/Jones/"));

        final Person person4 = new Person(root, new ObjectId("I4"));
        person4.insert(new Name(person4, "No Name/Johnson/"));

        final ObjectId xrefFam1 = new ObjectId("F1");
        final Family family1 = new Family(root, xrefFam1);
        final FamS famS1 = new FamS(person1, "FAMS", xrefFam1);
        final FamS famS2 = new FamS(person2, "FAMS", xrefFam1);
        final FamC famC3 = new FamC(person3, "FAMC", xrefFam1);
        final FamC famC4 = new FamC(person4, "FAMC", xrefFam1);

        final Husband husband1 = new Husband(family1, "Husband",
                new ObjectId("I1"));
        final Wife wife1 = new Wife(family1, "Wife", new ObjectId("I2"));
        final Child child1 = new Child(family1, "Child", new ObjectId("I3"));
        final Child child2 = new Child(family1, "Child", new ObjectId("I4"));

        final Attribute marriage1 = new Attribute(family1, "Marriage");
        family1.insert(marriage1);
        final Date marriageDate1 = new Date(marriage1, "27 MAY 1984");
        marriage1.insert(marriageDate1);

        root.insert(family1);
        root.insert(person1);
        root.insert(person2);
        root.insert(person3);
        root.insert(person4);

        family1.insert(husband1);
        family1.insert(wife1);
        family1.insert(child1);
        family1.insert(child2);
        person1.insert(famS1);
        person2.insert(famS2);
        person3.insert(famC3);
        person4.insert(famC4);

        final BirthDateEstimator estimator = createBirthEstimator(person4);
        final int birthYear = 1988;
        final int birthMonth = 5;
        final int birthDay = 1;
        final LocalDate expected =
                new LocalDate(birthYear, birthMonth, birthDay);
        final LocalDate actual = estimator.estimateBirthDate();
        assertMatch(expected, actual);
    }

    /** */
    @Test
    public final void testFromChild() {
        final Root root = new Root(null);

        final Person person1 = new Person(root, new ObjectId("I1"));
        person1.insert(new Name(person1, "J. Random/Schoeller/"));

        final Person person2 = new Person(root, new ObjectId("I2"));
        person2.insert(new Name(person2, "Anonymous/Smith/"));

        final Person person3 = new Person(root, new ObjectId("I3"));
        final Attribute birth3 = new Attribute(person3, "Birth");
        person3.insert(new Name(person3, "Anonymous/Jones/"));
        person3.insert(birth3);
        birth3.insert(new Date(birth3, "11 JUL 1960"));

        final ObjectId xrefFam1 = new ObjectId("F1");
        final Family family1 = new Family(root, xrefFam1);
        final FamS famS1 = new FamS(person1, "FAMS", xrefFam1);
        final FamC famC2 = new FamC(person2, "FAMC", xrefFam1);
        final FamC famC3 = new FamC(person3, "FAMC", xrefFam1);

        final Husband husband1 = new Husband(family1, "Husband",
                new ObjectId("I1"));
        final Child child1 = new Child(family1, "Child", new ObjectId("I2"));
        final Child child2 = new Child(family1, "Child", new ObjectId("I3"));

        root.insert(family1);
        root.insert(person1);
        root.insert(person2);
        root.insert(person3);

        family1.insert(husband1);
        family1.insert(child1);
        family1.insert(child2);
        person1.insert(famS1);
        person3.insert(famC2);
        person3.insert(famC3);

        final BirthDateEstimator estimator = createBirthEstimator(person1);
        final int birthYear = 1931;
        final int birthMonth = 7;
        final int birthDay = 1;
        final LocalDate expected =
                new LocalDate(birthYear, birthMonth, birthDay);
        final LocalDate actual = estimator.estimateBirthDate();
        assertMatch(expected, actual);
    }

    /** */
    @Test
    public final void testFromChildren() {
        final Root root = new Root(null);

        final Person person1 = new Person(root, new ObjectId("I1"));
        person1.insert(new Name(person1, "J. Random/Schoeller/"));

        final Person person3 = new Person(root, new ObjectId("I3"));
        final Attribute birth3 = new Attribute(person3, "Birth");
        person3.insert(new Name(person3, "Anonymous/Jones/"));
        person3.insert(birth3);
        birth3.insert(new Date(birth3, "11 JUL 1960"));

        final ObjectId xrefFam1 = new ObjectId("F1");
        final Family family1 = new Family(root, xrefFam1);
        final FamS famS1 = new FamS(person1, "FAMS", xrefFam1);
        final FamC famC3 = new FamC(person3, "FAMC", xrefFam1);

        final Husband husband1 = new Husband(family1, "Husband",
                new ObjectId("I1"));
        final Child child1 = new Child(family1, "Child", new ObjectId("I3"));

        root.insert(family1);
        root.insert(person1);
        root.insert(person3);

        family1.insert(husband1);
        family1.insert(child1);
        person1.insert(famS1);
        person3.insert(famC3);

        final BirthDateEstimator estimator = createBirthEstimator(person1);
        final int birthYear = 1933;
        final int birthMonth = 7;
        final int birthDay = 1;
        final LocalDate expected =
                new LocalDate(birthYear, birthMonth, birthDay);
        final LocalDate actual = estimator.estimateBirthDate();
        assertMatch(expected, actual);
    }

    /** */
    @Test
    public final void testFromHusband() {
        final Root root = new Root(null, "Root");

        final Person person1 = new Person(root, new ObjectId("I1"));
        person1.insert(new Name(person1, "J. Random/Schoeller/"));

        final Person person2 = new Person(root, new ObjectId("I2"));
        person2.insert(new Name(person2, "Anonymous/Smith/"));

        final ObjectId xrefFam1 = new ObjectId("F1");
        final Family family = new Family(root, xrefFam1);
        final FamS famS1 = new FamS(person1, "FAMS", xrefFam1);
        final FamS famS2 = new FamS(person2, "FAMS", xrefFam1);
        final Husband husband = new Husband(family, "Husband",
                new ObjectId("I1"));
        final Attribute birth1 = new Attribute(person1, "Birth");
        final Date birthDate1 = new Date(birth1, "11 JUL 1960");
        final Wife wife = new Wife(family, "Wife", new ObjectId("I2"));

        root.insert(null, person1);
        person1.insert(birth1);
        birth1.insert(birthDate1);
        root.insert(null, person2);
        root.insert(null, family);

        family.insert(husband);
        family.insert(wife);
        person1.insert(famS1);
        person2.insert(famS2);

        final BirthDateEstimator estimator = createBirthEstimator(person2);
        final int birthYear = 1965;
        final int birthMonth = 7;
        final int birthDay = 1;
        final LocalDate expected =
                new LocalDate(birthYear, birthMonth, birthDay);
        final LocalDate actual = estimator.estimateBirthDate();
        assertMatch(expected, actual);
    }

    /** */
    @Test
    public final void testFromWife() {
        final Root root = new Root(null, "Root");

        final Person person1 = new Person(root, new ObjectId("I1"));
        person1.insert(new Name(person1, "J. Random/Schoeller/"));

        final Person person2 = new Person(root, new ObjectId("I2"));
        person2.insert(new Name(person2, "Anonymous/Smith/"));

        final ObjectId xrefFam1 = new ObjectId("F1");
        final Family family = new Family(root, xrefFam1);
        final FamS famS1 = new FamS(person1, "FAMS", xrefFam1);
        final FamS famS2 = new FamS(person2, "FAMS", xrefFam1);
        final Husband husband = new Husband(family, "Husband",
                new ObjectId("I1"));
        final Wife wife = new Wife(family, "Wife", new ObjectId("I2"));
        final Attribute birth2 = new Attribute(person2, "Birth");
        final Date birthDate2 = new Date(birth2, "11 JUL 1960");

        root.insert(null, person1);
        person2.insert(birth2);
        birth2.insert(birthDate2);
        root.insert(null, person2);
        root.insert(null, family);

        family.insert(husband);
        family.insert(wife);
        person1.insert(famS1);
        person2.insert(famS2);

        final BirthDateEstimator estimator = createBirthEstimator(person1);
        final int birthYear = 1955;
        final int birthMonth = 7;
        final int birthDay = 1;
        final LocalDate expected =
                new LocalDate(birthYear, birthMonth, birthDay);
        final LocalDate actual = estimator.estimateBirthDate();
        assertMatch(expected, actual);
    }

    /**
     * Test for persons for who the algorithm fails to come up with
     * a birth date.
     *
     * TODO this test exercises stuff not test elsewhere. Fix that.
     *
     * @throws IOException if the file can't be read
     */
    @Test
    public final void testFactoryGedFile() throws IOException {
        final AbstractGedLine top = readFileTestSource();
        final GedObject root = top.createGedObject((AbstractGedLine) null);
        final List<Person> unhandled = new ArrayList<>();
        for (final String letter : root.findSurnameInitialLetters()) {
            for (final String surname : root.findBySurnamesBeginWith(letter)) {
                for (final Person person : root.findBySurname(surname)) {
                    final BirthDateEstimator estimator =
                            createBirthEstimator(person);
                    final LocalDate localDate = estimator.estimateBirthDate();
                    if (localDate == null) {
                        unhandled.add(person);
                    }
                }
            }
        }
        final int max = Math.min(unhandled.size(), 50);
        if (max != 0) {
            System.out.println("Found " + unhandled.size()
                    + " Persons with unestimated birth date");
            System.out.println("Top " + max + ":");
        }
        int printed = 0;
        for (final Person person : unhandled) {
            System.out.println("    " + person.getString() + " "
                    + person.getIndexName());
            if (printed++ > max) {
                break;
            }
        }
        assertEquals("Shouldn't have found any unhandled dates", 0, max);
    }

    /**
     * Wrap calls to the constructor, so that we avoid the creation in
     * loops warning.
     *
     * @param person the person to estimate
     * @return the new estimattor
     */
    private BirthDateEstimator createBirthEstimator(final Person person) {
        return new BirthDateEstimator(
                person);
    }

    /**
     * @param expected expected date
     * @param actual actual date
     */
    private void assertMatch(final LocalDate expected, final LocalDate actual) {
        assertTrue(mismatchString(expected, actual), expected.isEqual(actual));
    }

    /**
     * @param expected expected date
     * @param actual actual date
     * @return string describing the mismatch
     */
    private String mismatchString(final LocalDate expected,
            final LocalDate actual) {
        return "Don't match! expected: " + expected + ", actual: " + actual;
    }

    /**
     * Read data for tests available to prepare data for tests.
     *
     * @return a populated GedLine parse tree.
     * @throws IOException because reader might throw.
     */
    private AbstractGedLine readFileTestSource() throws IOException {
        return ReaderHelper.readFileTestSource(this,
                "/var/lib/gedbrowser/schoeller.ged");
    }
}
