package hu.webuni.cst.kamarasd.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.envers.Audited;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Entity
@NamedEntityGraph(name = "Course.students", attributeNodes = @NamedAttributeNode("students"))
@NamedEntityGraph(name = "Course.teachers", attributeNodes = @NamedAttributeNode("teachers"))
@Audited
public class Course {

	@Id
	@GeneratedValue
	@EqualsAndHashCode.Include()
	private Long id;
	
	@ToString.Include
	private String name;
	
	@ManyToMany
	private Set<Student> students;
	
	@ManyToMany
	private Set<Teacher> teachers;

	@OneToMany(mappedBy = "courses")
	private Set<Timetable> timetables;

	private Semester semester;

	public void addTimetable(Timetable timetable) {
		timetable.setCourses(this);
		if(Objects.isNull(this.timetables)) {
			this.timetables = new HashSet<>();
		}
		this.timetables.add(timetable);
	}

}
