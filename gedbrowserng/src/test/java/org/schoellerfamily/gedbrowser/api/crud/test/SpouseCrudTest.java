package org.schoellerfamily.gedbrowser.api.crud.test;

import static org.assertj.core.api.Assertions.fail;
import static org.assertj.core.api.BDDAssertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.api.Application;
import org.schoellerfamily.gedbrowser.api.controller.exception.ObjectNotFoundException;
import org.schoellerfamily.gedbrowser.api.crud.ChildCrud;
import org.schoellerfamily.gedbrowser.api.crud.FamilyCrud;
import org.schoellerfamily.gedbrowser.api.crud.PersonCrud;
import org.schoellerfamily.gedbrowser.api.crud.SpouseCrud;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.loader.GedDocumentFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
@Slf4j
public class SpouseCrudTest {

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
    private SpouseCrud crud;

    /** */
    private CrudTestHelper helper;

    /**
     * Set up some base objects.
     */
    @Before
    public void setUp() {
        helper = new CrudTestHelper(
                new PersonCrud(loader, toDocConverter, repositoryManager),
                new FamilyCrud(loader, toDocConverter, repositoryManager));
        crud = new SpouseCrud(loader, toDocConverter, repositoryManager);
    }

    /** */
    @Test
    public final void testLinkSpouse() {
        log.info("Beginning testLinkSpouse");
        final ApiPerson p1 = helper.createPerson();
        final ApiPerson p2 = helper.createPerson();
        ApiPerson gotP1 = crud.linkSpouse(helper.getDb(), p2.getString(), p1);
        then(gotP1.getString()).isEqualTo(p1.getString());
        then(gotP1.getFams().size()).isEqualTo(1);
        final ApiPerson gotP2 = helper.getPerson(p2);
        then(gotP2.getFams().size()).isEqualTo(1);
        assertEquals(gotP1.getFams().get(0).getString(), gotP2.getFams().get(0).getString(), "check ids");
    }

    /** */
    @Test
    public final void testLinkSpouseInFamily() {
        log.info("Beginning testLinkSpouseInFamily");
        final ApiPerson p1 = helper.createPerson();
        final ApiPerson child = createChildOfParent(p1);
        final String fam = child.getFamc().get(0).getString();

        final ApiPerson p2 = helper.createPerson();
        final ApiPerson gotP2 =
                crud.linkSpouseInFamily(helper.getDb(), fam, p2);
        then(gotP2.getString()).isEqualTo(p2.getString());
        then(gotP2.getFams().size()).isEqualTo(1);
        final ApiPerson gotP1 = helper.getPerson(p1);
        then(gotP1.getFams().size()).isEqualTo(1);
        assertEquals(gotP1.getFams().get(0).getString(), gotP2.getFams().get(0).getString(), "check ids");
    }

    /** */
    @Test
    public final void testUnlinkSpouseInFamily() {
        log.info("Beginning testUnlinkSpouseInFamily");
        final ApiPerson p1 = helper.createPerson();
        final ApiPerson child = createChildOfParent(p1);
        final String fam = child.getFamc().get(0).getString();

        final ApiPerson p2 = helper.createPerson();
        final ApiPerson gotP2 =
                crud.linkSpouseInFamily(helper.getDb(), fam, p2);
        then(gotP2.getString()).isEqualTo(p2.getString());
        then(gotP2.getFams().size()).isEqualTo(1);
        final ApiPerson gotP1 = helper.getPerson(p1);
        then(gotP1.getFams().size()).isEqualTo(1);

        crud.unlinkSpouseInFamily(helper.getDb(), fam, gotP1.getString());
        final ApiPerson gotP1again = helper.getPerson(gotP1);
        final ApiPerson gotP2again = helper.getPerson(gotP2);
        then(gotP1again.getFams().size()).isEqualTo(0);
        assertEquals(gotP2again.getFams().get(0).getString(), fam, "check ids");
    }

    @Test
    public final void testUnlinkSpouseInFamilyAndFamilyNotFound() {
        log.info("Beginning testUnlinkSpouseInFamily");
        final ApiPerson p1 = helper.createPerson();
        final ApiPerson child = createChildOfParent(p1);
        final String fam = child.getFamc().get(0).getString();

        final ApiPerson p2 = helper.createPerson();
        final ApiPerson gotP2 =
                crud.linkSpouseInFamily(helper.getDb(), fam, p2);
        then(gotP2.getString()).isEqualTo(p2.getString());
        then(gotP2.getFams().size()).isEqualTo(1);
        final ApiPerson gotP1 = helper.getPerson(p1);
        then(gotP1.getFams().size()).isEqualTo(1);

        final ApiPerson p1back =
                crud.unlinkSpouseInFamily(helper.getDb(), "XXXXX", gotP1.getString());
        boolean found = false;
        for (final ApiAttribute famsBack : p1back.getFams()) {
            final String fid = famsBack.getString();
            if (fid.equals(fam)) {
                found = true;
                break;
            }
        }
        assertTrue(found, "Shoud have found the original family undeleted");
    }

    @Test
    public final void testUnlinkSpouseInFamilyAndSpouseNotFound() {
        log.info("Beginning testUnlinkSpouseInFamily");
        final ApiPerson p1 = helper.createPerson();
        final ApiPerson child = createChildOfParent(p1);
        final String fam = child.getFamc().get(0).getString();

        final ApiPerson p2 = helper.createPerson();
        final ApiPerson gotP2 =
                crud.linkSpouseInFamily(helper.getDb(), fam, p2);
        then(gotP2.getString()).isEqualTo(p2.getString());
        then(gotP2.getFams().size()).isEqualTo(1);
        final ApiPerson gotP1 = helper.getPerson(p1);
        then(gotP1.getFams().size()).isEqualTo(1);

        try {
            crud.unlinkSpouseInFamily(helper.getDb(), fam, "XXXXX");
            fail("Should have thrown");
        } catch (ObjectNotFoundException e) {
            assertEquals("Object XXXXX of type person not found", e.getMessage(),
                    "exception message mismatch");
        }
    }

    /**
     * @param parent the parent
     * @return the child
     */
    private ApiPerson createChildOfParent(final ApiPerson parent) {
        final ApiPerson childReqPerson = helper.buildPerson();
        final ChildCrud childCrud =
                new ChildCrud(loader, toDocConverter, repositoryManager);
        return childCrud.createChild(helper.getDb(),
                parent.getString(), childReqPerson);
    }

    /** */
    @Test
    public final void testCreateSpouseInFamily() {
        log.info("Beginning testCreateSpouseInFamily");
        final ApiPerson reqSpouse = helper.createAlexandra();
        final ApiPerson resSpouse = crud.createSpouseInFamily(helper.getDb(),
                "F1", reqSpouse);
        then(resSpouse.getType()).isEqualTo(reqSpouse.getType());
        then(resSpouse.getSurname()).isEqualTo(reqSpouse.getSurname());
        then(resSpouse.getIndexName()).isEqualTo(reqSpouse.getIndexName());
        then(resSpouse.getFams().get(0).getString()).isEqualTo("F1");
    }

    /** */
    @Test
    public final void testCreateSpouseInFamily2() {
        log.info("Beginning testCreateSpouseInFamily2");
        final ApiPerson reqSpouse = helper.createAlexander();
        final ApiPerson resSpouse = crud.createSpouseInFamily(helper.getDb(),
                "F2", reqSpouse);
        then(resSpouse.getType()).isEqualTo(reqSpouse.getType());
        then(resSpouse.getSurname()).isEqualTo(reqSpouse.getSurname());
        then(resSpouse.getIndexName()).isEqualTo(reqSpouse.getIndexName());
        then(resSpouse.getFams().get(0).getString()).isEqualTo("F2");
    }

    /** */
    @Test
    public final void testGetPersonsMiniSchoellerI2AddSpouse() {
        log.info("Beginning testGetPersonsMiniSchoellerI2AddSpouse");
        final ApiPerson reqSpouse = helper.createAlexander();
        final ApiPerson resSpouse = crud.createSpouse("mini-schoeller", "I1",
                reqSpouse);
        then(resSpouse.getType()).isEqualTo(reqSpouse.getType());
        then(resSpouse.getSurname()).isEqualTo(reqSpouse.getSurname());
        then(resSpouse.getIndexName()).isEqualTo(reqSpouse.getIndexName());
    }
}
