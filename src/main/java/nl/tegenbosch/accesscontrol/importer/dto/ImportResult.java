package nl.tegenbosch.accesscontrol.importer.dto;

import nl.tegenbosch.accesscontrol.importer.domain.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Represent the result of the import for all the {@link User}s
 *
 * @author a3aanwisse
 */
public class ImportResult {

    private final List<ImportRecordResult> result = new ArrayList<>();

    private int skippedRecords = 0;

    /**
     * Adds a single import record result to the list with results
     *
     * @param importRecordResult the import record result
     */
    public void add(ImportRecordResult importRecordResult) {
        this.result.add(importRecordResult);
    }

    public List<ImportRecordResult> getResult() {
        return result;
    }

    /**
     * increase the number of skipped records with 1
     */
    public void increaseSkipped() {
        skippedRecords ++;
    }

    public int getSkippedRecords() {
        return skippedRecords;
    }

    public int getTotalNumberOfRecords() {
        return skippedRecords + result.size();
    }

}
