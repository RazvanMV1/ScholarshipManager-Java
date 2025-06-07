package ro.scholarship.service;

import ro.scholarship.model.Bursa;
import java.util.List;
import java.util.Optional;

public interface BursaService {
    List<Bursa> findAll();
    Optional<Bursa> findById(int id);
    Bursa save(Bursa bursa);
    Bursa update(int id, Bursa bursa);
    void delete(int id);

    boolean isValid(Bursa bursa);
}
