package ro.scholarship.service;

import ro.scholarship.model.BursaAcordata;
import ro.scholarship.model.Bursa;
import ro.scholarship.model.Student;

import java.util.List;
import java.util.Optional;

public interface BursaAcordataService {
    List<BursaAcordata> findAll();
    Optional<BursaAcordata> findById(int id);
    BursaAcordata save(BursaAcordata bursaAcordata);
    BursaAcordata update(int id, BursaAcordata bursaAcordata);
    void delete(int id);

    List<BursaAcordata> findByStudent(Student student);
    List<BursaAcordata> findByBursa(Bursa bursa);

    boolean isValid(BursaAcordata ba);
}
