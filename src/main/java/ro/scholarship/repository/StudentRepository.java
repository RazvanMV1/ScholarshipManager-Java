package ro.scholarship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.scholarship.model.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    // poți adăuga metode custom: List<Student> findByNume(String nume);
}
