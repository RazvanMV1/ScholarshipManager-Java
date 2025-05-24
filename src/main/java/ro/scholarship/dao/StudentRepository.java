package ro.scholarship.dao;

import jakarta.persistence.*;
import ro.scholarship.model.Student;

import java.util.List;

public class StudentRepository {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("ScholarshipPU");

    public void save(Student student) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(student);
            tx.commit();
        } finally {
            em.close();
        }
    }

    public Student findById(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Student.class, id);
        } finally {
            em.close();
        }
    }

    public List<Student> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                            "SELECT DISTINCT s FROM Student s LEFT JOIN FETCH s.burseAcordate", Student.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public void update(Student student) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(student);
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
            Student student = em.find(Student.class, id);
            if (student != null) {
                em.remove(student);
            }
            tx.commit();
        } finally {
            em.close();
        }
    }
}
