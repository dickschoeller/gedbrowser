package org.schoellerfamily.gedbrowser.writer.users;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import lombok.extern.slf4j.Slf4j;
import org.schoellerfamily.gedbrowser.datamodel.users.User;
import org.schoellerfamily.gedbrowser.datamodel.users.UserRoleName;
import org.schoellerfamily.gedbrowser.datamodel.users.Users;
import org.schoellerfamily.gedbrowser.writer.util.Backup;



/**
 * Writes users data to an external destination.
 *
 * @author Richard Schoeller
 */
@Slf4j
public class UsersWriter {

    /** */
    private final Users<? extends User> users;

    /** */
    private final String userfilename;

    /**
     * Creates a new UsersWriter.
     *
     * @param users the users
     * @param userfilename the userfilename to use
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
        log.info("writing {} users in {}", users.size(), userfilename);
        try {
            Backup.backup(userfilename);
        } catch (IOException e) {
            log.error("Problem backing up old copy of user file", e);
        }
        try (FileOutputStream fstream = new FileOutputStream(userfilename);
                BufferedOutputStream bstream = new BufferedOutputStream(
                        fstream)) {
            writeTheLines(bstream);
        } catch (IOException e) {
            log.error("Problem writing user file", e);
        }
    }

    private void writeTheLines(final BufferedOutputStream stream)
            throws IOException {
        for (final User user : users) {
            final String string = createLine(user);
            stream.write(string.getBytes(StandardCharsets.UTF_8));
            stream.write('\n');
        }
    }

    private String createLine(final User user) {
        log.debug("creating line for {}", user.getUsername());
        final StringBuilder builder = new StringBuilder();
        appendUserInfoFields(builder, user);
        appendRoles(builder, user);
        return builder.toString();
    }

    private void appendUserInfoFields(final StringBuilder builder,
            final User user) {
        append(builder, user.getUsername(), ",");
        append(builder, user.getFirstname(), ",");
        append(builder, user.getLastname(), ",");
        append(builder, user.getEmail(), ",");
        append(builder, user.getPassword(), "");
    }

    private void append(final StringBuilder builder, final String string,
            final String separator) {
        if (string != null) {
            builder.append(string);
        }
        builder.append(separator);
    }

    private void appendRoles(final StringBuilder builder, final User user) {
        for (final UserRoleName role : user.getRoles()) {
            append(builder, ",", role.name());
        }
    }
}
