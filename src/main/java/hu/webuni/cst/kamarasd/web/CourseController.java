package hu.webuni.cst.kamarasd.web;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.types.Predicate;

import hu.webuni.cst.kamarasd.dto.CourseDto;
import hu.webuni.cst.kamarasd.mapper.CourseMapper;
import hu.webuni.cst.kamarasd.model.Course;
import hu.webuni.cst.kamarasd.model.HistoryData;
import hu.webuni.cst.kamarasd.repository.CourseRepository;
import hu.webuni.cst.kamarasd.service.CourseService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/courses")
public class CourseController {
	
	private final CourseRepository courseRepository;
	private final CourseMapper courseMapper;
	private final CourseService courseService;
	
	@PostMapping
	public CourseDto createNewCourse(@RequestBody @Valid CourseDto courseDto) {
		Course course = courseRepository.save(courseMapper.dtoToCourse(courseDto));
		return courseMapper.courseToDto(course); 
	}
	
	@GetMapping("/search")
	public List<CourseDto> searchCourses(@QuerydslPredicate(root = Course.class) Predicate predicate, 
										@RequestParam Optional<Boolean> all,
										@SortDefault("id") Pageable pageable){
		
		boolean fullSearch = all.isEmpty() || !all.get();
		
		Iterable<Course> courseResult = fullSearch ? 
				courseRepository.findAll(predicate, pageable) : courseService.findCourse(predicate, pageable);
		
		return fullSearch ? 
				courseMapper.coursesSummariesToDtos(courseResult) : courseMapper.coursesToDtos(courseResult);
	}
	
	@GetMapping("/{id}/getHistory") 
	public List<HistoryData<CourseDto>> getCourseHistory(@PathVariable long id){
		List<HistoryData<Course>> courses = courseService.getCourseHistory(id);
		List<HistoryData<CourseDto>> historyCourses = new ArrayList<>();
		
		courses.forEach(historyData -> {
			historyCourses.add(new HistoryData<>(courseMapper.courseSummaryToDto(historyData.getData()),
					historyData.getRevType(),
					historyData.getRevision(),
					historyData.getRevDate()
				));
		});

		return historyCourses;
	}
	
	@GetMapping("/history") 
	public void getThisHistory(Long id, @NotNull @Valid OffsetDateTime date) {
		courseService.getThisHistory(id, date);
	}
	
}
