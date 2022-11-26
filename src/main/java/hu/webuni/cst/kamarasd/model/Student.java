package hu.webuni.cst.kamarasd.model;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

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
@Audited
public class Student {
	
	@Id
	@GeneratedValue
	@ToString.Include
	@EqualsAndHashCode.Include
	private Long id;
	
	@ToString.Include
	private String name;
	
	private LocalDate birthdate;
	
	private Integer semester;

	@ToString.Include
	@EqualsAndHashCode.Include
	private Integer balance;
	
	@ManyToMany(mappedBy = "students")
	private Set<Course> courses;
	
	@ToString.Include
	private String neptunId;
	
	private Integer freeSemesters;
	

}
