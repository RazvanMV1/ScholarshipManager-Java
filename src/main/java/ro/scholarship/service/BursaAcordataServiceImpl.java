package ro.scholarship.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.scholarship.model.BursaAcordata;
import ro.scholarship.model.Bursa;
import ro.scholarship.model.Student;
import ro.scholarship.repository.BursaAcordataRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BursaAcordataServiceImpl implements BursaAcordataService {
    @Autowired
    private BursaAcordataRepository bursaAcordataRepository;

    @Override
    public List<BursaAcordata> findAll() {
        return bursaAcordataRepository.findAll();
    }

    @Override
    public Optional<BursaAcordata> findById(int id) {
        return bursaAcordataRepository.findById(id);
    }

    @Override
    public BursaAcordata save(BursaAcordata ba) {
        if (!isValid(ba)) {
            throw new IllegalArgumentException("Bursa acordată invalidă!");
        }
        return bursaAcordataRepository.save(ba);
    }

    @Override
    public BursaAcordata update(int id, BursaAcordata ba) {
        ba.setId(id);
        if (!isValid(ba)) {
            throw new IllegalArgumentException("Bursa acordată invalidă!");
        }
        return bursaAcordataRepository.save(ba);
    }

    @Override
    public void delete(int id) {
        bursaAcordataRepository.deleteById(id);
    }

    @Override
    public List<BursaAcordata> findByStudent(Student student) {
        return bursaAcordataRepository.findByStudent(student);
    }

    @Override
    public List<BursaAcordata> findByBursa(Bursa bursa) {
        return bursaAcordataRepository.findByBursa(bursa);
    }

    @Override
    public boolean isValid(BursaAcordata ba) {
        // Adaptează regulile după nevoi!
        return ba.getStudent() != null && ba.getBursa() != null;
    }
}
