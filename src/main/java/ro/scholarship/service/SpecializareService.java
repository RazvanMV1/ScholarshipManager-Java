package ro.scholarship.service;

import ro.scholarship.model.Specializare;
import java.util.List;
import java.util.Optional;

public interface SpecializareService {
    List<Specializare> findAll();
    Optional<Specializare> findById(int id);
    Specializare save(Specializare specializare);
    Specializare update(int id, Specializare specializare);
    void delete(int id);

    boolean isValid(Specializare specializare);
}
