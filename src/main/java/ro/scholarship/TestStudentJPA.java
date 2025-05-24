package ro.scholarship;

import ro.scholarship.dao.*;
import ro.scholarship.model.*;
import java.time.LocalDate;
import java.util.List;

public class TestStudentJPA {
    public static void main(String[] args) {
        FacultateRepository facultateRepo = new FacultateRepository();
        SpecializareRepository specializareRepo = new SpecializareRepository();
        SemestruUniversitarRepository semestruRepo = new SemestruUniversitarRepository();
        BursaRepository bursaRepo = new BursaRepository();
        CriteriuMedieRepository criteriuMedieRepo = new CriteriuMedieRepository();
        CriteriuSocialRepository criteriuSocialRepo = new CriteriuSocialRepository();
        StudentRepository studentRepo = new StudentRepository();
        BursaAcordataRepository bursaAcordataRepo = new BursaAcordataRepository();

        var emf = jakarta.persistence.Persistence.createEntityManagerFactory("ScholarshipPU");
        var em = emf.createEntityManager();
        var tx = em.getTransaction();
        tx.begin();

        Facultate facultate = new Facultate("FMI");
        facultate.setBugetBurse(100000);
        em.persist(facultate);

        Specializare specializare = new Specializare("Informatica", 50);
        specializare.setFacultate(facultate);
        em.persist(specializare);

        SemestruUniversitar semestru = new SemestruUniversitar();
        semestru.setAnUniversitar(2024);
        semestru.setSemestru(2);
        semestru.setDataInceput(LocalDate.of(2024, 2, 15));
        semestru.setDataSfarsit(LocalDate.of(2024, 6, 30));
        em.persist(semestru);

        Bursa bursa = new Bursa("Bursa de merit", TipBursa.MERIT, 1200, 10);
        bursa.setSemestru(semestru);
        em.persist(bursa);

        CriteriuMedie criteriuMedie = new CriteriuMedie();
        criteriuMedie.setDenumire("Medie peste 8.50");
        criteriuMedie.setPondere(1.0f);
        criteriuMedie.setMedieMinimaAcceptata(8.5f);
        criteriuMedie.setBursa(bursa);
        em.persist(criteriuMedie);

        CriteriuSocial criteriuSocial = new CriteriuSocial();
        criteriuSocial.setDenumire("Venit sub 1500 lei");
        criteriuSocial.setPondere(0.5f);
        criteriuSocial.setVenitMaximAcceptat(1500f);
        criteriuSocial.setBursa(bursa);
        em.persist(criteriuSocial);

        Student student = new Student("Popescu", "Ion", "5180131012345", 1);
        student.setEmail("popescu.ion@student.ro");
        student.setTelefon("0722123123");
        student.setMedieSemestruAnterior(9.45f);
        student.setAreRestante(0);
        student.setSpecializare(specializare);
        em.persist(student);

        BursaAcordata bursaAcordata = new BursaAcordata();
        bursaAcordata.setStudent(student);
        bursaAcordata.setBursa(bursa);
        bursaAcordata.setDataAcordare(LocalDate.now());
        bursaAcordata.setEsteActiva(true);
        bursaAcordata.setPunctajTotal(95.2f);
        em.persist(bursaAcordata);

        facultate.getSpecializari().size();
        bursa.getCriteriiEligibilitate().size();
        student.getBurseAcordate().size();

        tx.commit();

        System.out.println("\n--- Facultati ---");
        facultateRepo.findAll().forEach(f -> {
            f.getSpecializari().size();
            System.out.println(f);
        });

        System.out.println("\n--- Specializari ---");
        specializareRepo.findAll().forEach(System.out::println);

        System.out.println("\n--- Semestre ---");
        semestruRepo.findAll().forEach(System.out::println);

        System.out.println("\n--- Burse ---");
        bursaRepo.findAll().forEach(b -> {
            b.getCriteriiEligibilitate().size();
            System.out.println(b);
        });

        System.out.println("\n--- Criterii Medie ---");
        criteriuMedieRepo.findAll().forEach(System.out::println);

        System.out.println("\n--- Criterii Sociale ---");
        criteriuSocialRepo.findAll().forEach(System.out::println);

        System.out.println("\n--- Studenti ---");
        studentRepo.findAll().forEach(s -> {
            s.getBurseAcordate().size();
            System.out.println(s);
        });

        System.out.println("\n--- Burse acordate ---");
        bursaAcordataRepo.findAll().forEach(System.out::println);

        em.close();
        emf.close();
    }
}
