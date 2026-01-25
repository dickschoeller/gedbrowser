package org.schoellerfamily.gedbrowser.api.crud.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.api.Application;
import org.schoellerfamily.gedbrowser.api.crud.ChildCrud;
import org.schoellerfamily.gedbrowser.api.crud.FamilyCrud;
import org.schoellerfamily.gedbrowser.api.crud.PersonCrud;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiFamily;
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
@SpringBootTest(classes = { Application.class, TestConfiguration.class },
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
@Slf4j
public class ChildCrudTest {

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
    private ChildCrud crud;

    /** */
    private CrudTestHelper helper;

    /** */
    @BeforeEach
    public void setUp() {
        helper = new CrudTestHelper(
                new PersonCrud(loader, toDocConverter, repositoryManager),
                new FamilyCrud(loader, toDocConverter, repositoryManager));
        crud = new ChildCrud(loader, toDocConverter, repositoryManager);
    }

    /** */
    @Test
    void testCreateChild() {
        log.info("Beginning testCreateChildInFamily2");
        final ApiPerson parent = helper.createPerson();
        final ApiPerson child = createChildOfParent(parent);
        log.info("famc: {}", child.getFamcs().get(0).getString());
        final ApiPerson gotParent = helper.getPerson(parent);
        assertEquals(child.getFamcs().get(0).getString(),
                gotParent.getFamss().get(0).getString(), "Child should be in family");
    }

    /** */
    @Test
    void testLinkChildInFamily() {
        log.info("Beginning testLinkChildInFamily");
        final ApiPerson parent = helper.createPerson();
        final ApiPerson child = createChildOfParent(parent);
        String famID = child.getFamcs().get(0).getString();
        log.info("famc: {}", famID);

        final ApiPerson secondChild = helper.createPerson();
        crud.linkChildInFamily(helper.getDb(), famID, secondChild);

        final ApiFamily family = helper.readFamily(famID);
        assertEquals(secondChild.getString(), family.getChildren().get(1).getString());
    }

    /** */
    @Test
    void testLinkChild() {
        log.info("Beginning testLinkChild");
        final ApiPerson parent = helper.createPerson();
        final ApiPerson child = helper.createPerson();
        final ApiPerson gotChild = crud.linkChild(helper.getDb(),
                parent.getString(), child);
        
        assertThat(gotChild)
            .returns(child.getString(), c -> c.getString())
            .returns(1, c -> c.getFamcs().size());
        
        final ApiPerson gotParent = helper.getPerson(parent);
        assertThat(gotParent)
            .returns(1, p -> p.getFamss().size());
        
        assertEquals(gotParent.getFamss().get(0).getString(),
                gotChild.getFamcs().get(0).getString(), "check ids");
    }

    /** */
    @Test
    void testUnlinkChild() {
        final ApiPerson parent = helper.createPerson();

        final ApiPerson child = createChildOfParent(parent);
        final String famID = child.getFamcs().get(0).getString();
        log.info("famc: {}", famID);
        crud.unlinkChild(helper.getDb(), famID, child.getString());
        final ApiPerson gotChild = helper.getPerson(child);
        assertEquals(0, gotChild.getFamcs().size(), "not in family");
    }

    /**
     * @param parent the parent
     * @return the child
     */
    private ApiPerson createChildOfParent(final ApiPerson parent) {
        final ApiPerson child = helper.buildPerson();
        return crud.createChild(helper.getDb(), parent.getString(), child);
    }

    /** */
    @Test
    void testCreateChildInFamily() {
        log.info("Beginning testCreateChildInFamily");
        final ApiPerson reqPerson = helper.createAlexandra();
        final ApiPerson resPerson =
                crud.createChildInFamily(helper.getDb(), "F1", reqPerson);

        assertThat(resPerson)
            .returns(reqPerson.getType(), p -> p.getType())
            .returns(reqPerson.getSurname(), p -> p.getSurname())
            .returns(reqPerson.getIndexName(), p -> p.getIndexName())
            .returns("F1", p -> p.getFamcs().get(0).getString());
    }

    /** */
    @Test
    void testCreateChildInFamily2() {
        log.info("Beginning testCreateChildInFamily2");
        final ApiPerson reqPerson = helper.createAlexander();
        final ApiPerson resPerson =
                crud.createChildInFamily(helper.getDb(), "F4", reqPerson);
        
        assertThat(resPerson)
            .returns(reqPerson.getType(), p -> p.getType())
            .returns(reqPerson.getSurname(), p -> p.getSurname())
            .returns(reqPerson.getIndexName(), p -> p.getIndexName())
            .returns("F4", p -> p.getFamcs().get(0).getString());
    }

    /** */
    @Test
    void testGetPersonsMiniSchoellerI2AddChild() {
        log.info("Beginning testGetPersonsMiniSchoellerI2AddChild");
        final ApiPerson reqChild = helper.createAlexander();
        final ApiPerson resChild = crud.createChild("mini-schoeller", "I9",
                reqChild);
        
        assertThat(resChild)
            .returns(reqChild.getType(), c -> c.getType())
            .returns(reqChild.getSurname(), c -> c.getSurname())
            .returns(reqChild.getIndexName(), c -> c.getIndexName());
    }

    /** */
    @Test
    void testGetPersonsMiniSchoellerI2AddChild2() {
        log.info("Beginning testGetPersonsMiniSchoellerI2AddChild2");
        final ApiPerson reqChild = helper.createAlexandra();
        final ApiPerson resChild = crud.createChild("mini-schoeller", "I10",
                reqChild);
        
        assertThat(resChild)
            .returns(reqChild.getType(), c -> c.getType())
            .returns(reqChild.getSurname(), c -> c.getSurname())
            .returns(reqChild.getIndexName(), c -> c.getIndexName());
    }
}
