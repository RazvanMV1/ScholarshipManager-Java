package ro.scholarship.service;

import ro.scholarship.model.Criteriu;
import java.util.List;
import java.util.Optional;

public interface CriteriuService {
    List<Criteriu> findAll();
    Optional<Criteriu> findById(int id);
    Criteriu save(Criteriu criteriu);
    Criteriu update(int id, Criteriu criteriu);
    void delete(int id);
    List<Criteriu> findByBursa(int bursaId);
    boolean isValid(Criteriu criteriu);
}
