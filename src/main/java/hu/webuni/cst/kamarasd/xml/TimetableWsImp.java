package hu.webuni.cst.kamarasd.xml;

import hu.webuni.cst.kamarasd.api.model.TimetableDto;
import hu.webuni.cst.kamarasd.mapper.TimetableMapper;
import hu.webuni.cst.kamarasd.model.Timetable;
import hu.webuni.cst.kamarasd.service.TimetableService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TimetableWsImp implements TimetableWs {

    private final TimetableMapper timetableMapper;
    private final TimetableService timetableService;

    @Override
    public List<TimetableDto> getTimetableForStudent(Long id, LocalDate from, LocalDate to) {
        try {
            if(id != null) {
                ArrayList<TimetableDto> res = new ArrayList<>();

                Map<LocalDate, List<Timetable>> studentTimetable = timetableService.getStudentTimeTable(id, from, to);

                for (Map.Entry<LocalDate, List<Timetable>> entry : studentTimetable.entrySet()) {
                    LocalDate day = entry.getKey();
                    List<Timetable> timetables = entry.getValue();
                    List<TimetableDto> timetableDtos = timetableMapper.timeTableToDtos(timetables);
                    timetableDtos.forEach(i -> i.setLessonDate(day.toString()));
                    res.addAll(timetableDtos);
                }
                return res;
            } else {
                return null;
            }

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
    }
}
