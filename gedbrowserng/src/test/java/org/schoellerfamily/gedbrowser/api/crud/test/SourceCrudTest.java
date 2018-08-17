package org.schoellerfamily.gedbrowser.api.crud.test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.api.Application;
import org.schoellerfamily.gedbrowser.api.controller.exception.DataSetNotFoundException;
import org.schoellerfamily.gedbrowser.api.controller.exception.ObjectNotFoundException;
import org.schoellerfamily.gedbrowser.api.crud.FamilyCrud;
import org.schoellerfamily.gedbrowser.api.crud.PersonCrud;
import org.schoellerfamily.gedbrowser.api.crud.SourceCrud;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSource;
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
public class SourceCrudTest {
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
    private SourceCrud crud;

    /** */
    private CrudTestHelper helper;

    /** */
    @Before
    public void setUp() {
        helper = new CrudTestHelper(
                new PersonCrud(loader, toDocConverter, repositoryManager),
                new FamilyCrud(loader, toDocConverter, repositoryManager));
        crud = new SourceCrud(loader, toDocConverter, repositoryManager);
    }

    /** */
    @Test
    public final void testReadSourcesGl120368() {
        logger.info("Beginning testReadSourcesGl120368");
        final List<ApiSource> list = crud.readAll(helper.getDb());
        final ApiSource firstSource = list.get(0);
        then(firstSource.getString()).isEqualTo("S1688");
        then(firstSource.getImages()).isEmpty();
        then(firstSource.getTitle()).isEqualTo("1841 England Census");
        final List<ApiAttribute> attributes = firstSource.getAttributes();
        then(attributes.get(0).getType()).isEqualTo("attribute");
        then(attributes.get(0).getString()).isEqualTo("Author");
        then(attributes.get(0).getTail()).isEqualTo("Ancestry.com");
        then(attributes.get(1).getType()).isEqualTo("attribute");
        then(attributes.get(1).getString()).isEqualTo("Title");
        then(attributes.get(1).getTail()).isEqualTo("1841 England Census");
        then(attributes.get(2).getType()).isEqualTo("attribute");
        then(attributes.get(2).getString()).isEqualTo("Published");
        then(attributes.get(2).getTail()).isEqualTo("Provo, UT, USA: The Generations Network, Inc., 2006");
    }

    /** */
    @Test
    public final void testReadSourcesMiniSchoeller() {
        logger.info("Beginning testReadSourcesMiniSchoeller");
        final List<ApiSource> list = crud.readAll("mini-schoeller");
        final ApiSource firstSource = list.get(0);
        then(firstSource.getString()).isEqualTo("S2");
        then(firstSource.getImages()).isEmpty();
        then(firstSource.getTitle()).isEqualTo("Schoeller, Melissa Robinson, birth certificate");
        final List<ApiAttribute> attributes = firstSource.getAttributes();
        then(attributes.get(0).getType()).isEqualTo("attribute");
        then(attributes.get(0).getString()).isEqualTo("Title");
        then(attributes.get(0).getTail()).isEqualTo("Schoeller, Melissa Robinson, birth certificate");
        then(attributes.get(1).getType()).isEqualTo("attribute");
        then(attributes.get(1).getString()).isEqualTo("Abbreviation");
        then(attributes.get(1).getTail()).isEqualTo("SchoellerMelissaBirthCert");
        then(attributes.get(2).getType()).isEqualTo("attribute");
        then(attributes.get(2).getString()).isEqualTo("Note");
        then(attributes.get(2).getTail()).isEqualTo("We have the original of this document");
    }

    /** */
    @Test
    public final void testReadSourcesMiniSchoellerS2() {
        logger.info("Beginning testReadSourcesMiniSchoellerS2");
        final ApiSource firstSource = crud.readSource("mini-schoeller", "S2");
        then(firstSource.getString()).isEqualTo("S2");
        then(firstSource.getImages()).isEmpty();
        then(firstSource.getTitle()).isEqualTo("Schoeller, Melissa Robinson, birth certificate");
        final List<ApiAttribute> attributes = firstSource.getAttributes();
        then(attributes.get(0).getType()).isEqualTo("attribute");
        then(attributes.get(0).getString()).isEqualTo("Title");
        then(attributes.get(0).getTail()).isEqualTo("Schoeller, Melissa Robinson, birth certificate");
        then(attributes.get(1).getType()).isEqualTo("attribute");
        then(attributes.get(1).getString()).isEqualTo("Abbreviation");
        then(attributes.get(1).getTail()).isEqualTo("SchoellerMelissaBirthCert");
        then(attributes.get(2).getType()).isEqualTo("attribute");
        then(attributes.get(2).getString()).isEqualTo("Note");
        then(attributes.get(2).getTail()).isEqualTo("We have the original of this document");
    }

    /** */
    @Test
    public final void testReadSourcesMiniSchoellerXyzzy() {
        logger.info("Beginning testReadSourcesMiniSchoellerXyzzy");
        try {
            final ApiSource source = crud.readSource("mini-schoeller", "Xyzzy");
            fail("The source should not be found: " + source.getString());
        } catch (ObjectNotFoundException e) {
            assertEquals("Mismatched message",
                    "Object Xyzzy of type source not found", e.getMessage());
        }
    }

    /** */
    @Test
    public final void testCreateSourcesSimple() {
        logger.info("Beginning testCreateSourcesSimple");
        final ApiSource inSource = new ApiSource("source", "", "Unknown");
        final ApiSource newSource = crud.createSource(helper.getDb(), inSource);
        then(newSource.getType()).isEqualTo(inSource.getType());
    }

    /** */
    @Test
    public final void testDeleteSource() {
        logger.info("Beginning testDeleteSource");
        final ApiSource reqSource = new ApiSource("source", "", "Unknown");
        final ApiSource resSource = crud.createSource(helper.getDb(),
                reqSource);
        final String id = resSource.getString();
        final ApiSource deletedSource = crud.deleteSource(helper.getDb(), id);

        try {
            final ApiSource foundSource = crud.readSource("mini-schoeller",
                    deletedSource.getString());
            fail("The source should not be found: " + foundSource.getString());
        } catch (ObjectNotFoundException e) {
            assertEquals("Mismatched message", "Object "
                    + deletedSource.getString() + " of type source not found",
                    e.getMessage());
        }
    }

    /** */
    @Test
    public final void testDeleteSourceNotFound() {
        logger.info("Beginning testDeleteSourceNotFound");
        try {
            final ApiSource deletedSource =
                    crud.deleteSource(helper.getDb(), "XXXXXXX");
            fail("The source should not be found: "
                    + deletedSource.getString());
        } catch (ObjectNotFoundException e) {
            assertEquals("Mismatched message",
                    "Object XXXXXXX of type source not found", e.getMessage());
        }
    }

    /** */
    @Test
    public final void testDeleteSubmitterDatabaseNotFound() {
        logger.info("Beginning testDeleteSubmitterDatabaseNotFound");
        try {
            final ApiSource deletedSource =
                    crud.deleteSource("XYZZY", "S1");
            fail("The dataset XYZZY should not be found,"
                    + " while looking for source "
                    + deletedSource.getString());
        } catch (DataSetNotFoundException e) {
            assertEquals("Mismatched message",
                    "Data set XYZZY not found", e.getMessage());
        }
    }

    /** */
    @Test
    public final void testUpdateSourceWithNote() {
        logger.info("Beginning testUpdateSourceWithNote");
        final List<ApiAttribute> attributes = new ArrayList<>();
        attributes.add(new ApiAttribute("attribute", "Note", "first note"));
        final ApiSource inSource = new ApiSource("source", "", attributes,
                "Unknown");
        final ApiSource newSource = crud.createSource(helper.getDb(), inSource);

        final ApiAttribute aNote = new ApiAttribute("attribute", "Note",
                "this is a note");
        newSource.getAttributes().add(aNote);
        final ApiSource updatedSource = crud.updateOne(helper.getDb(),
                newSource.getString(), newSource);
        assertEquals("attribute should be present", aNote,
                updatedSource.getAttributes().get(1));
    }
}
