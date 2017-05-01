package nl.tegenbosch.accesscontrol.importer.service;

import nl.tegenbosch.accesscontrol.importer.domain.User;
import nl.tegenbosch.accesscontrol.importer.dto.ImportResult;
import nl.tegenbosch.accesscontrol.importer.reader.UserReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Service responsible for reading and importing {@link User}s
 *
 * @author a3aanwisse
 */
@Component
public class ImportService {

    @Autowired
    private UserReader userReader;

    @Autowired
    private UserImporter userImporter;

    /**
     * Processes the given data and returns the result
     *
     * @param data the data
     * @return the result
     */
    public ImportResult process(byte[] data) {
        List<User> users = userReader.read(data);

        return userImporter.importUsers(users);
    }

}
