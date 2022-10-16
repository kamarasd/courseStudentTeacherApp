package hu.webuni.cst.kamarasd.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@NamedEntityGraph(name = "Course.students", attributeNodes = @NamedAttributeNode("students"))
@NamedEntityGraph(name = "Course.teachers", attributeNodes = @NamedAttributeNode("teachers"))
public class Course {

	@Id
	@GeneratedValue
	@EqualsAndHashCode.Include()
	private Long id;
	
	private String name;
	
	@ManyToMany
	private Set<Student> students;
	
	@ManyToMany
	private Set<Teacher> teachers;
}
