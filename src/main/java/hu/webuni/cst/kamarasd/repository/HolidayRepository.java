package hu.webuni.cst.kamarasd.repository;

import hu.webuni.cst.kamarasd.model.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface HolidayRepository extends JpaRepository<Holiday, Integer> {

    @Query("SELECT h FROM Holiday h WHERE h.sourceDay BETWEEN :from AND :to OR h.targetDay BETWEEN :from AND :to")
    List<Holiday> findBySourceDayOrTargetDay(LocalDate from, LocalDate to);
}
