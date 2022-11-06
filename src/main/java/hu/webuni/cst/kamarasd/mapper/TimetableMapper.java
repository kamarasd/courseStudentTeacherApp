package hu.webuni.cst.kamarasd.mapper;

import hu.webuni.cst.kamarasd.api.model.TimetableDto;
import hu.webuni.cst.kamarasd.model.Timetable;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TimetableMapper {

    TimetableDto timetableToDto(Timetable timetable);
}
