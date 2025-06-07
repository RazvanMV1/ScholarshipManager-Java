package ro.scholarship.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.scholarship.model.Student;
import ro.scholarship.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public Optional<Student> findById(int id) {
        return studentRepository.findById(id);
    }

    @Override
    public Student save(Student student) {
        if (!isValid(student)) {
            throw new IllegalArgumentException("Student invalid! (ex: media sau CNP incorecte)");
        }
        return studentRepository.save(student);
    }

    @Override
    public Student update(int id, Student student) {
        student.setId(id);
        if (!isValid(student)) {
            throw new IllegalArgumentException("Student invalid!");
        }
        return studentRepository.save(student);
    }

    @Override
    public void delete(int id) {
        studentRepository.deleteById(id);
    }

    @Override
    public boolean isValid(Student student) {
        // Exemplu de validare (poți adăuga câte reguli vrei!)
        return student.getMedieSemestruAnterior() >= 1.0 && student.getMedieSemestruAnterior() <= 10.0
                && student.getCnp() != null && student.getCnp().matches("\\d{13}");
    }
}
