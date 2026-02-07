package org.schoellerfamily.gedbrowser.api.crud.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.api.Application;
import org.schoellerfamily.gedbrowser.api.controller.exception.DataSetNotFoundException;
import org.schoellerfamily.gedbrowser.api.controller.exception.ObjectNotFoundException;
import org.schoellerfamily.gedbrowser.api.crud.FamilyCrud;
import org.schoellerfamily.gedbrowser.api.crud.PersonCrud;
import org.schoellerfamily.gedbrowser.api.crud.SubmissionCrud;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSubmission;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSubmission.ApiSubmissionBuilder;
import org.schoellerfamily.gedbrowser.api.loader.GedObjectFileLoader;
import org.schoellerfamily.gedbrowser.api.test.TestConfiguration;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Dick Schoeller
 */
@SpringBootTest(
    classes = { Application.class, TestConfiguration.class },
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = { "management.port=0" })
@Slf4j
class SubmissionCrudIT {

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
    void setUp() {
        helper = new CrudTestHelper(new PersonCrud(loader, toDocConverter, repositoryManager),
            new FamilyCrud(loader, toDocConverter, repositoryManager));
        crud = new SubmissionCrud(loader, toDocConverter, repositoryManager);
    }

    /** */
    @Test
    void testGetSubmissionsGl120368() {
        log.info("Beginning testGetSubmissionsGl120368");
        final List<ApiSubmission> list = crud.readAll(helper.getDb());
        final ApiSubmission firstSubmission = list.get(0);

        assertThat(firstSubmission)
            .returns("B1", o -> o.getString())
            .returns("attribute", o -> o.getAttributes().get(0).getType())
            .returns("Generations of descendants", o -> o.getAttributes().get(0).getString())
            .returns("2", o -> o.getAttributes().get(0).getTail());
    }

    /** */
    @Test
    void testGetSubmissionsMiniSchoeller() {
        log.info("Beginning testGetSubmissionsMiniSchoeller");
        final List<ApiSubmission> list = crud.readAll("mini-schoeller");
        assertTrue(list.isEmpty(), "should be no submissions");
    }

    /** */
    @Test
    void testGetSubmissionsGl120368B1() {
        log.info("Beginning testGetSubmissionsGl120368B1");
        final ApiSubmission submission = crud.readOne(helper.getDb(), "B1");

        assertThat(submission.getAttributes().get(0))
            .returns("attribute", o -> o.getType())
            .returns("Generations of descendants", o -> o.getString())
            .returns("2", o -> o.getTail());
    }

    /** */
    @Test
    void testGetSubmissionsGl120368Xyzzy() {
        log.info("Beginning testGetSubmissionsGl120368Xyzzy");
        assertThatExceptionOfType(ObjectNotFoundException.class)
            .isThrownBy(() -> crud.readOne(helper.getDb(), "Xyzzy"))
            .withMessage("Object Xyzzy of type submission not found");
    }

    /** */
    @Test
    void testCreateSubmissionsSimple() {
        log.info("Beginning testCreateSubmissionsSimple");
        final ApiSubmission inSubmission = ApiSubmission.builder()
            .type("submission")
            .string("")
            .attributes(List.of())
            .build();
        final ApiSubmission outSubmission = crud.createOne(helper.getDb(), inSubmission);
        assertThat(outSubmission).returns(inSubmission.getType(), o -> o.getType());
    }

    /** */
    @Test
    void testDeleteSubmission() {
        log.info("Beginning testDeleteSubmission");
        final ApiSubmission inSubmission = ApiSubmission.builder()
            .type("submission")
            .string("")
            .attributes(List.of())
            .build();
        final ApiSubmission outSubmission = crud.createOne(helper.getDb(), inSubmission);
        final String id = outSubmission.getString();

        final ApiSubmission deletedSubmission = crud.deleteOne(helper.getDb(), id);
        assertThat(deletedSubmission).returns(id, o -> o.getString());
    }

    /** */
    @Test
    void testDeleteSubmissionNotFound() {
        log.info("Beginning testDeleteSubmissionNotFound");
        assertThatExceptionOfType(ObjectNotFoundException.class)
            .isThrownBy(() -> crud.deleteOne(helper.getDb(), "XXXXXXX"))
            .withMessage("Object XXXXXXX of type submission not found");
    }

    /** */
    @Test
    void testDeleteSubmissionDatabaseNotFound() {
        log.info("Beginning testDeleteSubmissionDatabaseNotFound");
        assertThatExceptionOfType(DataSetNotFoundException.class)
            .isThrownBy(() -> crud.deleteOne("XYZZY", "SUBM1"))
            .withMessage("Data set XYZZY not found");
    }

    /** */
    @Test
    @SuppressWarnings({ "PMD.UnitTestContainsTooManyAsserts" })
    void testUpdateSubmissionWithNote() {
        log.info("Beginning testUpdateSubmissionWithNote");
        final ApiSubmission inSubmission = ApiSubmission.builder()
            .type("submission")
            .string("")
            .attribute(
                ApiAttribute.builder().type("attribute").string("Note").tail("first note").build())
            .build();
        final ApiSubmissionBuilder<?, ?> outSubmission = crud
            .createOne(helper.getDb(), inSubmission)
            .toBuilder();
        assertThat(outSubmission).returns(inSubmission.getType(), o -> o.getType());
        final ApiAttribute aNote = ApiAttribute.builder()
            .type("attribute")
            .string("Note")
            .tail("this is a note")
            .build();
        outSubmission.attribute(aNote);
        final ApiSubmission updatedSubmission = crud.updateOne(helper.getDb(),
            outSubmission.getString(), outSubmission.build());
        assertEquals(aNote,
            Optional.ofNullable(updatedSubmission.getAttributes().get(1)).orElse(null),
            "attribute should be present");
    }
}
