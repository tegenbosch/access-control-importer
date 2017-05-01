package nl.tegenbosch.accesscontrol.importer.reader;

import nl.tegenbosch.accesscontrol.importer.domain.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.constraint.UniqueHashCode;
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
 * Implementation which uses the {@link CsvBeanReader} to read data from a CSV file.
 *
 * @author a3aanwisse
 */
@Component
public class UserReaderCSVImpl implements UserReader {

    private static final Log LOG = LogFactory.getLog(UserReaderCSVImpl.class);

    private static final CellProcessor[] CELL_PROCESSORS = new CellProcessor[]{
            new NotNull(),                      // 01 - voornaam
            new Optional(),                     // 02 - tussenvoegsel
            new NotNull(),                      // 03 - achternaam
            new UniqueHashCode(new ParseInt())  // 04 - bondsnummer
    };

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


            while ((user = beanReader.read(User.class, header, CELL_PROCESSORS)) != null) {
                result.add(user);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        LOG.info(String.format("CSV file has %d lines", result.size()));
        return result;
    }

}
