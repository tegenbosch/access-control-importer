package nl.tegenbosch.accesscontrol.importer.dao;

import nl.tegenbosch.accesscontrol.importer.domain.Badge;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for {@link Badge}s
 *
 * @author a3aanwisse
 */
public interface BadgeRepository extends JpaRepository<Badge, Long> {

    /**
     * Find a badge by its facility code and its badge number
     *
     * @param facility the facility code
     * @param badge    the badge number
     * @return the found Badge or null
     */
    Badge findByFacilityAndBadge(int facility, int badge);

}
