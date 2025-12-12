package org.schoellerfamily.gedbrowser.api.crud.test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.api.Application;
import org.schoellerfamily.gedbrowser.api.controller.exception.DataSetNotFoundException;
import org.schoellerfamily.gedbrowser.api.crud.HeadCrud;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiHead;
import org.schoellerfamily.gedbrowser.api.loader.GedObjectFileLoader;
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
@SpringBootTest(classes = Application.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
@Slf4j
public class HeadCrudTest {

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
    private HeadCrud crud;

    /** */
    @BeforeEach
    public void setUp() {
        crud = new HeadCrud(loader, toDocConverter, repositoryManager);
    }


    /** */
    @Test
    public final void testGetHeadGl120368() {
        log.info("beginning testGetHeadGl120368");
        final ApiHead head = crud.readOne("gl120368");
        then(head.getType()).isEqualTo("head");
        then(head.getString()).isEqualTo("Header");
    }

    /** */
    @Test
    public final void testGetHeadMiniSchoeller() {
        log.info("beginning testGetHeadMiniSchoeller");
        final ApiHead head = crud.readOne("mini-schoeller");
        then(head.getType()).isEqualTo("head");
        then(head.getString()).isEqualTo("Header");
    }

    /** */
    @Test
    public final void testGetHeadBadDataSet() {
        log.info("beginning testGetHeadBadDataSet");
        final DataSetNotFoundException e = assertThrows(
                DataSetNotFoundException.class,
                () -> crud.readOne("XYZZY")
        );
        assertEquals("Data set XYZZY not found", e.getMessage(),
                "bad exception message");
    }
}
