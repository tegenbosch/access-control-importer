package nl.tegenbosch.accesscontrol.importer.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Represents a User within the Access Control System
 *
 * @author a3aanwisse
 */
@Getter
@Setter
public class User implements Serializable {

    private int badgenumber;

    private String firstname;

    private String middlename;

    private String lastname;

    @Override
    public String toString() {
        return "badgenummer=" + badgenumber +
                ", voornaam='" + firstname + '\'' +
                ", tussenvoegsel='" + middlename + '\'' +
                ", achternaam='" + lastname + '\'';
    }
}
