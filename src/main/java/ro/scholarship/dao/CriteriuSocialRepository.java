package ro.scholarship.dao;

import jakarta.persistence.*;
import ro.scholarship.model.CriteriuSocial;
import ro.scholarship.model.Bursa;

import java.util.List;

public class CriteriuSocialRepository {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("ScholarshipPU");

    public void save(CriteriuSocial criteriu) {
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

    public CriteriuSocial findById(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(CriteriuSocial.class, id);
        } finally {
            em.close();
        }
    }

    public List<CriteriuSocial> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT c FROM CriteriuSocial c", CriteriuSocial.class).getResultList();
        } finally {
            em.close();
        }
    }

    public void update(CriteriuSocial criteriu) {
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
            CriteriuSocial criteriu = em.find(CriteriuSocial.class, id);
            if (criteriu != null) {
                em.remove(criteriu);
            }
            tx.commit();
        } finally {
            em.close();
        }
    }

    public List<CriteriuSocial> findByBursa(Bursa bursa) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                            "SELECT c FROM CriteriuSocial c WHERE c.bursa = :bursa", CriteriuSocial.class)
                    .setParameter("bursa", bursa)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
