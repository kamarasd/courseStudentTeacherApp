package hu.webuni.cst.kamarasd.web;

import hu.webuni.cst.kamarasd.api.model.TimetableDto;
import hu.webuni.cst.kamarasd.mapper.TimetableMapper;
import hu.webuni.cst.kamarasd.repository.TimetableRepository;
import hu.webuni.cst.kamarasd.service.TimetableService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/timetable")
public class TimetableController {

    private final TimetableService timetableService;
    private final TimetableRepository timetableRepository;
    private final TimetableMapper timetableMapper;

    @GetMapping
    public List<TimetableDto> getAllTimetables() {
        return null;
    }


}
