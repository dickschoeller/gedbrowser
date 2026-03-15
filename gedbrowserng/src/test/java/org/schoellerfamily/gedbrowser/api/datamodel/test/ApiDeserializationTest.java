package org.schoellerfamily.gedbrowser.api.datamodel.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiFamily;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiHead;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiNote;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSource;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSubmission;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSubmitter;

import tools.jackson.databind.ObjectMapper;

/**
 * Tests JSON deserialization of API datamodel types using ObjectMapper.readValue.
 *
 * @author Dick Schoeller
 */
final class ApiDeserializationTest {

    /** */
    private ObjectMapper mapper;

    /** */
    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    /** */
    @Test
    void testApiObjectDeserializeType() throws Exception {
        final String json = "{\"type\":\"person\",\"string\":\"I1\",\"attributes\":[]}";
        final ApiObject obj = mapper.readValue(json, ApiObject.class);
        assertEquals("person", obj.getType(), "type mismatch");
    }

    /** */
    @Test
    void testApiObjectDeserializeString() throws Exception {
        final String json = "{\"type\":\"person\",\"string\":\"I1\",\"attributes\":[]}";
        final ApiObject obj = mapper.readValue(json, ApiObject.class);
        assertEquals("I1", obj.getString(), "string mismatch");
    }

    /** */
    @Test
    void testApiObjectDeserializeEmptyAttributes() throws Exception {
        final String json = "{\"type\":\"person\",\"string\":\"I1\",\"attributes\":[]}";
        final ApiObject obj = mapper.readValue(json, ApiObject.class);
        assertTrue(obj.getAttributes().isEmpty(), "attributes should be empty");
    }

    /** */
    @Test
    void testApiObjectDeserializeWithAttributes() throws Exception {
        final String json = "{\"type\":\"person\",\"string\":\"I1\","
            + "\"attributes\":[{\"type\":\"attribute\",\"string\":\"Name\",\"tail\":\"John Doe\"}]}";
        final ApiObject obj = mapper.readValue(json, ApiObject.class);
        assertEquals(1, obj.getAttributes().size(), "attributes size mismatch");
    }

    /** */
    @Test
    void testApiObjectDeserializeDefaultsWhenMissing() throws Exception {
        final String json = "{}";
        final ApiObject obj = mapper.readValue(json, ApiObject.class);
        assertNotNull(obj, "deserialized object should not be null");
        assertEquals("", obj.getType(), "type should default to empty");
        assertEquals("", obj.getString(), "string should default to empty");
    }

    /** */
    @Test
    void testApiAttributeDeserializeType() throws Exception {
        final String json = "{\"type\":\"attribute\",\"string\":\"Name\",\"tail\":\"John Doe\"}";
        final ApiAttribute attr = mapper.readValue(json, ApiAttribute.class);
        assertEquals("attribute", attr.getType(), "type mismatch");
    }

    /** */
    @Test
    void testApiAttributeDeserializeString() throws Exception {
        final String json = "{\"type\":\"attribute\",\"string\":\"Name\",\"tail\":\"John Doe\"}";
        final ApiAttribute attr = mapper.readValue(json, ApiAttribute.class);
        assertEquals("Name", attr.getString(), "string mismatch");
    }

    /** */
    @Test
    void testApiAttributeDeserializeTail() throws Exception {
        final String json = "{\"type\":\"attribute\",\"string\":\"Name\",\"tail\":\"John Doe\"}";
        final ApiAttribute attr = mapper.readValue(json, ApiAttribute.class);
        assertEquals("John Doe", attr.getTail(), "tail mismatch");
    }

    /** */
    @Test
    void testApiAttributeDeserializeDefaultTail() throws Exception {
        final String json = "{\"type\":\"attribute\",\"string\":\"Name\"}";
        final ApiAttribute attr = mapper.readValue(json, ApiAttribute.class);
        assertEquals("", attr.getTail(), "tail should default to empty");
    }

    /** */
    @Test
    void testApiPersonDeserializeString() throws Exception {
        final String json = "{\"type\":\"person\",\"string\":\"I1\","
            + "\"indexName\":\"Doe, John\",\"surname\":\"Doe\",\"attributes\":[]}";
        final ApiPerson person = mapper.readValue(json, ApiPerson.class);
        assertEquals("I1", person.getString(), "string mismatch");
    }

    /** */
    @Test
    void testApiPersonDeserializeIndexName() throws Exception {
        final String json = "{\"type\":\"person\",\"string\":\"I1\","
            + "\"indexName\":\"Doe, John\",\"surname\":\"Doe\",\"attributes\":[]}";
        final ApiPerson person = mapper.readValue(json, ApiPerson.class);
        assertEquals("Doe, John", person.getIndexName(), "indexName mismatch");
    }

    /** */
    @Test
    void testApiPersonDeserializeSurname() throws Exception {
        final String json = "{\"type\":\"person\",\"string\":\"I1\","
            + "\"indexName\":\"Doe, John\",\"surname\":\"Doe\",\"attributes\":[]}";
        final ApiPerson person = mapper.readValue(json, ApiPerson.class);
        assertEquals("Doe", person.getSurname(), "surname mismatch");
    }

    /** */
    @Test
    void testApiPersonDeserializeDefaultIndexName() throws Exception {
        final String json = "{\"type\":\"person\",\"string\":\"\",\"attributes\":[]}";
        final ApiPerson person = mapper.readValue(json, ApiPerson.class);
        assertEquals("?, ?", person.getIndexName(), "indexName should default to '?, ?'");
    }

    /** */
    @Test
    void testApiPersonDeserializeDefaultSurname() throws Exception {
        final String json = "{\"type\":\"person\",\"string\":\"\",\"attributes\":[]}";
        final ApiPerson person = mapper.readValue(json, ApiPerson.class);
        assertEquals("?", person.getSurname(), "surname should default to '?'");
    }

    /** */
    @Test
    void testApiPersonDeserializePlaces() throws Exception {
        final String json = "{\"type\":\"person\",\"string\":\"I1\","
            + "\"indexName\":\"Doe, John\",\"surname\":\"Doe\","
            + "\"places\":[{"
            + "\"placeName\":\"Needham, Massachusetts, USA\","
            + "\"location\":{\"longitude\":-71.2377548,\"latitude\":42.2809285},"
            + "\"southwest\":{\"longitude\":-71.2477548,\"latitude\":42.2709285},"
            + "\"northeast\":{\"longitude\":-71.2277548,\"latitude\":42.2909285}"
            + "}],\"attributes\":[]}";
        final ApiPerson person = mapper.readValue(json, ApiPerson.class);
        assertNotNull(person.getPlaces(), "places should deserialize");
        assertEquals(1, person.getPlaces().size(), "places size mismatch");
        assertEquals("Needham, Massachusetts, USA", person.getPlaces().get(0).getPlaceName(),
            "placeName mismatch");
    }

    /** */
    @Test
    void testApiFamilyDeserializeType() throws Exception {
        final String json = "{\"type\":\"family\",\"string\":\"F1\",\"attributes\":[]}";
        final ApiFamily family = mapper.readValue(json, ApiFamily.class);
        assertEquals("family", family.getType(), "type mismatch");
    }

    /** */
    @Test
    void testApiFamilyDeserializeString() throws Exception {
        final String json = "{\"type\":\"family\",\"string\":\"F1\",\"attributes\":[]}";
        final ApiFamily family = mapper.readValue(json, ApiFamily.class);
        assertEquals("F1", family.getString(), "string mismatch");
    }

    /** */
    @Test
    void testApiFamilyDeserializeSpouses() throws Exception {
        final String json = "{\"type\":\"family\",\"string\":\"F1\","
            + "\"spouses\":[{\"type\":\"husband\",\"string\":\"I1\",\"tail\":\"\"}],"
            + "\"children\":[],\"attributes\":[]}";
        final ApiFamily family = mapper.readValue(json, ApiFamily.class);
        assertEquals(1, family.getSpouses().size(), "spouses size mismatch");
    }

    /** */
    @Test
    void testApiFamilyDeserializeChildren() throws Exception {
        final String json = "{\"type\":\"family\",\"string\":\"F1\","
            + "\"children\":[{\"type\":\"child\",\"string\":\"I2\",\"tail\":\"\"}],"
            + "\"spouses\":[],\"attributes\":[]}";
        final ApiFamily family = mapper.readValue(json, ApiFamily.class);
        assertEquals(1, family.getChildren().size(), "children size mismatch");
    }

    /** */
    @Test
    void testApiHeadDeserializeType() throws Exception {
        final String json = "{\"type\":\"head\",\"string\":\"Head\",\"attributes\":[]}";
        final ApiHead head = mapper.readValue(json, ApiHead.class);
        assertEquals("head", head.getType(), "type mismatch");
    }

    /** */
    @Test
    void testApiNoteDeserializeTail() throws Exception {
        final String json = "{\"type\":\"note\",\"string\":\"N1\","
            + "\"tail\":\"This is a note.\",\"attributes\":[]}";
        final ApiNote note = mapper.readValue(json, ApiNote.class);
        assertEquals("This is a note.", note.getTail(), "tail mismatch");
    }

    /** */
    @Test
    void testApiSourceDeserializeString() throws Exception {
        final String json = "{\"type\":\"source\",\"string\":\"S1\",\"attributes\":[]}";
        final ApiSource source = mapper.readValue(json, ApiSource.class);
        assertEquals("S1", source.getString(), "string mismatch");
    }

    /** */
    @Test
    void testApiSubmitterDeserializeName() throws Exception {
        final String json = "{\"type\":\"submitter\",\"string\":\"SUBM\","
            + "\"name\":\"Jane Smith\",\"attributes\":[]}";
        final ApiSubmitter submitter = mapper.readValue(json, ApiSubmitter.class);
        assertEquals("Jane Smith", submitter.getName(), "name mismatch");
    }

    /** */
    @Test
    void testApiSubmissionDeserializeType() throws Exception {
        final String json = "{\"type\":\"submission\",\"string\":\"SUBN\",\"attributes\":[]}";
        final ApiSubmission submission = mapper.readValue(json, ApiSubmission.class);
        assertEquals("submission", submission.getType(), "type mismatch");
    }
}
