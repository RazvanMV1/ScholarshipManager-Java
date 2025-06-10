package ro.scholarship.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.scholarship.model.SemestruUniversitar;
import ro.scholarship.repository.SemestruUniversitarRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SemestruUniversitarServiceImpl implements SemestruUniversitarService {
    @Autowired
    private SemestruUniversitarRepository semestruRepo;

    @Override
    public List<SemestruUniversitar> findAll() {
        return semestruRepo.findAll();
    }

    @Override
    public Optional<SemestruUniversitar> findById(int id) {
        return semestruRepo.findById(id);
    }

    @Override
    public SemestruUniversitar save(SemestruUniversitar sem) {
        return semestruRepo.save(sem);
    }

    @Override
    public SemestruUniversitar update(int id, SemestruUniversitar sem) {
        sem.setId(id);
        return semestruRepo.save(sem);
    }

    @Override
    public void delete(int id) {
        semestruRepo.deleteById(id);
    }

    @Override
    public List<SemestruUniversitar> findByAnUniversitar(int anUniversitar) {
        return semestruRepo.findByAnUniversitar(anUniversitar);
    }
}
