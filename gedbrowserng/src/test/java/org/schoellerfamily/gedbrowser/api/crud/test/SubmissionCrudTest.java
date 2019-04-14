package org.schoellerfamily.gedbrowser.api.crud.test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.api.Application;
import org.schoellerfamily.gedbrowser.api.controller.exception.DataSetNotFoundException;
import org.schoellerfamily.gedbrowser.api.controller.exception.ObjectNotFoundException;
import org.schoellerfamily.gedbrowser.api.crud.FamilyCrud;
import org.schoellerfamily.gedbrowser.api.crud.PersonCrud;
import org.schoellerfamily.gedbrowser.api.crud.SubmissionCrud;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSubmission;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.loader.GedDocumentFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = { "management.port=0" })
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
public class SubmissionCrudTest {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** */
    @Autowired
    private transient GedDocumentFileLoader loader;

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
    @Before
    public void setUp() {
        helper = new CrudTestHelper(
                new PersonCrud(loader, toDocConverter, repositoryManager),
                new FamilyCrud(loader, toDocConverter, repositoryManager));
        crud = new SubmissionCrud(loader, toDocConverter, repositoryManager);
    }

    /** */
    @Test
    public final void testGetSubmissionsGl120368() {
        logger.info("Beginning testGetSubmissionsGl120368");
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
        logger.info("Beginning testGetSubmissionsMiniSchoeller");
        final List<ApiSubmission> list = crud.readAll("mini-schoeller");
        assertTrue("should be no submissions", list.isEmpty());
    }

    /** */
    @Test
    public final void testGetSubmissionsGl120368B1() {
        logger.info("Beginning testGetSubmissionsGl120368B1");
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
        logger.info("Beginning testGetSubmissionsGl120368Xyzzy");
        try {
            final ApiSubmission submission = crud.readOne(helper.getDb(),
                    "Xyzzy");
            fail("The submission should not be found: "
                    + submission.getString());
        } catch (ObjectNotFoundException e) {
            assertEquals("Mismatched message",
                    "Object Xyzzy of type submission not found",
                    e.getMessage());
        }
    }

    /** */
    @Test
    public final void testCreateSubmissionsSimple() {
        logger.info("Beginning testCreateSubmissionsSimple");
        final ApiSubmission inSubmission = new ApiSubmission("submission", "");
        final ApiSubmission outSubmission = crud
                .createOne(helper.getDb(), inSubmission);
        then(outSubmission.getType()).isEqualTo(inSubmission.getType());
    }

    /** */
    @Test
    public final void testDeleteSubmission() {
        logger.info("Beginning testDeleteSubmission");
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
        logger.info("Beginning testDeleteSubmissionNotFound");
        try {
            final ApiSubmission submission = crud
                    .deleteOne(helper.getDb(), "XXXXXXX");
            fail("The submission should not be found: "
                    + submission.getString());
        } catch (ObjectNotFoundException e) {
            assertEquals("Mismatched message",
                    "Object XXXXXXX of type submission not found",
                    e.getMessage());
        }
    }

    /** */
    @Test
    public final void testDeleteSubmissionDatabaseNotFound() {
        logger.info("Beginning testDeleteSubmissionDatabaseNotFound");
        try {
            crud.deleteOne("XYZZY", "SUBM1");
            fail("The dataset should not be found: XYZZY, when getting SUBM1");
        } catch (DataSetNotFoundException e) {
            assertEquals("Mismatched message", "Data set XYZZY not found",
                    e.getMessage());
        }
    }

    /** */
    @Test
    public final void testUpdateSubmissionWithNote() {
        logger.info("Beginning testUpdateSubmissionWithNote");
        final List<ApiAttribute> attributes = new ArrayList<>();
        attributes.add(new ApiAttribute("attribute", "Note", "first note"));
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
        assertEquals("attribute should be present", aNote,
                updatedSubmission.getAttributes().get(1));
    }
}
