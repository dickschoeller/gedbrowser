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
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiFamily;
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
@TestPropertySource(properties = { "management.port=0" })
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
public class FamilyCrudTest {
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
    private FamilyCrud crud;

    /** */
    @Before
    public void setUp() {
        crud = new FamilyCrud(loader, toDocConverter, repositoryManager);
    }

    /** */
    @Test
    public final void testGetFamiliesGl120368() {
        logger.info("Beginning testGetFamiliesGl120368");
        final List<ApiFamily> list = crud.readAll("gl120368");
        final ApiFamily firstFamily = list.get(0);
        then(firstFamily.getString()).isEqualTo("F1");
        then(firstFamily.getAttributes()).isEmpty();
        then(firstFamily.getImages()).isEmpty();
    }

    /** */
    @Test
    public final void testGetFamiliesMiniSchoeller() {
        logger.info("Beginning testGetFamiliesMiniSchoeller");
        final List<ApiFamily> list = crud.readAll("mini-schoeller");
        final ApiFamily firstFamily = list.get(0);
        then(firstFamily.getString()).isEqualTo("F1");
        final ApiAttribute firstAttribute = firstFamily.getAttributes().get(0);
        then(firstAttribute.getString()).isEqualTo("Marriage");
        then(firstAttribute.getAttributes().get(0).getType()).isEqualTo("date");
        then(firstAttribute.getAttributes().get(0).getString())
                .isEqualTo("27 MAY 1984");
    }

    /** */
    @Test
    public final void testGetFamiliesGl120368F1593() {
        logger.info("Beginning testGetFamiliesGl120368F1593");
        final ApiFamily family = crud.readFamily("gl120368", "F1593");
        then(family.getString()).isEqualTo("F1593");
        final ApiAttribute firstAttribute = family.getAttributes().get(0);
        then(firstAttribute.getType()).isEqualTo("sourcelink");
        then(firstAttribute.getString()).isEqualTo("S33723");
    }

    /** */
    @Test
    public final void testGetFamiliesMiniSchoellerF1() {
        logger.info("Beginning testGetFamiliesMiniSchoellerF1");
        final ApiFamily family = crud.readFamily("mini-schoeller", "F1");
        then(family.getString()).isEqualTo("F1");
        final ApiAttribute firstAttribute = family.getAttributes().get(0);
        then(firstAttribute.getType()).isEqualTo("attribute");
        then(firstAttribute.getString()).isEqualTo("Marriage");
        then(firstAttribute.getAttributes().get(0).getType()).isEqualTo("date");
        then(firstAttribute.getAttributes().get(0).getString())
                .isEqualTo("27 MAY 1984");
        then(firstAttribute.getAttributes().get(1).getType())
                .isEqualTo("place");
        then(firstAttribute.getAttributes().get(1).getString())
                .startsWith("Temple Emanu-el");
        then(firstAttribute.getAttributes().get(2).getType())
                .isEqualTo("attribute");
        then(firstAttribute.getAttributes().get(2).getString())
                .isEqualTo("Note");
        then(firstAttribute.getAttributes().get(2).getTail()).contains(
                "Wayne Franklin", "Dale Matcovitch",
                "Carol Robinson Sacerdote");
    }

    /** */
    @Test
    public final void testGetFamiliesMiniSchoellerXyzzy() {
        logger.info("Beginning testGetFamiliesMiniSchoellerXyzzy");
        try {
            final ApiFamily family = crud.readFamily("mini-schoeller", "Xyzzy");
            fail("The family should not be found: " + family.getString());
        } catch (ObjectNotFoundException e) {
            assertEquals("Mismatched message",
                    "Object Xyzzy of type family not found", e.getMessage());
        }
    }

    /** */
    @Test
    public final void testCreateFamiliesSimple() {
        logger.info("Beginning testCreateFamiliesSimple");
        final ApiFamily inFamily = new ApiFamily("family", "");
        final ApiFamily outFamily = crud.createFamily("gl120368", inFamily);
        then(outFamily.getType()).isEqualTo("family");
        then(outFamily.getAttributes()).isEmpty();
        then(outFamily.getString()).startsWith("F");
    }

    /** */
    @Test
    public final void testCreateFamiliesWithMarriage() {
        logger.info("Beginning testCreateFamiliesWithMarriage");
        final List<ApiAttribute> attributes = new ArrayList<>();
        attributes.add(new ApiAttribute("attribute", "Marriage", ""));
        final ApiFamily inFamily = new ApiFamily("family", "", attributes);
        final ApiFamily outFamily = crud.createFamily("gl120368", inFamily);
        then(outFamily.getType()).isEqualTo("family");
        then(outFamily.getString()).startsWith("F");
        then(outFamily.getAttributes().get(0).getString())
                .isEqualTo("Marriage");
    }

    /** */
    @Test
    public final void testDeleteFamily() {
        logger.info("Beginning testDeleteFamily");
        final ApiFamily inFamily = new ApiFamily("family", "");
        final ApiFamily outFamily = crud.createFamily("gl120368", inFamily);
        final String id = outFamily.getString();
        final ApiFamily deletedFamily = crud.deleteFamily("gl120368", id);
        then(deletedFamily.getString()).isEqualTo(id);
        try {
            final ApiFamily family = crud.readFamily("gl120368", id);
            fail("The family should not be found: " + family.getString());
        } catch (ObjectNotFoundException e) {
            assertEquals("Mismatched message",
                    "Object " + id + " of type family not found",
                    e.getMessage());
        }
    }

    /** */
    @Test
    public final void testDeleteFamilyNotFound() {
        logger.info("Beginning testDeleteFamilyNotFound");
        try {
            final ApiFamily family = crud.deleteFamily("gl120368", "XXXXXXX");
            fail("The family should not be found: " + family.getString());
        } catch (ObjectNotFoundException e) {
            assertEquals("Mismatched message",
                    "Object XXXXXXX of type family not found", e.getMessage());
        }
    }

    /** */
    @Test
    public final void testDeleteFamilyDatabaseNotFound() {
        logger.info("Beginning testDeleteFamilyDatabaseNotFound");
        try {
            final ApiFamily family = crud.deleteFamily("XYZZY", "SUBM1");
            fail("The family should not be found: " + family.getString());
        } catch (DataSetNotFoundException e) {
            assertEquals("Mismatched message", "Data set XYZZY not found",
                    e.getMessage());
        }
    }

    /** */
    @Test
    public final void testUpdateFamilyWithNote() {
        logger.info("Beginning testUpdateFamilyWithNote");
        final List<ApiAttribute> attributes = new ArrayList<>();
        attributes.add(new ApiAttribute("attribute", "Marriage", ""));
        final ApiFamily inFamily = new ApiFamily("family", "", attributes);
        inFamily.getChildren().add(new ApiAttribute("child", "I1"));
        final ApiFamily familyPostResponse = crud.createFamily("gl120368",
                inFamily);
        then(familyPostResponse.getType()).isEqualTo(inFamily.getType());
        then(familyPostResponse.getAttributes().size()).isEqualTo(1);
        then(familyPostResponse.getChildren().size()).isEqualTo(1);
        final ApiAttribute aNote = new ApiAttribute("attribute", "Note",
                "this is a note");
        familyPostResponse.getAttributes().add(aNote);
        then(familyPostResponse.getAttributes().size()).isEqualTo(2);
        final ApiFamily familyPutResponse = crud.updateOne("gl120368",
                familyPostResponse.getString(), familyPostResponse);
        final List<ApiAttribute> attributesPutResponse = familyPutResponse
                .getAttributes();
        logger.info("Attribute list size: " + attributesPutResponse.size());
        then(attributesPutResponse.size()).isEqualTo(2);
        for (final ApiAttribute a : attributesPutResponse) {
            logger.info("attribute: " + a.getType() + " " + a.getString() + " "
                    + a.getTail());
        }
        assertEquals("attribute should be present", aNote,
                attributesPutResponse.get(1));
    }
}
