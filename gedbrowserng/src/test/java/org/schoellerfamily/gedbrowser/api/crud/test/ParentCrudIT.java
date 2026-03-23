package org.schoellerfamily.gedbrowser.api.crud.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.api.Application;
import org.schoellerfamily.gedbrowser.api.controller.exception.ObjectNotFoundException;
import org.schoellerfamily.gedbrowser.api.crud.FamilyCrud;
import org.schoellerfamily.gedbrowser.api.crud.ParentCrud;
import org.schoellerfamily.gedbrowser.api.crud.PersonCrud;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson;
import org.schoellerfamily.gedbrowser.api.loader.GedObjectFileLoader;
import org.schoellerfamily.gedbrowser.api.test.TestConfiguration;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import lombok.extern.slf4j.Slf4j;



/**
 * Contains integration tests for parent crud.
 *
 * @author Richard Schoeller
 */
@SpringBootTest(classes = { Application.class,
    TestConfiguration.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = { "management.port=0" })
@Slf4j
class ParentCrudIT {

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
    private ParentCrud crud;

    /** */
    private CrudTestHelper helper;

    @BeforeEach
    void setUp() {
        helper = new CrudTestHelper(new PersonCrud(loader, toDocConverter, repositoryManager),
            new FamilyCrud(loader, toDocConverter, repositoryManager));
        crud = new ParentCrud(loader, toDocConverter, repositoryManager);
    }

    @Test
    void testCreateParent() {
        final ApiPerson child = helper.createPerson();
        final ApiPerson parent = crud.createParent(helper.getDb(), child.getString(),
            helper.buildPerson());
        log.info("fams: {}", parent.getFamss().get(0).getString());
        final ApiPerson gotChild = helper.getPerson(child);
        log.info("famc: {}", gotChild.getFamcs().get(0).getString());

        assertThat(parent.getFamss().get(0).getString())
            .isEqualTo(gotChild.getFamcs().get(0).getString());
    }

    @Test
    void testLinkParent() {
        final ApiPerson inParent = helper.createPerson();
        final ApiPerson child = helper.createPerson();
        final ApiPerson outParent = crud.linkParent(helper.getDb(), child.getString(), inParent);
        assertThat(outParent)
            .returns(inParent.getString(), o -> o.getString())
            .returns(1, o -> o.getFamss().size());
        final ApiPerson gotChild = helper.getPerson(child);
        assertThat(outParent).returns(1, o -> o.getFamss().size());
        assertEquals(outParent.getFamss().get(0).getString(),
            gotChild.getFamcs().get(0).getString(), "check ids");
    }

    @Test
    void testGetPersonsMiniSchoellerI2AddParent() {
        log.info("Beginning testGetPersonsMiniSchoellerI2AddParent");
        final ApiPerson reqParent = helper.createAlexander();
        final ApiPerson resParent = crud.createParent("mini-schoeller", "I1", reqParent);
        assertThat(resParent)
            .returns(reqParent.getType(), o -> o.getType())
            .returns(reqParent.getSurname(), o -> o.getSurname())
            .returns(reqParent.getIndexName(), o -> o.getIndexName());
    }

    @Test
    void testGetPersonsMiniSchoellerI2AddParent2() {
        log.info("Beginning testGetPersonsMiniSchoellerI2AddParent2");
        final ApiPerson reqParent = helper.createAlexandra();
        final ApiPerson resParent = crud.createParent("mini-schoeller", "I2", reqParent);
        assertThat(resParent)
            .returns(reqParent.getType(), o -> o.getType())
            .returns(reqParent.getSurname(), o -> o.getSurname())
            .returns(reqParent.getIndexName(), o -> o.getIndexName());
    }

    @Test
    void testCreateParentChildNotFound() {
        final String db = helper.getDb();
        assertThatExceptionOfType(ObjectNotFoundException.class)
            .isThrownBy(() -> crud.createParent(db, "IXXXXX", helper.buildPerson()))
            .withMessage("Object IXXXXX of type person not found");
    }

    @Test
    void testLinkParentParentNotFound() {
        final ApiPerson child = helper.createPerson();
        final String db = helper.getDb();
        final ApiPerson missingParent = ApiPerson.builder().string("IXXXXX").build();
        assertThatExceptionOfType(ObjectNotFoundException.class)
            .isThrownBy(() -> crud.linkParent(db, child.getString(), missingParent))
            .withMessage("Object IXXXXX of type person not found");
    }
}
