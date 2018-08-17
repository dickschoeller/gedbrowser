package org.schoellerfamily.gedbrowser.api.crud.test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.Assert.assertEquals;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.api.Application;
import org.schoellerfamily.gedbrowser.api.crud.FamilyCrud;
import org.schoellerfamily.gedbrowser.api.crud.ParentCrud;
import org.schoellerfamily.gedbrowser.api.crud.PersonCrud;
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
public class ParentCrudTest {
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
    private ParentCrud crud;

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
        crud = new ParentCrud(loader, toDocConverter, repositoryManager);
    }

    /** */
    @Test
    public final void testCreateParent() {
        final ApiPerson child = helper.createPerson();
        final ApiPerson parent = crud.createParent(helper.getDb(),
                child.getString(), helper.buildPerson());
        logger.info("fams: " + parent.getFams().get(0).getString());
        final ApiPerson gotChild = helper.getPerson(child);
        logger.info("famc: " + gotChild.getFamc().get(0).getString());

        assertEquals("Child should be in family",
                gotChild.getFamc().get(0).getString(),
                parent.getFams().get(0).getString());
    }

    /** */
    @Test
    public final void testLinkParent() {
        final ApiPerson inParent = helper.createPerson();
        final ApiPerson child = helper.createPerson();
        final ApiPerson outParent = crud.linkParent(helper.getDb(),
                child.getString(), inParent);
        then(outParent.getString()).isEqualTo(inParent.getString());
        then(outParent.getFams().size()).isEqualTo(1);
        final ApiPerson gotChild = helper.getPerson(child);
        then(outParent.getFams().size()).isEqualTo(1);
        assertEquals("check ids", outParent.getFams().get(0).getString(),
                gotChild.getFamc().get(0).getString());
    }

    /** */
    @Test
    public final void testGetPersonsMiniSchoellerI2AddParent() {
        logger.info("Beginning testGetPersonsMiniSchoellerI2AddParent");
        final ApiPerson reqParent = helper.createAlexander();
        final ApiPerson resParent = crud.createParent("mini-schoeller", "I1",
                reqParent);
        then(resParent.getType()).isEqualTo(reqParent.getType());
        then(resParent.getSurname()).isEqualTo(reqParent.getSurname());
        then(resParent.getIndexName()).isEqualTo(reqParent.getIndexName());
    }

    /** */
    @Test
    public final void testGetPersonsMiniSchoellerI2AddParent2() {
        logger.info("Beginning testGetPersonsMiniSchoellerI2AddParent2");
        final ApiPerson reqParent = helper.createAlexandra();
        final ApiPerson resParent = crud.createParent("mini-schoeller", "I2",
                reqParent);
        then(resParent.getType()).isEqualTo(reqParent.getType());
        then(resParent.getSurname()).isEqualTo(reqParent.getSurname());
        then(resParent.getIndexName()).isEqualTo(reqParent.getIndexName());
    }
}
