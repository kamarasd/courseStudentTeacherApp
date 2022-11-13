package hu.webuni.cst.kamarasd.web;

import java.lang.module.ResolutionException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import com.querydsl.core.types.Predicate;

import hu.webuni.cst.kamarasd.api.CourseControllerApi;
import hu.webuni.cst.kamarasd.api.model.CourseDto;
import hu.webuni.cst.kamarasd.api.model.HistoryDataCourseDto;
import hu.webuni.cst.kamarasd.mapper.CourseMapper;
import hu.webuni.cst.kamarasd.model.Course;
import hu.webuni.cst.kamarasd.repository.CourseRepository;
import hu.webuni.cst.kamarasd.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
public class CourseController implements CourseControllerApi {
	
	private final CourseRepository courseRepository;
	private final CourseMapper courseMapper;
	private final CourseService courseService;
	private final NativeWebRequest nativeWebRequest;
	private final ResolverHelper resolverHelper;
	private final SimpMessagingTemplate simpMessagingTemplate;
	
	@Override
	public Optional<NativeWebRequest> getRequest() {
		return Optional.of(nativeWebRequest);
	}

	@Override
	public ResponseEntity<CourseDto> createNewCourse(@Valid CourseDto courseDto) {
		Course course = courseRepository.save(courseMapper.dtoToCourse(courseDto));
		return ResponseEntity.ok(courseMapper.courseToDto(course)); 
	}

	@Override
	public ResponseEntity<List<HistoryDataCourseDto>> getCourseHistory(Long id) {
		return ResponseEntity.ok(courseMapper.coursesHistoryToHistoryDataCourseDtos(courseService.getCourseHistory(id)));
	}
	
	public void configPageable(@SortDefault("id") Pageable pageable) {}
	
	public void configurePredicate(@QuerydslPredicate(root = Course.class) Predicate predicate) {}

	@Override
	public ResponseEntity<List<CourseDto>> searchCourses(@Valid Boolean all, @Valid Integer page, @Valid Integer size,
			@Valid List<String> sort) {
		boolean fullSearch = all == null ? false : all;
		
		Pageable pageable = resolverHelper.createPageable(this.getClass(), "configPageable", nativeWebRequest);
		
		Predicate predicate = resolverHelper.createPredicate(this.getClass(), "configurePredicate", nativeWebRequest);
		
		Iterable<Course> courseResult = fullSearch ? 
				courseRepository.findAll(predicate, pageable) : courseService.findCourse(predicate, pageable);

		return fullSearch ? 
				ResponseEntity.ok(courseMapper.coursesSummariesToDtos(courseResult)) : 
				ResponseEntity.ok(courseMapper.coursesToDtos(courseResult));
	}

	@Override
	public ResponseEntity<CourseDto> getThisHistory(@NotNull @Valid Long id, @NotNull @Valid OffsetDateTime date) {
		return ResponseEntity.ok(courseMapper.courseToDto(courseService.getThisHistory(id, date)));
	}

	@Override
	public ResponseEntity<Void> cancelCourse(Long id, String day) {
		Course course = courseRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		simpMessagingTemplate.convertAndSend("/topic/courseChat/" + course.getId(), String.format("The %s course has been cancelled on: %s", course.getName(), day));

		return ResponseEntity.ok().build();
	}

}
