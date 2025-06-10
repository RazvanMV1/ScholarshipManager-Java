package ro.scholarship.service;

import ro.scholarship.model.Student;
import java.util.List;
import java.util.Optional;

public interface StudentService {
    List<Student> findAll();
    Optional<Student> findById(int id);
    Student save(Student student);
    Student update(int id, Student student);
    void delete(int id);

    boolean isValid(Student student);
}
