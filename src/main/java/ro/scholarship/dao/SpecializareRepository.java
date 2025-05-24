package ro.scholarship.dao;

import jakarta.persistence.*;
import ro.scholarship.model.Specializare;

import java.util.List;

public class SpecializareRepository {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("ScholarshipPU");

    public void save(Specializare specializare) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(specializare);
            tx.commit();
        } finally {
            em.close();
        }
    }

    public Specializare findById(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Specializare.class, id);
        } finally {
            em.close();
        }
    }

    public List<Specializare> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT s FROM Specializare s", Specializare.class).getResultList();
        } finally {
            em.close();
        }
    }

    public void update(Specializare specializare) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(specializare);
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
            Specializare specializare = em.find(Specializare.class, id);
            if (specializare != null) {
                em.remove(specializare);
            }
            tx.commit();
        } finally {
            em.close();
        }
    }
}
