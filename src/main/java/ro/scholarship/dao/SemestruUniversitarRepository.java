package ro.scholarship.dao;

import jakarta.persistence.*;
import ro.scholarship.model.SemestruUniversitar;

import java.util.List;

public class SemestruUniversitarRepository {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("ScholarshipPU");

    public void save(SemestruUniversitar semestru) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(semestru);
            tx.commit();
        } finally {
            em.close();
        }
    }

    public SemestruUniversitar findById(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(SemestruUniversitar.class, id);
        } finally {
            em.close();
        }
    }

    public List<SemestruUniversitar> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT s FROM SemestruUniversitar s", SemestruUniversitar.class).getResultList();
        } finally {
            em.close();
        }
    }

    public void update(SemestruUniversitar semestru) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(semestru);
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
            SemestruUniversitar semestru = em.find(SemestruUniversitar.class, id);
            if (semestru != null) {
                em.remove(semestru);
            }
            tx.commit();
        } finally {
            em.close();
        }
    }

    public List<SemestruUniversitar> findByAnUniversitar(int anUniversitar) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                            "SELECT s FROM SemestruUniversitar s WHERE s.anUniversitar = :an", SemestruUniversitar.class)
                    .setParameter("an", anUniversitar)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
