package hu.webuni.cst.kamarasd.model;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

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
public class Student {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	
	private LocalDate birthdate;
	
	private Integer semester;
	
	@ManyToMany(mappedBy = "students")
	private Set<Course> courses;

}
