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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Audited
public class Teacher extends CstUserDetails{
	
	@Id
	@GeneratedValue
	@EqualsAndHashCode.Include
	private Long id;
	
	private String name;
	
	private LocalDate birthdate;
	
	@ManyToMany(mappedBy = "teachers")
	private Set<Course> courses;


	@Override
	public UserType getUserType() {
		return UserType.TEACHER;
	}
}
