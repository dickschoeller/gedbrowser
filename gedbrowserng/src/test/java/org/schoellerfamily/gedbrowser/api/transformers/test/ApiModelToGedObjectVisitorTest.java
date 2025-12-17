package org.schoellerfamily.gedbrowser.api.transformers.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiFamily;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiHead;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSource;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSubmission;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSubmitter;
import org.schoellerfamily.gedbrowser.api.transformers.ApiModelToGedObjectVisitor;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.datamodel.SourceLink;
import org.schoellerfamily.gedbrowser.datamodel.Submission;
import org.schoellerfamily.gedbrowser.datamodel.Submitter;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;

/**
 * @author Dick Schoeller
 */
public class ApiModelToGedObjectVisitorTest {
    /** */
    private GedObjectBuilder builder;



    /** */
    @BeforeEach
    public void setUp() {
        builder = new GedObjectBuilder();
    }

    /** */
    @Test
    public void testHead() {
        final ApiModelToGedObjectVisitor visitor =
                new ApiModelToGedObjectVisitor(builder);
        final ApiHead apiHead = new ApiHead();
        apiHead.accept(visitor);
        final Head gob = (Head) visitor.getGedObject();
        assertEquals("Head", gob.getString(), "head mismatch");
    }

    /** */
    @Test
    public void testPerson() {
        final ApiModelToGedObjectVisitor visitor =
                new ApiModelToGedObjectVisitor(builder);
        final ApiPerson.Builder b = new ApiPerson.Builder()
                .id("I1")
                .surname("Schoeller")
                .indexName("Schoeller/Richard/")
                .build();
        final ApiPerson apiPerson = new ApiPerson(b);
        apiPerson.accept(visitor);
        final Person gob = (Person) visitor.getGedObject();
        assertEquals("I1", gob.getString(), "person mismatch");
    }

    /** */
    @Test
    public void testPersonWithAttributes() {
        final ApiModelToGedObjectVisitor visitor =
                new ApiModelToGedObjectVisitor(builder);
        final ApiPerson.Builder b = new ApiPerson.Builder()
                .id("I1")
                .surname("Schoeller")
                .indexName("Richard/Schoeller/")
                .add(new ApiAttribute("name", "Richard/Schoeller/", null))
                .add(new ApiAttribute("1 JAN 1900", "date", ""))
                .build();
        final ApiPerson apiPerson = new ApiPerson(b);
        apiPerson.accept(visitor);
        final Person gob = (Person) visitor.getGedObject();
        final Name name = gob.getName();
        assertEquals("Richard/Schoeller/", name.getString(), "name mismatch");
    }

    /** */
    @Test
    public void testFamily() {
        final ApiModelToGedObjectVisitor visitor =
                new ApiModelToGedObjectVisitor(builder);
        final ApiFamily apiFamily = new ApiFamily(
                "family", "F1");
        apiFamily.accept(visitor);
        final Family gob = (Family) visitor.getGedObject();
        assertEquals("F1", gob.getString(), "family mismatch");
    }

    /** */
    @Test
    public void testSource() {
        final ApiModelToGedObjectVisitor visitor =
                new ApiModelToGedObjectVisitor(builder);
        final ApiSource apiSource = new ApiSource(
                "source", "S1", "Unknown");
        apiSource.accept(visitor);
        final Source gob = (Source) visitor.getGedObject();
        assertEquals("S1", gob.getString(), "source mismatch");
    }

    /** */
    @Test
    public void testSourceLink() {
        final Person p = builder.createPerson("I1", "Richard/Schoeller/");
        final ApiModelToGedObjectVisitor visitor =
                new ApiModelToGedObjectVisitor(builder, p);
        final ApiAttribute apiSource =
                new ApiAttribute("sourcelink", "S1", null);
        apiSource.accept(visitor);
        final SourceLink gob = (SourceLink) visitor.getGedObject();
        assertEquals("S1", gob.getToString(), "source mismatch");
    }

    /** */
    @Test
    public void testSubmission() {
        final ApiModelToGedObjectVisitor visitor =
                new ApiModelToGedObjectVisitor(builder);
        final ApiSubmission apiSubmission = new ApiSubmission(
                "submission", "SUBN");
        apiSubmission.accept(visitor);
        final Submission gob = (Submission) visitor.getGedObject();
        assertEquals("SUBN", gob.getString(), "submission mismatch");
    }

    /** */
    @Test
    public void testSubmitter() {
        final ApiModelToGedObjectVisitor visitor =
                new ApiModelToGedObjectVisitor(builder);
        final ApiSubmitter apiSubmitter = new ApiSubmitter(
                "submitter", "SUB1", "? ?");
        apiSubmitter.accept(visitor);
        final Submitter gob = (Submitter) visitor.getGedObject();
        assertEquals("SUB1", gob.getString(), "submitter mismatch");
    }

    /** */
    @Test
    public void testBasicObject() {
        final ApiModelToGedObjectVisitor visitor =
                new ApiModelToGedObjectVisitor(builder);
        final ApiObject apiSubmitter = new ApiObject(
                "object", "OBJECT1");
        apiSubmitter.accept(visitor);
        final GedObject gob = visitor.getGedObject();
        assertEquals("OBJECT1", gob.getString(), "object mismatch");
    }

    /** */
    @Test
    public void testBasicObjectType() {
        final ApiModelToGedObjectVisitor visitor =
                new ApiModelToGedObjectVisitor(builder);
        final ApiObject apiSubmitter = new ApiObject(
                "object", "OBJECT1");
        apiSubmitter.accept(visitor);
        final GedObject gob = visitor.getGedObject();
        assertEquals("Attribute", gob.getClass().getSimpleName(), "object type mismatch");
    }
}