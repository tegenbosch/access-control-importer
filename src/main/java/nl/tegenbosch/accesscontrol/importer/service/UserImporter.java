package nl.tegenbosch.accesscontrol.importer.service;

import nl.tegenbosch.accesscontrol.importer.domain.User;
import nl.tegenbosch.accesscontrol.importer.dto.ImportResult;

import java.util.List;

/**
 * Responsible for importing a list of users into the Access Control System
 *
 * @author a3aanwisse
 */
public interface UserImporter {

    /**
     * Imports the given user collection.
     *
     * @param users the collection with users
     * @return the import result
     */
    ImportResult importUsers(List<User> users);

}
