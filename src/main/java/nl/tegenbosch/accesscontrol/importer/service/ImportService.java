package nl.tegenbosch.accesscontrol.importer.service;

import nl.tegenbosch.accesscontrol.importer.converter.XlsxToCsvConverter;
import nl.tegenbosch.accesscontrol.importer.domain.User;
import nl.tegenbosch.accesscontrol.importer.dto.ImportResult;
import nl.tegenbosch.accesscontrol.importer.reader.UserReader;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Service responsible for reading and importing {@link User}s
 *
 * @author a3aanwisse
 */
@Component
public class ImportService {

    private final UserReader userReader;

    private final UserImporter userImporter;

    private final XlsxToCsvConverter converter;

    public ImportService(UserReader userReader, UserImporter userImporter, XlsxToCsvConverter converter) {
        this.userReader = userReader;
        this.userImporter = userImporter;
        this.converter = converter;
    }

    /**
     * Processes the given data and returns the result
     *
     * @param excelData the excel data
     * @return the result
     */
    public ImportResult processExcel(byte[] excelData) {
        byte[] csvData = converter.convert(excelData);

        return processCSV(csvData);
    }

    /**
     * Processes the given data and returns the result
     *
     * @param csvData the csv data
     * @return the result
     */
    public ImportResult processCSV(byte[] csvData) {
        List<User> users = userReader.read(csvData);

        return userImporter.importUsers(users);
    }

}
