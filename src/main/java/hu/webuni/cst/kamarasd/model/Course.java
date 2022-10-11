package hu.webuni.cst.kamarasd.model;

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
public class Course {

	@Id
	@GeneratedValue
	@EqualsAndHashCode.Include()
	private Long id;
	
	private String name;
	
	@ManyToMany
	private Teacher teacher;
	
	@ManyToMany
	private Student student;
}
