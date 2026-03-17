package org.schoellerfamily.gedbrowser.api.test;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import lombok.extern.slf4j.Slf4j;



/**
 * Provides support for testing security test helper behavior.
 *
 * @author dick
 */
@Slf4j
public final class SecurityTestHelper {

    private SecurityTestHelper() {
        // Empty
    }
    /**
     * Executes reset user file.
     *
     * @param userFile the user file to use
     */
    public static void resetUserFile(final String userFile) {
        log.info("resetting {}", userFile);
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
            log.error("Problem writing user file", e);
        }
    }

}
