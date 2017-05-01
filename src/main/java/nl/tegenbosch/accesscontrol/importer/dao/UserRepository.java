package nl.tegenbosch.accesscontrol.importer.dao;

import nl.tegenbosch.accesscontrol.importer.domain.User;

/**
 * Repository for {@link User}s
 *
 * @author a3aanwisse
 */
public interface UserRepository {

    /**
     * Adds the given user to the repository
     *
     * @param user the user to add
     */
    void addUser(User user);
}
