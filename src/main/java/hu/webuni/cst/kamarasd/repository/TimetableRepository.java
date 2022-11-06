package hu.webuni.cst.kamarasd.repository;

import hu.webuni.cst.kamarasd.model.Timetable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimetableRepository extends JpaRepository<Timetable, Long> {
}
