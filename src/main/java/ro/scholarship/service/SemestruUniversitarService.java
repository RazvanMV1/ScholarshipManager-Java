package ro.scholarship.service;

import ro.scholarship.model.SemestruUniversitar;
import java.util.List;
import java.util.Optional;

public interface SemestruUniversitarService {
    List<SemestruUniversitar> findAll();
    Optional<SemestruUniversitar> findById(int id);
    SemestruUniversitar save(SemestruUniversitar sem);
    SemestruUniversitar update(int id, SemestruUniversitar sem);
    void delete(int id);

    List<SemestruUniversitar> findByAnUniversitar(int anUniversitar);
}
