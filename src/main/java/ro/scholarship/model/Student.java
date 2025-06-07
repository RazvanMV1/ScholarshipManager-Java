package ro.scholarship.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "STUDENTI")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "studenti_seq_gen")
    @SequenceGenerator(name = "studenti_seq_gen", sequenceName = "studenti_seq", allocationSize = 1)
    private int id;

    @Column(nullable = false)
    private String nume;

    @Column(nullable = false)
    private String prenume;

    @Column(unique = true)
    private String cnp;

    private String email;
    private String telefon;

    @ManyToOne
    @JoinColumn(name = "specializare_id")
    private Specializare specializare;

    @Column(name = "an_studiu")
    private int anStudiu;

    @Column(name = "medie_semestru_anterior")
    private float medieSemestruAnterior;

    @Column(name = "are_restante")
    private int areRestante; // 0 = nu, 1 = da (pentru Oracle)

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<BursaAcordata> burseAcordate = new ArrayList<>();

    public Student() {}

    public Student(String nume, String prenume, String cnp, int anStudiu) {
        this.nume = nume;
        this.prenume = prenume;
        this.cnp = cnp;
        this.anStudiu = anStudiu;
    }

    public Student(int id, String nume, String prenume, String cnp, String email,
                   String telefon, Specializare specializare, int anStudiu,
                   float medieSemestruAnterior, boolean areRestante) {
        this.id = id;
        this.nume = nume;
        this.prenume = prenume;
        this.cnp = cnp;
        this.email = email;
        this.telefon = telefon;
        this.specializare = specializare;
        this.anStudiu = anStudiu;
        this.medieSemestruAnterior = medieSemestruAnterior;
        this.areRestante = areRestante ? 1 : 0;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNume() { return nume; }
    public void setNume(String nume) { this.nume = nume; }

    public String getPrenume() { return prenume; }
    public void setPrenume(String prenume) { this.prenume = prenume; }

    public String getCnp() { return cnp; }
    public void setCnp(String cnp) { this.cnp = cnp; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefon() { return telefon; }
    public void setTelefon(String telefon) { this.telefon = telefon; }

    public Specializare getSpecializare() { return specializare; }
    public void setSpecializare(Specializare specializare) { this.specializare = specializare; }

    public int getAnStudiu() { return anStudiu; }
    public void setAnStudiu(int anStudiu) { this.anStudiu = anStudiu; }

    public float getMedieSemestruAnterior() { return medieSemestruAnterior; }
    public void setMedieSemestruAnterior(float medieSemestruAnterior) { this.medieSemestruAnterior = medieSemestruAnterior; }

    public int getAreRestante() { return areRestante; }
    public void setAreRestante(int areRestante) { this.areRestante = areRestante; }

    @JsonIgnore // ✅ Nu permite conflict în timpul mapping-ului JSON
    public boolean isAreRestante() {
        return areRestante == 1;
    }

    public List<BursaAcordata> getBurseAcordate() { return burseAcordate; }
    public void setBurseAcordate(List<BursaAcordata> burseAcordate) { this.burseAcordate = burseAcordate; }

    public void adaugaBursaAcordata(BursaAcordata bursaAcordata) {
        if (bursaAcordata != null) {
            burseAcordate.add(bursaAcordata);
            bursaAcordata.setStudent(this);
        }
    }

    public String getNumeComplet() {
        return nume + " " + prenume;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student student)) return false;
        return id == student.id || (cnp != null && cnp.equals(student.cnp));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cnp);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", cnp='" + cnp + '\'' +
                ", anStudiu=" + anStudiu +
                ", medieSemestruAnterior=" + medieSemestruAnterior +
                ", specializare=" + (specializare != null ? specializare.getDenumire() : "nespecificat") +
                '}';
    }
}
