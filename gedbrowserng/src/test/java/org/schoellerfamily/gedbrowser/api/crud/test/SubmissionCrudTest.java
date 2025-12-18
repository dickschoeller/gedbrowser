package org.schoellerfamily.gedbrowser.api.crud.test;

import static org.assertj.core.api.Assertions.fail;
import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.api.Application;
import org.schoellerfamily.gedbrowser.api.controller.exception.DataSetNotFoundException;
import org.schoellerfamily.gedbrowser.api.controller.exception.ObjectNotFoundException;
import org.schoellerfamily.gedbrowser.api.crud.FamilyCrud;
import org.schoellerfamily.gedbrowser.api.crud.PersonCrud;
import org.schoellerfamily.gedbrowser.api.crud.SubmissionCrud;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSubmission;
import org.schoellerfamily.gedbrowser.api.loader.GedObjectFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Dick Schoeller
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = { "management.port=0" })
@SuppressWarnings({"PMD.JUnitTestsShouldIncludeAssert", "null"})
@Slf4j
public class SubmissionCrudTest {

    /** */
    @Autowired
    private transient GedObjectFileLoader loader;

    /** */
    @Autowired
    private transient GedObjectToGedDocumentMongoConverter toDocConverter;

    /** */
    @Autowired
    private transient RepositoryManagerMongo repositoryManager;

    /** */
    private SubmissionCrud crud;

    /** */
    private CrudTestHelper helper;

    /** */
    @BeforeEach
    public void setUp() {
        helper = new CrudTestHelper(
                new PersonCrud(loader, toDocConverter, repositoryManager),
                new FamilyCrud(loader, toDocConverter, repositoryManager));
        crud = new SubmissionCrud(loader, toDocConverter, repositoryManager);
    }

    /** */
    @Test
    public final void testGetSubmissionsGl120368() {
        log.info("Beginning testGetSubmissionsGl120368");
        final List<ApiSubmission> list = crud.readAll(helper.getDb());
        final ApiSubmission firstSubmission = list.get(0);
        then(firstSubmission.getString()).isEqualTo("B1");
        final ApiAttribute firstAttribute = firstSubmission.getAttributes()
                .get(0);
        then(firstAttribute.getType()).isEqualTo("attribute");
        then(firstAttribute.getString())
                .isEqualTo("Generations of descendants");
        then(firstAttribute.getTail()).isEqualTo("2");
    }

    /** */
    @Test
    public final void testGetSubmissionsMiniSchoeller() {
        log.info("Beginning testGetSubmissionsMiniSchoeller");
        final List<ApiSubmission> list = crud.readAll("mini-schoeller");
        assertTrue(list.isEmpty(), "should be no submissions");
    }

    /** */
    @Test
    public final void testGetSubmissionsGl120368B1() {
        log.info("Beginning testGetSubmissionsGl120368B1");
        final ApiSubmission submission = crud.readOne(helper.getDb(),
                "B1");
        final ApiAttribute firstAttribute = submission.getAttributes().get(0);
        then(firstAttribute.getType()).isEqualTo("attribute");
        then(firstAttribute.getString())
                .isEqualTo("Generations of descendants");
        then(firstAttribute.getTail()).isEqualTo("2");
    }

    /** */
    @Test
    public final void testGetSubmissionsGl120368Xyzzy() {
        log.info("Beginning testGetSubmissionsGl120368Xyzzy");
        try {
            final ApiSubmission submission = crud.readOne(helper.getDb(),
                    "Xyzzy");
            fail("The submission should not be found: "
                    + submission.getString());
        } catch (ObjectNotFoundException e) {
            assertEquals("Object Xyzzy of type submission not found",
                    e.getMessage(), "Mismatched message");
        }
    }

    /** */
    @Test
    public final void testCreateSubmissionsSimple() {
        log.info("Beginning testCreateSubmissionsSimple");
        final ApiSubmission inSubmission = new ApiSubmission("submission", "");
        final ApiSubmission outSubmission = crud
                .createOne(helper.getDb(), inSubmission);
        then(outSubmission.getType()).isEqualTo(inSubmission.getType());
    }

    /** */
    @Test
    public final void testDeleteSubmission() {
        log.info("Beginning testDeleteSubmission");
        final ApiSubmission inSubmission = new ApiSubmission("submission", "");
        final ApiSubmission outSubmission = crud
                .createOne(helper.getDb(), inSubmission);
        final String id = outSubmission.getString();

        final ApiSubmission deletedSubmission = crud
                .deleteOne(helper.getDb(), id);
        then(deletedSubmission.getString()).isEqualTo(id);
    }

    /** */
    @Test
    public final void testDeleteSubmissionNotFound() {
        log.info("Beginning testDeleteSubmissionNotFound");
        try {
            final ApiSubmission submission = crud
                    .deleteOne(helper.getDb(), "XXXXXXX");
            fail("The submission should not be found: "
                    + submission.getString());
        } catch (ObjectNotFoundException e) {
            assertEquals("Object XXXXXXX of type submission not found",
                    e.getMessage(), "Mismatched message");
        }
    }

    /** */
    @Test
    public final void testDeleteSubmissionDatabaseNotFound() {
        log.info("Beginning testDeleteSubmissionDatabaseNotFound");
        try {
            crud.deleteOne("XYZZY", "SUBM1");
            fail("The dataset should not be found: XYZZY, when getting SUBM1");
        } catch (DataSetNotFoundException e) {
            assertEquals("Data set XYZZY not found",
                    e.getMessage(), "Mismatched message");
        }
    }

    /** */
    @Test
    public final void testUpdateSubmissionWithNote() {
        log.info("Beginning testUpdateSubmissionWithNote");
        final List<ApiAttribute> attributes = List.of(
                new ApiAttribute("attribute", "Note", "first note"));
        final ApiSubmission inSubmission = new ApiSubmission("submission", "",
                attributes);
        final ApiSubmission outSubmission = crud
                .createOne(helper.getDb(), inSubmission);
        then(outSubmission.getType()).isEqualTo(inSubmission.getType());
        final ApiAttribute aNote = new ApiAttribute("attribute", "Note",
                "this is a note");
        outSubmission.getAttributes().add(aNote);
        final ApiSubmission updatedSubmission = crud.updateOne(helper.getDb(),
                outSubmission.getString(), outSubmission);
        assertEquals(aNote,
                java.util.Optional.ofNullable(updatedSubmission.getAttributes().get(1))
                        .orElse(null), "attribute should be present");
    }
}