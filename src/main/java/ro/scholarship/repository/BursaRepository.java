package ro.scholarship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.scholarship.model.Bursa;
import ro.scholarship.model.TipBursa;
import ro.scholarship.model.SemestruUniversitar;

import java.util.List;

public interface BursaRepository extends JpaRepository<Bursa, Integer> {
    List<Bursa> findByTip(TipBursa tip);
    List<Bursa> findBySemestru(SemestruUniversitar semestru);
    List<Bursa> findByDenumire(String denumire);
}
