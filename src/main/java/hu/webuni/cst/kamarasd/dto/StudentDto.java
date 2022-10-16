package hu.webuni.cst.kamarasd.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class StudentDto {
	
	private Long id;
	private String name;
	private LocalDate birthdate;
	private Integer semester;

}
