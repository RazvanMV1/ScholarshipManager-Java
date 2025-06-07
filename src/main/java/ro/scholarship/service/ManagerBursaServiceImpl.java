package ro.scholarship.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.scholarship.model.*;
import ro.scholarship.repository.BursaAcordataRepository;
import ro.scholarship.repository.BursaRepository;
import ro.scholarship.repository.StudentRepository;

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

    @Override
    public List<BursaAcordata> proceseazaBursa(int idBursa) {
        Bursa bursa = bursaRepository.findById(idBursa)
                .orElseThrow(() -> new IllegalArgumentException("Bursa nu exista!"));

        // Extrage criteriile din bursa (folosește metodele OOP)
        CriteriuMedie criteriuMedie = bursa.getCriteriuMedie();
        CriteriuSocial criteriuSocial = bursa.getCriteriuSocial();

        Float medieMinima = criteriuMedie != null ? criteriuMedie.getMedieMinimaAcceptata() : null;
        Float venitMaxim = criteriuSocial != null ? criteriuSocial.getVenitMaximAcceptat() : null;

        List<Student> studenti = studentRepository.findAll();

        List<Student> eligibili = studenti.stream()
                .filter(s -> medieMinima == null || s.getMedieSemestruAnterior() >= medieMinima)
                .filter(s -> venitMaxim == null || (s.getVenitLunar() != null && s.getVenitLunar() <= venitMaxim))
                .collect(Collectors.toList());

        // Calculează scorul pentru fiecare student după TOATE criteriile bursei
        List<BursaAcordata> rezultate = eligibili.stream()
                .sorted(Comparator.comparing(Student::getMedieSemestruAnterior).reversed()
                        .thenComparing(s -> s.getVenitLunar() != null ? s.getVenitLunar() : Double.MAX_VALUE))
                .limit(bursa.getNumarBurseDisponibile())
                .map(student -> {
                    BursaAcordata acordata = new BursaAcordata();
                    acordata.setStudent(student);
                    acordata.setBursa(bursa);
                    acordata.setEsteActiva(1); // activă

                    // Calculează scorul total pentru fiecare criteriu
                    float punctajTotal = 0.0f;
                    for (Criteriu criteriu : bursa.getCriteriiEligibilitate()) {
                        punctajTotal += criteriu.evalueaza(student);
                    }
                    acordata.setPunctajTotal(punctajTotal);

                    return bursaAcordataRepository.save(acordata);
                }).collect(Collectors.toList());

        return rezultate;
    }
}
