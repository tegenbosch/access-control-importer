package nl.tegenbosch.accesscontrol.importer.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Represents a Badge
 *
 * @author a3aanwisse
 */
@Entity
@Getter
@Setter
public class Badge {

    @Id
    private int facility;

    private int badge;

    @Column(name = "AGroup1")
    private int accessGroup1;

    @Column(name = "AGroup2")
    private int accessGroup2;

}
