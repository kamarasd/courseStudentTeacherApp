package hu.webuni.cst.kamarasd.repository;

import hu.webuni.cst.kamarasd.model.Semester;
import hu.webuni.cst.kamarasd.model.Timetable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TimetableRepository extends JpaRepository<Timetable, Long> {

    @Query("SELECT t FROM Timetable t "
            + "WHERE t.courses IN ("
            + "SELECT c FROM Course c JOIN c.students s WHERE s.id = :studentId AND c.semester.year = :year AND c.semester.semesterType = :semesterType)")
    public List<Timetable> findByStudentAndSemester(long studentId, int year, Semester.SemesterType semesterType);

    @Query("SELECT t FROM Timetable t WHERE t.courses IN (SELECT c FROM Course c JOIN c.teachers teach WHERE teach.id = :teacherId AND c.semester.year = :year AND c.semester.semesterType = :semesterType)")
    public List<Timetable> findByTeacherAndSemester(long teacherId, int year, Semester.SemesterType semesterType);
}