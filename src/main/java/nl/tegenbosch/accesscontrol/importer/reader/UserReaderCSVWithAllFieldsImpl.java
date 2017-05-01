package nl.tegenbosch.accesscontrol.importer.reader;

import nl.tegenbosch.accesscontrol.importer.domain.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
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
public class UserReaderCSVWithAllFieldsImpl implements UserReader {

    private static final Log LOG = LogFactory.getLog(UserReaderCSVWithAllFieldsImpl.class);

    // voorvoegsel,voorletters,voornaam,tussenvoegsel,achternaam,primair emailadres,overige emailadressen,primair telefoonnummer,overige telefoonnummers,geboortedatum,
    // 1                2       3           4           5           6                   7                       8                   9                       10
    //adressen,huisnummers,huisnummertoevoegingen,postcodes,steden,rollen,clublidnummer,bondsnummer,clublid,bondslid,
    // 11          12          13                  14          15  16      17              18            19      20
    //startdatum lidmaatschap,geslacht,tennis speelsterkte enkel,tennis speelsterkte dubbel,tennis rating enkel,tennis rating dubbel
    // 21                           22         23                      24                         25               26
    private static final CellProcessor[] CELL_PROCESSORS = new CellProcessor[]{
            null,                         // 01 - voorvoegsel
            null,                         // 02 - voorletters
            new NotNull(),                // 03 - voornaam
            new Optional(),               // 04 - tussenvoegsel
            new NotNull(),                // 05 - achternaam
            null,                         // 06 - primair emailadres
            null,                         // 07 - overige emailadressen
            null,                         // 08 - primair telefoonnummer
            null,                         // 09 - overige telefoonnummers
            null,                         // 10 - geboortedatum
            null,                         // 11 - adressen
            null,                         // 12 - huisnummers
            null,                         // 13 - huisnummertoevoegingen
            null,                         // 14 - postcodes
            null,                         // 15 - steden
            null,                         // 16 - rollen
            null,                         // 17 - clublidnummer
            new UniqueHashCode(new ParseInt()),     // 18 - bondsnummer
            null,                         // 19 - clublid
            null,                         // 20 - bondslid
            null,                         // 21 - startdatum lidmaatschap
            null,                         // 22 - geslacht
            null,                         // 23 - tennis speelsterkte enkel
            null,                         // 24 - tennis speelsterkte dubbel
            null,                         // 25 - tennis rating enkel
            null                          // 26 - tennis rating dubbel
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
