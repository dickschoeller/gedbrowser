package org.schoellerfamily.gedbrowser.persistence.domain;

import java.util.List;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.GetString;
import org.schoellerfamily.gedbrowser.persistence.GedDocumentLoader;

/**
 * @author Dick Schoeller
 *
 * @param <G> the GedObject type that this is for
 */
public interface GedDocument<G extends GedObject> extends GetString {
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
     * Add a single attribute to the set of attributes.
     *
     * @param attribute the attribute
     */
    void addAttribute(GedDocument<?> attribute);

    /**
     * Empty out the attributes list.
     */
    void clearAttributes();

    /**
     * This method is used to assign the GedObject and to pass its values along
     * to the fields for persistence.
     *
     * @param loader load the document attributes
     * @param ged the associated gedObject
     */
    void loadGedObject(GedDocumentLoader loader, GedObject ged);

    /**
     * Allows visitor traversal by generic documents.
     *
     * @param visitor the visitor
     */
    void accept(GedDocumentVisitor visitor);
}
