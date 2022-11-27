package hu.webuni.cst.kamarasd.model;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.experimental.SuperBuilder;
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
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Audited
@SuperBuilder
public class Student extends CstUserDetails {
	
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

	@Override
	public UserType getUserType() {
		return UserType.STUDENT;
	}
}
