package org.schoellerfamily.gedbrowser.api.crud.test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.api.Application;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Dick Schoeller
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { Application.class,
    TestConfiguration.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = { "management.port=0" })
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
@Slf4j
public class ParentCrudTest {

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

    /**
     * Set up some base objects.
     */
    @BeforeEach
    public void setUp() {
        helper = new CrudTestHelper(new PersonCrud(loader, toDocConverter, repositoryManager),
            new FamilyCrud(loader, toDocConverter, repositoryManager));
        crud = new ParentCrud(loader, toDocConverter, repositoryManager);
    }

    /** */
    @Test
    public final void testCreateParent() {
        final ApiPerson child = helper.createPerson();
        final ApiPerson parent = crud.createParent(helper.getDb(), child.getString(),
            helper.buildPerson());
        log.info("fams: {}", parent.getFamss().get(0).getString());
        final ApiPerson gotChild = helper.getPerson(child);
        log.info("famc: {}", gotChild.getFamcs().get(0).getString());

        assertEquals(gotChild.getFamcs().get(0).getString(), parent.getFamss().get(0).getString(),
            "Child should be in family");
    }

    /** */
    @Test
    public final void testLinkParent() {
        final ApiPerson inParent = helper.createPerson();
        final ApiPerson child = helper.createPerson();
        final ApiPerson outParent = crud.linkParent(helper.getDb(), child.getString(), inParent);
        then(outParent.getString()).isEqualTo(inParent.getString());
        then(outParent.getFamss().size()).isEqualTo(1);
        final ApiPerson gotChild = helper.getPerson(child);
        then(outParent.getFamss().size()).isEqualTo(1);
        assertEquals(outParent.getFamss().get(0).getString(),
            gotChild.getFamcs().get(0).getString(), "check ids");
    }

    /** */
    @Test
    public final void testGetPersonsMiniSchoellerI2AddParent() {
        log.info("Beginning testGetPersonsMiniSchoellerI2AddParent");
        final ApiPerson reqParent = helper.createAlexander();
        final ApiPerson resParent = crud.createParent("mini-schoeller", "I1", reqParent);
        then(resParent.getType()).isEqualTo(reqParent.getType());
        then(resParent.getSurname()).isEqualTo(reqParent.getSurname());
        then(resParent.getIndexName()).isEqualTo(reqParent.getIndexName());
    }

    /** */
    @Test
    public final void testGetPersonsMiniSchoellerI2AddParent2() {
        log.info("Beginning testGetPersonsMiniSchoellerI2AddParent2");
        final ApiPerson reqParent = helper.createAlexandra();
        final ApiPerson resParent = crud.createParent("mini-schoeller", "I2", reqParent);
        then(resParent.getType()).isEqualTo(reqParent.getType());
        then(resParent.getSurname()).isEqualTo(reqParent.getSurname());
        then(resParent.getIndexName()).isEqualTo(reqParent.getIndexName());
    }
}
