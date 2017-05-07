package nl.tegenbosch.accesscontrol.importer.reader;

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
public class UserReaderCSVWithAllFieldsImpl extends AbstractUserReaderCSVImpl {

    @Override
    CellProcessor[] getCellProcessors() {
        return new CellProcessor[]{
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
    }

}
