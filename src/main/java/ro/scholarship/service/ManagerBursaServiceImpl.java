package ro.scholarship.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.scholarship.model.*;
import ro.scholarship.repository.BursaAcordataRepository;
import ro.scholarship.repository.BursaRepository;
import ro.scholarship.repository.StudentRepository;
import ro.scholarship.repository.CriteriuMedieRepository;
import ro.scholarship.repository.CriteriuSocialRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManagerBursaServiceImpl implements ManagerBursaService {

    @Autowired
    private BursaRepository bursaRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private BursaAcordataRepository bursaAcordataRepository;

    @Autowired
    private CriteriuMedieRepository criteriuMedieRepository;

    @Autowired
    private CriteriuSocialRepository criteriuSocialRepository;

    public List<BursaAcordata> proceseazaBursa(int idBursa) {
        Bursa bursa = bursaRepository.findById(idBursa)
                .orElseThrow(() -> new IllegalArgumentException("Bursa nu exista!"));

        CriteriuMedie criteriuMedie = bursa.getCriteriuMedie();
        CriteriuSocial criteriuSocial = bursa.getCriteriuSocial();

        if (criteriuMedie == null && bursa.getTip() == TipBursa.MERIT) {
            criteriuMedie = criteriuMedieRepository.findAll().stream().findFirst().orElse(null);
        }
        if (criteriuSocial == null && bursa.getTip() == TipBursa.SOCIALA) {
            criteriuSocial = criteriuSocialRepository.findAll().stream().findFirst().orElse(null);
        }

        final CriteriuMedie finalCriteriuMedie = criteriuMedie;
        final CriteriuSocial finalCriteriuSocial = criteriuSocial;
        final Float medieMinima = finalCriteriuMedie != null ? finalCriteriuMedie.getMedieMinimaAcceptata() : null;
        final Float venitMaxim = finalCriteriuSocial != null ? finalCriteriuSocial.getVenitMaximAcceptat() : null;

        List<Student> studenti = studentRepository.findAll();

        List<Student> eligibili = studenti.stream()
                .filter(s -> medieMinima == null || s.getMedieSemestruAnterior() >= medieMinima)
                .filter(s -> venitMaxim == null || (s.getVenitLunar() != null && s.getVenitLunar() <= venitMaxim))
                .collect(Collectors.toList());

        return eligibili.stream()
                .sorted(Comparator.comparing(Student::getMedieSemestruAnterior).reversed()
                        .thenComparing(s -> s.getVenitLunar() != null ? s.getVenitLunar() : Double.MAX_VALUE))
                .limit(bursa.getNumarBurseDisponibile())
                .map(student -> {
                    BursaAcordata acordata = new BursaAcordata();
                    acordata.setStudent(student);
                    acordata.setBursa(bursa);
                    acordata.setEsteActiva(1);

                    float punctajTotal = 0.0f;
                    if (!bursa.getCriteriiEligibilitate().isEmpty()) {
                        for (Criteriu criteriu : bursa.getCriteriiEligibilitate()) {
                            punctajTotal += criteriu.evalueaza(student);
                        }
                    } else {
                        if (finalCriteriuMedie != null) {
                            punctajTotal += finalCriteriuMedie.evalueaza(student);
                        }
                        if (finalCriteriuSocial != null) {
                            punctajTotal += finalCriteriuSocial.evalueaza(student);
                        }
                    }
                    acordata.setPunctajTotal(punctajTotal);
                    return bursaAcordataRepository.save(acordata);
                })
                .collect(Collectors.toList());
    }

}
