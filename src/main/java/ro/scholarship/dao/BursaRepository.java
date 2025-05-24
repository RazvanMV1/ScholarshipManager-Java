package ro.scholarship.dao;

import jakarta.persistence.*;
import ro.scholarship.model.Bursa;
import ro.scholarship.model.TipBursa;
import ro.scholarship.model.SemestruUniversitar;

import java.util.List;

public class BursaRepository {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("ScholarshipPU");

    public void save(Bursa bursa) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(bursa);
            tx.commit();
        } finally {
            em.close();
        }
    }

    public Bursa findById(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Bursa.class, id);
        } finally {
            em.close();
        }
    }

    public List<Bursa> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                            "SELECT DISTINCT b FROM Bursa b LEFT JOIN FETCH b.criteriiEligibilitate", Bursa.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }


    public void update(Bursa bursa) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(bursa);
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
            Bursa bursa = em.find(Bursa.class, id);
            if (bursa != null) {
                em.remove(bursa);
            }
            tx.commit();
        } finally {
            em.close();
        }
    }

    public List<Bursa> findByTip(TipBursa tip) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                            "SELECT b FROM Bursa b WHERE b.tip = :tip", Bursa.class)
                    .setParameter("tip", tip)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Bursa> findBySemestru(SemestruUniversitar semestru) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                            "SELECT b FROM Bursa b WHERE b.semestru = :semestru", Bursa.class)
                    .setParameter("semestru", semestru)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}

