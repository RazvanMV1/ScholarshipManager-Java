package ro.scholarship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.scholarship.model.Criteriu;
import ro.scholarship.model.Bursa;

import java.util.List;

public interface CriteriuRepository extends JpaRepository<Criteriu, Integer> {
    List<Criteriu> findByBursa(Bursa bursa);
}