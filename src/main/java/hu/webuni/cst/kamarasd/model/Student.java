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
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Entity
public class Student {
	
	@Id
	@GeneratedValue
	@ToString.Include
	@EqualsAndHashCode.Include
	private Long id;
	
	@ToString.Include
	private String name;
	
	private LocalDate birthdate;
	
	private int semester;
	
	@ManyToMany(mappedBy = "students")
	private Set<Course> courses;

}
