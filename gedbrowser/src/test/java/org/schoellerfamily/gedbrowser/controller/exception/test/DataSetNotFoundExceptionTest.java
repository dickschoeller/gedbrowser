package org.schoellerfamily.gedbrowser.controller.exception.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.controller.exception.DataSetNotFoundException;

/**
 * @author Dick Schoeller
 */
public class DataSetNotFoundExceptionTest {
    /** */
    private DataSetNotFoundException exception;

    /** */
    @Before
    public void setUp() {
        exception = new DataSetNotFoundException("Data set not found", "xyzzy");
    }

    /** */
    @Test
    public void testMessage() {
        assertEquals("Message doesn't match", "Data set not found",
                exception.getMessage());
    }

    /** */
    @Test
    public void testDatasetName() {
        assertEquals("Dataset name doesn't match", "xyzzy",
                exception.getDatasetName());
    }
}
