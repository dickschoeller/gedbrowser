package org.schoellerfamily.gedbrowser.writer.creator;

import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.writer.GedWriterLine;

/**
 * @author Dick Schoeller
 */
public interface DateLineVisitor extends GedObjectLineVisitor {
    /**
     * {@inheritDoc}
     */
    @Override
    default void visit(final Date date) {
        final GedWriterLine line = new GedWriterLine(getLevel(), date,
                getLevel() + " DATE " + date.getDate());
        getLines().add(line);
        handleChildren(date);
    }
}
