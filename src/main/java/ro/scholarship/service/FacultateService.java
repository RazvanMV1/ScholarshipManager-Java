package ro.scholarship.service;

import ro.scholarship.model.Facultate;
import java.util.List;
import java.util.Optional;

public interface FacultateService {
    List<Facultate> findAll();
    Optional<Facultate> findById(int id);
    Facultate save(Facultate facultate);
    Facultate update(int id, Facultate facultate);
    void delete(int id);

    // Poți pune validări custom dacă vrei (ex: buget pozitiv)
    boolean isValid(Facultate facultate);
}
