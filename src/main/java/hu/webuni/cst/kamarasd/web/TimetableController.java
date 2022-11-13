package hu.webuni.cst.kamarasd.web;

import hu.webuni.cst.kamarasd.api.TimetableControllerApi;
import hu.webuni.cst.kamarasd.api.model.TimetableDto;
import hu.webuni.cst.kamarasd.mapper.TimetableMapper;
import hu.webuni.cst.kamarasd.model.Timetable;
import hu.webuni.cst.kamarasd.service.TimetableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class TimetableController implements TimetableControllerApi {

    private final TimetableService timetableService;
    private final TimetableMapper timetableMapper;

    @Override
    public ResponseEntity<List<TimetableDto>> getTimetableBetween(Long studentId, Long teacherId, LocalDate from, LocalDate to) {
        ArrayList<TimetableDto> result = new ArrayList<>();

        try {
            if(studentId != null) {
                Map<LocalDate, List<Timetable>> studentTimeTable = timetableService.getStudentTimeTable(studentId, from, to);

                for(Map.Entry<LocalDate, List<Timetable>> entry : studentTimeTable.entrySet()) {
                    LocalDate day = entry.getKey();
                    List<Timetable> item = entry.getValue();
                    List<TimetableDto> itemDto = timetableMapper.timeTableToDtos(item);
                    itemDto.forEach(i -> i.setLessonDate(String.valueOf(day)));
                    result.addAll(itemDto);

                }
                return ResponseEntity.ok(result);
            } else if(teacherId != null) {

                Map<LocalDate, List<Timetable>> teacherTimetable = timetableService.getTeacherTimeTable(teacherId, from, to);

                for(Map.Entry<LocalDate, List<Timetable>> entry : teacherTimetable.entrySet()) {
                    LocalDate day = entry.getKey();
                    List<Timetable> item = entry.getValue();
                    List<TimetableDto> itemDto = timetableMapper.timeTableToDtos(item);
                    itemDto.forEach(i -> i.setLessonDate(String.valueOf(day)));
                    result.addAll(itemDto);

                }
                return ResponseEntity.ok(result);
            }
            return ResponseEntity.badRequest().build();


        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<TimetableDto> searchForTimetable(Long studentId, Long teacherId, LocalDate from, String course) {
        Map.Entry<LocalDate, Timetable> foundTimetableEntry = null;

        if(studentId != null) {
            foundTimetableEntry = timetableService.searchStudentTimetable(studentId, from, course);
        } else if (teacherId != null) {
            foundTimetableEntry = timetableService.getTeacherTimeTable(teacherId, from, course);
        }

        if(foundTimetableEntry == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        TimetableDto timetableDto = timetableMapper.timetableToDto(foundTimetableEntry.getValue());
        timetableDto.setLessonDate(String.valueOf(foundTimetableEntry.getKey()));

        return ResponseEntity.ok(timetableDto);
    }
}
