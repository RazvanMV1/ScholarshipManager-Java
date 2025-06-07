// CriteriuMedieServiceImpl.java
package ro.scholarship.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.scholarship.model.Bursa;
import ro.scholarship.model.CriteriuMedie;
import ro.scholarship.repository.CriteriuMedieRepository;
import java.util.List;
import java.util.Optional;

@Service
public class CriteriuMedieServiceImpl implements CriteriuMedieService {

    @Autowired
    private CriteriuMedieRepository criteriuMedieRepository;

    @Override
    public List<CriteriuMedie> findAll() {
        return criteriuMedieRepository.findAll();
    }

    @Override
    public Optional<CriteriuMedie> findById(int id) {
        return criteriuMedieRepository.findById(id);
    }

    @Override
    public CriteriuMedie save(CriteriuMedie criteriuMedie) {
        if (!isValid(criteriuMedie)) {
            throw new IllegalArgumentException("Criteriu medie invalid!");
        }
        return criteriuMedieRepository.save(criteriuMedie);
    }

    @Override
    public CriteriuMedie update(int id, CriteriuMedie criteriuMedie) {
        criteriuMedie.setId(id);
        if (!isValid(criteriuMedie)) {
            throw new IllegalArgumentException("Criteriu medie invalid!");
        }
        return criteriuMedieRepository.save(criteriuMedie);
    }

    @Override
    public void delete(int id) {
        criteriuMedieRepository.deleteById(id);
    }

    @Override
    public List<CriteriuMedie> findByBursa(int bursaId) {
        Bursa bursa = new Bursa();
        bursa.setId(bursaId);
        return criteriuMedieRepository.findByBursa(bursa);
    }

    @Override
    public boolean isValid(CriteriuMedie criteriuMedie) {
        // Pune aici regulile tale (exemplu simplu)
        return criteriuMedie.getDenumire() != null && !criteriuMedie.getDenumire().isBlank();
    }
}
