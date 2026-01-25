package org.schoellerfamily.gedbrowser.api.crud.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson.ApiPersonBuilder;
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
@SpringBootTest(classes = { Application.class,
    TestConfiguration.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = { "management.port=0" })
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
@Slf4j
public class PersonCrudTest {

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
    private PersonCrud crud;

    /** */
    private CrudTestHelper helper;

    /** */
    private FamilyCrud familyCrud;

    /** */
    @BeforeEach
    public void setUp() {
        crud = new PersonCrud(loader, toDocConverter, repositoryManager);
        familyCrud = new FamilyCrud(loader, toDocConverter, repositoryManager);
        helper = new CrudTestHelper(crud, familyCrud);
    }

    /** */
    @Test
    void testGetPersonsGl120368() {
        log.info("Beginning testReadSourcesGl120368");
        final List<ApiPerson> list = crud.readAll(helper.getDb());
        final ApiPerson firstPerson = list.get(0);

        assertThat(firstPerson)
            .returns("I1", p -> p.getString())
            .returns("name", p -> p.getAttributes().get(0).getType())
            .returns("Living /Williams/", p -> p.getAttributes().get(0).getString())
            .returns(true, p -> p.getAttributes().get(0).getTail().isEmpty());
    }

    /** */
    @Test
    void testGetPersonsMiniSchoeller() {
        log.info("Beginning testGetPersonsMiniSchoeller");
        final List<ApiPerson> list = crud.readAll("mini-schoeller");
        final ApiPerson firstPerson = list.get(0);

        assertThat(firstPerson)
            .returns("I1", p -> p.getString())
            .returns("name", p -> p.getAttributes().get(0).getType())
            .returns("Melissa Robinson/Schoeller/", p -> p.getAttributes().get(0).getString())
            .returns(true, p -> p.getAttributes().get(0).getTail().isEmpty());
    }

    /** */
    @Test
    void testGetPersonsMiniSchoellerI2() {
        log.info("Beginning testGetPersonsMiniSchoellerI2");
        final ApiPerson firstPerson = crud.readOne("mini-schoeller", "I2");

        assertThat(firstPerson)
            .returns("I2", p -> p.getString())
            .returns("name", p -> p.getAttributes().get(0).getType())
            .returns("Richard John/Schoeller/", p -> p.getAttributes().get(0).getString())
            .returns(true, p -> p.getAttributes().get(0).getTail().isEmpty());
    }

    /** */
    @Test
    void testGetPersonsMiniSchoellerXyzzy() {
        log.info("Beginning testGetPersonsMiniSchoellerXyzzy");
        assertThatExceptionOfType(ObjectNotFoundException.class)
            .isThrownBy(() -> crud.readOne("mini-schoeller", "Xyzzy"))
            .withMessage("Object Xyzzy of type person not found");
    }

    /** */
    @Test
    void testCreatePersonsSimple() {
        log.info("Beginning testCreatePersonsSimple");
        final ApiPerson reqPerson = ApiPerson.builder()
            .type("person")
            .string("")
            .surname("")
            .indexName("")
            .build();
        final ApiPerson resPerson = crud.createOne(helper.getDb(), reqPerson);

        assertThat(resPerson)
            .returns(reqPerson.getType(), p -> p.getType())
            .returns(reqPerson.getSurname(), p -> p.getSurname())
            .returns(reqPerson.getIndexName(), p -> p.getIndexName())
            .returns(true, p -> !p.getString().isEmpty());
    }

    /** */
    @Test
    void testCreatePersonsWithName() {
        log.info("Beginning testCreatePersonsWithName");
        final ApiPerson reqPerson = createRJS();
        final ApiPerson resPerson = crud.createOne(helper.getDb(), reqPerson);

        assertThat(resPerson)
            .returns(reqPerson.getType(), p -> p.getType())
            .returns(reqPerson.getSurname(), p -> p.getSurname())
            .returns(reqPerson.getIndexName(), p -> p.getIndexName())
            .returns(true, p -> !p.getString().isEmpty());
    }

    /**
     * @return the newly created person
     */
    private ApiPerson createRJS() {
        return ApiPerson.builder()
            .type("person")
            .string("")
            .attribute(ApiAttribute.builder()
                .type("name")
                .string("Richard/Schoeller/")
                .tail("")
                .attributes(List.of())
                .build())
            .attribute(ApiAttribute.builder()
                .type("attribute")
                .string("Sex")
                .tail("M")
                .attributes(List.of())
                .build())
            .surname("Schoeller")
            .indexName("Schoeller, Richard")
            .attributes(List.of())
            .build();
    }

    /** */
    @Test
    void testDeletePerson() {
        log.info("Beginning testDeletePerson");
        final ApiPerson reqPerson = createRJS();
        final ApiPerson resPerson = crud.createOne(helper.getDb(), reqPerson);
        final String id = resPerson.getString();
        crud.readOne(helper.getDb(), id);
        final ApiPerson deletedPerson = crud.deleteOne(helper.getDb(), id);

        assertThat(deletedPerson)
            .returns(id, p -> p.getString());
        assertThatExceptionOfType(ObjectNotFoundException.class)
            .isThrownBy(() -> crud.readOne(helper.getDb(), id))
            .withMessage("Object " + id + " of type person not found");
    }

    /** */
    @Test
    void testDeleteSpouseLinkedPerson() {
        log.info("Beginning testDeleteSpouseLinkedPerson");
        final ApiPerson reqPerson = createRJS();
        final ApiPerson resPerson = crud.createOne(helper.getDb(), reqPerson);
        final String id = resPerson.getString();
        final ApiPerson childReqPerson = helper.createAlexander();
        final ChildCrud childCrud = new ChildCrud(loader, toDocConverter, repositoryManager);
        final ApiPerson child = childCrud.createChild(helper.getDb(), id, childReqPerson);
        final String fam = child.getFamcs().get(0).getString();
        final ApiPerson p2 = helper.createAlexandra();
        final SpouseCrud spouseCrud = new SpouseCrud(loader, toDocConverter, repositoryManager);
        final ApiPerson gotP2 = spouseCrud.createSpouseInFamily(helper.getDb(), fam, p2);

        assertThat(gotP2)
            .returns(fam, p -> p.getFamss().get(0).getString());

        final ApiPerson readPerson = crud.readOne(helper.getDb(), id);
        assertThat(readPerson)
            .returns(id, p -> p.getString());

        final ApiPerson deletedPerson = crud.deleteOne(helper.getDb(), id);
        assertThat(deletedPerson)
            .returns(id, p -> p.getString());

        assertThatExceptionOfType(ObjectNotFoundException.class)
            .isThrownBy(() -> crud.readOne(helper.getDb(), id))
            .withMessage("Object " + id + " of type person not found");

        final ApiFamily readFamily = familyCrud.readOne(helper.getDb(), fam);
        assertThat(readFamily)
            .returns(1, f -> f.getSpouses().size())
            .returns(gotP2.getString(), f -> f.getSpouses().get(0).getString());
    }

    /** */
    @Test
    void testDeleteChildLinkedPerson() {
        log.info("Beginning testDeleteChildLinkedPerson");
        final ApiPerson reqPerson = createRJS();
        final ApiPerson resPerson = crud.createOne(helper.getDb(), reqPerson);
        final String id = resPerson.getString();
        final ApiPerson childReqPerson = helper.createAlexander();
        final ChildCrud childCrud = new ChildCrud(loader, toDocConverter, repositoryManager);
        final ApiPerson child = childCrud.createChild(helper.getDb(), id, childReqPerson);
        final String fam = child.getFamcs().get(0).getString();
        final String childId = child.getString();
        final ApiPerson p2 = helper.createAlexandra();
        final SpouseCrud spouseCrud = new SpouseCrud(loader, toDocConverter, repositoryManager);
        final ApiPerson gotP2 = spouseCrud.createSpouseInFamily(helper.getDb(), fam, p2);
        
        assertThat(gotP2)
            .returns(fam, p -> p.getFamss().get(0).getString());
        
        final ApiPerson deletedPerson = crud.deleteOne(helper.getDb(), childId);
        assertThat(deletedPerson)
            .returns(childId, p -> p.getString());
        
        assertThatExceptionOfType(ObjectNotFoundException.class)
            .isThrownBy(() -> crud.readOne(helper.getDb(), childId))
            .withMessage("Object " + childId + " of type person not found");
        
        final ApiFamily readFamily = familyCrud.readOne(helper.getDb(), fam);
        assertThat(readFamily)
            .returns(0, f -> f.getChildren().size());
    }

    /** */
    @Test
    void testDeletePersonNotFound() {
        log.info("Beginning testDeletePersonNotFound");
        assertThatExceptionOfType(ObjectNotFoundException.class)
            .isThrownBy(() -> crud.deleteOne(helper.getDb(), "XXXXXXX"))
            .withMessage("Object XXXXXXX of type person not found");
    }

    /** */
    @Test
    void testDeletePersonDatabaseNotFound() {
        log.info("Beginning testDeletePersonDatabaseNotFound");
        log.info("Beginning testDeletePersonNotFound");
        assertThatExceptionOfType(DataSetNotFoundException.class)
            .isThrownBy(() -> crud.deleteOne("XYZZY", "XXXXXXX"))
            .withMessage("Data set XYZZY not found");
    }

    /** */
    @Test
    void testUpdatePersonWithNote() {
        log.info("Beginning testUpdatePersonWithNote");
        final ApiPerson reqPerson = createRJS();
        final ApiPersonBuilder<?, ?> resPerson = crud.createOne(helper.getDb(), reqPerson)
            .toBuilder();
        
        assertThat(resPerson)
            .returns(reqPerson.getType(), o -> o.getType());

        final ApiAttribute aNote = ApiAttribute.builder()
            .type("attribute")
            .string("Note")
            .tail("this is a note")
            .build();
        resPerson.attribute(aNote);
        final ApiPerson updatedPerson = crud.updateOne(helper.getDb(), resPerson.getString(),
            resPerson.build());
        assertEquals(aNote, updatedPerson.getAttributes().get(2), "attribute should be present");
    }
}
