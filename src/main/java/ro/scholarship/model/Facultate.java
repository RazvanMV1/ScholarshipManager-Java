package ro.scholarship.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Facultate {
    private int id;
    private String denumire;
    private List<Specializare> specializari;
    private float bugetBurse;

    public Facultate() {
        this.specializari = new ArrayList<>();
    }

    public Facultate(String denumire) {
        this();
        this.denumire = denumire;
    }

    public Facultate(int id, String denumire, float bugetBurse) {
        this();
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
        this.specializari = specializari;
    }

    public float getBugetBurse() {
        return bugetBurse;
    }

    public void setBugetBurse(float bugetBurse) {
        this.bugetBurse = bugetBurse;
    }

    public void adaugaSpecializare(Specializare specializare) {
        if (this.specializari == null) {
            this.specializari = new ArrayList<>();
        }
        specializare.setFacultate(this);
        this.specializari.add(specializare);
    }

    public boolean eliminaSpecializare(Specializare specializare) {
        return this.specializari.remove(specializare);
    }

    public int getNumarTotalLocuriBurse() {
        return specializari.stream()
                .mapToInt(Specializare::getNumarLocuriBurse)
                .sum();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
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

