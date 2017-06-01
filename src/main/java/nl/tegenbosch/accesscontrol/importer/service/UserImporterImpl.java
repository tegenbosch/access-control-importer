package nl.tegenbosch.accesscontrol.importer.service;

import nl.tegenbosch.accesscontrol.importer.dao.BadgeRepository;
import nl.tegenbosch.accesscontrol.importer.dao.UserRepository;
import nl.tegenbosch.accesscontrol.importer.domain.Badge;
import nl.tegenbosch.accesscontrol.importer.domain.User;
import nl.tegenbosch.accesscontrol.importer.dto.ImportRecordResult;
import nl.tegenbosch.accesscontrol.importer.dto.ImportResult;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Imports the users into the underlaying repository
 *
 * @author a3aanwisse
 */
@Component
public class UserImporterImpl implements UserImporter {

    private final UserRepository personnelDataRepository;

    private final BadgeRepository badgeRepository;

    @Value("${user.projectcode}")
    private int projectCode;

    public UserImporterImpl(UserRepository personnelDataRepository, BadgeRepository badgeRepository) {
        this.personnelDataRepository = personnelDataRepository;
        this.badgeRepository = badgeRepository;
    }

    @Override
    public ImportResult importUsers(List<User> users) {
        ImportResult importResult = new ImportResult();

        users.stream()
                .filter(user -> noBadgenumber(user, importResult))
                .filter(user -> notExisting(user, importResult))
                .map(this::importRecord)
                .forEach(importResult::add);

        return importResult;
    }

    private boolean notExisting(User user, ImportResult importResult) {
        Badge badge = badgeRepository.findByFacilityAndBadge(projectCode, user.getBadgenumber());
        if (badge != null) {
            importResult.increaseSkipped();
        }
        return badge == null;
    }

    private boolean noBadgenumber(User user, ImportResult importResult) {
        if (user.getBadgenumber() == null) {
            importResult.addUserWithoutBondsnummer(user);
        }
        return user.getBadgenumber() != null;
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
