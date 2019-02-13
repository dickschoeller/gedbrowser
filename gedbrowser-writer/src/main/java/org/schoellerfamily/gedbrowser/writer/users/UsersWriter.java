package org.schoellerfamily.gedbrowser.writer.users;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.datamodel.users.User;
import org.schoellerfamily.gedbrowser.datamodel.users.UserRoleName;
import org.schoellerfamily.gedbrowser.datamodel.users.Users;

/**
 * @author Dick Schoeller
 */
public class UsersWriter {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** */
    private final Users<? extends User> users;

    /** */
    private final String userfilename;

    /**
     * @param users the users to write
     * @param userfilename the file name to write
     */
    public UsersWriter(final Users<? extends User> users,
            final String userfilename) {
        this.users = users;
        this.userfilename = userfilename;
    }

    /**
     * Write the users file.
     */
    public void write() {
        logger.info("writing " + users.size() + " users in " + userfilename);
        try {
            backup(userfilename);
        } catch (IOException e) {
            logger.error("Problem backing up old copy of user file", e);
        }
        try (FileOutputStream fstream = new FileOutputStream(userfilename);
                BufferedOutputStream bstream = new BufferedOutputStream(
                        fstream)) {
            writeTheLines(bstream);
        } catch (IOException e) {
            logger.error("Problem writing user file", e);
        }
    }


    /**
     * Save the existing version of the file by renaming it to something ending
     * with .&lt;number&gt;.
     *
     * @param filename the full path to create
     * @throws IOException if the rename fails
     */
    private void backup(final String filename) throws IOException {
        final File dest = createFile(filename);
        if (dest.exists()) {
            final File backupFile = generateBackupFilename(filename);
            logger.debug("backing up user file from " + filename + " to "
                    + backupFile.getName());
            if (!dest.renameTo(backupFile)) {
                throw new IOException("Could not rename file from "
                        + dest.getName() + " to " + backupFile.getName());
            }
        }
    }

    /**
     * @param filename the name of the file being backedup
     * @return the filename.n that doesn't exist
     */
    private File generateBackupFilename(final String filename) {
        int i = 1;
        while (true) {
            final File backupFile = createFile(filename + "." + i);
            if (!backupFile.exists()) {
                return backupFile;
            }
            i++;
        }
    }

    /**
     * @param filename the name of the file to create
     * @return the file object
     */
    private File createFile(final String filename) {
        return new File(filename);
    }

    /**
     * Loop through the lines from the line creator and write them to the
     * stream.
     *
     * @param stream the stream to write to
     * @throws IOException if there is a problem writing to the stream
     */
    private void writeTheLines(final BufferedOutputStream stream)
            throws IOException {
        for (final User user : users) {
            final String string = createLine(user);
            stream.write(string.getBytes(StandardCharsets.UTF_8));
            stream.write('\n');
        }
    }

    /**
     * @param user the user to create row for
     * @return the row string
     */
    private String createLine(final User user) {
        logger.debug("creating line for " + user.getUsername());
        final StringBuilder builder = new StringBuilder();
        appendUserInfoFields(builder, user);
        appendRoles(builder, user);
        return builder.toString();
    }

    private void appendUserInfoFields(final StringBuilder builder, final User user) {
        append(builder, user.getUsername(), ",");
        append(builder, user.getFirstname(), ",");
        append(builder, user.getLastname(), ",");
        append(builder, user.getEmail(), ",");
        append(builder, user.getPassword(), "");
    }

    /**
     * Append a token and separator to the builder.
     *
     * @param builder the builder
     * @param string the token
     * @param separator the separator
     */
    private void append(final StringBuilder builder, final String string,
            final String separator) {
        if (string != null) {
            builder.append(string);
        }
        builder.append(separator);
    }

    /**
     * Append roles to the builder.
     *
     * @param builder the builder
     * @param user the user whose roles are appended
     */
    private void appendRoles(StringBuilder builder, final User user) {
        for (final UserRoleName role : user.getRoles()) {
            append(builder, ",", role.name());
        }
    }
}
