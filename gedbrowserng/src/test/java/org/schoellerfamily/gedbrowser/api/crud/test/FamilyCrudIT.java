package org.schoellerfamily.gedbrowser.api.crud.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.api.Application;
import org.schoellerfamily.gedbrowser.api.controller.exception.DataSetNotFoundException;
import org.schoellerfamily.gedbrowser.api.controller.exception.ObjectNotFoundException;
import org.schoellerfamily.gedbrowser.api.crud.FamilyCrud;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiFamily;
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
class FamilyCrudIT {

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
    private FamilyCrud crud;

    /** */
    @BeforeEach
    void setUp() {
        crud = new FamilyCrud(loader, toDocConverter, repositoryManager);
    }

    /** */
    @Test
    void testGetFamiliesGl120368() {
        log.info("Beginning testGetFamiliesGl120368");
        final List<ApiFamily> list = crud.readAll("gl120368");
        final ApiFamily firstFamily = list.get(0);

        assertThat(firstFamily)
            .returns("F1", f -> f.getString())
            .returns(true, f -> f.getAttributes().isEmpty())
            .returns(true, f -> f.getImages().isEmpty());
    }

    /** */
    @Test
    void testGetFamiliesMiniSchoeller() {
        log.info("Beginning testGetFamiliesMiniSchoeller");
        final List<ApiFamily> list = crud.readAll("mini-schoeller");
        final ApiFamily firstFamily = list.get(0);

        assertThat(firstFamily)
            .returns("F1", f -> f.getString())
            .returns("Marriage", f -> f.getAttributes().get(0).getString())
            .returns("date", f -> f.getAttributes().get(0).getAttributes().get(0).getType())
            .returns("27 MAY 1984",
                f -> f.getAttributes().get(0).getAttributes().get(0).getString());
    }

    /** */
    @Test
    void testGetFamiliesGl120368F1593() {
        log.info("Beginning testGetFamiliesGl120368F1593");
        final ApiFamily family = crud.readOne("gl120368", "F1593");

        assertThat(family)
            .returns("F1593", f -> f.getString())
            .returns("sourcelink", f -> f.getAttributes().get(0).getType())
            .returns("S33723", f -> f.getAttributes().get(0).getString());
    }

    /** */
    @Test
    void testGetFamiliesMiniSchoellerF1() {
        log.info("Beginning testGetFamiliesMiniSchoellerF1");
        final ApiFamily family = crud.readOne("mini-schoeller", "F1");

        assertThat(family)
            .returns("F1", f -> f.getString())
            .returns("attribute", f -> f.getAttributes().get(0).getType())
            .returns("Marriage", f -> f.getAttributes().get(0).getString())
            .returns("date", f -> f.getAttributes().get(0).getAttributes().get(0).getType())
            .returns("27 MAY 1984",
                f -> f.getAttributes().get(0).getAttributes().get(0).getString())
            .returns("place", f -> f.getAttributes().get(0).getAttributes().get(1).getType())
            .returns(true, f -> f.getAttributes().get(0).getAttributes().get(1).getString()
                .startsWith("Temple Emanu-el"))
            .returns("attribute", f -> f.getAttributes().get(0).getAttributes().get(2).getType())
            .returns("Note", f -> f.getAttributes().get(0).getAttributes().get(2).getString())
            .returns(true, f -> f.getAttributes().get(0).getAttributes().get(2).getTail()
                .contains("Wayne Franklin"))
            .returns(true, f -> f.getAttributes().get(0).getAttributes().get(2).getTail()
                .contains("Dale Matcovitch"))
            .returns(true, f -> f.getAttributes().get(0).getAttributes().get(2).getTail()
                .contains("Carol Robinson Sacerdote"));
    }

    /** */
    @Test
    void testGetFamiliesMiniSchoellerXyzzy() {
        log.info("Beginning testGetFamiliesMiniSchoellerXyzzy");
        assertThatExceptionOfType(ObjectNotFoundException.class)
            .isThrownBy(() -> crud.readOne("mini-schoeller", "Xyzzy"))
            .withMessage("Object Xyzzy of type family not found");
    }

    /** */
    @Test
    void testCreateFamiliesSimple() {
        log.info("Beginning testCreateFamiliesSimple");
        final ApiFamily inFamily = ApiFamily.builder().type("family").string("").build();
        final ApiFamily outFamily = crud.createOne("gl120368", inFamily);

        assertThat(outFamily)
            .returns("family", f -> f.getType())
            .returns(true, f -> f.getAttributes().isEmpty())
            .returns(true, f -> f.getString().startsWith("F"));
    }

    /** */
    @Test
    void testCreateFamiliesWithMarriage() {
        log.info("Beginning testCreateFamiliesWithMarriage");
        final ApiFamily inFamily = ApiFamily.builder()
            .type("family")
            .string("")
            .attributes(List.of(ApiAttribute.builder()
                .type("attribute")
                .string("Marriage")
                .tail("")
                .build()))
            .build();
        final ApiFamily outFamily = crud.createOne("gl120368", inFamily);

        assertThat(outFamily)
            .returns("family", f -> f.getType())
            .returns(true, f -> f.getString().startsWith("F"))
            .returns("Marriage", f -> f.getAttributes().get(0).getString());
    }

    /** */
    @Test
    @SuppressWarnings({ "PMD.UnitTestContainsTooManyAsserts" })
    void testDeleteFamily() {
        log.info("Beginning testDeleteFamily");
        final ApiFamily inFamily = ApiFamily.builder().type("family").string("").build();
        final ApiFamily outFamily = crud.createOne("gl120368", inFamily);
        final String id = outFamily.getString();
        final ApiFamily deletedFamily = crud.deleteOne("gl120368", id);

        assertThat(deletedFamily)
            .returns(id, f -> f.getString());
        assertThatExceptionOfType(ObjectNotFoundException.class)
            .isThrownBy(() -> crud.readOne("gl120368", id))
            .withMessage("Object " + id + " of type family not found");
    }

    /** */
    @Test
    void testDeleteFamilyNotFound() {
        log.info("Beginning testDeleteFamilyNotFound");
        assertThatExceptionOfType(ObjectNotFoundException.class)
            .isThrownBy(() -> crud.deleteOne("gl120368", "XXXXXXX"))
            .withMessage("Object XXXXXXX of type family not found");
    }

    /** */
    @Test
    void testDeleteFamilyDatabaseNotFound() {
        log.info("Beginning testDeleteFamilyDatabaseNotFound");
        assertThatExceptionOfType(DataSetNotFoundException.class)
            .isThrownBy(() -> crud.deleteOne("XYZZY", "SUBM1"))
            .withMessage("Data set XYZZY not found");
    }

    /** */
    @Test
    @SuppressWarnings({ "PMD.UnitTestContainsTooManyAsserts" })
    void testUpdateFamilyWithNote() {
        log.info("Beginning testUpdateFamilyWithNote");
        final List<ApiAttribute> attributes = List
            .of(ApiAttribute.builder().type("attribute").string("Marriage").tail("").build());
        final ApiFamily inFamily = ApiFamily.builder()
            .type("family")
            .string("")
            .attributes(attributes)
            .children(List.of(ApiAttribute.builder()
                .type("child")
                .string("I1")
                .build()))
            .build();
        final ApiFamily familyPostResponse = crud.createOne("gl120368", inFamily);
        final ApiAttribute aNote = ApiAttribute.builder()
            .type("attribute")
            .string("Note")
            .tail("this is a note")
            .build();
        final ApiFamily forPut = familyPostResponse.toBuilder().attribute(aNote).build();
        final ApiFamily familyPutResponse = crud.updateOne("gl120368", forPut.getString(), forPut);
        final List<ApiAttribute> attributesPutResponse = familyPutResponse.getAttributes();
        log.info("Attribute list size: {}", attributesPutResponse.size());
        for (final ApiAttribute a : attributesPutResponse) {
            log.info("attribute: {} {} {}", a.getType(), a.getString(), a.getTail());
        }

        assertThat(familyPostResponse)
            .returns(inFamily.getType(), f -> f.getType())
            .returns(1, f -> f.getAttributes().size())
            .returns(1, f -> f.getChildren().size());
        assertThat(forPut)
            .returns(2, f -> f.getAttributes().size());
        assertThat(attributesPutResponse).hasSize(2);
        assertEquals(aNote, attributesPutResponse.get(1), "attribute should be present");
    }
}
