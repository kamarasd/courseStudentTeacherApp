package hu.webuni.cst.kamarasd.model;

import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Embeddable
public class Semester {

    public enum SemesterType {
        SPRING,FALL
    }

    @EqualsAndHashCode.Include
    private int year;

    @EqualsAndHashCode.Include
    private SemesterType semesterType;

    @Transient
    private LocalDate semesterStart;

    @Transient
    private LocalDate semesterEnd;

    public Semester (int year, SemesterType semesterType) {
        super();
        this.year = year;
        this.semesterType = semesterType;
    }

    public static Semester fromMidSemesterDay(LocalDate midSemesterDay) {
        Semester semester = new Semester();
        semester.year = midSemesterDay.getYear();
        int monthNumber = midSemesterDay.getMonth().getValue();

        if(monthNumber >= 2 && monthNumber <= 5) {
            semester.semesterType = SemesterType.SPRING;
        } else if (monthNumber >= 9 && monthNumber <= 12) {
            semester.semesterType = SemesterType.FALL;
        } else {
            throw new IllegalArgumentException("Timetable date is not fit any semester");
        }

        semester.semesterStart = getSemesterStart(semester.year, semester.semesterType);
        semester.semesterEnd = getSemesterEnd(semester.semesterStart);
        return semester;
    }

    private static LocalDate getSemesterStart(int year, SemesterType semester) {
        LocalDate localDate = LocalDate.of(year, semester == SemesterType.FALL ? 8 : 1,31);
        return localDate = localDate.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
    }

    private static LocalDate getSemesterEnd(LocalDate semesterStart) {
        return semesterStart.plusWeeks(14).minusDays(1);
    }
}
