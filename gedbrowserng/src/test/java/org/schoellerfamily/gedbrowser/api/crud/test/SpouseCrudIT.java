package org.schoellerfamily.gedbrowser.api.crud.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.api.Application;
import org.schoellerfamily.gedbrowser.api.controller.exception.ObjectNotFoundException;
import org.schoellerfamily.gedbrowser.api.crud.ChildCrud;
import org.schoellerfamily.gedbrowser.api.crud.FamilyCrud;
import org.schoellerfamily.gedbrowser.api.crud.PersonCrud;
import org.schoellerfamily.gedbrowser.api.crud.SpouseCrud;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
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
 * @author Dick Schoeller
 */
@SpringBootTest(classes = { Application.class,
    TestConfiguration.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = { "management.port=0" })
@Slf4j
final class SpouseCrudIT {

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
    private SpouseCrud crud;

    /** */
    private CrudTestHelper helper;

    @BeforeEach
    void setUp() {
        helper = new CrudTestHelper(new PersonCrud(loader, toDocConverter, repositoryManager),
            new FamilyCrud(loader, toDocConverter, repositoryManager));
        crud = new SpouseCrud(loader, toDocConverter, repositoryManager);
    }

    @Test
    void testLinkSpouse() {
        log.info("Beginning testLinkSpouse");
        final ApiPerson p1 = helper.createPerson();
        final ApiPerson p2 = helper.createPerson();
        final ApiPerson gotP1 = crud.linkSpouse(helper.getDb(), p2.getString(), p1);
        assertThat(gotP1)
            .returns(p1.getString(), o -> o.getString())
            .returns(1, o -> o.getFamss().size());
        final ApiPerson gotP2 = helper.getPerson(p2);
        assertThat(gotP2).returns(1, o -> o.getFamss().size());
        assertEquals(gotP1.getFamss().get(0).getString(), gotP2.getFamss().get(0).getString(),
            "check ids");
    }

    @Test
    void testLinkSpouseInFamily() {
        log.info("Beginning testLinkSpouseInFamily");
        final ApiPerson p1 = helper.createPerson();
        final ApiPerson child = createChildOfParent(p1);
        final String fam = child.getFamcs().get(0).getString();

        final ApiPerson p2 = helper.createPerson();
        final ApiPerson gotP2 = crud.linkSpouseInFamily(helper.getDb(), fam, p2);
        assertThat(gotP2)
            .returns(p2.getString(), o -> o.getString())
            .returns(1, o -> o.getFamss().size());
        final ApiPerson gotP1 = helper.getPerson(p1);
        assertThat(gotP1).returns(1, o -> o.getFamss().size());
        assertEquals(gotP1.getFamss().get(0).getString(), gotP2.getFamss().get(0).getString(),
            "check ids");
    }

    @Test
    void testUnlinkSpouseInFamily() {
        log.info("Beginning testUnlinkSpouseInFamily");
        final ApiPerson p1 = helper.createPerson();
        final ApiPerson child = createChildOfParent(p1);
        final String fam = child.getFamcs().get(0).getString();

        final ApiPerson p2 = helper.createPerson();
        final ApiPerson gotP2 = crud.linkSpouseInFamily(helper.getDb(), fam, p2);
        assertThat(gotP2)
            .returns(p2.getString(), o -> o.getString())
            .returns(1, o -> o.getFamss().size());
        final ApiPerson gotP1 = helper.getPerson(p1);
        assertThat(gotP1).returns(1, o -> o.getFamss().size());

        crud.unlinkSpouseInFamily(helper.getDb(), fam, gotP1.getString());
        final ApiPerson gotP1again = helper.getPerson(gotP1);
        final ApiPerson gotP2again = helper.getPerson(gotP2);
        assertThat(gotP1again).returns(0, o -> o.getFamss().size());
        assertEquals(gotP2again.getFamss().get(0).getString(), fam, "check ids");
    }

    @Test
    void testUnlinkSpouseInFamilyAndFamilyNotFound() {
        log.info("Beginning testUnlinkSpouseInFamily");
        final ApiPerson p1 = helper.createPerson();
        final ApiPerson child = createChildOfParent(p1);
        final String fam = child.getFamcs().get(0).getString();

        final ApiPerson p2 = helper.createPerson();
        final ApiPerson gotP2 = crud.linkSpouseInFamily(helper.getDb(), fam, p2);
        assertThat(gotP2)
            .returns(p2.getString(), o -> o.getString())
            .returns(1, o -> o.getFamss().size());
        final ApiPerson gotP1 = helper.getPerson(p1);
        assertThat(gotP1).returns(1, o -> o.getFamss().size());

        final ApiPerson p1back = crud.unlinkSpouseInFamily(helper.getDb(), "XXXXX",
            gotP1.getString());
        boolean found = false;
        for (final ApiAttribute famsBack : p1back.getFamss()) {
            final String fid = famsBack.getString();
            if (fid.equals(fam)) {
                found = true;
                break;
            }
        }
        assertTrue(found, "Shoud have found the original family undeleted");
    }

    @Test
    void testUnlinkSpouseInFamilyAndSpouseNotFound() {
        log.info("Beginning testUnlinkSpouseInFamily");
        final ApiPerson p1 = helper.createPerson();
        final ApiPerson child = createChildOfParent(p1);
        final String fam = child.getFamcs().get(0).getString();

        final ApiPerson p2 = helper.createPerson();
        final ApiPerson gotP2 = crud.linkSpouseInFamily(helper.getDb(), fam, p2);
        assertThat(gotP2)
            .returns(p2.getString(), o -> o.getString())
            .returns(1, o -> o.getFamss().size());
        final ApiPerson gotP1 = helper.getPerson(p1);
        assertThat(gotP1).returns(1, o -> o.getFamss().size());

        assertThatExceptionOfType(ObjectNotFoundException.class)
            .isThrownBy(() -> crud.unlinkSpouseInFamily(helper.getDb(), fam, "XXXXX"))
            .withMessage("Object XXXXX of type person not found");
    }

    /**
     * @param parent the parent
     * @return the child
     */
    private ApiPerson createChildOfParent(final ApiPerson parent) {
        final ApiPerson childReqPerson = helper.buildPerson();
        final ChildCrud childCrud = new ChildCrud(loader, toDocConverter, repositoryManager);
        return childCrud.createChild(helper.getDb(), parent.getString(), childReqPerson);
    }

    @Test
    void testCreateSpouseInFamily() {
        log.info("Beginning testCreateSpouseInFamily");
        final ApiPerson reqSpouse = helper.createAlexandra();
        final ApiPerson resSpouse = crud.createSpouseInFamily(helper.getDb(), "F1", reqSpouse);
        assertThat(resSpouse)
            .returns(reqSpouse.getType(), o -> o.getType())
            .returns(reqSpouse.getSurname(), o -> o.getSurname())
            .returns(reqSpouse.getIndexName(), o -> o.getIndexName())
            .returns("F1", o -> o.getFamss().get(0).getString());
    }

    @Test
    void testCreateSpouseInFamily2() {
        log.info("Beginning testCreateSpouseInFamily2");
        final ApiPerson reqSpouse = helper.createAlexander();
        final ApiPerson resSpouse = crud.createSpouseInFamily(helper.getDb(), "F2", reqSpouse);
        assertThat(resSpouse)
            .returns(reqSpouse.getType(), o -> o.getType())
            .returns(reqSpouse.getSurname(), o -> o.getSurname())
            .returns(reqSpouse.getIndexName(), o -> o.getIndexName())
            .returns("F2", o -> o.getFamss().get(0).getString());
    }

    @Test
    void testGetPersonsMiniSchoellerI2AddSpouse() {
        log.info("Beginning testGetPersonsMiniSchoellerI2AddSpouse");
        final ApiPerson reqSpouse = helper.createAlexander();
        final ApiPerson resSpouse = crud.createSpouse("mini-schoeller", "I1", reqSpouse);
        assertThat(resSpouse)
            .returns(reqSpouse.getType(), o -> o.getType())
            .returns(reqSpouse.getSurname(), o -> o.getSurname())
            .returns(reqSpouse.getIndexName(), o -> o.getIndexName());
    }
}
