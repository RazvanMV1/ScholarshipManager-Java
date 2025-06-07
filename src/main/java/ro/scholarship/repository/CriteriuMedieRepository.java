package ro.scholarship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.scholarship.model.CriteriuMedie;
import ro.scholarship.model.Bursa;

import java.util.List;

public interface CriteriuMedieRepository extends JpaRepository<CriteriuMedie, Integer> {
    List<CriteriuMedie> findByBursa(Bursa bursa);
}
