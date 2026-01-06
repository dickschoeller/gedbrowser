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
    void testHead() {
        final ApiModelToGedObjectVisitor visitor = new ApiModelToGedObjectVisitor(builder);
        final ApiHead apiHead = ApiHead.builder().build();
        apiHead.accept(visitor);
        final Head gob = (Head) visitor.getGedObject();
        assertEquals("Head", gob.getString(), "head mismatch");
    }

    /** */
    @Test
    void testPerson() {
        final ApiModelToGedObjectVisitor visitor = new ApiModelToGedObjectVisitor(builder);
        final ApiPerson apiPerson = ApiPerson.builder()
            .string("I1")
            .surname("Schoeller")
            .indexName("Schoeller/Richard/")
            .build();
        apiPerson.accept(visitor);
        final Person gob = (Person) visitor.getGedObject();
        assertEquals("I1", gob.getString(), "person mismatch");
    }

    /** */
    @Test
    void testPersonWithAttributes() {
        final ApiModelToGedObjectVisitor visitor = new ApiModelToGedObjectVisitor(builder);
        final ApiPerson apiPerson = ApiPerson.builder()
            .string("I1")
            .surname("Schoeller")
            .indexName("Schoeller/Richard/")
            .attribute(ApiAttribute.builder().type("name").string("Richard/Schoeller/").build())
            .attribute(ApiAttribute.builder().type("date").string("1 JAN 1900").build())
            .build();
        apiPerson.accept(visitor);
        final Person gob = (Person) visitor.getGedObject();
        final Name name = gob.getName();
        assertEquals("Richard/Schoeller/", name.getString(), "name mismatch");
    }

    /** */
    @Test
    void testFamily() {
        final ApiModelToGedObjectVisitor visitor = new ApiModelToGedObjectVisitor(builder);
        final ApiFamily apiFamily = ApiFamily.builder().type("family").string("F1").build();
        apiFamily.accept(visitor);
        final Family gob = (Family) visitor.getGedObject();
        assertEquals("F1", gob.getString(), "family mismatch");
    }

    /** */
    @Test
    void testSource() {
        final ApiModelToGedObjectVisitor visitor = new ApiModelToGedObjectVisitor(builder);
        final ApiSource apiSource = ApiSource.builder()
            .type("source")
            .string("S1")
            .title("Unknown")
            .build();
        apiSource.accept(visitor);
        final Source gob = (Source) visitor.getGedObject();
        assertEquals("S1", gob.getString(), "source mismatch");
    }

    /** */
    @Test
    void testSourceLink() {
        final Person p = builder.createPerson("I1", "Richard/Schoeller/");
        final ApiModelToGedObjectVisitor visitor = new ApiModelToGedObjectVisitor(builder, p);
        final ApiAttribute apiSource = ApiAttribute.builder()
            .type("sourcelink")
            .string("S1")
            .build();
        apiSource.accept(visitor);
        final SourceLink gob = (SourceLink) visitor.getGedObject();
        assertEquals("S1", gob.getToString(), "source mismatch");
    }

    /** */
    @Test
    void testSubmission() {
        final ApiModelToGedObjectVisitor visitor = new ApiModelToGedObjectVisitor(builder);
        final ApiSubmission apiSubmission = ApiSubmission.builder()
            .type("submission")
            .string("SUBN")
            .build();
        apiSubmission.accept(visitor);
        final Submission gob = (Submission) visitor.getGedObject();
        assertEquals("SUBN", gob.getString(), "submission mismatch");
    }

    /** */
    @Test
    void testSubmitter() {
        final ApiModelToGedObjectVisitor visitor = new ApiModelToGedObjectVisitor(builder);
        final ApiSubmitter apiSubmitter = ApiSubmitter.builder()
            .type("submitter")
            .string("SUB1")
            .name("? ?")
            .build();
        apiSubmitter.accept(visitor);
        final Submitter gob = (Submitter) visitor.getGedObject();
        assertEquals("SUB1", gob.getString(), "submitter mismatch");
    }

    /** */
    @Test
    void testBasicObject() {
        final ApiModelToGedObjectVisitor visitor = new ApiModelToGedObjectVisitor(builder);
        final ApiObject apiSubmitter = ApiObject.builder().type("object").string("OBJECT1").build();
        apiSubmitter.accept(visitor);
        final GedObject gob = visitor.getGedObject();
        assertEquals("OBJECT1", gob.getString(), "object mismatch");
    }

    /** */
    @Test
    void testBasicObjectType() {
        final ApiModelToGedObjectVisitor visitor = new ApiModelToGedObjectVisitor(builder);
        final ApiObject apiSubmitter = ApiObject.builder().type("object").string("OBJECT1").build();
        apiSubmitter.accept(visitor);
        final GedObject gob = visitor.getGedObject();
        assertEquals("Attribute", gob.getClass().getSimpleName(), "object type mismatch");
    }
}
