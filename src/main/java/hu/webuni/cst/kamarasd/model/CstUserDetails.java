package hu.webuni.cst.kamarasd.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Audited
@Inheritance(strategy = InheritanceType.JOINED)
@SuperBuilder
public abstract class CstUserDetails {

    public enum UserType {
        TEACHER, STUDENT;
    }

    @Id
    @GeneratedValue
    @ToString.Include
    @EqualsAndHashCode.Include
    private Long id;

    @ToString.Include
    private String name;

    private LocalDate birthday;

    private String username;
    private String password;
    private String facebookId;
    private String googleId;

    public abstract UserType getUserType();
}
