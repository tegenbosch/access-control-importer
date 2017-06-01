package nl.tegenbosch.accesscontrol.importer.converter;

import au.com.bytecode.opencsv.CSVWriter;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Adriaan Wisse
 */
@Component
public class XlsxToCsvConverter {

    private String[] CELLS_TO_CONVERT = new String[]{"voornaam", "tussenvoegsel", "achternaam", "bondsnummer"};

    public byte[] convert(byte[] xlsxData) {
        try (InputStream inputStream = new ByteArrayInputStream(xlsxData);
             Writer writer = new StringWriter();
             CSVWriter csvWriter = new CSVWriter(writer)) {

            XSSFWorkbook xlsWorkbook = new XSSFWorkbook(inputStream);
            XSSFSheet worksheet = xlsWorkbook.getSheetAt(0);

            Map<String, Integer> headerMapping = getHeaderMapping(worksheet);

            for (Row row : worksheet) {
                int columnNumber = 0;
                String[] csvdata = new String[CELLS_TO_CONVERT.length];

                for (Map.Entry<String, Integer> headerEntry : headerMapping.entrySet()) {
                    Cell cell = row.getCell(headerEntry.getValue());
                    String value;
                    if (cell != null) {
                        switch (cell.getCellTypeEnum()) {
                            case NUMERIC:
                                value = new BigDecimal(cell.getNumericCellValue()).toPlainString();
                                break;
                            case STRING:
                                value = cell.getStringCellValue();
                                break;
                            default:
                                value = null;
                        }
                    } else {
                        value = null;
                    }
                    csvdata[columnNumber] = value;
                    columnNumber++;
                }
                if (hasData(csvdata)) {
                    csvWriter.writeNext(csvdata);
                }
            }
            return writer.toString().getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean hasData(String[] csvdata) {
        return Arrays.stream(csvdata).anyMatch(Objects::nonNull);
    }

    private Map<String, Integer> getHeaderMapping(XSSFSheet worksheet) {
        Map<String, Integer> headerMapping = new LinkedHashMap<>(CELLS_TO_CONVERT.length);

        XSSFRow headerRow = worksheet.getRow(0);
        Iterator<Cell> headerRowCellIterator = headerRow.cellIterator();
        int columnNumber = 0;
        while (headerRowCellIterator.hasNext()) {
            Cell headerCell = headerRowCellIterator.next();
            if (ArrayUtils.contains(CELLS_TO_CONVERT, headerCell.getStringCellValue())) {
                headerMapping.put(headerCell.getStringCellValue(), columnNumber);
            }
            columnNumber++;
        }
        return headerMapping;
    }
}
