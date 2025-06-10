package ro.scholarship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.scholarship.model.Facultate;

public interface FacultateRepository extends JpaRepository<Facultate, Integer> {
}
