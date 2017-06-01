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

    private Integer badgenumber;

    private String firstname;

    private String middlename;

    private String lastname;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (badgenumber != null) {
            sb.append("badgenummer: ").append(badgenumber).append(", ");
        }
        sb.append(" voornaam: '").append(firstname).append("', ");
        if (middlename != null) {
            sb.append(" tussenvoegsel: '").append(middlename).append("', ");
        }
        sb.append(" achternaam: '").append(lastname).append("'");
        return sb.toString();
    }
}
