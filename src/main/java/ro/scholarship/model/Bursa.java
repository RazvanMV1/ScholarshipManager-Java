package ro.scholarship.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "burse")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Bursa {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "burse_seq_gen")
    @SequenceGenerator(name = "burse_seq_gen", sequenceName = "burse_seq", allocationSize = 1)
    private int id;

    @Column(nullable = false)
    private String denumire;

    @Enumerated(EnumType.STRING)
    @Column(name = "tip_bursa", nullable = false)
    private TipBursa tip;

    @Column(nullable = false)
    private float valoare;

    @ManyToOne
    @JoinColumn(name = "semestru_id")
    private SemestruUniversitar semestru;

    @OneToMany(mappedBy = "bursa", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Criteriu> criteriiEligibilitate = new ArrayList<>();

    @Column(name = "numar_burse_disponibile")
    private int numarBurseDisponibile;

    public Bursa() {}

    public Bursa(String denumire, TipBursa tip, float valoare, int numarBurseDisponibile) {
        this.denumire = denumire;
        this.tip = tip;
        this.valoare = valoare;
        this.numarBurseDisponibile = numarBurseDisponibile;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDenumire() { return denumire; }
    public void setDenumire(String denumire) { this.denumire = denumire; }

    public TipBursa getTip() { return tip; }
    public void setTip(TipBursa tip) { this.tip = tip; }

    public float getValoare() { return valoare; }
    public void setValoare(float valoare) { this.valoare = valoare; }

    public SemestruUniversitar getSemestru() { return semestru; }
    public void setSemestru(SemestruUniversitar semestru) { this.semestru = semestru; }

    public List<Criteriu> getCriteriiEligibilitate() { return criteriiEligibilitate; }
    public void setCriteriiEligibilitate(List<Criteriu> criteriiEligibilitate) { this.criteriiEligibilitate = criteriiEligibilitate; }

    public int getNumarBurseDisponibile() { return numarBurseDisponibile; }
    public void setNumarBurseDisponibile(int numarBurseDisponibile) { this.numarBurseDisponibile = numarBurseDisponibile; }

    public CriteriuMedie getCriteriuMedie() {
        return criteriiEligibilitate.stream()
                .filter(c -> c instanceof CriteriuMedie)
                .map(c -> (CriteriuMedie) c)
                .findFirst()
                .orElse(null);
    }

    public CriteriuSocial getCriteriuSocial() {
        return criteriiEligibilitate.stream()
                .filter(c -> c instanceof CriteriuSocial)
                .map(c -> (CriteriuSocial) c)
                .findFirst()
                .orElse(null);
    }

    @JsonIgnore
    public List<CriteriuMedie> getCriteriiMedie() {
        return criteriiEligibilitate.stream()
                .filter(c -> c instanceof CriteriuMedie)
                .map(c -> (CriteriuMedie) c)
                .toList();
    }

    @JsonIgnore
    public List<CriteriuSocial> getCriteriiSociale() {
        return criteriiEligibilitate.stream()
                .filter(c -> c instanceof CriteriuSocial)
                .map(c -> (CriteriuSocial) c)
                .toList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bursa bursa = (Bursa) o;
        return id == bursa.id || (denumire != null && denumire.equals(bursa.denumire));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, denumire);
    }

    @Override
    public String toString() {
        return "Bursa{" +
                "id=" + id +
                ", denumire='" + denumire + '\'' +
                ", tip=" + (tip != null ? tip.name() : "nespecificat") +
                ", valoare=" + valoare +
                ", semestru=" + (semestru != null ? semestru.toString() : "nespecificat") +
                ", numarBurseDisponibile=" + numarBurseDisponibile +
                '}';
    }
}
