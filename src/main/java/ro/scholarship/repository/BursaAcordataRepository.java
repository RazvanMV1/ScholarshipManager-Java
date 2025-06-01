package ro.scholarship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.scholarship.model.BursaAcordata;
import ro.scholarship.model.Bursa;
import ro.scholarship.model.Student;

import java.util.List;

public interface BursaAcordataRepository extends JpaRepository<BursaAcordata, Integer> {
    List<BursaAcordata> findByStudent(Student student);
    List<BursaAcordata> findByBursa(Bursa bursa);
    List<BursaAcordata> findByEsteActiva(int esteActiva);
    // Poți adăuga și alte metode custom după nevoie
}
