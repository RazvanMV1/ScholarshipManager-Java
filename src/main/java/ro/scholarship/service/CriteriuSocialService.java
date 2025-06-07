package ro.scholarship.service;

import ro.scholarship.model.CriteriuSocial;
import ro.scholarship.model.Bursa;
import java.util.List;
import java.util.Optional;

public interface CriteriuSocialService {
    List<CriteriuSocial> findAll();
    Optional<CriteriuSocial> findById(int id);
    CriteriuSocial save(CriteriuSocial criteriuSocial);
    CriteriuSocial update(int id, CriteriuSocial criteriuSocial);
    void delete(int id);
    boolean isValid(CriteriuSocial criteriuSocial);

    // Dacă ai nevoie de filtrare după bursă
    List<CriteriuSocial> findByBursa(Bursa bursa);
}
