package ro.scholarship.model;

import java.util.Objects;

public class Specializare {
    private int id;
    private String denumire;
    private Facultate facultate;
    private int numarLocuriBurse;

    public Specializare() {
    }

    public Specializare(String denumire, int numarLocuriBurse) {
        this.denumire = denumire;
        this.numarLocuriBurse = numarLocuriBurse;
    }

    public Specializare(int id, String denumire, Facultate facultate, int numarLocuriBurse) {
        this.id = id;
        this.denumire = denumire;
        this.facultate = facultate;
        this.numarLocuriBurse = numarLocuriBurse;
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

    public Facultate getFacultate() {
        return facultate;
    }

    public void setFacultate(Facultate facultate) {
        this.facultate = facultate;
    }

    public int getNumarLocuriBurse() {
        return numarLocuriBurse;
    }

    public void setNumarLocuriBurse(int numarLocuriBurse) {
        this.numarLocuriBurse = numarLocuriBurse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Specializare that = (Specializare) o;
        return id == that.id ||
                (denumire != null && denumire.equals(that.denumire)
                        && facultate != null && facultate.equals(that.facultate));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, denumire, facultate != null ? facultate.getId() : 0);
    }

    @Override
    public String toString() {
        return "Specializare{" +
                "id=" + id +
                ", denumire='" + denumire + '\'' +
                ", facultate=" + (facultate != null ? facultate.getDenumire() : "nespecificat") +
                ", numarLocuriBurse=" + numarLocuriBurse +
                '}';
    }
}
