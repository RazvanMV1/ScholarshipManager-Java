package ro.scholarship.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.scholarship.model.Bursa;
import ro.scholarship.model.Criteriu;
import ro.scholarship.repository.CriteriuRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CriteriuServiceImpl implements CriteriuService {

    @Autowired
    private CriteriuRepository criteriuRepository;

    @Override
    public List<Criteriu> findAll() {
        return criteriuRepository.findAll();
    }

    @Override
    public Optional<Criteriu> findById(int id) {
        return criteriuRepository.findById(id);
    }

    @Override
    public Criteriu save(Criteriu criteriu) {
        if (!isValid(criteriu)) {
            throw new IllegalArgumentException("Date criteriu invalide!");
        }
        return criteriuRepository.save(criteriu);
    }

    @Override
    public Criteriu update(int id, Criteriu criteriu) {
        criteriu.setId(id);
        if (!isValid(criteriu)) {
            throw new IllegalArgumentException("Date criteriu invalide!");
        }
        return criteriuRepository.save(criteriu);
    }

    @Override
    public void delete(int id) {
        criteriuRepository.deleteById(id);
    }

    @Override
    public List<Criteriu> findByBursa(int bursaId) {
        Bursa bursa = new Bursa();
        bursa.setId(bursaId);
        return criteriuRepository.findByBursa(bursa);
    }

    @Override
    public boolean isValid(Criteriu criteriu) {
        return criteriu.getDenumire() != null && !criteriu.getDenumire().isBlank();
    }
}
