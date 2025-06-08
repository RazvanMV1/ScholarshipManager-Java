package ro.scholarship.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.scholarship.model.CriteriuSocial;
import ro.scholarship.repository.CriteriuSocialRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CriteriuSocialServiceImpl implements CriteriuSocialService {

    @Autowired
    private CriteriuSocialRepository criteriuSocialRepository;

    @Override
    public List<CriteriuSocial> findAll() {
        return criteriuSocialRepository.findAll();
    }

    @Override
    public Optional<CriteriuSocial> findById(int id) {
        return criteriuSocialRepository.findById(id);
    }

    @Override
    public CriteriuSocial save(CriteriuSocial criteriuSocial) {
        if (!isValid(criteriuSocial)) {
            throw new IllegalArgumentException("Date criteriu social invalide!");
        }
        return criteriuSocialRepository.save(criteriuSocial);
    }

    @Override
    public CriteriuSocial update(int id, CriteriuSocial criteriuSocial) {
        criteriuSocial.setId(id);
        if (!isValid(criteriuSocial)) {
            throw new IllegalArgumentException("Date criteriu social invalide!");
        }
        return criteriuSocialRepository.save(criteriuSocial);
    }

    @Override
    public void delete(int id) {
        criteriuSocialRepository.deleteById(id);
    }

    // Metoda principală recomandată (după id bursă)
    @Override
    public List<CriteriuSocial> findByBursaId(int bursaId) {
        return criteriuSocialRepository.findByBursaId(bursaId);
    }

    public List<CriteriuSocial> findByBursa(ro.scholarship.model.Bursa bursa) {
        return criteriuSocialRepository.findByBursaId(bursa.getId());
    }

    @Override
    public boolean isValid(CriteriuSocial criteriuSocial) {
        return criteriuSocial != null &&
                criteriuSocial.getDenumire() != null && !criteriuSocial.getDenumire().isBlank() &&
                criteriuSocial.getPondere() > 0 &&
                criteriuSocial.getVenitMaximAcceptat() >= 0;
    }
}
