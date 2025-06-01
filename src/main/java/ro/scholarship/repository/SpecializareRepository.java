package ro.scholarship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.scholarship.model.Specializare;

public interface SpecializareRepository extends JpaRepository<Specializare, Integer> {
    // poți adăuga metode custom aici, ex: List<Specializare> findByDenumire(String denumire);
}
