package hu.webuni.cst.kamarasd.model;

import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Embeddable
@Entity
public class Holiday {

    @Id
    @GeneratedValue
    private int id;
    private LocalDate sourceDay;
    private LocalDate targetDay;
}
