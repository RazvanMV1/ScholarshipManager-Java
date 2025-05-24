package ro.scholarship.dao;

import jakarta.persistence.*;
import ro.scholarship.model.Facultate;

import java.util.List;

public class FacultateRepository {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("ScholarshipPU");

    public void save(Facultate facultate) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(facultate);
            tx.commit();
        } finally {
            em.close();
        }
    }

    public Facultate findById(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Facultate.class, id);
        } finally {
            em.close();
        }
    }

    public List<Facultate> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                            "SELECT DISTINCT f FROM Facultate f LEFT JOIN FETCH f.specializari", Facultate.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }


    public void update(Facultate facultate) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(facultate);
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
            Facultate facultate = em.find(Facultate.class, id);
            if (facultate != null) {
                em.remove(facultate);
            }
            tx.commit();
        } finally {
            em.close();
        }
    }
}
