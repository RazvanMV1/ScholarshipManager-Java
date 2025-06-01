package ro.scholarship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.scholarship.model.SemestruUniversitar;

import java.util.List;

public interface SemestruUniversitarRepository extends JpaRepository<SemestruUniversitar, Integer> {
    List<SemestruUniversitar> findByAnUniversitar(int anUniversitar);
    // Poți adăuga și alte metode custom, ex: findBySemestru(int semestru)
}
