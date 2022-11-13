package hu.webuni.cst.kamarasd.model;

import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Audited
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Timetable {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;

    private LocalDate lessonDate;

    private LocalTime lessonStart;

    private LocalTime lessonEnd;

    @ManyToOne
    private Course courses;
}
