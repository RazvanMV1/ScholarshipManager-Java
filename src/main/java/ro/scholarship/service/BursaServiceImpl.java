package ro.scholarship.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.scholarship.model.Bursa;
import ro.scholarship.repository.BursaRepository;
import java.util.List;
import java.util.Optional;

@Service
public class BursaServiceImpl implements BursaService {
    @Autowired
    private BursaRepository bursaRepository;

    @Override
    public List<Bursa> findAll() {
        return bursaRepository.findAll();
    }

    @Override
    public Optional<Bursa> findById(int id) {
        return bursaRepository.findById(id);
    }

    @Override
    public Bursa save(Bursa bursa) {
        if (!isValid(bursa)) {
            throw new IllegalArgumentException("Date bursă invalide!");
        }
        return bursaRepository.save(bursa);
    }

    @Override
    public Bursa update(int id, Bursa bursa) {
        bursa.setId(id);
        if (!isValid(bursa)) {
            throw new IllegalArgumentException("Date bursă invalide!");
        }
        return bursaRepository.save(bursa);
    }

    @Override
    public void delete(int id) {
        bursaRepository.deleteById(id);
    }

    @Override
    public boolean isValid(Bursa bursa) {
        return bursa.getDenumire() != null && !bursa.getDenumire().isBlank()
                && bursa.getValoare() > 0
                && bursa.getNumarBurseDisponibile() > 0
                && bursa.getTip() != null
                && bursa.getSemestru() != null;
    }
}
