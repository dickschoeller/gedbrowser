package org.schoellerfamily.gedbrowser.persistence.domain;

import java.util.List;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;

/**
 * @author Dick Schoeller
 */
public interface GedDocument<G extends GedObject> {
    /**
     * @return the idString
     */
    String getIdString();

    /**
     * @param idString the idString to set
     */
    void setIdString(String idString);

    /**
     * @return the type
     */
    String getType();

    /**
     * @param type the type to set
     */
    void setType(String type);

    /**
     * @return the string
     */
    String getString();

    /**
     * @param string the string to set
     */
    void setString(String string);

    /**
     * @return the filename
     */
    String getFilename();

    /**
     * @param filename the filename to set
     */
    void setFilename(String filename);

    /**
     * @return the DB name
     */
    String getDbName();

    /**
     * @param dbName the DB name to set
     */
    void setDbName(String dbName);

    /**
     * @return the gedObject
     */
    G getGedObject();

    /**
     * @param gedObject the gedObject to set
     */
    void setGedObject(G gedObject);

    /**
     * @return the attributes
     */
    List<GedDocument<? extends GedObject>> getAttributes();

    /**
     * @param attributes a new set of attributes
     */
    void setAttributes(List<GedDocument<? extends GedObject>> attributes);

    /**
     * Empty out the attributes list.
     */
    void clearAttributes();

    /**
     * @param gedAttributes load in all of the attributes (sub-objects)
     */
    void loadAttributes(List<GedObject> gedAttributes);

    /**
     * This method is used to assign the GedObject and to pass its values along
     * to the fields for persistence.
     *
     * @param ged the associated gedObject
     */
    void loadGedObject(GedObject ged);
}