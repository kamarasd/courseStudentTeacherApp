package hu.webuni.cst.kamarasd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.webuni.cst.kamarasd.model.Student;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findStudentsByNeptunId(String neptunId);
}
