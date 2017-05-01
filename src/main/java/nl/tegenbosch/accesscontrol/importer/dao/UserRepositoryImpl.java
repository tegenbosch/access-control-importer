package nl.tegenbosch.accesscontrol.importer.dao;

import nl.tegenbosch.accesscontrol.importer.domain.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Default implementation of the {@link UserRepository}
 *
 * @author a3aanwisse
 */
@Repository
public class UserRepositoryImpl implements UserRepository {

    private static final Log LOG = LogFactory.getLog(UserRepositoryImpl.class);

    @Value("${user.projectcode}")
    private String projectcode;

    @Value("${user.default.accessgroup1}")
    private int defaultAccessgroup1;

    @Value("${user.default.accessgroup2}")
    private int defaultAccessgroup2;

    @Value("${database.storedprocedure.insertpersonneldata}")
    private String storedProcedureName;

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void addUser(User user) {
        LOG.info(String.format("Aanroep van StoredProcedure voor '%s'", user));

        this.em.createNativeQuery(String.format("EXEC %s :Badge, :Facility, :LastName, :FirstName, :MiddleName, :EMPNO, :AccessGroup1, :AccessGroup2", storedProcedureName))
                .setParameter("Badge", user.getBadgenumber())
                .setParameter("Facility", projectcode)
                .setParameter("FirstName", user.getFirstname())
                .setParameter("LastName", user.getLastname())
                .setParameter("MiddleName", StringUtils.isBlank(user.getMiddlename()) ? StringUtils.EMPTY : user.getMiddlename())
                .setParameter("EMPNO", null)
                .setParameter("AccessGroup1", defaultAccessgroup1)
                .setParameter("AccessGroup2", defaultAccessgroup2)
                .executeUpdate();
        LOG.info(String.format("Executed StoredProcedure successfully for user '%s'", user));

    }
}
