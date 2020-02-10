package org.schoellerfamily.gedbrowser.api.test;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author dick
 */
public final class SecurityTestHelper {
    /** Logger. */
    private static final transient Log LOGGER =
            LogFactory.getLog(SecurityTestHelper.class);

    /**
     * Private constructor.
     */
    private SecurityTestHelper() {
        // Empty
    }
    /**
     * @param userFile the path of the file to reset
     */
    public static void resetUserFile(final String userFile) {
        LOGGER.info("resetting " + userFile);
        final String[] strings = {
                "schoeller@comcast.net,Richard,Schoeller,"
                + "schoeller@comcast.net,HAHANOWAY,USER,ADMIN\n",
                "guest,,,,guest,USER\n" };
        try (FileOutputStream fstream = new FileOutputStream(userFile);
                BufferedOutputStream bstream = new BufferedOutputStream(
                        fstream)) {
            for (final String string : strings) {
                bstream.write(string.getBytes(StandardCharsets.UTF_8));
            }
        } catch (IOException e) {
            LOGGER.error("Problem writing user file", e);
        }
    }

}
