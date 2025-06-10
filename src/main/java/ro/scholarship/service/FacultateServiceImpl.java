package ro.scholarship.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.scholarship.model.Facultate;
import ro.scholarship.repository.FacultateRepository;
import java.util.List;
import java.util.Optional;

@Service
public class FacultateServiceImpl implements FacultateService {
    @Autowired
    private FacultateRepository facultateRepository;

    @Override
    public List<Facultate> findAll() {
        return facultateRepository.findAll();
    }

    @Override
    public Optional<Facultate> findById(int id) {
        return facultateRepository.findById(id);
    }

    @Override
    public Facultate save(Facultate facultate) {
        if (!isValid(facultate)) {
            throw new IllegalArgumentException("Date facultate invalide!");
        }
        return facultateRepository.save(facultate);
    }

    @Override
    public Facultate update(int id, Facultate facultate) {
        facultate.setId(id);
        if (!isValid(facultate)) {
            throw new IllegalArgumentException("Date facultate invalide!");
        }
        return facultateRepository.save(facultate);
    }

    @Override
    public void delete(int id) {
        facultateRepository.deleteById(id);
    }

    @Override
    public boolean isValid(Facultate facultate) {
        return facultate.getBugetBurse() > 0 && facultate.getDenumire() != null && !facultate.getDenumire().isBlank();
    }
}
