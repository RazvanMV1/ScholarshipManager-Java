package ro.scholarship.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Bursa {
    private int id;
    private String denumire;
    private TipBursa tip;
    private float valoare;
    private SemestruUniversitar semestru;
    private List<Criteriu> criteriiEligibilitate;
    private int numarBurseDisponibile;

    public Bursa() {
        this.criteriiEligibilitate = new ArrayList<>();
    }

    public Bursa(String denumire, TipBursa tip, float valoare, int numarBurseDisponibile) {
        this();
        this.denumire = denumire;
        this.tip = tip;
        this.valoare = valoare;
        this.numarBurseDisponibile = numarBurseDisponibile;
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

    public TipBursa getTip() {
        return tip;
    }

    public void setTip(TipBursa tip) {
        this.tip = tip;
    }

    public float getValoare() {
        return valoare;
    }

    public void setValoare(float valoare) {
        this.valoare = valoare;
    }

    public SemestruUniversitar getSemestru() {
        return semestru;
    }

    public void setSemestru(SemestruUniversitar semestru) {
        this.semestru = semestru;
    }

    public List<Criteriu> getCriteriiEligibilitate() {
        return criteriiEligibilitate;
    }

    public void setCriteriiEligibilitate(List<Criteriu> criteriiEligibilitate) {
        this.criteriiEligibilitate = criteriiEligibilitate;
    }

    public int getNumarBurseDisponibile() {
        return numarBurseDisponibile;
    }

    public void setNumarBurseDisponibile(int numarBurseDisponibile) {
        this.numarBurseDisponibile = numarBurseDisponibile;
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
