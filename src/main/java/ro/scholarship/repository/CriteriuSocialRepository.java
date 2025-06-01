package ro.scholarship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.scholarship.model.CriteriuSocial;
import ro.scholarship.model.Bursa;

import java.util.List;

public interface CriteriuSocialRepository extends JpaRepository<CriteriuSocial, Integer> {
    List<CriteriuSocial> findByBursa(Bursa bursa);
}
