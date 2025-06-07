package ro.scholarship.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.scholarship.model.Specializare;
import ro.scholarship.repository.SpecializareRepository;
import java.util.List;
import java.util.Optional;

@Service
public class SpecializareServiceImpl implements SpecializareService {
    @Autowired
    private SpecializareRepository specializareRepository;

    @Override
    public List<Specializare> findAll() {
        return specializareRepository.findAll();
    }

    @Override
    public Optional<Specializare> findById(int id) {
        return specializareRepository.findById(id);
    }

    @Override
    public Specializare save(Specializare specializare) {
        if (!isValid(specializare)) {
            throw new IllegalArgumentException("Date specializare invalide!");
        }
        return specializareRepository.save(specializare);
    }

    @Override
    public Specializare update(int id, Specializare specializare) {
        specializare.setId(id);
        if (!isValid(specializare)) {
            throw new IllegalArgumentException("Date specializare invalide!");
        }
        return specializareRepository.save(specializare);
    }

    @Override
    public void delete(int id) {
        specializareRepository.deleteById(id);
    }

    @Override
    public boolean isValid(Specializare specializare) {
        return specializare.getDenumire() != null && !specializare.getDenumire().isBlank()
                && specializare.getNumarLocuriBurse() > 0
                && specializare.getFacultate() != null;
    }
}
