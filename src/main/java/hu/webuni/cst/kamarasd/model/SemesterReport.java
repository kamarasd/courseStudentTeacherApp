package hu.webuni.cst.kamarasd.model;

import java.util.Date;

import org.hibernate.envers.RevisionType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SemesterReport {
	
	public Long courseId;
	public String courseName;
	public double avgSemStudents;
}
