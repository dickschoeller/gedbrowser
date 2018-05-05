package org.schoellerfamily.gedbrowser.api.transformers.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
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
    @Before
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
        assertEquals("head mismatch", "Head", gob.getString());
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
        assertEquals("person mismatch", "I1", gob.getString());
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
        assertEquals("name mismatch", "Richard/Schoeller/", name.getString());
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
        assertEquals("family mismatch", "F1", gob.getString());
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
        assertEquals("source mismatch", "S1", gob.getString());
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
        assertEquals("source mismatch", "S1", gob.getToString());
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
        assertEquals("submission mismatch", "SUBN", gob.getString());
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
        assertEquals("submitter mismatch", "SUB1", gob.getString());
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
        assertEquals("object mismatch", "OBJECT1", gob.getString());
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
        assertEquals("object type mismatch",
                "Attribute", gob.getClass().getSimpleName());
    }
}
