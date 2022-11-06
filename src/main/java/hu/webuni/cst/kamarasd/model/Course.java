package hu.webuni.cst.kamarasd.model;

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
@NamedEntityGraph(name = "Course.timetables", attributeNodes = @NamedAttributeNode("timetables"))
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

	@ManyToMany
	private Set<Timetable> timetables;

}
