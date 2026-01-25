package org.schoellerfamily.gedbrowser.api.crud.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.api.Application;
import org.schoellerfamily.gedbrowser.api.controller.exception.DataSetNotFoundException;
import org.schoellerfamily.gedbrowser.api.crud.HeadCrud;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiHead;
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
@SpringBootTest(classes = { Application.class, TestConfiguration.class },
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
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
    void testGetHeadGl120368() {
        log.info("beginning testGetHeadGl120368");
        final ApiHead head = crud.readOne("gl120368");
        assertThat(head)
            .returns("head", o -> o.getType())
            .returns("Header", o -> o.getString());
    }

    /** */
    @Test
    void testGetHeadMiniSchoeller() {
        log.info("beginning testGetHeadMiniSchoeller");
        final ApiHead head = crud.readOne("mini-schoeller");
        assertThat(head)
            .returns("head", o -> o.getType())
            .returns("Header", o -> o.getString());
    }

    /** */
    @Test
    void testGetHeadBadDataSet() {
        log.info("beginning testGetHeadBadDataSet");
        assertThatExceptionOfType(DataSetNotFoundException.class)
            .isThrownBy(() -> crud.readOne("XYZZY"))
            .withMessage("Data set XYZZY not found");
    }
}
