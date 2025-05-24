package ro.scholarship.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "facultati")
public class Facultate {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "facultati_seq_gen")
    @SequenceGenerator(name = "facultati_seq_gen", sequenceName = "facultati_seq", allocationSize = 1)
    private int id;

    @Column(nullable = false)
    private String denumire;

    @OneToMany(
            mappedBy = "facultate",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<Specializare> specializari = new ArrayList<>();

    @Column(name = "buget_burse")
    private float bugetBurse;

    public Facultate() {}

    public Facultate(String denumire) {
        this.denumire = denumire;
    }

    public Facultate(int id, String denumire, float bugetBurse) {
        this.id = id;
        this.denumire = denumire;
        this.bugetBurse = bugetBurse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public List<Specializare> getSpecializari() {
        return specializari;
    }

    public void setSpecializari(List<Specializare> specializari) {
        this.specializari.clear();
        if (specializari != null) {
            for (Specializare spec : specializari) {
                adaugaSpecializare(spec);
            }
        }
    }

    public float getBugetBurse() {
        return bugetBurse;
    }

    public void setBugetBurse(float bugetBurse) {
        this.bugetBurse = bugetBurse;
    }

    public void adaugaSpecializare(Specializare specializare) {
        if (specializare != null && !specializari.contains(specializare)) {
            specializari.add(specializare);
            specializare.setFacultate(this);
        }
    }

    public boolean eliminaSpecializare(Specializare specializare) {
        if (specializare != null && specializari.remove(specializare)) {
            specializare.setFacultate(null);
            return true;
        }
        return false;
    }

    public int getNumarTotalLocuriBurse() {
        return specializari.stream()
                .mapToInt(Specializare::getNumarLocuriBurse)
                .sum();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Facultate)) return false;
        Facultate facultate = (Facultate) o;
        return id == facultate.id || (denumire != null && denumire.equals(facultate.denumire));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, denumire);
    }

    @Override
    public String toString() {
        return "Facultate{" +
                "id=" + id +
                ", denumire='" + denumire + '\'' +
                ", bugetBurse=" + bugetBurse +
                ", nrSpecializari=" + (specializari != null ? specializari.size() : 0) +
                '}';
    }
}
