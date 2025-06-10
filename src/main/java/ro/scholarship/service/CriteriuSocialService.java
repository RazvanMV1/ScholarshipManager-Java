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

    List<CriteriuSocial> findByBursaId(int bursaId);

    List<CriteriuSocial> findByBursa(Bursa bursa);

    boolean isValid(CriteriuSocial criteriuSocial);
}
