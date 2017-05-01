package nl.tegenbosch.accesscontrol.importer.service;

import nl.tegenbosch.accesscontrol.importer.dao.UserRepository;
import nl.tegenbosch.accesscontrol.importer.domain.User;
import nl.tegenbosch.accesscontrol.importer.dto.ImportRecordResult;
import nl.tegenbosch.accesscontrol.importer.dto.ImportResult;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Imports the users into the underlaying repository
 *
 * @author a3aanwisse
 */
@Component
public class UserImporterImpl implements UserImporter {

    @Autowired
    private UserRepository personnelDataRepository;

    @Override
    public ImportResult importUsers(List<User> users) {
        ImportResult importResult = new ImportResult();

        users.stream()
                .map(this::importRecord)
                .forEach(importResult::add);

        return importResult;
    }

    private ImportRecordResult importRecord(User user) {
        try {
            personnelDataRepository.addUser(user);
            return new ImportRecordResult(user);
        } catch (Exception e) {
            return new ImportRecordResult(user, ExceptionUtils.getRootCauseMessage(e));
        }
    }

}
