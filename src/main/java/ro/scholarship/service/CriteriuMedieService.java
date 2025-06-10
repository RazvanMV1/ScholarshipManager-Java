package ro.scholarship.service;

import ro.scholarship.model.CriteriuMedie;
import java.util.List;
import java.util.Optional;

public interface CriteriuMedieService {
    List<CriteriuMedie> findAll();
    Optional<CriteriuMedie> findById(int id);
    CriteriuMedie save(CriteriuMedie criteriuMedie);
    CriteriuMedie update(int id, CriteriuMedie criteriuMedie);
    void delete(int id);
    List<CriteriuMedie> findByBursa(int bursaId);
    boolean isValid(CriteriuMedie criteriuMedie);
}
