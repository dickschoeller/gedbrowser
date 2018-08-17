package org.schoellerfamily.gedbrowser.api.crud.test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.Assert.assertEquals;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.api.Application;
import org.schoellerfamily.gedbrowser.api.crud.ChildCrud;
import org.schoellerfamily.gedbrowser.api.crud.FamilyCrud;
import org.schoellerfamily.gedbrowser.api.crud.PersonCrud;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiFamily;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson;
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
@TestPropertySource(properties = {"management.port=0"})
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
public class ChildCrudTest {
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
    private ChildCrud crud;

    /** */
    private CrudTestHelper helper;

    /** */
    @Before
    public void setUp() {
        helper = new CrudTestHelper(
                new PersonCrud(loader, toDocConverter, repositoryManager),
                new FamilyCrud(loader, toDocConverter, repositoryManager));
        crud = new ChildCrud(loader, toDocConverter, repositoryManager);
    }

    /** */
    @Test
    public final void testCreateChild() {
        logger.info("Beginning testCreateChildInFamily2");
        final ApiPerson parent = helper.createPerson();
        final ApiPerson child = createChildOfParent(parent);
        logger.info("famc: " + child.getFamc().get(0).getString());
        final ApiPerson gotParent = helper.getPerson(parent);
        assertEquals("Child should be in family",
                child.getFamc().get(0).getString(),
                gotParent.getFams().get(0).getString());
    }

    /** */
    @Test
    public final void testLinkChildInFamily() {
        logger.info("Beginning testLinkChildInFamily");
        final ApiPerson parent = helper.createPerson();
        final ApiPerson child = createChildOfParent(parent);
        String famID = child.getFamc().get(0).getString();
        logger.info("famc: " + famID);

        final ApiPerson secondChild = helper.createPerson();
        crud.linkChildInFamily(helper.getDb(), famID, secondChild);

        final ApiFamily family = helper.readFamily(famID);
        assertEquals(family.getChildren().get(1).getString(),
                secondChild.getString());
    }

    /** */
    @Test
    public final void testLinkChild() {
        logger.info("Beginning testLinkChild");
        final ApiPerson parent = helper.createPerson();
        final ApiPerson child = helper.createPerson();
        final ApiPerson gotChild = crud.linkChild(helper.getDb(),
                parent.getString(), child);
        then(gotChild.getString()).isEqualTo(child.getString());
        then(gotChild.getFamc().size()).isEqualTo(1);
        final ApiPerson gotParent = helper.getPerson(parent);
        then(gotParent.getFams().size()).isEqualTo(1);
        assertEquals("check ids",
                gotParent.getFams().get(0).getString(),
                gotChild.getFamc().get(0).getString());
    }

    /** */
    @Test
    public final void testUnlinkChild() {
        final ApiPerson parent = helper.createPerson();

        final ApiPerson child = createChildOfParent(parent);
        final String famID = child.getFamc().get(0).getString();
        logger.info("famc: " + famID);
        crud.unlinkChild(helper.getDb(), famID, child.getString());
        final ApiPerson gotChild = helper.getPerson(child);
        assertEquals("not in family", 0, gotChild.getFamc().size());
    }

    private ApiPerson createChildOfParent(final ApiPerson parent) {
        final ApiPerson child = helper.buildPerson();
        return crud.createChild(helper.getDb(), parent.getString(), child);
    }

    /** */
    @Test
    public final void testCreateChildInFamily() {
        logger.info("Beginning testCreateChildInFamily");
        final ApiPerson reqPerson = helper.createAlexandra();
        final ApiPerson resPerson = crud.createChildInFamily(helper.getDb(), "F1", reqPerson);

        then(resPerson.getType()).isEqualTo(reqPerson.getType());
        then(resPerson.getSurname()).isEqualTo(reqPerson.getSurname());
        then(resPerson.getIndexName()).isEqualTo(reqPerson.getIndexName());
        then(resPerson.getFamc().get(0).getString()).isEqualTo("F1");
    }

    /** */
    @Test
    public final void testCreateChildInFamily2() {
        logger.info("Beginning testCreateChildInFamily2");
        final ApiPerson reqPerson = helper.createAlexander();
        final ApiPerson resPerson = crud.createChildInFamily(helper.getDb(), "F4", reqPerson);
        then(resPerson.getType()).isEqualTo(reqPerson.getType());
        then(resPerson.getSurname()).isEqualTo(reqPerson.getSurname());
        then(resPerson.getIndexName()).isEqualTo(reqPerson.getIndexName());
        then(resPerson.getFamc().get(0).getString()).isEqualTo("F4");
    }

    /** */
    @Test
    public final void testGetPersonsMiniSchoellerI2AddChild() {
        logger.info("Beginning testGetPersonsMiniSchoellerI2AddChild");
        final ApiPerson reqChild = helper.createAlexander();
        final ApiPerson resChild = crud.createChild("mini-schoeller", "I9",
                reqChild);
        then(resChild.getType()).isEqualTo(reqChild.getType());
        then(resChild.getSurname()).isEqualTo(reqChild.getSurname());
        then(resChild.getIndexName()).isEqualTo(reqChild.getIndexName());
    }

    /** */
    @Test
    public final void testGetPersonsMiniSchoellerI2AddChild2() {
        logger.info("Beginning testGetPersonsMiniSchoellerI2AddChild2");
        final ApiPerson reqChild = helper.createAlexandra();
        final ApiPerson resChild = crud.createChild("mini-schoeller", "I10",
                reqChild);
        then(resChild.getType()).isEqualTo(reqChild.getType());
        then(resChild.getSurname()).isEqualTo(reqChild.getSurname());
        then(resChild.getIndexName()).isEqualTo(reqChild.getIndexName());
    }
}
