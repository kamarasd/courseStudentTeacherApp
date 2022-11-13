package hu.webuni.cst.kamarasd.mapper;

import hu.webuni.cst.kamarasd.api.model.TimetableDto;
import hu.webuni.cst.kamarasd.model.Timetable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TimetableMapper {

    @Mapping(target = "courseName", source = "courses.name")
    public TimetableDto timetableToDto(Timetable item);

    public List<TimetableDto> timeTableToDtos(List<Timetable> items);



}
