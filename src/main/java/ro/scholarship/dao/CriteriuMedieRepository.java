package ro.scholarship.dao;

import jakarta.persistence.*;
import ro.scholarship.model.CriteriuMedie;
import ro.scholarship.model.Bursa;

import java.util.List;

public class CriteriuMedieRepository {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("ScholarshipPU");

    public void save(CriteriuMedie criteriu) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(criteriu);
            tx.commit();
        } finally {
            em.close();
        }
    }

    public CriteriuMedie findById(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(CriteriuMedie.class, id);
        } finally {
            em.close();
        }
    }

    public List<CriteriuMedie> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT c FROM CriteriuMedie c", CriteriuMedie.class).getResultList();
        } finally {
            em.close();
        }
    }

    public void update(CriteriuMedie criteriu) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(criteriu);
            tx.commit();
        } finally {
            em.close();
        }
    }

    public void delete(int id) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            CriteriuMedie criteriu = em.find(CriteriuMedie.class, id);
            if (criteriu != null) {
                em.remove(criteriu);
            }
            tx.commit();
        } finally {
            em.close();
        }
    }

    public List<CriteriuMedie> findByBursa(Bursa bursa) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                            "SELECT c FROM CriteriuMedie c WHERE c.bursa = :bursa", CriteriuMedie.class)
                    .setParameter("bursa", bursa)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
