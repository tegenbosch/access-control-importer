package nl.tegenbosch.accesscontrol.importer.reader;

import nl.tegenbosch.accesscontrol.importer.domain.User;

import java.util.List;

/**
 * Responsible for reading data and converting it to {@link User}s
 *
 * @author a3aanwisse
 */
public interface UserReader {

    /**
     * Reads the data from the given byte array and transfers it to a list of {@link User}
     *
     * @param data the data
     * @return a list of personnel data
     */
    List<User> read(byte[] data);

}
