package nl.tegenbosch.accesscontrol.importer.reader;

import nl.tegenbosch.accesscontrol.importer.domain.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Base class for reading Users from a CSV file.
 *
 * @author Adriaan Wisse
 */
public abstract class AbstractUserReaderCSVImpl implements UserReader {

    private static final Log LOG = LogFactory.getLog(AbstractUserReaderCSVImpl.class);

    private final Map<String, String> fieldsToMap = new HashMap<>(4);

    @Value("${mapping.columnname.firstname}")
    private String firstnameColumnName;

    @Value("${mapping.columnname.middlename}")
    private String middlenameColumnName;

    @Value("${mapping.columnname.lastname}")
    private String lastnameColumnName;

    @Value("${mapping.columnname.badgenumber}")
    private String badgenumberColumnName;

    @PostConstruct
    public void init() {
        fieldsToMap.put(firstnameColumnName, "firstname");
        fieldsToMap.put(middlenameColumnName, "middlename");
        fieldsToMap.put(lastnameColumnName, "lastname");
        fieldsToMap.put(badgenumberColumnName, "badgenumber");
    }

    @Override
    public List<User> read(byte[] csvData) {
        LOG.info("Reading the CSV file");
        List<User> result = new ArrayList<>();

        try (ICsvBeanReader beanReader = new CsvBeanReader(new InputStreamReader(new ByteArrayInputStream(csvData)), CsvPreference.STANDARD_PREFERENCE)) {

            final String[] header = beanReader.getHeader(true);

            Arrays.setAll(header, i -> fieldsToMap.getOrDefault(header[i], null));

            User user;

            while ((user = beanReader.read(User.class, header, getCellProcessors())) != null) {
                result.add(user);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        LOG.info(String.format("CSV file has %d lines", result.size()));
        return result;
    }

    abstract CellProcessor[] getCellProcessors();
}
