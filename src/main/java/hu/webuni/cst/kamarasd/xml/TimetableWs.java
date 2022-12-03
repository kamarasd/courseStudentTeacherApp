package hu.webuni.cst.kamarasd.xml;

import hu.webuni.cst.kamarasd.api.model.TimetableDto;

import javax.jws.WebService;
import java.time.LocalDate;
import java.util.List;

@WebService
public interface TimetableWs {
    public List<TimetableDto> getTimetableForStudent(Long id, LocalDate from, LocalDate to);
}
