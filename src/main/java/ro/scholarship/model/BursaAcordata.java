package ro.scholarship.model;

import java.time.LocalDate;
import java.util.Objects;

public class BursaAcordata {
    private int id;
    private Student student;
    private Bursa bursa;
    private LocalDate dataAcordare;
    private boolean esteActiva;
    private float punctajTotal;

    public BursaAcordata() {}

    public BursaAcordata(Student student, Bursa bursa, LocalDate dataAcordare, boolean esteActiva, float punctajTotal) {
        this.student = student;
        this.bursa = bursa;
        this.dataAcordare = dataAcordare;
        this.esteActiva = esteActiva;
        this.punctajTotal = punctajTotal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Object getBursa() {
        return bursa;
    }

    public void setBursa(Bursa bursa) {
        this.bursa = bursa;
    }

    public LocalDate getDataAcordare() {
        return dataAcordare;
    }

    public void setDataAcordare(LocalDate dataAcordare) {
        this.dataAcordare = dataAcordare;
    }

    public boolean isEsteActiva() {
        return esteActiva;
    }

    public void setEsteActiva(boolean esteActiva) {
        this.esteActiva = esteActiva;
    }

    public float getPunctajTotal() {
        return punctajTotal;
    }

    public void setPunctajTotal(float punctajTotal) {
        this.punctajTotal = punctajTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BursaAcordata that = (BursaAcordata) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "BursaAcordata{" +
                "id=" + id +
                ", student=" + (student != null ? student.getNumeComplet() : "null") +
                ", bursa=" + (bursa != null ? bursa.toString() : "null") +
                ", dataAcordare=" + dataAcordare +
                ", esteActiva=" + esteActiva +
                ", punctajTotal=" + punctajTotal +
                '}';
    }
}
