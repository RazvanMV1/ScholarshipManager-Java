package ro.scholarship.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "burse_acordate")
public class BursaAcordata {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "burse_acordate_seq_gen")
    @SequenceGenerator(name = "burse_acordate_seq_gen", sequenceName = "burse_acordate_seq", allocationSize = 1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "bursa_id")
    private Bursa bursa;

    @Column(name = "data_acordare")
    private LocalDate dataAcordare;

    @Column(name = "este_activa")
    private int esteActiva; // 0/1

    @Column(name = "punctaj_total")
    private float punctajTotal;

    public BursaAcordata() {}

    // Constructor folosind int pentru esteActiva
    public BursaAcordata(Student student, Bursa bursa, LocalDate dataAcordare, int esteActiva, float punctajTotal) {
        this.student = student;
        this.bursa = bursa;
        this.dataAcordare = dataAcordare;
        this.esteActiva = esteActiva;
        this.punctajTotal = punctajTotal;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public Bursa getBursa() { return bursa; }
    public void setBursa(Bursa bursa) { this.bursa = bursa; }

    public LocalDate getDataAcordare() { return dataAcordare; }
    public void setDataAcordare(LocalDate dataAcordare) { this.dataAcordare = dataAcordare; }

    public float getPunctajTotal() { return punctajTotal; }
    public void setPunctajTotal(float punctajTotal) { this.punctajTotal = punctajTotal; }

    // Doar getter/setter pentru int!
    public int getEsteActiva() { return esteActiva; }
    public void setEsteActiva(int esteActiva) { this.esteActiva = esteActiva; }

    // Utilitar (nu setter Jackson!) pentru a lucra cu boolean în codul tău, dacă vrei:
    public boolean isActiva() { return esteActiva == 1; }
    public void setActiva(boolean activa) { this.esteActiva = activa ? 1 : 0; }

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
