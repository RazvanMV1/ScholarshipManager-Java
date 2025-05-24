package ro.scholarship.dao;

import jakarta.persistence.*;
import ro.scholarship.model.BursaAcordata;

import java.util.List;

public class BursaAcordataRepository {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("ScholarshipPU");

    public void save(BursaAcordata bursaAcordata) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(bursaAcordata);
            tx.commit();
        } finally {
            em.close();
        }
    }

    public BursaAcordata findById(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(BursaAcordata.class, id);
        } finally {
            em.close();
        }
    }

    public List<BursaAcordata> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT b FROM BursaAcordata b", BursaAcordata.class).getResultList();
        } finally {
            em.close();
        }
    }

    public void update(BursaAcordata bursaAcordata) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(bursaAcordata);
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
            BursaAcordata bursaAcordata = em.find(BursaAcordata.class, id);
            if (bursaAcordata != null) {
                em.remove(bursaAcordata);
            }
            tx.commit();
        } finally {
            em.close();
        }
    }

    public List<BursaAcordata> findByStudentId(int studentId) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                            "SELECT b FROM BursaAcordata b WHERE b.student.id = :studentId", BursaAcordata.class)
                    .setParameter("studentId", studentId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<BursaAcordata> findByBursaId(int bursaId) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                            "SELECT b FROM BursaAcordata b WHERE b.bursa.id = :bursaId", BursaAcordata.class)
                    .setParameter("bursaId", bursaId)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}

