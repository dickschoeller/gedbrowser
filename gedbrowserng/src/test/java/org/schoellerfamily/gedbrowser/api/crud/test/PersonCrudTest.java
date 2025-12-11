package org.schoellerfamily.gedbrowser.api.crud.test;

import static org.assertj.core.api.Assertions.fail;
import static org.assertj.core.api.BDDAssertions.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.api.Application;
import org.schoellerfamily.gedbrowser.api.controller.exception.DataSetNotFoundException;
import org.schoellerfamily.gedbrowser.api.controller.exception.ObjectNotFoundException;
import org.schoellerfamily.gedbrowser.api.crud.ChildCrud;
import org.schoellerfamily.gedbrowser.api.crud.FamilyCrud;
import org.schoellerfamily.gedbrowser.api.crud.PersonCrud;
import org.schoellerfamily.gedbrowser.api.crud.SpouseCrud;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiFamily;
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
public class PersonCrudTest {

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
    private PersonCrud crud;

    /** */
    private CrudTestHelper helper;

    /** */
    private FamilyCrud familyCrud;

    /** */
    @Before
    public void setUp() {
        crud = new PersonCrud(loader, toDocConverter, repositoryManager);
        familyCrud = new FamilyCrud(loader, toDocConverter, repositoryManager);
        helper = new CrudTestHelper(crud, familyCrud);
    }

    /** */
    @Test
    public final void testGetPersonsGl120368() {
        log.info("Beginning testReadSourcesGl120368");
        final List<ApiPerson> list = crud.readAll(helper.getDb());
        final ApiPerson firstPerson = list.get(0);
        then(firstPerson.getString()).isEqualTo("I1");
        final ApiAttribute firstAttribute = firstPerson.getAttributes().get(0);
        then(firstAttribute.getType()).isEqualTo("name");
        then(firstAttribute.getString()).isEqualTo("Living /Williams/");
        then(firstAttribute.getTail()).isEmpty();
    }

    /** */
    @Test
    public final void testGetPersonsMiniSchoeller() {
        log.info("Beginning testGetPersonsMiniSchoeller");
        final List<ApiPerson> list = crud.readAll("mini-schoeller");
        final ApiPerson firstPerson = list.get(0);
        then(firstPerson.getString()).isEqualTo("I1");
        final ApiAttribute firstAttribute = firstPerson.getAttributes().get(0);
        then(firstAttribute.getType()).isEqualTo("name");
        then(firstAttribute.getString()).isEqualTo(
                "Melissa Robinson/Schoeller/");
        then(firstAttribute.getTail()).isEmpty();
    }

    /** */
    @Test
    public final void testGetPersonsMiniSchoellerI2() {
        log.info("Beginning testGetPersonsMiniSchoellerI2");
        final ApiPerson firstPerson = crud.readOne("mini-schoeller", "I2");
        then(firstPerson.getString()).isEqualTo("I2");
        final ApiAttribute firstAttribute = firstPerson.getAttributes().get(0);
        then(firstAttribute.getType()).isEqualTo("name");
        then(firstAttribute.getString()).isEqualTo("Richard John/Schoeller/");
        then(firstAttribute.getTail()).isEmpty();
    }

    /** */
    @Test
    public final void testGetPersonsMiniSchoellerXyzzy() {
        log.info("Beginning testGetPersonsMiniSchoellerXyzzy");
        try {
            crud.readOne("mini-schoeller", "Xyzzy");
            fail("should not have found person "
                    + "Xyzzy in data set mini-schoeller");
        } catch (ObjectNotFoundException e) {
            assertEquals("Mismatched message",
                    "Object Xyzzy of type person not found", e.getMessage());
        }
    }

    /** */
    @Test
    public final void testCreatePersonsSimple() {
        log.info("Beginning testCreatePersonsSimple");
        final ApiPerson.Builder builder = new ApiPerson.Builder().build();
        final ApiPerson reqPerson = new ApiPerson(builder);
        final ApiPerson resPerson = crud.createOne(helper.getDb(),
                reqPerson);
        then(resPerson.getType()).isEqualTo(reqPerson.getType());
        then(resPerson.getSurname()).isEqualTo(reqPerson.getSurname());
        then(resPerson.getIndexName()).isEqualTo(reqPerson.getIndexName());
        then(resPerson.getString()).isNotEmpty();
    }

    /** */
    @Test
    public final void testCreatePersonsWithName() {
        log.info("Beginning testCreatePersonsWithName");
        final ApiPerson reqPerson = createRJS();
        final ApiPerson resPerson =
                crud.createOne(helper.getDb(), reqPerson);
        then(resPerson.getType()).isEqualTo(reqPerson.getType());
        then(resPerson.getSurname()).isEqualTo(reqPerson.getSurname());
        then(resPerson.getIndexName()).isEqualTo(reqPerson.getIndexName());
        then(resPerson.getString()).isNotEmpty();
    }

    /**
     * @return the newly created person
     */
    private ApiPerson createRJS() {
        final ApiPerson.Builder builder = new ApiPerson.Builder()
                .id("")
                .add(new ApiAttribute("name", "Richard/Schoeller/", ""))
                .add(new ApiAttribute("attribute", "Sex", "M"))
                .surname("Schoeller")
                .indexName("Schoeller, Richard")
                .build();
        return new ApiPerson(builder);
    }

    /** */
    @Test
    public final void testDeletePerson() {
        log.info("Beginning testDeletePerson");
        final ApiPerson reqPerson = createRJS();
        final ApiPerson resPerson =
                crud.createOne(helper.getDb(), reqPerson);
        final String id = resPerson.getString();
        crud.readOne(helper.getDb(), id);
        final ApiPerson deletedPerson = crud.deleteOne(helper.getDb(), id);
        then(deletedPerson.getString()).isEqualTo(id);
        try {
            crud.readOne(helper.getDb(), id);
            fail("should not have found person " + id + " in data set "
                    + helper.getDb());
        } catch (ObjectNotFoundException e) {
            assertEquals("Mismatched message",
                    "Object " + id + " of type person not found",
                    e.getMessage());
        }
    }

    /** */
    @Test
    public final void testDeleteSpouseLinkedPerson() {
        log.info("Beginning testDeleteSpouseLinkedPerson");
        final ApiPerson reqPerson = createRJS();
        final ApiPerson resPerson =
                crud.createOne(helper.getDb(), reqPerson);
        final String id = resPerson.getString();
        final ApiPerson childReqPerson = helper.createAlexander();
        final ChildCrud childCrud = new ChildCrud(loader, toDocConverter,
                repositoryManager);
        final ApiPerson child = childCrud.createChild(helper.getDb(), id,
                childReqPerson);
        final String fam = child.getFamc().get(0).getString();
        final ApiPerson p2 = helper.createAlexandra();
        final SpouseCrud spouseCrud = new SpouseCrud(loader, toDocConverter,
                repositoryManager);
        final ApiPerson gotP2 = spouseCrud.createSpouseInFamily(helper.getDb(),
                fam, p2);
        then(fam).isEqualTo(gotP2.getFams().get(0).getString());

        ApiPerson readPerson = crud.readOne(helper.getDb(), id);
        then(readPerson.getString()).isEqualTo(id);
        ApiPerson deletedPerson = crud.deleteOne(helper.getDb(), id);
        then(deletedPerson.getString()).isEqualTo(id);
        try {
            crud.readOne(helper.getDb(), id);
            fail("should not have found person " + id + " in data set "
                    + helper.getDb());
        } catch (ObjectNotFoundException e) {
            assertEquals("Mismatched message",
                    "Object " + id + " of type person not found",
                    e.getMessage());
        }
        final ApiFamily readFamily = familyCrud.readOne(helper.getDb(), fam);
        then(readFamily.getSpouses().size()).isEqualTo(1);
        then(readFamily.getSpouses().get(0).getString())
                .isEqualTo(gotP2.getString());
    }

    /** */
    @Test
    public final void testDeleteChildLinkedPerson() {
        log.info("Beginning testDeleteChildLinkedPerson");
        final ApiPerson reqPerson = createRJS();
        final ApiPerson resPerson =
                crud.createOne(helper.getDb(), reqPerson);
        final String id = resPerson.getString();
        final ApiPerson childReqPerson = helper.createAlexander();
        final ChildCrud childCrud = new ChildCrud(loader, toDocConverter,
                repositoryManager);
        final ApiPerson child = childCrud.createChild(helper.getDb(), id,
                childReqPerson);
        final String fam = child.getFamc().get(0).getString();
        final String childId = child.getString();
        final ApiPerson p2 = helper.createAlexandra();
        final SpouseCrud spouseCrud =
                new SpouseCrud(loader, toDocConverter, repositoryManager);
        final ApiPerson gotP2 =
                spouseCrud.createSpouseInFamily(helper.getDb(), fam, p2);
        then(gotP2.getFams().get(0).getString()).isEqualTo(fam);
        final ApiPerson deletedPerson =
                crud.deleteOne(helper.getDb(), childId);
        then(deletedPerson.getString()).isEqualTo(childId);
        try {
            crud.readOne(helper.getDb(), childId);
            fail("should not have found person " + childId + " in data set "
                    + helper.getDb());
        } catch (ObjectNotFoundException e) {
            assertEquals("Mismatched message",
                    "Object " + childId + " of type person not found",
                    e.getMessage());
        }
        final ApiFamily readFamily = familyCrud.readOne(helper.getDb(), fam);
        then(readFamily.getChildren().size()).isEqualTo(0);
    }

    /** */
    @Test
    public final void testDeletePersonNotFound() {
        log.info("Beginning testDeletePersonNotFound");
        try {
            crud.deleteOne(helper.getDb(), "XXXXXXX");
            fail("should not have found person XXXXXXX in data set "
                    + helper.getDb());
        } catch (ObjectNotFoundException e) {
            assertEquals("Mismatched message",
                    "Object XXXXXXX of type person not found", e.getMessage());
        }
    }

    /** */
    @Test
    public final void testDeletePersonDatabaseNotFound() {
        log.info("Beginning testDeletePersonDatabaseNotFound");
        log.info("Beginning testDeletePersonNotFound");
        try {
            crud.deleteOne("XYZZY", "XXXXXXX");
            fail("should not have found data set "
                    + "XYZZY while looking for person XXXXXXX");
        } catch (DataSetNotFoundException e) {
            assertEquals("Mismatched message",
                    "Data set XYZZY not found", e.getMessage());
        }
    }

    /** */
    @Test
    public final void testUpdatePersonWithNote() {
        log.info("Beginning testUpdatePersonWithNote");
        final ApiPerson reqPerson = createRJS();
        final ApiPerson resPerson =
                crud.createOne(helper.getDb(), reqPerson);
        then(resPerson.getType()).isEqualTo(reqPerson.getType());

        final ApiAttribute aNote = new ApiAttribute("attribute", "Note",
                "this is a note");
        resPerson.getAttributes().add(aNote);
        final ApiPerson updatedPerson = crud.updateOne(helper.getDb(),
                resPerson.getString(), resPerson);
        assertEquals("attribute should be present", aNote,
                updatedPerson.getAttributes().get(2));
    }
}
