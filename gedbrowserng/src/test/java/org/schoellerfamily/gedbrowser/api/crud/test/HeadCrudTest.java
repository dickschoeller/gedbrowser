package org.schoellerfamily.gedbrowser.api.crud.test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.api.Application;
import org.schoellerfamily.gedbrowser.api.controller.exception.DataSetNotFoundException;
import org.schoellerfamily.gedbrowser.api.crud.HeadCrud;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiHead;
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
public class HeadCrudTest {
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
    private HeadCrud crud;

    /** */
    @Before
    public void setUp() {
        crud = new HeadCrud(loader, toDocConverter, repositoryManager);
    }


    /** */
    @Test
    public final void testGetHeadGl120368() {
        logger.info("beginning testGetHeadGl120368");
        final ApiHead head = crud.readOne("gl120368");
        then(head.getType()).isEqualTo("head");
        then(head.getString()).isEqualTo("Header");
    }

    /** */
    @Test
    public final void testGetHeadMiniSchoeller() {
        logger.info("beginning testGetHeadMiniSchoeller");
        final ApiHead head = crud.readOne("mini-schoeller");
        then(head.getType()).isEqualTo("head");
        then(head.getString()).isEqualTo("Header");
    }

    /** */
    @Test
    public final void testGetHeadBadDataSet() {
        logger.info("beginning testGetHeadBadDataSet");
        try {
            crud.readOne("XYZZY");
            fail("should have thrown exception");
        } catch (DataSetNotFoundException e) {
            assertEquals("bad exception message", "Data set XYZZY not found",
                    e.getMessage());
        }
    }
}
