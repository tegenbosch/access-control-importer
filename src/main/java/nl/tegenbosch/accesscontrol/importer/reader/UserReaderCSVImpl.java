package nl.tegenbosch.accesscontrol.importer.reader;

import org.springframework.stereotype.Component;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.constraint.UniqueHashCode;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;

/**
 * Implementation which uses the {@link CsvBeanReader} to read data from a CSV file.
 *
 * @author a3aanwisse
 */
@Component
public class UserReaderCSVImpl extends AbstractUserReaderCSVImpl {

    @Override
    CellProcessor[] getCellProcessors() {
        return new CellProcessor[]{
                new NotNull(),                                    // 01 - voornaam
                new Optional(),                                   // 02 - tussenvoegsel
                new NotNull(),                                    // 03 - achternaam
                new Optional(new UniqueHashCode(new ParseInt()))  // 04 - bondsnummer
        };
    }

}
