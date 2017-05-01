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

    /**
     * Adds a single import record result to the list with results
     *
     * @param importRecordResult
     */
    public void add(ImportRecordResult importRecordResult) {
        this.result.add(importRecordResult);
    }

    public List<ImportRecordResult> getResult() {
        return result;
    }

}
