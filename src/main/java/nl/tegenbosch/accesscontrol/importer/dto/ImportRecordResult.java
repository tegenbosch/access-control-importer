package nl.tegenbosch.accesscontrol.importer.dto;

import nl.tegenbosch.accesscontrol.importer.domain.User;

/**
 * Represents the import result for a single {@link User}
 *
 * @author a3aanwisse
 */
public class ImportRecordResult {

    private final User user;

    private final boolean succeeded;

    private final String errorText;

    public ImportRecordResult(User user) {
        this.user = user;
        this.succeeded = true;
        this.errorText = null;
    }

    public ImportRecordResult(User user, String errorText) {
        this.user = user;
        this.succeeded = false;
        this.errorText = errorText;
    }

    public String getUserAsString() {
        return user.toString();
    }

    public boolean isSucceeded() {
        return succeeded;
    }

    public String getErrorText() {
        return errorText;
    }
}
