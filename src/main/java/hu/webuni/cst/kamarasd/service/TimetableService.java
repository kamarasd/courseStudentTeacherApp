package hu.webuni.cst.kamarasd.service;

import hu.webuni.cst.kamarasd.model.Holiday;
import hu.webuni.cst.kamarasd.model.Semester;
import hu.webuni.cst.kamarasd.model.Timetable;
import hu.webuni.cst.kamarasd.repository.HolidayRepository;
import hu.webuni.cst.kamarasd.repository.StudentRepository;
import hu.webuni.cst.kamarasd.repository.TeacherRepository;
import hu.webuni.cst.kamarasd.repository.TimetableRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TimetableService {

    private final TimetableRepository timetableRepository;
    private final StudentRepository studentRepository;
    private final HolidayRepository holidayRepository;
    private final TeacherRepository teacherRepository;

    @Lazy
    @Autowired
    private TimetableService self;

    @Cacheable("studentTimeTableResult")
    public Map<LocalDate, List<Timetable>> getStudentTimeTable(long studentId, LocalDate from, LocalDate to) {
        Map<LocalDate, List<Timetable>> timetable = new LinkedHashMap<>();

        Semester semester = Semester.fromMidSemesterDay(from);
        Semester semesterTo = Semester.fromMidSemesterDay(to);
        System.out.println(studentId);
        if(!semester.equals(semesterTo)) {
            throw new IllegalArgumentException("from and to should be in the same semester");
        }

        if(!studentRepository.existsById(studentId)) {
            throw new IllegalArgumentException("No student found");
        }
        List<Timetable> actualTimetable = timetableRepository.findByStudentAndSemester(studentId, semester.getYear(), semester.getSemesterType());

        Map<LocalDate, List<Timetable>> timeTableDays = actualTimetable.stream().collect(Collectors.groupingBy(Timetable::getLessonDate));

        List<Holiday> isHolidayAffected = holidayRepository.findBySourceDayOrTargetDay(from ,to);
        Map<LocalDate, List<Holiday>> sourceDay = isHolidayAffected.stream()
                .collect(Collectors.groupingBy(Holiday::getSourceDay));
        Map<LocalDate, List<Holiday>> targetDay = isHolidayAffected.stream()
                .collect(Collectors.groupingBy(Holiday::getTargetDay));

        for(LocalDate day = from; !day.isAfter(to); day = day.plusDays(1)) {
            List<Timetable> onDay = new ArrayList<>();
            int dayOfWeek = day.getDayOfWeek().getValue();
            List<Timetable> normalDay = timeTableDays.get(dayOfWeek);

            if(normalDay != null && dayIsNotAWeekend(sourceDay, day)) {
                onDay.addAll(normalDay);
            }

            Integer aDayMovedToToday = getMovedDay(targetDay, day);

            if(aDayMovedToToday != null) {
                onDay.addAll(timeTableDays.get(aDayMovedToToday));
            }

            onDay.sort(Comparator.comparing(Timetable::getLessonStart));

            timetable.put(day, onDay);


        }
        return timetable;
    }

    public Integer getMovedDay(Map<LocalDate, List<Holiday>> sourceDay, LocalDate day) {
        List<Holiday> movedToThisDay = sourceDay.get(day);
        if(movedToThisDay == null || movedToThisDay.isEmpty()) {
            return null;
        }

        return movedToThisDay.get(0).getSourceDay().getDayOfWeek().getValue();
    }

    public boolean dayIsNotAWeekend(Map<LocalDate, List<Holiday>> targetDay, LocalDate day) {
        List<Holiday> specialDaysOnDay = targetDay.get(day);
        return Objects.isNull(specialDaysOnDay) || specialDaysOnDay.isEmpty();
    }

    public Map.Entry<LocalDate, Timetable> searchStudentTimetable(long studentId, LocalDate from, String courseName) {
        Map.Entry<LocalDate, Timetable> studentTimeTable = null;

        Map<LocalDate, List<Timetable>> timeTableForStudent = self.getStudentTimeTable(studentId, from, Semester.fromMidSemesterDay(from).getSemesterEnd());

        for(Map.Entry<LocalDate, List<Timetable>> entry : timeTableForStudent.entrySet()) {
            LocalDate day = entry.getKey();
            List<Timetable> onDay = entry.getValue();
            for(Timetable tt : onDay) {
                if(tt.getCourses().getName().toLowerCase().startsWith(courseName.toLowerCase())) {
                    studentTimeTable = Map.entry(day, tt);
                    break;
                }

            }
            if(Objects.nonNull(studentTimeTable)) {
                break;
            }
        }
        return studentTimeTable;
    }

    public Map.Entry<LocalDate, Timetable> getTeacherTimeTable(long teacherId, LocalDate from, String courseName) {
        Map.Entry<LocalDate, Timetable> teacherTimeTable = null;

        Map<LocalDate, List<Timetable>> timeTableForTeacher = self.getTeacherTimeTable(teacherId, from, Semester.fromMidSemesterDay(from).getSemesterEnd());

        for(Map.Entry<LocalDate, List<Timetable>> entry : timeTableForTeacher.entrySet()) {
            LocalDate day = entry.getKey();
            List<Timetable> onDay = entry.getValue();
            for(Timetable tt : onDay) {
                if(tt.getCourses().getName().toLowerCase().startsWith(courseName.toLowerCase())) {
                    teacherTimeTable = Map.entry(day, tt);
                    break;
                }

            }
            if(Objects.nonNull(teacherTimeTable)) {
                break;
            }
        }
        return teacherTimeTable;
    }

    public Map<LocalDate, List<Timetable>> getTeacherTimeTable(long teacherId, LocalDate from, LocalDate to) {
        Map<LocalDate, List<Timetable>> timetable = new LinkedHashMap<>();

        Semester semester = Semester.fromMidSemesterDay(from);
        Semester semesterTo = Semester.fromMidSemesterDay(to);

        if(!semester.equals(semesterTo)) {
            throw new IllegalArgumentException("from and to should be in the same semester");
        }

        if(!teacherRepository.existsById(teacherId)) {
            throw new IllegalArgumentException("No teacher found");
        }
        List<Timetable> actualTimetable = timetableRepository.findByTeacherAndSemester(teacherId, semester.getYear(), semester.getSemesterType());

        Map<LocalDate, List<Timetable>> timeTableDays = actualTimetable.stream().collect(Collectors.groupingBy(Timetable::getLessonDate));

        List<Holiday> isHolidayAffected = holidayRepository.findBySourceDayOrTargetDay(from ,to);
        Map<LocalDate, List<Holiday>> sourceDay = isHolidayAffected.stream()
                .collect(Collectors.groupingBy(Holiday::getSourceDay));
        Map<LocalDate, List<Holiday>> targetDay = isHolidayAffected.stream()
                .collect(Collectors.groupingBy(Holiday::getTargetDay));

        for(LocalDate day = from; !day.isAfter(to); day = day.plusDays(1)) {
            List<Timetable> onDay = new ArrayList<>();
            int dayOfWeek = day.getDayOfWeek().getValue();
            List<Timetable> normalDay = timeTableDays.get(dayOfWeek);

            if(normalDay != null && dayIsNotAWeekend(sourceDay, day)) {
                onDay.addAll(normalDay);
            }

            Integer aDayMovedToToday = getMovedDay(targetDay, day);

            if(aDayMovedToToday != null) {
                onDay.addAll(timeTableDays.get(aDayMovedToToday));
            }

            onDay.sort(Comparator.comparing(Timetable::getLessonStart));

            timetable.put(day, onDay);


        }
        System.out.println(timetable);
        return timetable;
    }
}
