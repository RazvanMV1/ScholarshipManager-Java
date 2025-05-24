package ro.scholarship.dao;

import jakarta.persistence.*;
import ro.scholarship.model.Criteriu;
import ro.scholarship.model.Bursa;

import java.util.List;

public class CriteriuRepository {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("ScholarshipPU");

    public void save(Criteriu criteriu) {
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

    public Criteriu findById(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Criteriu.class, id);
        } finally {
            em.close();
        }
    }

    public List<Criteriu> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT c FROM Criteriu c", Criteriu.class).getResultList();
        } finally {
            em.close();
        }
    }

    public void update(Criteriu criteriu) {
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
            Criteriu criteriu = em.find(Criteriu.class, id);
            if (criteriu != null) {
                em.remove(criteriu);
            }
            tx.commit();
        } finally {
            em.close();
        }
    }

    public List<Criteriu> findByBursa(Bursa bursa) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                            "SELECT c FROM Criteriu c WHERE c.bursa = :bursa", Criteriu.class)
                    .setParameter("bursa", bursa)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
