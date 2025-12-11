package org.schoellerfamily.gedbrowser.api.test;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import lombok.extern.slf4j.Slf4j;

/**
 * @author dick
 */
@Slf4j
public final class SecurityTestHelper {

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
