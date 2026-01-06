package org.schoellerfamily.gedbrowser.controller.exception.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.controller.exception.DataSetNotFoundException;

/**
 * @author Dick Schoeller
 */
public class DataSetNotFoundExceptionTest {
    /** */
    private DataSetNotFoundException exception;

    /** */
    @BeforeEach
    public void setUp() {
        exception = new DataSetNotFoundException("Data set not found", "xyzzy");
    }

    /** */
    @Test
    void testMessage() {
        assertEquals("Data set not found", exception.getMessage(), "Message doesn't match");
    }

    /** */
    @Test
    void testDatasetName() {
        assertEquals("xyzzy", exception.getDatasetName(), "Dataset name doesn't match");
    }
}
